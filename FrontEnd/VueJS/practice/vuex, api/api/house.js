import { apiInstance } from "./index.js";

const api = apiInstance();

// ================================ RealDong===============================
function sidoList(success, fail) {
  api
    .get(`/legaldong/sido`)
    .then(success)
    .catch(fail);
}

function gugunList(params, success, fail) {
  api
    .get(`/legaldong/gugun/`, { params: params })
    .then(success)
    .catch(fail);
}

function mapCenter(gugunCode, success, fail) {
  api
    .get(`/legaldong/center/${gugunCode}`)
    .then(success)
    .catch(fail);
}

//========================= House ======================
//@PathVariable
function houseList(params, success, fail) {
  api
    .get(`/house/houselist/gugun`, { params: params })
    .then(success)
    .catch(fail);
}

function houseListRange(params, success, fail) {
  api
    .get(`/house/houselist`, { params: params })
    .then(success)
    .catch(fail);
}

function HouseDealInfo(aptCode, success, fail) {
  api
    .get(`/house/housedeal/${aptCode}`)
    .then(success)
    .catch(fail);
}

function HouseInfoExp(aptCode, success, fail) {
  api
    .get(`/house/housedeal/expanded/${aptCode}`)
    .then(success)
    .catch(fail);
}

function GugunAvgList(params, success, fail) {
  api
    .get(`/house/gugunavg`, { params: params })
    .then(success)
    .catch(fail);
}

function BookMarkList(userId, success, fail) {
  api
    .get(`/bookmark/list/${userId}`)
    .then(success)
    .catch(fail);
}

function addBookMark(params, success, fail) {
  api
    .post(`/bookmark/insert/${params.memberId}/${params.aptCode}`)
    .then(success)
    .catch(fail);
}

function removeBookMark({ memberId, aptCode }, success, fail) {
  api
    .delete(`/bookmark/delete/${memberId}/${aptCode}`)
    .then(success)
    .catch(fail);
}

function searchInfo(query, success, fail) {
  api
    .get("/house/search", {
      params: {
        keyword: query,
      },
    })
    .then(success)
    .catch(fail);
}

function houseDealChart(aptCode, success, fail) {
  api
    .get(`/house/housedeal/chart/${aptCode}`)
    .then(success)
    .catch(fail);
}

function searchHouseInfo(aptCode, success, fail) {
  api
    .get(`/house/search/${aptCode}`)
    .then(success)
    .catch(fail);
}

function getHouseTrend(sidoCode, success, fail) {
  api
    .get(`/house/trend/${sidoCode}`)
    .then(success)
    .catch(fail);
}

function getHouseTrendAll(success, fail) {
  api
    .get("/house/trend/all")
    .then(success)
    .catch(fail);
}

export {
  sidoList,
  mapCenter,
  gugunList,
  houseList,
  houseListRange,
  HouseDealInfo,
  BookMarkList,
  addBookMark,
  removeBookMark,
  HouseInfoExp,
  GugunAvgList,
  searchInfo,
  searchHouseInfo,
  houseDealChart,
  getHouseTrend,
  getHouseTrendAll,
};
