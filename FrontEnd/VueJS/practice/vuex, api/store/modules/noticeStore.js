import {
  listNotice,
  writeNotice,
  getNotice,
  deleteNotice,
} from "@/api/notice.js";

const noticeStore = {
  namespaced: true,
  state: {
    items: [],
    notice: {},
  },
  mutations: {
    SET_NOTICE_LIST: (state, noticeList) => {
      state.items = noticeList;
    },
    SET_NOTICE: (state, item) => {
      state.notice = item;
    },
    DELETE_NOTICE: () => {},
    WRITE_NOTICE: () => {},
  },
  actions: {
    getNoticeList: ({ commit }) => {
      listNotice(
        ({ data }) => {
          commit("SET_NOTICE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getNotice: ({ commit }, boardId) => {
      getNotice(
        boardId,
        (response) => {
          commit("SET_NOTICE", response.data);
        },
        (error) => {
          console.log("조회 중 에러발생!!", error);
        }
      );
    },
    deleteNotice: ({ commit }, boardId) => {
      deleteNotice(
        boardId,
        () => {
          commit("DELETE_NOTICE");
        },
        () => {}
      );
    },
    writeNotice: ({ commit }, article) => {
      writeNotice(
        article,
        () => {
          commit("WRITE_NOTICE");
        },
        () => {}
      );
    },
  },
};
export default noticeStore;
