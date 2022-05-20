import { apiInstance } from "./index.js";

const api = apiInstance();

function listNotice(success, fail) {
  api
    .get(`/notice/list`)
    .then(success)
    .catch(fail);
}

function writeNotice(notice, success, fail) {
  api
    .post(`/notice/write`, JSON.stringify(notice))
    .then(success)
    .catch(fail);
}
function getNotice(boardId, success, fail) {
  api
    .get(`/notice/${boardId}`)
    .then(success)
    .catch(fail);
}
function deleteNotice(boardId, success, fail) {
  api
    .delete(`/notice/delete/${boardId}`)
    .then(success)
    .catch(fail);
}
export { listNotice, writeNotice, getNotice, deleteNotice };
