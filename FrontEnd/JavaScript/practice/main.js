$(function () {
  let cityData = {
    regcode_pattern: "*00000000",
  };

  // 날짜 초기세팅
  let year = 2022;
  let month = 2;
  $("#input-date").append(`${year}년 ${month}월`);

  // 지도 세팅

  // 지도 생성
  var container = document.getElementById("map");
  var options = {
    // 기본 위치 멀티캠퍼스
    center: new kakao.maps.LatLng(37.5012743, 127.039585),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // 마커 생성
  var markerPosition = new kakao.maps.LatLng(37.5012743, 127.039585);
  var marker = new kakao.maps.Marker({
    position: markerPosition,
  });
  marker.setMap(map);

  // 인포윈도우 생성
  var iwContent =
      '<div style="padding:5px;">멀티캠퍼스 <br><a href="https://map.kakao.com/link/map/멀티캠퍼스,37.5012743, 127.039585" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/멀티캠퍼스,37.5012743, 127.039585" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(33.450701, 126.570667);
  var infowindow = new kakao.maps.InfoWindow({
    position: iwPosition,
    content: iwContent,
  });
  infowindow.open(map, marker);

  // geolocation으로 페이지 로딩 시 지도의 위치를 사용자 위치로 설정
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
      var lat = position.coords.latitude,
        lon = position.coords.longitude;

      var locPosition = new kakao.maps.LatLng(lat, lon),
        // 인포윈도우
        message = '<div style="padding:5px;">여기에 계신가요?!</div>';

      // 마커와 인포윈도우 표시
      displayMarker(locPosition, message);
    });
  } else {
    // geolocation 실패 시 초기 주소로 이동
    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
      message = "geolocation을 사용할수 없어요..";
    displayMarker(locPosition, message);
  }

  // 지도에 마커와 인포윈도우 표시
  function displayMarker(locPosition, message) {
    var marker = new kakao.maps.Marker({
      map: map,
      position: locPosition,
    });

    var iwContent = message,
      iwRemoveable = true;

    var infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      removable: iwRemoveable,
    });

    infowindow.open(map, marker);
    map.setCenter(locPosition);
  }

  // 로딩과 함께 시-도 정보는 불러놓기
  $.ajax({
    url: "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes",
    type: "GET",
    data: cityData,
    contentType: "application/json;charset=utf-8",
    dataType: "json",

    success: function (resource) {
      $.each(resource.regcodes, function () {
        let cityName = $(this)[0].name;
        let cityOption = ``;
        cityOption += `
        <option value="${$(this)[0].code}">${cityName}</option>
        `;
        $("#city-select").append(cityOption);
      });
    },
    error: function () {
      console.log("error");
    },
  });

  // 시/도 선택 시 시/군/구 더블셀렉트 시작
  $("#city-select").on("change", function () {
    let selectedCityCode = $("#city-select").find(":checked").val();

    let regionData = {
      regcode_pattern: selectedCityCode.substring(0, 2) + "***00000",
      is_ignore_zero: true,
    };

    $.ajax({
      url: "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes",
      type: "GET",
      data: regionData,
      contentType: "application/json;charset=utf-8",
      dataType: "json",

      success: function (resource) {
        $("#region-select").empty().append("<option>==시/군/구==</option>");
        let i = 0;
        $.each(resource.regcodes, function () {
          let regionName = $(this)[0].name;
          let regionOption = ``;
          // TODO: 성남시 분당구 같은거

          regionOption += `
          <option value="${$(this)[0].code}">${regionName.split(" ")[1]}</option>
          `;
          $("#region-select").append(regionOption);
        });
        $.each(resource.regcodes, function () {
          let regionName = $(this)[0].name;
          let regionOption = ``;
          regionOption += `
          <option value="${$(this)[0].code}">${regionName.split(" ")[1]}</option>
          `;
          $("#region-select").append(regionOption);
        });
      },
      error: function () {
        console.log("error");
      },
    });
  });

  // 지역선택 완료 시 간단정보 출력
  $("#region-select").on("change", function () {
    let inputDate = "";

    if (month >= 10) {
      inputDate = `${year}${month}`;
    } else {
      inputDate = `${year}0${month}`;
    }

    initiateByDate(inputDate);
  });

  // 날짜 변경 시 작동하는 부분
  $("#prev-date").on("click", function () {
    if (month >= 2) {
      month--;
    } else {
      month = 12;
      year--;
    }
    $("#input-date").empty().append(`${year}년 ${month}월`);

    let inputDate = "";

    if (month >= 10) {
      inputDate = `${year}${month}`;
    } else {
      inputDate = `${year}0${month}`;
    }

    initiateByDate(inputDate);
  });

  $("#next-date").on("click", function () {
    if (month <= 11) {
      month++;
    } else {
      month = 1;
      year++;
    }
    $("#input-date").empty().append(`${year}년 ${month}월`);

    let inputDate = "";

    if (month >= 10) {
      inputDate = `${year}${month}`;
    } else {
      inputDate = `${year}0${month}`;
    }

    initiateByDate(inputDate);
  });

  function initiateByDate(inputDate) {
    //TODO
    let selectedRegionCode = $("#region-select").find(":checked").val().substring(0, 5);

    let requestData = {
      serviceKey:
        "7NDGKNSJ36Y6ap6Iy7q9TRUlxBRBlCy0OgV0U8EAlXYH0MXVrAGSZlQXPJefiTP8j/ZFSzJ6jVXJIIRkOipOlw==",
      LAWD_CD: selectedRegionCode,
      DEAL_YMD: inputDate,
      // TODO: 날짜 이동 가능하게 구현해볼까,,,
      pageNo: "1",
      numOfRows: "1000",
    };

    $.ajax({
      url: "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev",
      type: "GET",
      data: requestData,
      dataType: "xml",
      success: function (response) {
        // 지도 제목에 선택한 위치 써놓기
        let regionName = "";
        if ($("#city-select").find(":checked").text() == "==시/도==") {
          regionName = "";
        } else if ($("#region-select").find(":checked").text() == "==시/군/구==") {
          regionName = $("#city-select").find(":checked").text();
        } // 지역 선택이 완전히 이루어진 경우에만 정보 출력
        else {
          regionName = `
          ${$("#city-select").find(":checked").text()} ${$("#region-select")
            .find(":checked")
            .text()}
          `;
          // 지역 선택 시 간략정보 뿌리는 함수 호출
          makeRoughData(response);
        }
        $("#map-header-region").empty().append(regionName);

        // 테이블에 마우스 올릴 시의 css 속성 부여  ----> bootstrap 대체 예정
        $("#apt-outline tr")
          .mouseover(function () {
            $(this).children().css({
              backgroundColor: "#DCDCDC",
              cursor: "pointer",
            });
          })
          .mouseout(function () {
            $(this).children().css({
              backgroundColor: "#FFFFFF",
              cursor: "default",
            });
          });

        // 테이블 선택 시 상세정보 출력하는 함수 호출
        makeDetailData(response);
      },
      error: function () {
        console.log(error);
      },
    });
  }

  // 지역 선택 완료 시 간략 정보 뿌리는 함수
  function makeRoughData(response) {
    let roughData = ``;
    roughData += `
          <tr>
            <th>아파트명</th>
            <th>거래일자</th>
          </tr>`;
    $(response)
      .find("item")
      .each(function () {
        roughData += `
              <tr>
              <td>${$(this).find("아파트").text()}</td>
              <td>${$(this).find("년").text()}.${$(this).find("월").text()}.${$(this)
          .find("일")
          .text()}</td></tr>
              `;
      });

    $("#apt-outline").empty().append(roughData);
  }

  // 상세정보를 뿌리는 함수
  function makeDetailData(response) {
    $("#apt-outline tr").on("click", function () {
      let td = $(this).children();

      let name = td.eq(0).text();
      let date = td.eq(1).text();

      $(response)
        .find("item")
        .each(function () {
          if (
            $(this).find("아파트").text() == name &&
            `${$(this).find("년").text()}.${$(this).find("월").text()}.${$(this)
              .find("일")
              .text()}` == date
          ) {
            let detailData = ``;
            detailData += `
            <tr>
              <th>이름</th>
              <th>거래금액</th>
              <th>거래일자</th>
              <th>전용면적</th>
              <th>층</th>
              <th>건축년도</th>
            </tr>
            <tr>
              <td>${$(this).find("아파트").text()}</td>
              <td>${$(this).find("거래금액").text()}</td>
              <td>${$(this).find("년").text()}.${$(this).find("월").text()}.${$(this)
              .find("일")
              .text()}</td>
              <td>${$(this).find("전용면적").text()}</td>
              <td>${$(this).find("층").text()}</td>
              <td>${$(this).find("건축년도").text()}</td>
            </tr>
            `;
            $("#apt-detailInfo").empty().append(detailData);

            // 지도 구현을 위해 좌표를 구하는 함수 호출
            findProjection($(this));
          }
        });
    });
  }

  // 좌표를 호출하는 함수
  function findProjection(response) {
    let mapRequest = {
      confmKey: "devU01TX0FVVEgyMDIyMDMxMjIwMjEwOTExMjMzOTI=",
      admCd: `${response.find("법정동시군구코드").text()}${response
        .find("법정동읍면동코드")
        .text()}`,
      rnMgtSn: `${response.find("도로명시군구코드").text()}${response.find("도로명코드").text()}`,
      udrtYn: response.find("도로명지상지하코드").text(),
      buldMnnm: response.find("도로명건물본번호코드").text(),
      buldSlno: response.find("도로명건물부번호코드").text(),
    };

    $.ajax({
      url: "https://www.juso.go.kr/addrlink/addrCoordApi.do",
      type: "GET",
      data: mapRequest,
      dataType: "xml",
      success: function (response) {
        let entX = Math.round($(response).find("entX").text());
        let entY = Math.round($(response).find("entY").text());
        console.log(entX);
        console.log(entY);

        // TODO: 좌표가 한번씩 뻑나는데???

        let grs80 =
          "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
        let wgs84 =
          "+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees";

        let newPos = proj4(grs80, wgs84).forward([entX, entY]);
        console.log(newPos);
        // 해당 좌표값으로 지도를 이동시키고 마커찍기
      },
      error: function () {
        console.log(error);
      },
    });
  }
});
