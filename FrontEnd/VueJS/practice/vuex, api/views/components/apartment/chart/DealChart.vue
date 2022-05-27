<template>
  <LineChartGenerator
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
</template>

<script>
import { Line as LineChartGenerator } from "vue-chartjs/legacy";
import { mapState } from "vuex";

const houseStore = "houseStore";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement
);

export default {
  name: "LineChart",
  components: {
    LineChartGenerator,
  },
  props: {
    chartId: {
      type: String,
      default: "line-chart",
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
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      chartData: {
        labels: [],
        datasets: [
          {
            label: "매매가(만원)",
            backgroundColor: "#f87979",
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
    ...mapState(houseStore, ["dealCharts"]),
  },
  mounted() {
    let label = [];
    let data = [];
    for (const i in this.dealCharts) {
      label.push(this.dealCharts[i].dealDate);
      data.push(Math.floor(this.dealCharts[i].dealAvg));
    }

    this.chartData.labels = label;
    this.chartData.datasets[0].data = data;
  },
  watch: {
    dealCharts: function() {
      let label = [];
      let data = [];
      for (const i in this.dealCharts) {
        label.push(this.dealCharts[i].dealDate);
        data.push(Math.floor(this.dealCharts[i].dealAvg));
      }

      this.chartData.labels = label;
      this.chartData.datasets[0].data = data;
    },
  },
};
</script>
