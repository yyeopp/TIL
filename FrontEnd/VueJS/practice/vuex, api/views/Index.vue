<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="brand">
            <h1>HappyHouse</h1>
            <h3>Find your place</h3>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <div class="container">
          <div class="title" style="text-align:right; margin-right: 5px">
            <h2>Daily Trend</h2>
          </div>
          <trend-chart></trend-chart>
          <div class="blockquote undefined" style="text-align:right">
            <p>
              거래량, 조회수, 북마크를 기반으로 자체 도출한 트렌드를
              보여드립니다.
            </p>
          </div>
        </div>
      </div>
      <div class="section section-basic">
        <div class="container">
          <v-container>
            <v-row>
              <div class="title" style="text-align:right; margin-right: 10px">
                <h2>Articles</h2>
              </div>
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
                  <div class="carousel-caption" @click="moveArticle(news.link)">
                    <h3 v-html="news.title"></h3>
                    <br />
                    <p v-html="news.description"></p>
                  </div>
                  <div
                    class="carousel-caption"
                    style="cursor: pointer"
                    @click="moveArticle(listNews[i].link)"
                  ></div>
                  <img :src="testCarousel[i]" alt="carousel1" />
                </slide>
              </carousel>
            </v-row>
            <v-row style="margin-top: 40px" v-if="this.listBlog.length > 0">
              <v-col>
                <v-card
                  class="mx-auto"
                  max-width="344"
                  min-height="150"
                  style="background-color: #DBE6EC"
                >
                  <v-list-item three-line>
                    <v-list-item-content>
                      <div class="text-overline mb-4">
                        WRITTEN BY <br />
                        {{ this.listBlog[0].name }}
                      </div>
                      <v-list-item-subtitle v-html="this.listBlog[0].title">
                      </v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-avatar tile size="50" color="transparent"
                      ><img
                        src="@/assets/img/naverMember.png"
                        alt=""
                        @click="moveBlog(0)"
                        style="cursor:pointer"
                    /></v-list-item-avatar>
                  </v-list-item>
                </v-card>
              </v-col>
              <v-col
                ><v-card
                  class="mx-auto"
                  max-width="344"
                  min-height="150"
                  style="background-color: #C2CBCE"
                >
                  <v-list-item three-line>
                    <v-list-item-content>
                      <div class="text-overline mb-4">
                        WRITTEN BY <br />{{ this.listBlog[1].name }}
                      </div>
                      <v-list-item-subtitle v-html="this.listBlog[1].title">
                      </v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-avatar tile size="50" color="transparent"
                      ><img
                        src="@/assets/img/naverMember.png"
                        alt=""
                        @click="moveBlog(1)"
                        style="cursor:pointer"
                    /></v-list-item-avatar>
                  </v-list-item> </v-card
              ></v-col>
              <v-col
                ><v-card
                  class="mx-auto"
                  max-width="344"
                  min-height="150"
                  style="background-color: #A4BCC2"
                >
                  <v-list-item three-line>
                    <v-list-item-content>
                      <div class="text-overline mb-4">
                        WRITTEN BY <br />
                        {{ this.listBlog[2].name }}
                      </div>
                      <v-list-item-subtitle v-html="this.listBlog[2].title">
                      </v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-avatar tile size="50" color="transparent">
                      <img
                        src="@/assets/img/naverMember.png"
                        alt=""
                        @click="moveBlog(2)"
                        style="cursor:pointer"
                    /></v-list-item-avatar>
                  </v-list-item> </v-card
              ></v-col>
            </v-row>
          </v-container>
        </div>
      </div>
      <div class="section section-basic">
        <div class="container">
          <v-container fluid>
            <v-row dense>
              <v-col
                style="margin-bottom:30px"
                v-for="card in cards"
                :key="card.title"
                :cols="card.flex"
              >
                <v-card @click.prevent="moveSight(card.link)">
                  <v-img
                    :src="card.src"
                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                    height="250px"
                  >
                  </v-img>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <h4 v-text="card.title"></h4>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TrendChart from "@/views/components/apartment/chart/TrendChart.vue";

export default {
  components: { TrendChart },
  name: "index",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/building2.jpg"),
    },
    signup: {
      type: String,
    },
    landing: {
      type: String,
    },
    profile: {
      type: String,
    },
  },
  data() {
    return {
      listNews: [],
      listBlog: [],
      pleaseWait: true,
      testCarousel: [
        "https://picsum.photos/1920/840?random=1",
        "https://picsum.photos/1920/840?random=2",
        "https://picsum.photos/1920/840?random=3",
        "https://picsum.photos/1920/840?random=4",
        "https://picsum.photos/1920/840?random=5",
        "https://picsum.photos/1920/840?random=6",
        "https://picsum.photos/1920/840?random=7",
        "https://picsum.photos/1920/840?random=8",
        "https://picsum.photos/1920/840?random=9",
        "https://picsum.photos/1920/840?random=10",
      ],
      cards: [
        {
          title: "Apartment",
          src: require("@/assets/img/apt1.jpg"),
          link: "/aptView",
          flex: 12,
        },
        {
          title: "QnA",
          src: require("@/assets/img/qna.jpg"),
          link: "/board/question",
          flex: 6,
        },
        {
          title: "Notice",
          src: require("@/assets/img/news.jpg"),
          link: "/board/notice",
          flex: 6,
        },
      ],
    };
  },
  methods: {
    moveArticle(link) {
      window.open(link);
    },
    moveSight(link) {
      this.$router.push(link);
    },
    moveBlog(num) {
      window.open(this.listBlog[num].link);
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.image})`,
      };
    },
    signupImage() {
      return {
        backgroundImage: `url(${this.signup})`,
      };
    },
  },
  filters: {},
  created() {
    axios.get("http://localhost/news/list").then((response) => {
      this.pleaseWait = false;
      this.listNews = response.data;
    });
    axios.get("http://localhost/news/blog").then((response) => {
      this.pleaseWait = false;
      this.listBlog = response.data;
    });
  },
};
</script>
<style lang="scss">
.section-download {
  .md-button + .md-button {
    margin-left: 5px;
  }
}

@media all and (min-width: 991px) {
  .btn-container {
    display: flex;
  }
}

.news-title {
  cursor: pointer;
}

.news-title:hover {
  text-decoration: underline;
}

.carousel-caption {
  cursor: pointer;
  background-color: rgb(0, 0, 0, 0.4);
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
