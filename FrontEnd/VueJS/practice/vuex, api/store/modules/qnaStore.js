import { listQuestion, writeAnswer, writeQuestion } from "@/api/qna.js";

const qnaStore = {
  namespaced: true,
  state: {
    items: [],
  },
  mutations: {
    SET_QUESTION_LIST: (state, qnaList) => {
      state.items = qnaList;
    },
    WRITE_QUESTION: () => {},
    WRITE_ANSWER: () => {},
  },
  actions: {
    getQnaList: ({ commit }) => {
      listQuestion(
        ({ data }) => {
          commit("SET_QUESTION_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    writeQuestion: ({ commit }) => {
      writeQuestion(
        question,
        () => {
          commit("WRITE_QUESTION");
        },
        () => {}
      );
    },
    writeAns: ({ commit }, answer) => {
      writeAnswer(
        answer,
        () => {
          commit("WRITE_ANSWER");
        },
        () => {}
      );
    },
  },
};
export default qnaStore;
