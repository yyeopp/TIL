<template>
  <div class="container">
    <div class="mx-auto" style="margin-top: 20px">
      <h1>Notice</h1>
    </div>
    <div class="md-layout">
      <div
        class="md-layout-item md-size-75 mx-auto"
        style="margin-bottom: 30px"
      >
        <b-card header="featured" header-tag="header" :title="notice.title">
          <b-card-text>
            <br />
            {{ notice.content }}
          </b-card-text>
          <template #header>
            <span>@{{ notice.memberId }}</span>
            <span style="float:right"
              ><md-icon>schedule</md-icon>
              {{ notice.createdDate | writeTime }}</span
            >
          </template>
        </b-card>
        <span style="float : right"
          ><md-button class="md-danger" @click="delNotice"
            ><md-icon
              ><span class="material-icons">
                delete
              </span></md-icon
            ></md-button
          >
          <md-button style="float:right" @click="moveNotice"
            ><md-icon
              ><span class="material-icons">
                list
              </span></md-icon
            ></md-button
          >
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { mapState, mapActions } from "vuex";

const noticeStore = "noticeStore";

export default {
  bodyClass: "profile-page",
  data() {},
  created() {
    this.getNotice(this.$route.params.boardId);
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
    ...mapState(noticeStore, ["notice"]),
  },
  methods: {
    ...mapActions(noticeStore, ["getNotice", "deleteNotice"]),
    moveNotice() {
      this.$router.push("/board/notice");
    },
    delNotice() {
      try {
        this.deleteNotice(this.notice.boardId);
        alert("삭제 성공!");
        this.$router.push("/board/notice");
      } catch {
        alert("삭제 실패!");
        this.$router.push("/board/notice");
      }
    },
  },
  filters: {
    writeTime(value) {
      let createdTime = moment(new Date(value)).format("YY.MM.DD");
      if (createdTime != moment().format("YY.MM.DD")) return createdTime;
      return moment(new Date(value)).format("HH:mm");
    },
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
a:hover {
  cursor: pointer;
}
</style>
