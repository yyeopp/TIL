$(function () {
  let cityData = {
    regcode_pattern: "*00000000",
  };

  // 날짜 초기세팅
  let year = 2022;
  let month = 2;
  $("#input-date").append(`${year}년 ${month}월`);

  // 지도 세팅
  var map;
  const defaultPos = [37.5012743, 127.039585];
  const defaultNm = "멀티캠퍼스 역삼";
  var markers = [];
  var markerPositions = [];

  // 지도 생성
  var container = document.getElementById("map");
  var options = {
    // 기본 위치 멀티캠퍼스
    center: new kakao.maps.LatLng(defaultPos[0], defaultPos[1]),
    level: 6,
  };
  map = new kakao.maps.Map(container, options);

  // 마커 생성
  var markerPosition = new kakao.maps.LatLng(defaultPos[0], defaultPos[1]);
  var marker = new kakao.maps.Marker({
    position: markerPosition,
  });
  markers.push(marker);
  marker.setMap(map);

  // 인포윈도우 생성
  var iwContent = "",
    iwPosition = new kakao.maps.LatLng(defaultPos[0], defaultPos[1]);

  var infowindow = new kakao.maps.InfoWindow({
    position: iwPosition,
    content: iwContent,
  });
  // infowindow.open(map, marker);

  // geolocation으로 페이지 로딩 시 지도의 위치를 사용자 위치로 설정
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
      var lat = position.coords.latitude;
      var lon = position.coords.longitude;
      mapPosition = new kakao.maps.LatLng(lat, lon);
      marker = new kakao.maps.Marker({
        position: mapPosition,
        clickable: true,
      });

      map.setCenter(mapPosition);
      marker.setMap(map);
      markers.push(marker);
    });
  } else {
    // geolocation 실패 시 초기 주소로 이동
    moveMap(defaultPos, defaultNm);
  }

  function moveMap(newPos, bdNm) {
    mapPosition = new kakao.maps.LatLng(newPos[0], newPos[1]);
    marker = new kakao.maps.Marker({
      position: mapPosition,
      clickable: true,
    });

    map.setCenter(mapPosition);
    marker.setMap(map);
    markers.push(marker);
    markerPositions.push(newPos);
    kakao.maps.event.addListener(marker, "click", genInfoWin(marker, bdNm));
  }

  function genInfoWin(marker, bdNm) {
    return function () {
      iwContent = bdNm;
      infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: true,
      });
      infowindow.open(map, marker);
    };
  }

  $("#map-header-btn").on("click", function () {
    removeMarkers();
  });

  // 마커를 전부 지우는 함수
  function removeMarkers() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers.length = 0;
    markerPositions.length = 0;

    let inputDate = "";
    if (month >= 10) {
      inputDate = `${year}${month}`;
    } else {
      inputDate = `${year}0${month}`;
    }
    initiateByDate(inputDate);
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
    doubleSelect();
  });

  function doubleSelect() {
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
        let responsedCodes = resource.regcodes;

        // 성남시 분당구 같은 예외 케이스를 위한 알고리즘 문제 풀이 시작
        let validResources = [];

        let presentReg = responsedCodes[0].code.substring(0, 4);
        let presentRegBig = responsedCodes[0].code.substring(4, 5);
        for (let i = 1; i < responsedCodes.length; i++) {
          let temp = responsedCodes[i].code.substring(0, 4);
          if (presentReg != temp) {
            validResources.push(responsedCodes[i - 1]);
            presentReg = temp;
            presentRegBig = responsedCodes[i].code.substring(4, 5);
            continue;
          } else {
            if (presentRegBig == "0") {
              presentRegBig = responsedCodes[i].code.substring(4, 5);
              continue;
            } else {
              validResources.push(responsedCodes[i - 1]);
              presentRegBig = responsedCodes[i].code.substring(4, 5);
            }
          }
        }
        validResources.push(responsedCodes[responsedCodes.length - 1]);

        $.each(validResources, function () {
          let temp = $(this)[0].name.split(" ");
          let regionName = "";
          for (let i = 1; i < temp.length; i++) {
            regionName += temp[i];
            regionName += " ";
          }
          regionName.length = regionName.length - 1;

          let regionOption = ``;

          regionOption += `
          <option value="${$(this)[0].code}">${regionName}</option>
          `;
          $("#region-select").append(regionOption);
        });

        // 시도를 바꾸면 마커 다 지우기
        removeMarkers();
      },
      error: function () {
        console.log("error");
      },
    });
  }

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
    removeMarkers();
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
    removeMarkers();
  });

  function initiateByDate(inputDate) {
    //TODO
    if ($("#region-select").find(":checked").val() == "==시/군/구==") {
      // console.log("Return");
      return;
    }
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
        // console.log("requested");
        let regionName = "";
        if ($("#city-select").find(":checked").text() == "==시/도==") {
          regionName = "";
        } else if ($("#region-select").find(":checked").text() == "==시/군/구==") {
          regionName = $("#city-select").find(":checked").text();
          // console.log("여기???");
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

        // 테이블 선택 시 상세정보 출력하는 함수 호출
        makeDetailData(response);
      },
      error: function () {
        console.log(error);
      },
    });
  }

  // 북마크들 저장
  var bookmarks = [];

  // 북마크 추가하기
  $("#btn-bookmark").on("click", function () {
    let cityName = $("#city-select").find(":checked").text();
    let regName = $("#region-select").find(":checked").text();
    if (regName == "==시/군/구==") {
      alert("지역을 선택해주세요.");
      return;
    }
    let regionName = `${cityName}<br> ${regName}`;

    // 북마크 추가 유효성 검사
    for (let i = 0; i < bookmarks.length; i++) {
      if (bookmarks[i] == regionName) {
        alert("이미 추가된 지역입니다.");
        return;
      }
    }

    bookmarks.push(regionName);
    $("#region-bookmark").append(`
      <tr>
        <td>
          <div class="row">
            <div class="col-7" style="font-size: 12px">${regionName}</div>
            <div class="col-2">
              <input type="button" id="btn-bookmark-use" value="조회" style="font-size: 6px">
            </div>
            <div class="col-2">
              <input type="button" id="btn-bookmark-del" value="삭제" style="font-size: 6px">
            </div>
          </div>
        </td>
      </tr>
    `);

    // 저장된 북마크를 눌러서 조회하기
    $("#btn-bookmark-use").on("click", function () {
      useBookmark($(this));
    });
  });

  $("#btn-bookmark-del").on("click", function () {
    delBookmark($(this));
  });

  //TDOD: 고장남
  function useBookmark(button) {
    cityName = button.parent().parent().children().eq(0).text().split(" ")[0];
    regName = button.parent().parent().children().eq(0).text().split(" ")[1];
    // select에 성공하게 만들면 되는데..
    //$('select[name="options"]').find('option:contains("Blue")').attr("selected",true);
    $("#city-select")
      .children()
      .each(function () {
        if ($(this).text() == cityName) {
          $(this).prop("selected", true);
          doubleSelect();
        }
      });

    $("#region-select")
      .children()
      .each(function () {
        if ($(this).text() == regName) {
          $(this).prop("selected", true);
        }
      });
    // if (month >= 10) {
    //   inputDate = `${year}${month}`;
    // } else {
    //   inputDate = `${year}0${month}`;
    // }
    // console.log($("#region-select").find(":checked").text());

    // initiateByDate(inputDate);
  }

  // TODO: 고장남
  function delBookmark(button) {
    button.parent().parent().remove();
  }

  // 북마크 버튼으로 지역 조회
  // 지역 선택 완료 시 간략 정보 뿌리는 함수
  function makeRoughData(response) {
    // console.log("여기?");
    let roughData = ``;
    roughData += `
        <thead>  
          <tr>
            <th>아파트명</th>
            <th>거래일자</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
        `;
    $(response)
      .find("item")
      .each(function () {
        roughData += `
            <tr>
              <td>${$(this).find("아파트").text()}</td>
              <td>${$(this).find("년").text()}.${$(this).find("월").text()}.${$(this)
          .find("일")
          .text()}</td>
              <td style="visibility: hidden">0</td>
            </tr>
            
              `;
      });
    roughData += `</tbody>`;

    $("#apt-outline").empty().append(roughData);
  }

  // 상세정보를 뿌리는 함수
  function makeDetailData(response) {
    $("#apt-outline tbody tr").on("click", function () {
      let td = $(this).children();

      // roughData 테이블의 행을 클릭했을 때 음영부여+버튼생성
      if ($(this).children().eq(2).text() == 0) {
        trClick(this);
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
      } else {
        trDefault($(this));
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
              choosedMarkerRemoving($(this));
            }
          });
      }
    });
  }

  function trClick(tr) {
    $(tr).css("background-color", "bisque").css("font-weight", "bold");
    $(tr).children().eq(2).empty().append(1);
  }

  function trDefault(tr) {
    $(tr).css("background-color", "white").css("color", "black").css("font-weight", "");
    $(tr).children().eq(2).empty().append(0);
  }

  // 좌표호출 + 지도 이동 + 마커 생성
  function findProjection(response) {
    let mapRequest = {
      confmKey: "devU01TX0FVVEgyMDIyMDMxMjIwMjEwOTExMjMzOTI=",
      admCd: `${response.find("법정동시군구코드").text()}${response
        .find("법정동읍면동코드")
        .text()}`,
      rnMgtSn: `${response.find("도로명시군구코드").text()}${response.find("도로명코드").text()}`,
      udrtYn: "0",
      buldMnnm: response.find("도로명건물본번호코드").text(),
      buldSlno: response.find("도로명건물부번호코드").text(),
    };

    let bdNm = $(response).find("아파트").text();

    $.ajax({
      url: "https://www.juso.go.kr/addrlink/addrCoordApi.do",
      type: "GET",
      data: mapRequest,
      dataType: "xml",
      success: function (response) {
        let entX = Math.round($(response).find("entX").text());
        let entY = Math.round($(response).find("entY").text());

        let grs80 =
          "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
        let wgs84 =
          "+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees";

        let temp = proj4(grs80, wgs84).forward([entX, entY]);
        let newPos = [temp[1], temp[0]];

        // 해당 좌표값으로 지도를 이동시키고 마커찍기
        moveMap(newPos, bdNm);
      },
      error: function () {
        console.log(error);
      },
    });
  }

  // 좌표 호출 + 이미 존재하던 마커 삭제
  function choosedMarkerRemoving(response) {
    let mapRequest = {
      confmKey: "devU01TX0FVVEgyMDIyMDMxMjIwMjEwOTExMjMzOTI=",
      admCd: `${response.find("법정동시군구코드").text()}${response
        .find("법정동읍면동코드")
        .text()}`,
      rnMgtSn: `${response.find("도로명시군구코드").text()}${response.find("도로명코드").text()}`,
      udrtYn: "0",
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

        let grs80 =
          "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
        let wgs84 =
          "+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees";

        let temp = proj4(grs80, wgs84).forward([entX, entY]);
        let deletePos = [temp[1], temp[0]];

        for (let i = 0; i < markerPositions.length; i++) {
          if (deletePos[0] == markerPositions[i][0] && deletePos[1] == markerPositions[i][1]) {
            markers[i].setMap(null);
          }
        }
      },
      error: function () {
        console.log(error);
      },
    });
  }
});
