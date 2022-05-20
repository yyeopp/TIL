import { apiInstance } from "./index.js";

const api = apiInstance();

function listQuestion(success, fail) {
  api
    .get(`/qna/list`)
    .then(success)
    .catch(fail);
}

function writeAnswer(answer, success, fail) {
  api
    .post(`/qna/write/comment`, JSON.stringify(answer))
    .then(success)
    .catch(fail);
}

function writeQuestion(qustion, success, fail) {
  api
    .post(`/qna/write`, JSON.stringify(qustion))
    .then(success)
    .catch(fail);
}
export { listQuestion, writeAnswer, writeQuestion };
