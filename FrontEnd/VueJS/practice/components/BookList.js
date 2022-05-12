export default {
  template: "#book-list-tmpl",
  data() {
    return {
      books: [],
    };
  },
  methods: {
    movePage: function () {
      this.$router.push("/create");
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
