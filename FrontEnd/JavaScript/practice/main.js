$(function () {
  let cityData = {
    regcode_pattern: "*00000000",
  };

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
      regcode_pattern: selectedCityCode.substring(0, 2) + "**000000",

      is_ignore_zero: true,
    };

    $.ajax({
      url: "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes",
      type: "GET",
      data: regionData,
      contentType: "application/json;charset=utf-8",
      dataType: "json",

      success: function (resource) {
        $("#region-select").empty();
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
    let selectedRegionCode = $("#region-select").find(":checked").val().substring(0, 5);

    let requestData = {
      serviceKey:
        "7NDGKNSJ36Y6ap6Iy7q9TRUlxBRBlCy0OgV0U8EAlXYH0MXVrAGSZlQXPJefiTP8j/ZFSzJ6jVXJIIRkOipOlw==",
      LAWD_CD: selectedRegionCode,
      DEAL_YMD: "202202",
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

        // 지도 제목에 선택한 위치 써놓기
        let regionName = `
        ${$("#city-select").find(":checked").text()} ${$("#region-select").find(":checked").text()}
        `;
        $("#map-header-region").empty().append(regionName);

        // 테이블에 마우스 올릴 시의 css 속성 부여
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

        // 테이블 선택 시 상세정보 출력
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

                // 선택한 아파트 좌표값 가져오기
                let mapRequest = {
                  confmKey: "devU01TX0FVVEgyMDIyMDMxMjIwMjEwOTExMjMzOTI=",
                  admCd: `${$(this).find("법정동시군구코드").text()}${$(this)
                    .find("법정동읍면동코드")
                    .text()}`,
                  rnMgtSn: `${$(this).find("도로명시군구코드").text()}${$(this)
                    .find("도로명코드")
                    .text()}`,
                  udrtYn: $(this).find("도로명지상지하코드").text(),
                  buldMnnm: $(this).find("도로명건물본번호코드").text(),
                  buldSlno: $(this).find("도로명건물부번호코드").text(),
                };
                $.ajax({
                  url: "https://www.juso.go.kr/addrlink/addrCoordApi.do",
                  type: "GET",
                  data: mapRequest,
                  dataType: "xml",
                  success: function (response) {
                    let entX = $(response).find("entX").text();
                    let entY = $(response).find("entY").text();
                    console.log(entX);
                    console.log(entY);

                    // 해당 좌표값으로 지도를 이동시키고 마커찍기
                    // !!!비상!!! 좌표도 종류가 여러갠데 이건 구글맵이랑 일치하지가 않음
                    // 따로 변환하는 방법을 구해야함
                  },
                  error: function () {
                    console.log(error);
                  },
                });
              }
            });
        });
      },
      error: function () {
        console.log(error);
      },
    });
  });
});
