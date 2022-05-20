import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import houseStore from "@/store/modules/houseStore.js";
import accountStore from "@/store/modules/accountStore.js";
import noticeStore from "@/store/modules/noticeStore.js";
import qnaStore from "@/store/modules/qnaStore.js";

export default new Vuex.Store({
  modules: { houseStore, accountStore, noticeStore, qnaStore },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});
