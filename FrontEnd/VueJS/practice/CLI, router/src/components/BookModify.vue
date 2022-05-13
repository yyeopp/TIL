<template>
  <div class="regist">
    <h1 class="underline">SSAFY 도서 수정</h1>
    <div class="regist_form">
      <label for="isbn">도서번호</label>
      <input type="text" id="isbn" name="isbn" v-model="book.isbn" /><br />
      <label for="title">도서명</label>
      <input type="text" id="title" name="title" v-model="book.title" /><br />
      <label for="author">저자</label>
      <input
        type="text"
        id="author"
        name="author"
        v-model="book.author"
      /><br />
      <label for="price">가격</label>
      <input type="number" id="price" name="price" v-model="book.price" /><br />
      <label for="content">설명</label>
      <br />
      <textarea
        id="content"
        name="content"
        cols="35"
        rows="5"
        v-model="book.content"
      ></textarea
      ><br />
      <button v-on:click="modifyBook">수정</button>
      <button v-on:click="moveList">목록</button>
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
    modifyBook() {
      axios
        .put("http://localhost/ws/book/modify", {
          isbn: this.book.isbn,
          title: this.book.title,
          author: this.book.author,
          price: this.book.price,
          content: this.book.content,
        })
        .then(() => {
          alert("수정 완료!");
          this.$router.push("/book/list");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    moveList() {
      this.$router.push("/book/list");
    },
  },
  created() {
    this.getBook();
  },
};
</script>

<style></style>
