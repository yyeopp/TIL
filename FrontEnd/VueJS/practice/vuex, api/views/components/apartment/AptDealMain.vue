<template>
  <div class="section">
    <div class="control-bar">
      <div class="control-bar-regal">
        <b-row>
          <div class="control-bar-inputbar" style="text-align:center">
            <a class="on">아파트 매매정보</a>
            <a @click="$router.push('/aptView/interests')">My Bookmark</a>
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
                @md-selected="[searchCenter(), searchApt()]"
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
          <div class="filter">
            <b-button v-b-toggle.filter variant="outline-secondary">
              <i class="material-icons filter-item">settings_input_composite</i>
              <p class="filter-item">필터</p>
            </b-button>
          </div>
          <div class="search-box-view">
            <search-box></search-box>
          </div>
        </b-row>
      </div>
    </div>
    <b-collapse id="filter" class="mt-2">
      <b-card>
        <h5 class="card-text">금액</h5>
        <p>{{ priceText }}</p>
        <b-row>
          <b-col>
            <v-range-slider v-model="price" min="0" max="20"></v-range-slider>
          </b-col>
        </b-row>
        <h5 class="card-text" style="margin-top : 15px">준공년도</h5>
        <hr />
        <b-col>
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[0]"
            @click="selecteYear(0)"
            >전체</b-button
          >
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[1]"
            @click="selecteYear(1)"
            >5년 이내</b-button
          >
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[2]"
            @click="selecteYear(2)"
            >10년 이내</b-button
          >
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[3]"
            value="15"
            @click="selecteYear(3)"
            >15년 이내</b-button
          >
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[4]"
            @click="selecteYear(4)"
            >20년 이내</b-button
          >
          <b-button
            class="buildyear"
            variant="outline-secondary"
            :pressed.sync="buildyear[5]"
            @click="selecteYear(5)"
            >25년 이내</b-button
          >
        </b-col>
      </b-card>
    </b-collapse>
    <b-row>
      <div class="apt-list-view" v-show="house"><deal-list-view /></div>
      <div class="map-view">
        <deal-map-view
          :isHospital="isHospital"
          :isSchool="isSchool"
          :isBusStation="isBusStation"
          :isSubway="isSubway"
          :getFilter="getFilter"
        />
      </div>
    </b-row>
  </div>
</template>

<script>
import DealMapView from "@/views/components/apartment/DealMapView.vue";
import DealListView from "@/views/components/apartment/DealListView.vue";
import SearchBox from "@/views/components/apartment/SearchBox.vue";

import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";
const accountStore = "accountStore";

export default {
  components: {
    DealMapView,
    DealListView,
    SearchBox,
  },
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      isSchool: false,
      isHospital: false,
      isBusStation: false,
      isSubway: false,
      price: [0, 20],
      buildyear: [true, false, false, false, false, false],
      selected: 0,
    };
  },

  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.getSido();
  },
  mounted() {
    if (this.userInfo) this.getBookMarks(this.userInfo.id);
    this.CLEAR_DETAIL_HOUSE();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getHouseList",
      "setMapCenter",
      "getBookMarks",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DETAIL_HOUSE",
    ]),
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    searchApt() {
      if (this.gugunCode)
        this.getHouseList({
          gugunCode: this.gugunCode,
          filter: this.getFilter,
        });
    },
    searchCenter() {
      if (this.gugunCode) this.setMapCenter(this.gugunCode);
    },
    selecteYear(idx) {
      this.buildyear[this.selected] = false;
      this.selected = idx;
    },
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "houses", "house"]),
    ...mapState(accountStore, ["userInfo"]),
    priceText() {
      if (this.price[0] == "0" && this.price[1] == "20") return "무제한";
      if (this.price[0] == "0") return ` ~ ${this.price[1]}억`;
      if (this.price[1] == "20") return `${this.price[0]}억 ~`;

      return `${this.price[0]}억 ~ ${this.price[1]}억`;
    },

    getFilter() {
      let filter = {
        buildYear: this.selected != 0 ? this.selected * 5 : 100,
        minPrice: this.price[0] * 10000,
        maxPrice: this.price[1] == 20 ? 2000000 : this.price[1] * 10000,
      };
      return filter;
    },
  },
};
</script>

<style lang="scss" scoped>
.apt-list-view {
  padding-right: 0;
  padding-left: 0;
  width: 23%;
  position: absolute;
  left: 0;
  z-index: 1000;
  background-color: white;
}

.search-box-view {
  right: 0;
  width: 22%;
  background-color: white;
  position: absolute;
  z-index: 1000;
}

.section {
  padding: 7px 0;
}

.map-view {
  width: 100%;
}

.control-bar {
  padding-bottom: 12px;
}

.regal-select {
  width: 12%;
}

.info_check {
  width: 24%;
  padding-top: 5px;
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

.filter {
  width: 7%;
}

.filter > button {
  width: 100%;
  height: 60%;
  margin-top: 7px;
}
.filter-item {
  display: inline-block;
}

#filter {
  width: 24%;
  position: absolute;
  top: 60px;
  right: 68px;
  z-index: 1000;
}

.filter-data {
  width: 20%;
  text-align: center;
}

.filter-data p {
  margin: 0;
}

.filter-data-div {
  width: 80%;
  height: 70%;
  margin: 0 auto;
  margin-top: 8px;
  border: 0.1px solid black;
  box-sizing: border-box;
  padding-top: 10px;
}

.buildyear {
  margin: 1px;
  width: 110px;
  padding: 6px, 6px;
  font: inherit;
  z-index: 1000;
}

.v-application--wrap {
  height: 50px !important;
}
</style>
