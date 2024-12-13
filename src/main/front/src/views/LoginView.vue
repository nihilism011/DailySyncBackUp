<template>
  <div>
    <label>이메일<input type="text" v-model="email" @input="removeWhitespace('email')" /></label>
    <label
      >비번<input
        type="password"
        v-model="loginPassword"
        @input="removeWhitespace('loginPassword')"
    /></label>
    <label>자동 로그인<input type="checkbox" v-model="loginFix" /></label>
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
      loginFix: false,
      email: '',
      loginPassword: '',
    }
  },
  methods: {
    removeWhitespace(field) {
      this[field] = this[field].replace(/\s+/g, '')
    },
    async login() {
      if (this.email.trim() === '') {
        alert('이메일 입력 바람')
        return
      }
      if (this.loginPassword.trim() === '') {
        alert('비밀번호 입력 바람')
        return
      }
      const loginRequest = {
        email: this.email,
        password: this.loginPassword,
      }
      const url = 'login'
      const res = await this.$axios.post(url, loginRequest)
      console.log(res)
      if (res.status) {
        if (this.loginFix) {
          localStorage.setItem('jwtToken', res.data)
        } else {
          sessionStorage.setItem('jwtToken', res.data)
        }
        this.router.push('/')
      } else {
        alert(res.message)
      }
    },
    goToSignup() {
      this.router.push('/signup')
    },
  },
}
</script>
<style lang="scss" scoped></style>
