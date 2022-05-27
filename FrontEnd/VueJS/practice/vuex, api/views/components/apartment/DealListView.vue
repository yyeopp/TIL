<template>
  <div class="apt-info" style="overflow:scroll" v-if="house">
    <div class="aptMainInfo">
      <road-view :road="house"></road-view>
      <share-apt :house="house"></share-apt>
      <div class="aptName" style="margin-top: 10px">
        {{ house.apartmentName }}
        <p class="explain" style="margin-top: 3px">
          {{ house.buildyear }}년 건설
        </p>
      </div>
      <div>
        <p class="explain">
          <md-icon>place</md-icon> {{ house.sidoname }} {{ house.gugunname }}
          {{ house.dongname }} {{ house.jibun }}번지
        </p>
      </div>
      <div style="display : flex;" v-if="userInfo">
        <md-button
          v-if="isBookMark"
          @click="removeBookMark"
          class="md-rose md-sm"
          style="min-width : 30px; margin-left: auto; padding-bottom : auto; "
          ><i class="material-icons">favorite</i>
          {{ house.bookMarkHit }}</md-button
        >
        <md-button
          v-else
          @click="AddBookMark"
          class="md-default md-sm"
          style="min-width : 30px; margin-left: auto; padding-bottom : auto; "
          ><i class="material-icons">favorite_border</i
          >{{ house.bookMarkHit }}</md-button
        >
      </div>
    </div>

    <div class="div-line"></div>

    <!-- <div class="div-line"></div> -->
    <div class="InfoTitle" v-b-toggle.dealInfo>
      <div class="typeInfo" style="font-weight: bold">
        매매정보
        <i class="material-icons downbox" style="float : right">expand_more</i>
      </div>
    </div>
    <b-collapse id="dealInfo">
      <div
        style="    color: rgb(45, 96, 163);
    font-family: SpoqaHanSans;
    font-size: 20px;
    font-weight: bold;
    line-height: 26px;"
      >
        매매 {{ house.minPrice | money }} ~ {{ house.maxPrice | money }}
      </div>
      <div style="margin-top : 5px">실거래가</div>
      <deal-chart></deal-chart>
      <b-table
        id="my-table"
        :items="houseDeals"
        :fields="fields"
        :per-page="perPage"
        :current-page="currentPage"
      >
        <template #cell(dealDate)="{item}">
          <p class="explain">
            {{ item.dealYear.substr(2, 2) }}.{{ item.dealMonth }}.{{
              item.dealDay
            }}
          </p>
        </template>
        <template #cell(dealAmount)="{item}">
          {{ item.dealAmount | money }}
        </template>
        <template #cell(area)="{item}">
          <p>{{ Math.round(item.area * 100) / 100 }} m<sup>2</sup></p>
        </template>
      </b-table>
      <b-pagination
        v-model="currentPage"
        :total-rows="houseDeals.length"
        :per-page="perPage"
        size="sm"
        aria-controls="my-table"
      ></b-pagination>
    </b-collapse>

    <div class="div-line"></div>

    <div class="InfoTitle" v-b-toggle.rank>
      <div class="typeInfo" style="font-weight: bold">
        단지 인기 순위
        <i class="material-icons downbox" style="float : right">expand_more</i>
      </div>
    </div>
    <b-collapse id="rank">
      <b-row v-if="hitRank">
        <b-col>
          <div>
            <p><md-icon>check</md-icon> 조회수</p>
          </div>
          <div class="rankTitle">
            {{ hitRank.regionName }} 상위
            {{ Math.floor((hitRank.aptRank / hitRank.regionTotal) * 100) }}%
          </div>
          <div>
            <p>{{ hitRank.regionTotal }}단지 중 {{ hitRank.aptRank }}위</p>
          </div>
        </b-col>
        <b-col>
          <div>
            <p><md-icon>favorite</md-icon> 북마크</p>
          </div>
          <div class="rankTitle">
            {{ hitRank.regionName }} 상위
            {{ Math.floor((house.bookMarkRank / hitRank.regionTotal) * 100) }}%
          </div>
          <div>
            <p>{{ hitRank.regionTotal }}단지 중 {{ house.bookMarkRank }}위</p>
          </div>
        </b-col>
      </b-row>
    </b-collapse>

    <div class="div-line"></div>

    <div class="InfoTitle" v-b-toggle.expanded>
      <div class="typeInfo" style="font-weight: bold">
        주변 정보
        <i class="material-icons downbox" style="float : right">expand_more</i>
      </div>
    </div>
    <b-collapse id="expanded">
      <b-tabs content-class="mt-3">
        <b-tab title="학군" active>
          <b-row>
            <b-col class="school">
              <div
                class="school-item"
                v-for="school in nearSchools"
                :key="school.name"
              >
                {{ school.name }}
                <p v-if="school.dist < 1000">
                  <md-icon>east</md-icon> {{ Math.floor(school.dist) }} m
                </p>
                <p v-else>
                  <md-icon>east</md-icon>
                  {{ Math.round((Math.floor(school.dist) / 1000) * 10) / 10 }}
                  km
                </p>
              </div>
            </b-col>
          </b-row></b-tab
        >
        <b-tab title="병원">
          <b-row>
            <b-col class="hospital" v-if="nearHospital">
              <div v-for="hospital in nearHospital" :key="hospital.name">
                {{ hospital.name }}
                <p v-if="hospital.dist < 1000">
                  <md-icon>east</md-icon> {{ Math.floor(hospital.dist) }} m
                </p>
                <p v-else>
                  <md-icon>east</md-icon>
                  {{ Math.round((Math.floor(hospital.dist) / 1000) * 10) / 10 }}
                  km
                </p>
              </div>
            </b-col>
            <div v-else>근처에 병원이 없습니다</div>
          </b-row>
        </b-tab>
        <b-tab title="지하철"
          ><b-row>
            <b-col class="subway">
              <div v-for="(subway, idx) in nearSubways" :key="idx">
                {{ subway.name }}
                <p v-if="subway.dist < 1000">
                  <md-icon>east</md-icon> {{ Math.floor(subway.dist) }} m
                </p>
                <p v-else>
                  <md-icon>east</md-icon>
                  {{ Math.round((Math.floor(subway.dist) / 1000) * 10) / 10 }}
                  km
                </p>
              </div>
            </b-col>
          </b-row></b-tab
        >
      </b-tabs>
    </b-collapse>

    <div class="div-line"></div>

    <div class="InfoTitle" v-b-toggle.localNews>
      <div class="typeInfo" style="font-weight: bold">
        관련 뉴스
        <i class="material-icons downbox" style="float : right">expand_more</i>
      </div>
    </div>
    <b-collapse id="localNews">
      <span
        style="color: blue; font-weight: bold; margin-left: 20px; margin-top: 10px"
      >
        {{ this.house.gugunname }}</span
      ><span> 부동산 관련 최신 뉴스</span>
      <b-card
        v-for="(item, index) in parsedNews"
        :key="index"
        @click="moveArticle(item.link)"
      >
        <b-card-text style="cursor:pointer; font-weight: bold; font-size: 16px">
          {{ item.title }}
        </b-card-text>
        <b-card-text style="cursor: pointer"
          >{{ item.description }}
        </b-card-text>
        <b-card-text style="text-align: right;">{{ item.pubDate }}</b-card-text>
      </b-card>
    </b-collapse>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import DealChart from "@/views/components/apartment/chart/DealChart.vue";
import RoadView from "@/views/components/apartment/RoadView.vue";
import ShareApt from "@/views/components/apartment/ShareApt.vue";
const houseStore = "houseStore";
const accountStore = "accountStore";
const expandedStore = "expandedStore";

export default {
  data() {
    return {
      currentPage: 1,
      perPage: 10,
      fields: [
        { key: "dealDate", label: "거래일", thStyle: { width: "23%" } },
        { key: "dealAmount", label: "거래금액", thStyle: { width: "38%" } },
        { key: "area", label: "면적", thStyle: { width: "24%" } },
        { key: "floor", label: "층수", thStyle: { width: "20%" } },
      ],
      newsFields: [],
    };
  },
  components: {
    DealChart,
    RoadView,
    ShareApt,
  },
  computed: {
    ...mapState(houseStore, ["house", "houseDeals", "hitRank", "localNews"]),
    ...mapGetters(houseStore, ["isBookMark", "parsedNews"]),
    ...mapState(accountStore, ["userInfo"]),
    ...mapState(expandedStore, ["nearSchools", "nearSubways", "nearHospital"]),
  },
  methods: {
    ...mapActions(houseStore, ["setBookMark", "deleteBookMark"]),

    AddBookMark() {
      this.house.bookMarkHit++;
      const params = {
        memberId: this.userInfo.id,
        aptCode: this.house.aptCode,
      };
      this.setBookMark(params);
    },
    removeBookMark() {
      this.house.bookMarkHit--;
      const params = {
        memberId: this.userInfo.id,
        aptCode: this.house.aptCode,
      };
      this.deleteBookMark(params);
    },
    moveArticle(link) {
      window.open(link);
    },
  },
};
</script>

<style scope>
.explain {
  color: rgb(117, 117, 117);
  flex-shrink: 1;
  flex-wrap: wrap;
  font-family: SpoqaHanSans;
  font-size: 14px;
  line-height: 18px;
  margin-bottom: 0;
}
.apt-info {
  height: 73vh;
}

.apt-info > div > div {
  padding-left: 12px;
  padding-right: 12px;
}

.aptMainInfo {
  padding-top: 8px;
}

.aptName {
  color: rgb(34, 34, 34);
  flex-shrink: 1;
  flex-wrap: wrap;
  font-family: SpoqaHanSans;
  font-size: 23px;
  font-weight: bold;
  line-height: 30px;
}

.div-line {
  height: 12px;
  background-color: rgb(238, 238, 238);
}

.InfoTitle {
  padding-left: 0;
  padding-right: 0;
}

.InfoTitle :active {
  background-color: rgba(0, 0, 0, 0.1);
  background-origin: border-box;
}

.typeInfo {
  padding-left: 12px;
  padding-right: 12px;
  min-height: 48px;
  padding-top: 12px;
  color: rgb(34, 34, 34);
  flex-shrink: 1;
  font-family: SpoqaHanSans;
  font-size: 18px;
  line-height: 21px;
}

.collapsed i {
  transform: rotate(180deg);
  transition: all 0.2s linear;
}
.not-collapsed i {
  transform: rotate(0deg);
  transition: all 0.2s linear;
}

#expanded p {
  color: rgb(127, 126, 146);
}

#rank p {
  color: rgb(117, 117, 117);
  font-family: SpoqaHanSans;
  font-size: 13px;
  line-height: 18px;
}

.rankTitle {
  color: rgb(45, 96, 163);
  font-family: SpoqaHanSans;
  font-size: 17px;
  font-weight: bold;
  line-height: 26px;
}
</style>
