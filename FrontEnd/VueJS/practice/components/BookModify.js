export default {
  template: "#book-modify-tmpl",
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
          this.$router.push("/list");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    moveList() {
      this.$router.push("/list");
    },
  },
  created() {
    this.getBook();
  },
};
