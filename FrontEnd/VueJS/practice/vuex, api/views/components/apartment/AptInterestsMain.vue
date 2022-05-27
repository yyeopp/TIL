<template>
  <div class="section">
    <div class="control-bar">
      <div class="control-bar-regal">
        <b-row>
          <div class="control-bar-inputbar" style="text-align:center">
            <a @click="$router.push('/aptView/aptdeal')">아파트 매매정보</a>
            <a class="on">My Bookmark</a>
          </div>
          <div class="regal-select">
            <md-field>
              <label for="sido">시도선택</label>
              <md-select v-model="sidoCode" md-dense @md-selected="gugunList">
                <md-option
                  v-for="sido in sidos"
                  :key="sido.value"
                  :value="sido.value"
                  >{{ sido.text }}</md-option
                >
              </md-select>
            </md-field>
          </div>
          <div class="regal-select">
            <md-field>
              <label for="gugun">구군선택</label>
              <md-select
                v-model="gugunCode"
                md-dense
                @md-selected="searchCenter()"
              >
                <md-option
                  v-for="gugun in guguns"
                  :key="gugun.value"
                  :value="gugun.value"
                  >{{ gugun.text }}</md-option
                >
              </md-select>
            </md-field>
          </div>
          <div class="info_check">
            <md-checkbox v-model="isSchool">학군</md-checkbox>
            <md-checkbox v-model="isHospital">병원</md-checkbox>
            <md-checkbox v-model="isBusStation">버스정류장</md-checkbox>
            <md-checkbox v-model="isSubway">지하철역</md-checkbox>
          </div>
        </b-row>
      </div>
    </div>
    <b-row>
      <div class="apt-list-view"><interests-list-view /></div>
      <div class="map-view">
        <interests-map-view
          :isHospital="isHospital"
          :isSchool="isSchool"
          :isBusStation="isBusStation"
          :isSubway="isSubway"
        />
      </div>
    </b-row>
  </div>
</template>

<script>
import InterestsMapView from "@/views/components/apartment/InterestsMapView.vue";
import InterestsListView from "@/views/components/apartment/InterestsListView.vue";

import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";
export default {
  components: {
    InterestsMapView,
    InterestsListView,
  },
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      isSchool: false,
      isHospital: false,
      isBusStation: false,
      isSubway: false,
    };
  },

  mounted() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.getSido();
    this.CLEAR_DETAIL_HOUSE();
  },

  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "setMapCenter"]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "SET_DETAIL_HOUSE",
      "CLEAR_DETAIL_HOUSE",
    ]),
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    searchCenter() {
      if (this.gugunCode) this.setMapCenter(this.gugunCode);
    },
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "house"]),
  },
};
</script>

<style lang="scss" scoped>
.apt-list-view {
  padding-right: 0;
  padding-left: 12px;
  width: 23%;
}

.section {
  padding: 7px 0;
}

.map-view {
  width: 77%;
  padding-left: 0;
}

.regal-select {
  width: 180px;
}

.info_check {
  width: 420px;
  padding-top: 5px;
}
.control-bar {
  padding-bottom: 12px;
}

.control-bar-inputbar {
  padding-left: 4vh;
  width: 23%;
}

.on {
  font-weight: bold;
  color: rgb(68, 68, 68);
  overflow: hidden;
}
.on::after {
  content: "";
  position: absolute;
  border-bottom: 4px solid rgb(0, 0, 0);
  left: 0px;
  right: 0px;
  bottom: 0px;
}

a {
  display: inline-block;
  padding: 15px 20px;
  position: relative;
  font-size: 15px;
  line-height: 18px;
  color: rgb(166, 166, 166);
  vertical-align: top;
  transition: background-color 0.15s ease 0s;
}

a:not(.md-button) {
  color: black;
}
a:hover {
  color: black;
}
</style>
