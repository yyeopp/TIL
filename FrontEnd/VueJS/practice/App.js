import BookCreate from "./components/BookCreate.js";
import BookList from "./components/BookList.js";
import BookModify from "./components/BookModify.js";
import BookView from "./components/BookView.js";
import MainContent from "./components/MainContent.js";

Vue.use(VueRouter);
export default new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: MainContent,
    },
    {
      path: "/list",
      component: BookList,
    },
    {
      path: "/create",
      component: BookCreate,
    },
    {
      path: "/view",
      component: BookView,
    },
    {
      path: "/modify",
      component: BookModify,
    },
  ],
});
