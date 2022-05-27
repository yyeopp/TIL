<template>
  <div class="box">
    <div>
      <md-field class="md-form-group">
        <md-icon style="margin-right:10px">search</md-icon>
        <input
          type="text"
          :value="keyword"
          placeholder="지역, 아파트, 지하철 검색"
          @input="changeKeyword"
          style="width: 100%;"
        />
        <v-popover offset="4" placement="top" style="float: right;">
          <md-icon>filter_list</md-icon>

          <template slot="popover">
            <div class="popover-body" style="background-color: white">
              <md-checkbox v-model="regionOK">지역</md-checkbox>
              <md-checkbox v-model="subwayOK">지하철역</md-checkbox>
              <md-checkbox v-model="aptOK">아파트</md-checkbox>
            </div>
          </template>
        </v-popover>
      </md-field>
    </div>
    <div v-if="validSearch" style="overflow: scroll; max-height: 500px">
      <div v-if="regionOK">
        <b-table
          borderless
          small
          hover
          :items="this.searchInfo.regions"
          :fields="regionFields"
          @row-clicked="moveMap"
        >
          <template #cell(regionName)="{item}">
            <p class="explain">
              <md-icon>place</md-icon> {{ item.regionName }}
            </p>
          </template>
          <template #cell(aptNum)="{item}">
            <p class="explain">
              <md-icon>apartment</md-icon> {{ item.aptNum }}
            </p>
          </template>
        </b-table>
      </div>
      <div v-if="subwayOK">
        <b-table
          borderless
          small
          hover
          :items="this.searchInfo.subways"
          :fields="subwayFields"
          @row-clicked="moveMap"
        >
          <template #cell(subwayname)="{item}">
            <p class="explain">
              <md-icon>subway</md-icon> {{ item.subwayname }}
            </p>
          </template>
          <template #cell(linename)="{item}">
            <p class="explain">
              {{ item.linename }}
            </p>
          </template>
        </b-table>
      </div>
      <div v-if="aptOK">
        <b-table
          borderless
          small
          hover
          :items="this.searchInfo.houses"
          :fields="aptFields"
          @row-clicked="moveHouse"
        >
          <template #cell(apartmentName)="{item}">
            <p class="explain">
              <md-icon>domain</md-icon> {{ item.apartmentName }}
            </p>
          </template>
          <template #cell(bookmarkHit)="{item}">
            <p class="explain">
              <md-icon>favorite</md-icon> {{ item.bookmarkHit }}
            </p>
          </template>
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  data() {
    return {
      regionOK: true,
      subwayOK: true,
      aptOK: true,

      regionView: [],
      regionFields: [
        { key: "regionName", label: "", thStyle: { width: "70%" } },
        { key: "aptNum", label: "", thStyle: { width: "30%" } },
      ],
      subwayFields: [
        { key: "subwayname", label: "", thStyle: { width: "70%" } },
        { key: "linename", label: "", thStyle: { width: "30%" } },
      ],
      aptFields: [
        { key: "apartmentName", label: "", thStyle: { width: "70%" } },
        { key: "bookmarkHit", label: "", thStyle: { width: "30%" } },
      ],
      keyword: "",
    };
  },
  methods: {
    ...mapActions(houseStore, [
      "infoSearch",
      "houseSearch",
      "gethouseDealChart",
    ]),
    ...mapMutations(houseStore, ["SET_MAP_CENTER"]),
    changeKeyword(e) {
      this.keyword = e.target.value;
    },
    moveMap(record) {
      this.SET_MAP_CENTER({ lat: record.lat, lng: record.lng });
    },
    async moveHouse(record) {
      await this.houseSearch(record.aptCode);
      this.SET_MAP_CENTER({ lat: record.lat, lng: record.lng });
      this.gethouseDealChart(record.aptCode);
    },
  },
  computed: {
    ...mapState(houseStore, ["searchInfo"]),
    validSearch() {
      return this.keyword.length > 1;
    },
  },
  watch: {
    keyword() {
      if (this.keyword.length > 1) {
        this.infoSearch(this.keyword);
      }
    },
  },
};
</script>

<style>
.box {
  max-height: 600px;
  -ms-overflow-style: none;
}
.box::-webkit-scrollbar {
  display: none;
}
</style>
