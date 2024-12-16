<template>
  <div class="user-wrap">
    <div class="user-container">
      <h2 class="logo">DailySync로고</h2>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="email">이메일</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="text" id="email" v-model="email" @input="removeWhitespace('email')" />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="password">비밀번호</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input
              type="password"
              id="password"
              v-model="loginPassword"
              @input="removeWhitespace('loginPassword')"
            />
          </div>
        </div>
      </div>
      <button @click="login" class="btn-default">로그인</button>
    </div>
    <div class="user-bot-wrap">
      <div class="ip-chk-txt">
        <input type="checkbox" v-model="loginFix" id="auto" />
        <label for="auto">자동 로그인</label>
      </div>
      <button @click="goToSignup">회원가입 페이지 이동</button>
    </div>
  </div>
</template>
<script>
import { useRouter } from 'vue-router'
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  setup() {
    localStorage.removeItem('jwtToken')
    sessionStorage.removeItem('jwtToken')
    const router = useRouter()
    const refreshStore = useRefreshStore()
    return { router, refreshStore }
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
      if (res.status) {
        if (this.loginFix) {
          localStorage.setItem('jwtToken', res.data)
        } else {
          sessionStorage.setItem('jwtToken', res.data)
        }
        this.refreshStore.setRefresh()
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
