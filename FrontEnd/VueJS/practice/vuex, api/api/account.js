import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/member/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(decode_token, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");

  if (decode_token.exp < Date.now() / 1000 + 60) {
    api.defaults.headers["refresh-token"] = sessionStorage.getItem(
      "refresh-token"
    );
  }

  await api
    .get(`/member/info/${decode_token.userid}`)
    .then(success)
    .catch(fail);
}

function updateAccount(user, success, fail) {
  api
    .put(`/member/update`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function deleteAccount(id, success, fail) {
  api
    .delete(`/member/delete/${id}`)
    .then(success)
    .catch(fail);
}

function idCheck(id, success, fail) {
  api
    .get(`/member/idcheck/${id}`)
    .then(success)
    .catch(fail);
}

function createAccount(user, success, fail) {
  api
    .post("/member/regist", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findPassword(user, success, fail) {
  await api
    .post("/member/findpwd", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

export {
  login,
  findById,
  updateAccount,
  deleteAccount,
  idCheck,
  createAccount,
  findPassword,
};
