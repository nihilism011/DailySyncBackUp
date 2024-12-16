<template>
  <header id="header">
    <RouterLink to="/" class="logo"
      ><img alt="DailySync" src="@/assets/images/h_logo.png"
    /></RouterLink>
    <nav>
      <template v-if="isLogin">
        <div class="gnb">
          <RouterLink to="/">메인</RouterLink>
          <RouterLink to="/2">메인 4x1</RouterLink>
          <RouterLink to="/todo">Todo</RouterLink>
          <RouterLink to="/schedule">일정</RouterLink>
          <RouterLink to="/account">가계부</RouterLink>
          <RouterLink to="/meal">식단</RouterLink>
        </div>
        <div class="user">
          <button @click="fade = !fade" title="유저정보" class="profile">유저정보</button>
          <div class="fade" v-if="fade">
            <button @click="handleLogout">로그아웃</button>
            <RouterLink to="/userInfo" @click="fade = !fade">회원정보</RouterLink>
          </div>
        </div>

      </template>
      <template v-if="!isLogin">
        <RouterLink to="/login">User</RouterLink>
        <RouterLink to="/signup">회원가입</RouterLink>
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
      fade: false,
    }
  },
  watch: {
    'refreshStore.refreshState': 'checkLoginStatus',
    $route: 'fadeClose',
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
    fadeClose() {
      this.fade = false
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
  padding: 10px 30px;
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
    flex-grow: 1;
    justify-content: space-between;
    align-items: center;
    a,
    button {
      &.router-link-active {
        font-weight: bold;
        color: var(--color-red);
      }
    }
    .gnb {
      display: flex;
      gap: 0 20px;
    }
    .user {
      position: relative;
      .profile {
        width: 40px;
        height: 40px;
        background: url('@/assets/images/nav_profile.png');
        font-size: 0;
        border: 0;
      }
      .fade {
        position: absolute;
        width: max-content;
        top: 100%;
        right: 0;
        display: flex;
        flex-direction: column;
        background-color: var(--base-white);
        padding: 10px 15px;
        box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
        border-radius: 10px;
        button,
        a {
          border: 0;
          font-size: 14px;
          font-weight: bold;
          padding: 5px;
          transition: all 0.2s;
          color: var(--color-contrasty2) !important;
          + button,
          + a {
            border-top: 1px solid var(--color-contrastyC);
            padding-top: 5px;
          }
          &:hover {
            color: var(--color-blue) !important;
          }
        }
      }
    }
  }
}
</style>
