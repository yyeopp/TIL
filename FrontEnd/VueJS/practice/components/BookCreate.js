export default {
  template: "#book-create-tmpl",
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
              this.$router.push("/list");
            })
            .catch((error) => {
              console.log(error);
            });
        });
    },
    moveList() {
      this.$router.push("/list");
    },
  },
};
