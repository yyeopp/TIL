<template>
  <div id="roadMap" class="map" ref="map"></div>
</template>

<script>
export default {
  props: ["road"],
  watch: {
    road: {
      deep: true,
      handler() {
        this.kakao && this.kakao.maps
          ? this.initMap()
          : this.addKakaoMapScript();
      },
    },
  },
  mounted() {
    this.kakao && this.kakao.maps ? this.initMap() : this.addKakaoMapScript();
  },
  methods: {
    addKakaoMapScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=53e7214792a38308832d05f2b2981bdf&libraries=services`;
      document.head.appendChild(script);
    },
    initMap() {
      var roadviewContainer = document.getElementById("roadMap");
      var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
      var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

      var position = new kakao.maps.LatLng(this.road.lat, this.road.lng); //지도의 중심좌표.

      roadviewClient.getNearestPanoId(position, 50, function(panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      }); //지도 생성 및 객체 리턴
    },
  },
};
</script>

<style>
.map {
  width: 100%;
  height: 200px;
}
#loadview {
  height: 600px;
  background-color: #ffffff;
}
</style>
