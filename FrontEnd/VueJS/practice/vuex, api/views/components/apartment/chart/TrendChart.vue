<template>
  <v-card style="padding-bottom: 10px; padding-top: 10px">
    <Bar
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :dataset-id-key="datasetIdKey"
      :plugins="plugins"
      :css-classes="cssClasses"
      :styles="styles"
      :width="width"
      :height="height"
    />
    <hr />
    <div style="text-align:center">
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 0 }"
        @click="getTrendAll(0)"
        >전국</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 1 }"
        variant="secondary"
        @click="getTrend(11, 1)"
        >서울</md-button
      >
      <md-button
        class="md-button md-sm "
        :class="{ 'md-simple': selected != 2 }"
        @click="getTrend(26, 2)"
        >부산</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 3 }"
        @click="getTrend(27, 3)"
        >대구</md-button
      >
      <md-button
        class="md-button  md-sm"
        :class="{ 'md-simple': selected != 4 }"
        @click="getTrend(28, 4)"
        >인천</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 5 }"
        @click="getTrend(29, 5)"
        >광주</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 6 }"
        @click="getTrend(30, 6)"
        >대전</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 7 }"
        @click="getTrend(31, 7)"
        >울산</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 8 }"
        @click="getTrend(36, 8)"
        >세종</md-button
      >
      <br />
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 9 }"
        @click="getTrend(41, 9)"
        >경기</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 10 }"
        @click="getTrend(42, 10)"
        >강원</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 11 }"
        @click="getTrend(43, 11)"
        >충북</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 12 }"
        @click="getTrend(44, 12)"
        >충남</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 13 }"
        @click="getTrend(45, 13)"
        >전북</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 14 }"
        @click="getTrend(46, 14)"
        >전남</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 15 }"
        @click="getTrend(47, 15)"
        >경북</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 16 }"
        @click="getTrend(48, 16)"
        >경남</md-button
      >
      <md-button
        class="md-button md-sm"
        :class="{ 'md-simple': selected != 17 }"
        @click="getTrend(50, 17)"
        >제주</md-button
      >
    </div>
  </v-card>
</template>

<script>
import { Bar } from "vue-chartjs/legacy";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

import { mapState, mapActions } from "vuex";
const houseStore = "houseStore";

export default {
  name: "BarChart",
  components: { Bar },
  props: {
    chartId: {
      type: String,
      default: "bar-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 250,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      selected: 1,
      loading: false,
      chartData: {
        labels: [],
        datasets: [
          {
            label: "관심도",
            backgroundColor: "#FFD4AD",
            data: [],
          },
          {
            label: "거래량",
            backgroundColor: "#E5FBA5",
            data: [],
          },
          {
            label: "조회수",
            backgroundColor: "#FFF49B",
            data: [],
          },
          {
            label: "북마크",
            backgroundColor: "#A1F7FF",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  computed: {
    ...mapState(houseStore, ["houseTrends"]),
  },
  mounted() {
    this.getTrendAll(0);
  },
  watch: {
    houseTrends: function() {
      let label = [];
      let resultData = [];
      let dealData = [];
      let hitData = [];
      let bookmarkData = [];
      for (const i in this.houseTrends) {
        label.push(this.houseTrends[i].regionName);
        resultData.push(this.houseTrends[i].result);
        dealData.push(this.houseTrends[i].dealSum);
        hitData.push(this.houseTrends[i].hitSum);
        bookmarkData.push(this.houseTrends[i].bookmarkSum);
      }

      this.chartData.labels = label;
      this.chartData.datasets[0].data = resultData;
      this.chartData.datasets[1].data = dealData;
      this.chartData.datasets[2].data = hitData;
      this.chartData.datasets[3].data = bookmarkData;
    },
  },
  created() {},
  methods: {
    ...mapActions(houseStore, ["getHouseTrend", "getHouseTrendAll"]),

    getTrend(sidoCode, num) {
      this.selected = num;
      this.getHouseTrend(sidoCode);
    },
    getTrendAll(num) {
      this.selected = num;
      this.getHouseTrendAll();
    },
  },
};
</script>
