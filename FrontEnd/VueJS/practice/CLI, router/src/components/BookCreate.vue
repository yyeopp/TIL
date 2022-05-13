<template>
  <div class="regist">
    <h1 class="underline">SSAFY 도서 등록</h1>
    <div class="regist_form">
      <label for="isbn">도서번호</label>
      <input type="text" v-model="isbn" id="isbn" name="isbn" /><br />
      <label for="title">도서명</label>
      <input type="text" v-model="title" id="title" name="title" /><br />
      <label for="author">저자</label>
      <input type="text" v-model="author" id="author" name="author" /><br />
      <label for="price">가격</label>
      <input type="number" v-model="price" id="price" name="price" /><br />
      <label for="content">설명</label>
      <br />
      <textarea
        id="content"
        v-model="content"
        name="content"
        cols="35"
        rows="5"
      ></textarea
      ><br />
      <button @click="regist">등록</button>
      <button @click="moveList">목록</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      isbn: "",
      title: "",
      author: "",
      price: 0,
      content: "",
    };
  },
  methods: {
    regist() {
      let book = {
        isbn: this.isbn,
        title: this.title,
        author: this.author,
        price: this.price,
        content: this.content,
      };

      if (
        book.isbn == 0 ||
        book.title == 0 ||
        book.author == 0 ||
        book.price == 0 ||
        book.content == 0
      ) {
        alert("모든 정보를 입력하세요");
        return;
      }

      axios
        .get("http://localhost/ws/book/search/" + this.isbn)
        .then((response) => {
          if (response.status == 200) {
            alert("이미 등록된 책입니다.");
            return;
          }
        })
        .then(() => {
          axios
            .post("http://localhost/ws/book/create", {
              isbn: this.isbn,
              title: this.title,
              author: this.author,
              price: this.price,
              content: this.content,
            })
            .then((response) => {
              console.log(response);
              alert("등록 성공!");
              this.$router.push("/book/list");
            })
            .catch((error) => {
              console.log(error);
            });
        });
    },
    moveList() {
      this.$router.push("/book/list");
    },
  },
};
</script>

<style></style>
