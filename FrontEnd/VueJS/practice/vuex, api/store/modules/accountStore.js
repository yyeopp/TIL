import jwt_decode from "jwt-decode";
import {
  login,
  findById,
  updateAccount,
  deleteAccount,
  idCheck,
  createAccount,
  findPassword,
} from "@/api/account.js";

const accountStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    idValid: false,
    pwdStatus: 0,
  },
  getters: {
    checkUserInfo: function(state) {
      return state.userInfo;
    },
    checkPwdStatus(state) {
      return state.pwdStatus;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    UPDATE_ACCOUNT: () => {},
    DELETE_ACCOUNT: () => {},
    ID_CHECK: (state, judge) => {
      state.idValid = judge;
    },
    CREATE_ACCOUNT: () => {},
    FIND_PASSWORD: (state, code) => {
      state.pwdStatus = code;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let accessToken = response.data["access-token"];
            let refreshToken = response.data["refresh-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);

      findById(
        decode_token,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
            if (data.accessToken) {
              sessionStorage.setItem("access-token", data.accessToken);
              sessionStorage.setItem("refresh-token", data.refreshToken);
            }
          } else {
            commit("SET_USER_INFO", null);
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateMember({ commit }, user) {
      updateAccount(
        user,
        () => {
          commit("UPDATE_ACCOUNT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteMember({ commit }, id) {
      deleteAccount(
        id,
        () => {
          commit("DELETE_ACCOUNT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    checkId({ commit }, id) {
      idCheck(
        id,
        (response) => {
          if (response.data == 0) {
            commit("ID_CHECK", true);
          } else {
            commit("ID_CHECK", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    registerMember({ commit }, user) {
      createAccount(
        user,
        () => {
          commit("CREATE_ACCOUNT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async findPwd({ commit }, user) {
      await findPassword(
        user,
        (response) => {
          commit("FIND_PASSWORD", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default accountStore;
