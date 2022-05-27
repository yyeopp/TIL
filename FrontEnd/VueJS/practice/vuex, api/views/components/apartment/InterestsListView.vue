<template>
  <div>
    <deal-list-view v-if="house"></deal-list-view>
    <div class="bookmark-info" style="overflow:scroll" v-else>
      <div
        class="bookmark-itmes"
        v-for="(bookmark, index) in bookMarks"
        :key="index"
      >
        <div
          class="item"
          @click="
            [
              detailHouse(bookmark),
              SET_MAP_CENTER({ lat: bookmark.lat, lng: bookmark.lng }),
            ]
          "
        >
          <div style="font-weight: bold; margin-bottom: 5px; margin-top: 2px">
            {{ bookmark.apartmentName }}
          </div>
          <div style="margin-bottom: 2px;">
            <md-icon>shopping_cart</md-icon> 매매
            {{ bookmark.minPrice | money }} ~
            {{ bookmark.maxPrice | money }}
          </div>
          <div>
            <p>
              <md-icon>place</md-icon> {{ bookmark.sidoname }}
              {{ bookmark.gugunname }}
              {{ bookmark.dongname }}
              {{ bookmark.jibun }}
            </p>
          </div>
          <hr />
        </div>
      </div>

      <div v-if="bookMarks.length == 0" class="none-bookmark">
        <i class="material-icons" style="font-size : 50px">error_outline</i>
        <h4>관심 매물이 없습니다.</h4>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import DealListView from "@/views/components/apartment/DealListView.vue";

const houseStore = "houseStore";
const accountStore = "accountStore";

export default {
  components: { DealListView },
  data() {
    return {};
  },
  computed: {
    ...mapState(houseStore, ["house", "houseDeals", "bookMarks"]),
    ...mapState(accountStore, ["userInfo"]),
  },
  created() {
    this.CLEAR_DETAIL_HOUSE();
  },
  methods: {
    ...mapActions(houseStore, ["setBookMark", "detailHouse"]),
    ...mapMutations(houseStore, ["CLEAR_DETAIL_HOUSE", "SET_MAP_CENTER"]),

    AddBookMark() {
      const params = {
        memberId: this.userInfo.id,
        aptCode: this.house.aptCode,
      };
      this.setBookMark(params);
    },
  },
  filters: {
    money: function(value) {
      if (value.length < 6) return value;
      let tenMillion = value.slice(-5);
      let hundredMillion = value.slice(0, value.length - 5);
      return hundredMillion + "억" + tenMillion;
    },
  },
};
</script>

<style scope>
.bookmark-info {
  height: 73vh;
}

.bookmark-info > div {
  padding-left: 18px;
  padding-right: 12px;
}

.bookmark-itmes :hover {
  background-color: #e4eef8;
  cursor: pointer;
}

.item {
  padding-top: 5px;
}

.none-bookmark {
  text-align: center;
  position: relative;
  top: 25vh;
}

hr {
  margin: 0;
}
</style>
