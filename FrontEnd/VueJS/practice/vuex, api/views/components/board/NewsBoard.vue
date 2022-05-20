<template>
  <div class="container">
    <div class="mx-auto" style="margin: 20px">
      <h1>News</h1>
    </div>
    <div class="md-layout">
      <div
        class="md-layout-item md-size-75 mx-auto"
        style="margin-bottom: 30px"
      >
        <div v-if="pleaseWait">
          <div class="alert">
            <div class="container" style="text-align: center">
              <div class="alert-icon">
                <md-icon>info_outline</md-icon>
              </div>
              <b> 로딩중 ... </b>
            </div>
          </div>
        </div>
        <carousel
          v-else
          :per-page="1"
          loop
          :speed="700"
          autoplay
          :autoplay-timeout="10000"
          :mouse-drag="false"
          navigationEnabled
          navigationNextLabel="<i class='material-icons'>keyboard_arrow_right</i>"
          navigationPrevLabel="<i class='material-icons'>keyboard_arrow_left</i>"
        >
          <slide v-for="(news, i) in listNews" :key="i">
            <div
              class="carousel-caption"
              style="cursor: pointer"
              @click="moveArticle(listNews[i].link)"
            >
              <h3 v-html="listNews[i].title"></h3>
              <br />
              <p v-html="listNews[i].description"></p>
            </div>
            <img :src="carousel1" alt="carousel1" />
          </slide>
        </carousel>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  bodyClass: "profile-page",
  data() {
    return {
      listNews: [],
      carousel1: require("@/assets/img/news-bg2.jpg"),
      pleaseWait: true,
    };
  },
  created() {
    let id = "xPjKwK6tSXOB8XcW3zIz";
    let pass = "HmXEPSxUid";
    axios
      .get(
        "https://cors-anywhere.herokuapp.com/https://openapi.naver.com/v1/search/news.json",
        {
          params: {
            query: "부동산",
            display: 10,
          },

          headers: {
            Accept: "*/*",
            "X-Naver-Client-Id": id,
            "X-Naver-Client-Secret": pass,
          },
        }
      )
      .then((response) => {
        this.pleaseWait = false;
        this.listNews = response.data.items;
      });
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
    img: {
      type: String,
      default: require("@/assets/img/faces/christian.jpg"),
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  methods: {
    moveArticle(link) {
      window.open(link);
    },
  },
  filters: {},
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}

.profile-tabs::v-deep {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    img {
      margin-bottom: 2.142rem;
    }
  }
}
a:hover {
  cursor: pointer;
}
</style>
