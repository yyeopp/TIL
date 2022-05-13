import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/book",
    name: "book",
    redirect: "/book/list",
    component: () => import("@/views/BookView.vue"),
    children: [
      {
        path: "create",
        name: "create",
        component: () => import("@/components/BookCreate.vue"),
      },
      {
        path: "list",
        name: "list",
        component: () => import("@/components/BookList.vue"),
      },
      {
        path: "modify",
        name: "modify",
        component: () => import("@/components/BookModify.vue"),
      },
      {
        path: "delete",
        name: "delete",
        component: () => import("@/components/BookDelete.vue"),
      },
      {
        path: "detail",
        name: "detail",
        component: () => import("@/components/BookDetail.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
