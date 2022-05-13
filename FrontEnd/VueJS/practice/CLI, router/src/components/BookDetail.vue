<template>
  <div class="regist">
    <h1 class="underline">SSAFY 도서 정보</h1>
    <div class="regist_form">
      <label for="isbn">도서번호</label>
      <div class="view">{{ book.isbn }}</div>
      <label for="title">도서명</label>
      <div class="view">{{ book.title }}</div>
      <label for="author">저자</label>
      <div class="view">{{ book.author }}</div>
      <label for="price">가격</label>
      <div class="view">{{ book.price }}원</div>
      <label for="content">설명</label>
      <div class="view">{{ book.content }}</div>
      <div style="padding-top: 15px">
        <button @click="modifyBook" class="btn">수정</button>
        <button @click="deleteBook" class="btn">삭제</button>
        <router-link to="/list" class="btn">목록</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      book: {},
    };
  },
  methods: {
    getBook() {
      const params = new URL(document.location).searchParams;
      axios
        .get("http://localhost/ws/book/search/" + params.get("isbn"))
        .then((response) => {
          this.book = response.data;
        });
    },
    deleteBook() {
      axios
        .delete("http://localhost/ws/book/delete/" + this.book.isbn)
        .then(() => {
          alert("삭제 성공!");
          this.$router.push("/book/list");
        });
    },
    modifyBook() {
      this.$router.push("/book/modify?isbn=" + this.book.isbn);
    },
  },
  created() {
    this.getBook();
  },
};
</script>

<style></style>
