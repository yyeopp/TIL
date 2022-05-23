<template>
  <div class="container">
    <div class="md-layout">
      <div class="md-layout-item md-size-50 mx-auto">
        <div class="profile">
          <div class="avatar">
            <img
              :src="img"
              alt="Circle Image"
              class="img-raised rounded-circle img-fluid"
            />
          </div>
          <div class="name">
            <h3 class="title" style="color:black">
              {{ checkUserInfo.id }}
            </h3>
          </div>
        </div>
      </div>
    </div>
    <div class="description text-center" v-if="viewMode">
      <md-field class="md-form-group">
        <md-icon>person</md-icon>
        <md-input disabled :value="checkUserInfo.name"></md-input>
      </md-field>
      <md-field class="md-form-group">
        <md-icon>email</md-icon>
        <md-input disabled :value="checkUserInfo.email"></md-input>
      </md-field>
      <md-field class="md-form-group">
        <md-icon>calendar_month</md-icon>
        <md-input disabled :value="checkUserInfo.age"></md-input>
      </md-field>
      <md-button
        @click.prevent="editMode"
        class="md-just-icon md-simple md-dribbble"
        ><md-icon>mode_edit</md-icon></md-button
      >
      <md-button
        @click.prevent="deleteAcc"
        class="md-just-icon md-simple md-twitter"
        ><md-icon>delete</md-icon></md-button
      >
    </div>
    <div class="description text-center" v-else>
      <md-field class="md-form-group">
        <md-icon>vpn_key</md-icon>
        <md-input type="password" v-model="password"></md-input>
      </md-field>
      <md-field class="md-form-group">
        <md-icon>person</md-icon>
        <md-input v-model="name"></md-input>
      </md-field>
      <md-field class="md-form-group">
        <md-icon>email</md-icon>
        <md-input v-model="email"></md-input>
      </md-field>
      <md-field class="md-form-group">
        <md-icon>calendar_month</md-icon>
        <md-input v-model="age"></md-input>
      </md-field>
      <md-button
        @click.prevent="updateProfile"
        class="md-just-icon md-simple md-dribbble"
        ><md-icon>check_circle</md-icon></md-button
      >
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const accountStore = "accountStore";

export default {
  bodyClass: "profile-page",
  data() {
    return {
      viewMode: true,
      password: this.checkUserInfo.password,
      name: this.checkUserInfo.name,
      email: this.checkUserInfo.email,
      age: this.checkUserInfo.age,
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
    img: {
      type: String,
      default: require("@/assets/img/anonymous.png"),
    },
    checkUserInfo: {
      type: Object,
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  methods: {
    ...mapActions(accountStore, ["updateMember"]),
    editMode() {
      this.viewMode = false;
    },
    updateProfile() {
      try {
        this.updateMember({
          id: this.checkUserInfo.id,
          password: this.password,
          name: this.name,
          email: this.email,
          age: this.age,
        });
        alert("회원 정보를 수정했습니다.");
      } catch {
        alert("오류가 발생했습니다.");
      }
      this.$router.go();
    },
    deleteAcc() {
      this.$emit("deleteAccount");
    },
  },
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}

.profile-tabs::v-deep {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    img {
      margin-bottom: 2.142rem;
    }
  }
}
</style>
