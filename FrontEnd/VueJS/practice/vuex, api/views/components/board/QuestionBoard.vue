<template>
  <div class="container">
    <div class="mx-auto" style="margin: 20px">
      <h1>QnA</h1>
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
          <template #cell(answerState)="row">
            {{ row.item.commentDto ? "답변완료" : "미답변" }}
          </template>
          <template #cell(title)="row">
            <a @click="row.toggleDetails">{{ row.item.title }}</a>
          </template>
          <template #cell(createdDate)="row">
            {{ row.item.createdDate | writeTime }}
          </template>

          <template #row-details="row">
            <question-board-item
              @writeAnswer="writeAnswer"
              :item="row.item"
            ></question-board-item>
          </template>
        </b-table>
        <md-button @click="moveWrite" style="float : right">문의하기</md-button>
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
import QuestionBoardItem from "@/views/components/board/QuestionBoardItem";
import moment from "moment";
import { Pagination } from "@/components";
import { mapState, mapActions } from "vuex";

const qnaStore = "qnaStore";
export default {
  bodyClass: "profile-page",
  components: {
    Pagination,
    QuestionBoardItem,
  },
  data() {
    return {
      fields: [
        { key: "answerState", label: "답변상태", thStyle: { width: "15%" } },
        { key: "title", label: "제목", thStyle: { width: "45%" } },
        { key: "memberId", label: "작성자", thStyle: { width: "15%" } },
        { key: "createdDate", label: "작성시간", thStyle: { width: "25%" } },
      ],
      currentPage: 1,
      perPage: 5,
    };
  },
  created() {
    this.getQnaList();
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
    ...mapState(qnaStore, ["items"]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
    rows() {
      return this.items.length;
    },
  },
  methods: {
    ...mapActions(qnaStore, ["getQnaList", "writeAns"]),
    moveWrite() {
      this.$router.push("/board/questionWrite");
    },
    writeAnswer(boardId, content) {
      try {
        this.writeAns({ boardId, content });
        alert("답변 작성에 성공했습니다.");
        this.getQnaList();
      } catch {
        alert("답변 작성 중 오류가 발생했습니다.");
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
