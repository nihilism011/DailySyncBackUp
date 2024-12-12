<template>
  <div>
    <label>아이디<input type="text" v-model="loginId" /></label>
    <label>비번<input type="password" v-model="loginPassword" /></label>
    <button @click="login">로그인</button>
    <button @click="goToSignup">회원가입 페이지 이동</button>
  </div>
</template>
<script>
import { useRouter } from 'vue-router'
export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      loginId: '',
      loginPassword: '',
    }
  },
  methods: {
    login() {
      const userName = this.loginId
      const password = this.loginPassword
      console.log(userName)
      console.log(password)
    },
    goToSignup() {
      this.router.push('/signup')
    },
  },
}
</script>
<style lang="scss" scoped></style>
