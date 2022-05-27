<template>
  <div class="share-kakao">
    <a id="create-kakao-link-btn" @click="sendkakao">
      <img
        style="  width: 40px; height: 40px;"
        src="@/assets/img/kakao.jpeg"
        alt="카카오링크 보내기 버튼"
      />
    </a>
  </div>
</template>
<script>
export default {
  props: {
    house: {
      type: Object,
    },
  },
  methods: {
    sendkakao: function() {
      Kakao.Link.sendDefault({
        objectType: "location",
        address: `${this.house.gugunname} ${this.house.dong} ${this.house.jibun}`,
        addressTitle: this.house.apartmentName,
        content: {
          title: `해피하우스 ${this.house.apartmentName}`,
          description: `최근 매매가 ${this.house.recentPrice}`,
          imageUrl: "https://ifh.cc/g/vOCWa4.jpg",
          link: {
            mobileWebUrl: "http://localhost:8080/",
            webUrl: "http://localhost:8080/",
          },
        },
        social: {
          likeCount: this.house.bookMarkHit,
        },
        buttons: [
          {
            title: "해피하우스",
            link: {
              mobileWebUrl: "http://localhost:8080/",
              webUrl: "http://localhost:8080/",
            },
          },
        ],
      });
    },
  },
};
</script>

<style>
.share-kakao {
  float: right;
  margin-top: 4px;
}
</style>
