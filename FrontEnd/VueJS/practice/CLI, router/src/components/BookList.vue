<template>
  <div>
    <h1 class="underline">도서 목록</h1>
    <div style="text-align: right">
      <button v-on:click="movePage">도서 등록</button>
    </div>
    <div v-if="books.length > 0">
      <table id="book-list">
        <colgroup>
          <col style="width: 5%" />
          <col style="width: 20%" />
          <col style="width: 40%" />
          <col style="width: 20%" />
          <col style="width: 15%" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>ISBN</th>
            <th>제목</th>
            <th>저자</th>
            <th>가격</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(book, index) in books" :key="book.isbn">
            <td>{{ index + 1 }}</td>
            <td>{{ book.isbn }}</td>
            <td>
              <router-link :to="'/book/detail?isbn=' + book.isbn">{{
                book.title
              }}</router-link>
            </td>
            <td>{{ book.author }}</td>
            <td>{{ book.price }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center">게시글이 없습니다.</div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return { books: [] };
  },
  methods: {
    movePage: function () {
      this.$router.push("/book/create");
    },
  },
  created() {
    axios
      .get("http://localhost/ws/book/list")
      .then((response) => {
        this.books = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
