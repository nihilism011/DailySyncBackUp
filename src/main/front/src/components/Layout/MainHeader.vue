<template>
  <header id="header">
    <RouterLink to="/" class="logo"
      ><img alt="DailySync" src="@/assets/images/h_logo.png"
    /></RouterLink>
    <nav>
      <template v-if="isLogin">
        <RouterLink to="/">메인</RouterLink>
        <RouterLink to="/2">메인 4x1</RouterLink>
        <RouterLink to="/todo">Todo</RouterLink>
        <RouterLink to="/schedule">일정</RouterLink>
        <RouterLink to="/account">가계부</RouterLink>
        <RouterLink to="/meal">식단</RouterLink>
        <button @click="handleLogout">로그아웃</button>
        <button><RouterLink to="/userInfo">회원정보</RouterLink></button>
      </template>
      <template v-if="!isLogin">
        <RouterLink to="/login">User</RouterLink>
        <RouterLink to="/signup">회원가입?</RouterLink>
      </template>
    </nav>
  </header>
</template>
<script>
import { RouterLink, useRouter } from 'vue-router'
import { useRefreshStore } from '@/stores/refreshStore'
import { logout } from '@/lib/auth'
export default {
  components: { RouterLink },
  setup() {
    const router = useRouter()
    const refreshStore = useRefreshStore()
    return { router, refreshStore }
  },
  data() {
    return {
      isLogin: false,
    }
  },
  watch: {
    'refreshStore.refreshState': 'checkLoginStatus',
  },
  methods: {
    handleLogout() {
      if (!confirm('로그아웃 하시겠습니까?')) {
        return
      }
      logout()
      this.refreshStore.setRefresh()
      this.router.push('/login')
    },
    checkLoginStatus() {
      const token = localStorage.getItem('jwtToken') || sessionStorage.getItem('jwtToken')
      this.isLogin = !!token
    },
  },
  mounted() {
    this.checkLoginStatus()
  },
}
</script>
<style lang="scss" scoped>
header {
  position: fixed;
  height: 90px;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  gap: 40px;
  top: 0;
  z-index: 10;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(4px);
  box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.1);
  .logo {
    display: block;
  }
  nav {
    display: flex;
    gap: 0 20px;
    a,
    button {
      &.router-link-active {
        font-weight: bold;
        color: var(--color-red);
      }
    }
  }
}
</style>
