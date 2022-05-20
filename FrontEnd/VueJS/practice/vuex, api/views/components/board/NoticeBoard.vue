<template>
  <div class="container">
    <div class="mx-auto" style="margin-top: 20px">
      <h1>Notice</h1>
    </div>
    <div class="md-layout">
      <div class="md-layout-item md-size-75 mx-auto">
        <b-table
          id="my-table"
          :items="items"
          :fields="fields"
          :per-page="perPage"
          :current-page="currentPage"
          details-td-class="bg-secondary bg-opacity-10 border-top"
        >
          <template #cell(title)="row">
            <router-link :to="'/board/notice/' + row.item.boardId">{{
              row.item.title
            }}</router-link>
          </template>
          <template #cell(createdDate)="row">
            {{ row.item.createdDate | writeTime }}
          </template>
          <template #cell(hit)="row">
            {{ row.item.hit }}
          </template>
        </b-table>
        <md-button @click="moveWrite" style="float : right">공지작성</md-button>
      </div>
    </div>

    <div class="mx-auto">
      <pagination
        type="info"
        v-model="currentPage"
        :total="rows"
        :perPage="perPage"
      >
      </pagination>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { Pagination } from "@/components";
import { mapState, mapActions } from "vuex";

const noticeStore = "noticeStore";

export default {
  bodyClass: "profile-page",
  components: {
    Pagination,
  },
  data() {
    return {
      fields: [
        { key: "boardId", label: "번호", thStyle: { width: "10%" } },
        { key: "title", label: "제목", thStyle: { width: "40%" } },
        { key: "memberId", label: "작성자", thStyle: { width: "15%" } },
        { key: "hit", label: "조회수", thStyle: { width: "10%" } },
        { key: "createdDate", label: "작성시간", thStyle: { width: "25%" } },
      ],
      currentPage: 1,
      perPage: 5,
    };
  },
  created() {
    this.getNoticeList();
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
    rows() {
      return this.items.length;
    },
    ...mapState(noticeStore, ["items"]),
  },
  methods: {
    moveWrite() {
      this.$router.push("/board/noticeWrite");
    },
    ...mapActions(noticeStore, ["getNoticeList"]),
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
