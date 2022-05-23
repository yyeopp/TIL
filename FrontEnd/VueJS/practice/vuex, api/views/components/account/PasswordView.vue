<template>
  <div
    class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
  >
    <login-card header-color="green">
      <h4 slot="title" class="card-title">Find Password</h4>

      <p slot="description" class="description">가입시의 정보를 입력하세요</p>
      <md-field slot="inputs">
        <md-icon>face</md-icon>
        <label>ID...</label>
        <md-input v-model="id"></md-input>
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
        @click.prevent="submitPwd"
        slot="footer"
        class="md-simple md-success md-lg"
      >
        Submit
      </md-button>
    </login-card>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import { mapActions, mapMutations, mapGetters } from "vuex";

const accountStore = "accountStore";

export default {
  components: {
    LoginCard,
  },
  bodyClass: "login-page",
  data() {
    return {
      id: "",
      name: "",
      email: "",
      age: null,
    };
  },
  computed: {
    ...mapGetters(accountStore, ["checkPwdStatus"]),
  },
  created() {
    this.FIND_PASSWORD(0);
  },
  methods: {
    ...mapActions(accountStore, ["findPwd"]),
    ...mapMutations(accountStore, ["FIND_PASSWORD"]),

    confirmForm() {
      if (!this.id || !this.email || !this.name || !this.age) {
        return false;
      }
      return true;
    },
    submitPwd() {
      if (!this.confirmForm()) {
        alert("모든 정보를 입력해주세요.");
        return;
      }
      if (
        confirm(
          `입력된 정보가 정확한가요?
등록하신 이메일로 비밀번호를 보내드립니다.`
        )
      ) {
        try {
          this.findPwd({
            id: this.id,
            name: this.name,
            email: this.email,
            age: this.age,
          });
        } catch {
          alert("예기치 않은 오류가 발생했습니다.");
        }
      }
    },
  },
  mounted() {
    this.$store.watch(
      () => this.checkPwdStatus,
      (n) => {
        if (n == 1) {
          alert("이메일이 발송되었습니다.");
          this.$router.push("/account/login");
        } else if (n == 2) {
          alert("일치하는 계정이 없습니다.");
        } else {
          alert("서버 오류가 발생했습니다.");
          this.$router.go();
        }
      }
    );
  },
};
</script>

<style lang="css"></style>
