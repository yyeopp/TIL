<template>
  <div
    class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
  >
    <!-- <div style="visibility:hidden; margin:150px"></div> -->
    <login-card header-color="green">
      <h4 slot="title" class="card-title">Create Account</h4>

      <p slot="description" class="description">Create Account</p>
      <md-field :class="idClass" slot="inputs">
        <md-icon>face</md-icon>
        <label>{{ idMsg }}</label>
        <md-input v-model="id" @keyup="idCheck"></md-input>
      </md-field>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>lock_outline</md-icon>
        <label>Password...</label>
        <md-input type="password" v-model="password"></md-input>
      </md-field>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>person_outline</md-icon>
        <label>Name...</label>
        <md-input v-model="name"></md-input>
      </md-field>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>email</md-icon>
        <label>Email...</label>
        <md-input v-model="email"></md-input>
      </md-field>
      <md-field class="md-form-group" slot="inputs">
        <md-icon>calendar_month</md-icon>
        <label>Age...</label>
        <md-input v-model="age"></md-input>
      </md-field>
      <md-button
        @click.prevent="registerAcc"
        slot="footer"
        class="md-simple md-success md-lg"
      >
        Get Started
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
      id: "",
      password: "",
      name: "",
      email: "",
      age: null,
    };
  },
  computed: {
    ...mapState(accountStore, ["idValid"]),
    idClass() {
      if (this.idValid) {
        return "md-valid";
      } else {
        return "md-error";
      }
    },
    idMsg() {
      if (this.id == "") {
        return "ID...";
      } else if (this.id.length < 4) {
        return "4글자 이상 입력해주세요.";
      } else if (this.id.length >= 4 && !this.idValid) {
        return "사용할 수 없는 아이디입니다.";
      } else {
        return "사용 가능한 아이디입니다.";
      }
    },
  },
  methods: {
    ...mapActions(accountStore, ["checkId", "registerMember"]),
    idCheck() {
      if (this.id.length < 4) {
        return;
      } else {
        this.checkId(this.id);
      }
    },
    confirmForm() {
      if (
        !this.idValid ||
        !this.password ||
        !this.email ||
        !this.name ||
        !this.age
      ) {
        return false;
      }
      return true;
    },
    registerAcc() {
      if (!this.confirmForm()) {
        alert("모든 정보를 입력해주세요.");
        return;
      }
      if (confirm("입력된 정보로 가입하시겠습니까?")) {
        try {
          this.registerMember({
            id: this.id,
            password: this.password,
            name: this.name,
            email: this.email,
            age: this.age,
          });
          alert("가입 성공!!");
          this.$router.push("/");
        } catch {
          alert("회원 가입 중 오류가 발생했습니다.");
          this.$router.push("/account/login");
        }
      }
    },
  },
};
</script>

<style lang="css"></style>
