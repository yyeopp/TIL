<template>
  <div
    class="md-layout-item md-size-40 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
  >
    <div style="visibility:hidden; margin:150px"></div>
    <login-card header-color="green">
      <h4 slot="title" class="card-title">Login</h4>

      <p slot="description" class="description">Login</p>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>face</md-icon>
        <label>User Id...</label>
        <md-input v-model="user.id"></md-input>
      </md-field>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>lock_outline</md-icon>
        <label>Password...</label>
        <md-input
          type="password"
          v-model="user.password"
          @keyup.enter="confirm"
        ></md-input>
      </md-field>
      <md-button
        @click.prevent="findPwd"
        slot="footer"
        class="md-simple md-danger md-lg"
      >
        Forgot password?
      </md-button>
      <md-button
        @click.prevent="goRegister"
        slot="footer"
        class="md-simple md-success md-lg"
      >
        Create Account
      </md-button>
    </login-card>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import { mapState, mapActions } from "vuex";

const accountStore = "accountStore";

export default {
  components: {
    LoginCard,
  },
  bodyClass: "login-page",
  data() {
    return {
      user: {
        id: null,
        password: null,
      },
      id: null,
    };
  },
  computed: {
    ...mapState(accountStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(accountStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "index" });
      } else {
        alert("아이디 또는 비밀번호를 확인해주세요.");
      }
    },
    movePage() {
      this.$router.push({ name: "login" });
    },
    goRegister() {
      this.$router.push({ name: "register" });
    },
    findPwd() {
      this.$router.push({ name: "password" });
    },
  },
};
</script>

<style lang="css"></style>
