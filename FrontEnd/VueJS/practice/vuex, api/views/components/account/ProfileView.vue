<template>
  <div class="wrapper">
    <div style="visibility:hidden; margin:100px"></div>
    <div class="main main-raised">
      <div class="section profile-content">
        <profile-item
          :checkUserInfo="checkUserInfo"
          @deleteAccount="delMember"
        ></profile-item>
        <div class="container">
          <hr style="color:black" />
          <div class="md-layout-item  ">
            <tabs
              :tab-name="['My QnA', 'My Bookmark']"
              :tab-icon="['content_paste', 'star']"
              plain
              flex-column
              nav-pills-icons
              color-button="default"
              style="width: 100vw;"
            >
              <template slot="tab-pane-1">
                <b-table
                  sticky-header
                  :items="qna.items"
                  :fields="qna.fields"
                  :sort-by.sync="sortBy"
                  :sort-desc.sync="sortDesc"
                  responsive="sm"
                  style="max-height: 200px; width: 900px;"
                ></b-table>
                <md-button
                  class="md-success md-sm"
                  @click="moveQna"
                  style="float : right;"
                  >질문하기</md-button
                >
              </template>
              <template slot="tab-pane-2">
                <b-table
                  sticky-header
                  :items="bookmark.items"
                  :fields="bookmark.fields"
                  :sort-by.sync="sortBy"
                  :sort-desc.sync="sortDesc"
                  responsive="sm"
                  style="max-height: 200px;  width: 900px;"
                ></b-table>
                <md-button
                  class="md-success md-sm"
                  @click="moveMap"
                  style="float : right;"
                  >지도에서 보기</md-button
                >
              </template>
            </tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Tabs } from "@/components";
import ProfileItem from "@/views/components/account/ProfileItem.vue";
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";

const accountStore = "accountStore";
const houseStore = "houseStore";
const qnaStore = "qnaStore";

export default {
  components: {
    Tabs,
    ProfileItem,
  },
  bodyClass: "profile-page",
  data() {
    return {
      sortBy: "아파트명",
      sortDesc: false,
      bookmark: {
        fields: [
          { key: "아파트명", sortable: true, thStyle: { width: "35%" } },
          { key: "지역", sortable: true, thStyle: { width: "25%" } },
          { key: "건설년도", sortable: true, thStyle: { width: "10%" } },
          { key: "거래가", sortable: true, thStyle: { width: "30%" } },
        ],
        items: [],
      },
      qna: {
        fields: [
          { key: "제목", thStyle: { width: "20%" } },
          { key: "내용", thStyle: { width: "40%" } },
          { key: "일자", sortable: true, thStyle: { width: "20%" } },
          { key: "답변", thStyle: { width: "20%" } },
        ],
        items: [],
      },
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
    img: {
      type: String,
      default: require("@/assets/img/anonymous.png"),
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
    ...mapGetters(accountStore, ["checkUserInfo"]),

    ...mapState(houseStore, ["bookMarks"]),
    ...mapState(qnaStore, ["itemsById"]),
  },
  methods: {
    ...mapActions(accountStore, ["deleteMember"]),
    ...mapActions(houseStore, ["getBookMarks"]),
    ...mapActions(qnaStore, ["getQnaListById"]),

    ...mapMutations(accountStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),

    delMember() {
      if (confirm("탈퇴하시겠습니까?")) {
        try {
          this.deleteMember(this.checkUserInfo.id);
          alert("탈퇴하였습니다.");
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("access-token");
          this.$router.push("/");
        } catch {
          alert("탈퇴 중 문제가 발생했습니다.");
        }
      }
    },
    moveQna() {
      this.$router.push("/board/question");
    },
    moveMap() {
      this.$router.push("/aptView/interests");
    },
  },
  created() {
    this.getBookMarks(this.checkUserInfo.id);
    for (let i = 0; i < this.bookMarks.length; i++) {
      this.bookmark.items.push({
        아파트명: this.bookMarks[i].apartmentName,
        지역: `${this.bookMarks[i].gugunname} ${this.bookMarks[i].dong}`,
        건설년도: this.bookMarks[i].buildyear,
        거래가: `${this.bookMarks[i].minPrice} ~ ${this.bookMarks[i].maxPrice}`,
      });
    }

    this.getQnaListById(this.checkUserInfo.id);
    for (let i = 0; i < this.itemsById.length; i++) {
      if (this.itemsById[i].commentDto != null) {
        this.qna.items.push({
          제목: this.itemsById[i].title,
          내용: this.itemsById[i].content,
          일자: this.itemsById[i].createdDate.substr(2, 9),
          답변: this.itemsById[i].commentDto.content,
        });
      } else {
        this.qna.items.push({
          제목: this.itemsById[i].title,
          내용: this.itemsById[i].content,
          일자: this.itemsById[i].createdDate.substr(2, 9),
          답변: "",
        });
      }
    }
  },
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
</style>
