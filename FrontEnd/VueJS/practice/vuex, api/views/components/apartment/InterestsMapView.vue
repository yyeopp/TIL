<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";

const houseStore = "houseStore";
const expandedStore = "expandedStore";

export default {
  data() {
    return {
      map: null,
      aptMarkers: [],
      hospitalMarkers: [],
      schoolMarkers: [],
      busStationMarkers: [],
      subwayMarkers: [],
      latlng: { neLat: "", neLng: "", swLat: "", swLng: "" },
    };
  },
  props: {
    isHospital: Boolean,
    isSchool: Boolean,
    isBusStation: Boolean,
    isSubway: Boolean,
  },

  computed: {
    ...mapState(houseStore, ["houses", "center", "bookMarks"]),
    ...mapState(expandedStore, [
      "hospitals",
      "schools",
      "busStations",
      "subways",
    ]),
  },
  methods: {
    ...mapActions(houseStore, [
      "detailHouse",
      "houseInfoExp",
      "gethouseDealChart",
      "getNearObject",
    ]),
    ...mapActions(expandedStore, [
      "getHospitaListRange",
      "getSchoolListRange",
      "getBusStationListRange",
      "getSubwayListRange",
    ]),
    ...mapMutations(houseStore, ["SET_MAP_CENTER", "SET_DETAIL_HOUSE"]),

    // 마커 지우기
    markerClear(markers) {
      if (markers.length > 0) {
        markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },

    AllMarkerClear() {
      this.markerClear(this.hospitalMarkers);
      this.markerClear(this.schoolMarkers);
      this.markerClear(this.busStationsMarkers);
      this.markerClear(this.subwayMarkers);
    },

    changeLatLng() {
      this.latlng.neLat = this.map
        .getBounds()
        .getNorthEast()
        .getLat();
      this.latlng.neLng = this.map
        .getBounds()
        .getNorthEast()
        .getLng();
      this.latlng.swLat = this.map
        .getBounds()
        .getSouthWest()
        .getLat();
      this.latlng.swLng = this.map
        .getBounds()
        .getSouthWest()
        .getLng();
    },

    // ===================  마커 찍기
    setAptsOnMap() {
      let imageSrc = require("@/assets/img/house-marker.png"),
        imageSize = new kakao.maps.Size(40, 47);

      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      this.bookMarks.forEach((bookMrak) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(bookMrak.lat, bookMrak.lng),
          title: bookMrak.apartmentName,
          image: markerImage,
        });
        this.aptMarkers.push(marker);

        //마커 클릭이벤트
        kakao.maps.event.addListener(marker, "click", () => {
          this.houseInfoExp(bookMrak);
          this.detailHouse(bookMrak);
          this.gethouseDealChart(bookMrak.aptCode);
          this.getNearObject({ lat: bookMrak.lat, lng: bookMrak.lng });
          this.SET_MAP_CENTER({ lat: bookMrak.lat, lng: bookMrak.lng });
        });
      });
    },
    setHospitalOnMap() {
      let imageSrc = require("@/assets/img/hospital-marker.png"),
        imageSize = new kakao.maps.Size(35, 39);
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      this.hospitals.forEach((hospital) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(hospital.lat, hospital.lng),
          title: hospital.hospitalName,
          image: markerImage,
        });
        this.hospitalMarkers.push(marker);

        var content = `<div style="background-color:white; width:auto; text-align:center; padding : 4px,4px,0,4px;">
        <span style="color :#fb6066; font-size : 9 ">병원</span> <span style="font-size : 9;"> ${hospital.hospitalName}</span>
                    <p>${hospital.telNo}</p></div>`;

        var customOverlay = new kakao.maps.CustomOverlay({
          position: new kakao.maps.LatLng(hospital.lat, hospital.lng),
          content: content,
          yAnchor: 0,
        });
        kakao.maps.event.addListener(marker, "mouseover", () => {
          customOverlay.setMap(this.map);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          customOverlay.setMap(null);
        });
      });
    },
    setSchoolOnMap() {
      let imageSrc = require("@/assets/img/school-marker.png"),
        imageSize = new kakao.maps.Size(35, 39);
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      this.schools.forEach((school) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(school.lat, school.lng),
          title: school.schoolNm,
          image: markerImage,
        });
        this.schoolMarkers.push(marker);

        var content = `<div style="background-color:white; width:auto; text-align:center; padding : 4px;">
        <span style="color :#4ba198; font-size : 9 ">학교</span>
                <span style="font-size : 9;"> ${school.schoolNm}</span></div>`;

        var customOverlay = new kakao.maps.CustomOverlay({
          position: new kakao.maps.LatLng(school.lat, school.lng),
          content: content,
          yAnchor: 0,
        });
        kakao.maps.event.addListener(marker, "mouseover", () => {
          customOverlay.setMap(this.map);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          customOverlay.setMap(null);
        });
      });
    },

    setBusStationOnMap() {
      let imageSrc = require("@/assets/img/bus-stop-marker.png"),
        imageSize = new kakao.maps.Size(23, 23);
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      this.busStations.forEach((busStation) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(busStation.lat, busStation.lng),
          title: busStation.stationName,
          image: markerImage,
        });
        this.busStationMarkers.push(marker);

        var content = `<div style="background-color:white; width:auto; text-align:center; padding : 4px;">
        <span style="color :#b6964c; font-size : 9; ">버스정류장</span>
        <span style="font-size : 9;"> ${busStation.stationName}</span></div>`;

        var customOverlay = new kakao.maps.CustomOverlay({
          position: new kakao.maps.LatLng(busStation.lat, busStation.lng),
          content: content,
          yAnchor: 0,
        });
        kakao.maps.event.addListener(marker, "mouseover", () => {
          customOverlay.setMap(this.map);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          customOverlay.setMap(null);
        });
      });
    },

    setSubwayOnMap() {
      let imageSrc = require("@/assets/img/subway-marker.png"),
        imageSize = new kakao.maps.Size(23, 23);
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      let geocoder = new kakao.maps.services.Geocoder();

      this.subways.forEach((subway) => {
        geocoder.addressSearch(subway.addr, (result, status) => {
          if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            const marker = new kakao.maps.Marker({
              map: this.map,
              position: coords,
              title: subway.subwayName,
              image: markerImage,
            });
            this.subwayMarkers.push(marker);

            var content = `<div style="background-color:white; width:auto; text-align:center; padding : 4px;">
        <span style="color :#3dd36b; font-size : 9; ">지하철</span>
        <span style="font-size : 7;"> ${subway.subwayName}역</span></div>`;

            var customOverlay = new kakao.maps.CustomOverlay({
              position: new kakao.maps.LatLng(subway.lat, subway.lng),
              content: content,
              yAnchor: 0,
            });
            kakao.maps.event.addListener(marker, "mouseover", () => {
              customOverlay.setMap(this.map);
            });
            kakao.maps.event.addListener(marker, "mouseout", () => {
              customOverlay.setMap(null);
            });
          }
        });
      });
    },

    //=================================================================

    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 6,
      };

      this.map = new kakao.maps.Map(container, options);
      this.changeLatLng();
      kakao.maps.event.addListener(this.map, "dragend", () => {
        this.SET_DETAIL_HOUSE(null);
        if (this.map.getLevel() <= 4) {
          this.changeLatLng();
        }
      });

      kakao.maps.event.addListener(this.map, "click", () => {
        this.SET_DETAIL_HOUSE(null);
      });

      this.setAptsOnMap();
    },
  },

  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=53e7214792a38308832d05f2b2981bdf&libraries=services";

      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });
      document.head.appendChild(script);
    } else {
      this.initMap();
    }
  },

  watch: {
    center: function() {
      let center = new kakao.maps.LatLng(this.center.lat, this.center.lng);
      this.map.panTo(center);
      this.changeLatLng();

      if (this.isHospital) this.getHospitaListRange(this.latlng);
      if (this.isSchool) this.getSchoolListRange(this.latlng);
    },
    bookMarks: function() {
      this.markerClear(this.aptMarkers);
      this.setAptsOnMap();
    },
    hospitals: function() {
      this.markerClear(this.hospitalMarkers);
      this.setHospitalOnMap();
    },
    schools: function() {
      this.markerClear(this.schoolMarkers);
      this.setSchoolOnMap();
    },
    busStations: function() {
      this.markerClear(this.busStationMarkers);
      this.setBusStationOnMap();
    },
    subways: function() {
      this.markerClear(this.subwayMarkers);
      this.setSubwayOnMap();
    },
    isHospital: function() {
      if (this.map.getLevel() > 5) return;

      if (this.isHospital) this.getHospitaListRange(this.latlng);
      else this.markerClear(this.hospitalMarkers);
    },
    isSchool: function() {
      if (this.map.getLevel() > 5) return;
      if (this.isSchool) this.getSchoolListRange(this.latlng);
      else this.markerClear(this.schoolMarkers);
    },
    isBusStation: function() {
      if (this.map.getLevel() > 5) return;
      if (this.isBusStation) this.getBusStationListRange(this.latlng);
      else this.markerClear(this.busStationMarkers);
    },
    isSubway: function() {
      if (this.map.getLevel() > 5) return;
      if (this.isSubway) this.getSubwayListRange(this.latlng);
      else this.markerClear(this.subwayMarkers);
    },
    latlng: {
      deep: true,
      handler() {
        if (this.map.getLevel() > 5) return;
        if (this.isHospital) this.getHospitaListRange(this.latlng);
        if (this.isSchool) this.getSchoolListRange(this.latlng);
        if (this.isBusStation) this.getBusStationListRange(this.latlng);
        if (this.isSubway) this.getSubwayListRange(this.latlng);
      },
    },
  },
};
</script>

<style scoped>
#map {
  height: 73vh;
}
</style>
