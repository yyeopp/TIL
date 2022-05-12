export default {
  template: "#book-view-tmpl",
  data() {
    return {
      book: {},
    };
  },
  methods: {
    getBook() {
      const params = new URL(document.location).searchParams;
      axios.get("http://localhost/ws/book/search/" + params.get("isbn")).then((response) => {
        this.book = response.data;
      });
    },
    deleteBook() {
      axios.delete("http://localhost/ws/book/delete/" + this.book.isbn).then((response) => {
        alert("삭제 성공!");
        this.$router.push("/list");
      });
    },
    modifyBook() {
      this.$router.push("/modify?isbn=" + this.book.isbn);
    },
  },
  created() {
    this.getBook();
  },
};
