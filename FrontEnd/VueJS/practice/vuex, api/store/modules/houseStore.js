import {
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
} from "@/api/house.js";
import moment from "moment";

const houseStore = {
  namespaced: true,

  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    center: { lat: "", lng: "" },
    houses: [],
    house: {},
    houseDeals: [],
    bookMarks: [],
    hitRank: null,
    gugunAvgs: [],
    searchInfo: {},
    dealCharts: [],
    houseTrends: [],
    localNews: [],
  },

  getters: {
    isBookMark: function(state) {
      return state.bookMarks.find(
        (house) => house.aptCode === state.house.aptCode
      );
    },
    parsedNews: function(state) {
      let parsedNews = [];
      for (let i = 0; i < state.localNews.length; i++) {
        parsedNews.push({
          title: state.localNews[i].title.replace(/<[^>]*>?/gm, ""),
          description: state.localNews[i].description.replace(/<[^>]*>?/gm, ""),
          link: state.localNews[i].link,
          pubDate: moment(new Date(state.localNews[i].pubDate)).format(
            "YYYY.MM.DD hh:mm"
          ),
        });
      }
      return parsedNews;
    },
  },

  mutations: {
    //=============== regalDong
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.code, text: sido.name });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.code, text: gugun.name });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    SET_MAP_CENTER: (state, center) => {
      state.center = center;
    },
    SET_GUGUNAVG_LIST: (state, gugunAvg) => {
      state.gugunAvgs = gugunAvg;
    },

    //========================= house
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_HOUSEDEAL_LIST: (state, houseDeals) => {
      state.houseDeals = houseDeals;
    },
    CLEAR_DETAIL_HOUSE: (state) => {
      state.house = null;
    },
    SET_HITRANK: (state, hitRank) => {
      state.hitRank = hitRank;
    },
    SET_BOOKMARK_INFO: (state, bookMarkInfo) => {
      state.house.bookMarkHit = bookMarkInfo.bookmarkHit;
      state.house.bookMarkRank = bookMarkInfo.bookmarkRank;
    },
    SET_DEALCHART_LIST: (state, dealCharts) => {
      state.dealCharts = dealCharts;
    },
    //===================== bookmark
    SET_BOOKMARK_LIST: (state, bookMarks) => {
      state.bookMarks = bookMarks;
      if (state.bookMarks == "") {
        state.bookMarks = [];
      }
    },
    ADD_BOOKMARK: (state) => {
      state.bookMarks.push(state.house);
    },
    DELETE_BOOKMARK: (state) => {
      let idx = state.bookMarks.indexOf(state.house);
      state.bookMarks.splice(idx, 1);
    },
    SEARCH_INFO: (state, data) => {
      state.searchInfo = data;
    },
    SEARCH_HOUSE_INFO: (state, data) => {
      state.house = data;
    },
    SET_HOUSE_TRENDS: (state, data) => {
      state.houseTrends = data;
    },
    SET_LOCAL_NEWS: (state, data) => {
      state.localNews = data;
    },
  },

  actions: {
    //=============== regalDong
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        code: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setMapCenter: ({ commit }, gugunCode) => {
      mapCenter(
        gugunCode,
        ({ data }) => {
          commit("SET_MAP_CENTER", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    //========================= house
    getHouseList: ({ commit }, { gugunCode, filter }) => {
      let params = { ...gugunCode, ...filter };
      houseList(
        params,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseListRange: ({ commit }, { latlng, filter }) => {
      let params = { ...latlng, ...filter };
      houseListRange(
        params,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      HouseDealInfo(
        house.aptCode,
        (response) => {
          commit("SET_HOUSEDEAL_LIST", response.data.dealList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugunAvg: ({ commit }, { latlng, filter }) => {
      let params = { ...latlng, ...filter };
      GugunAvgList(
        params,
        (response) => {
          commit("SET_GUGUNAVG_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    houseInfoExp: ({ commit }, house) => {
      house.bookMarkHit = 0;
      commit("SET_DETAIL_HOUSE", house);
      HouseInfoExp(
        house.aptCode,
        (response) => {
          commit("SET_BOOKMARK_INFO", response.data.bookmarkInfo);
          commit("SET_HITRANK", response.data.hitRank);
          commit("SET_LOCAL_NEWS", response.data.news);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getBookMarks: ({ commit }, userId) => {
      BookMarkList(
        userId,
        (response) => {
          if (response.data != null) {
            commit("SET_BOOKMARK_LIST", response.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setBookMark: ({ commit }, params) => {
      addBookMark(
        params,
        () => {
          commit("ADD_BOOKMARK");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteBookMark: ({ commit }, { memberId, aptCode }) => {
      removeBookMark(
        { memberId, aptCode },
        () => {
          commit("DELETE_BOOKMARK");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    infoSearch: ({ commit }, keyword) => {
      searchInfo(keyword, (response) => {
        if (response) {
          commit("SEARCH_INFO", response.data);
        }
      });
    },
    houseSearch: ({ commit }, aptCode) => {
      searchHouseInfo(
        aptCode,
        (response) => {
          commit("SEARCH_HOUSE_INFO", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    gethouseDealChart: ({ commit }, aptCode) => {
      houseDealChart(
        aptCode,
        (response) => {
          commit("SET_DEALCHART_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseTrend: ({ commit }, sidoCode) => {
      getHouseTrend(
        sidoCode,
        (response) => {
          commit("SET_HOUSE_TRENDS", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseTrendAll: ({ commit }) => {
      getHouseTrendAll(
        (response) => {
          commit("SET_HOUSE_TRENDS", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseStore;
