<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header id="header">
    <RouterLink to="/" class="logo"
      ><img alt="DailySync" src="@/assets/images/h_logo.png"
    /></RouterLink>
    <nav>
      <RouterLink to="/">메인</RouterLink>
      <RouterLink to="/todo">Todo</RouterLink>
      <RouterLink to="/schedule">일정</RouterLink>
      <RouterLink to="/account">가계부</RouterLink>
      <RouterLink to="/meal">식단</RouterLink>
      <RouterLink to="/sample">Sample</RouterLink>
      <RouterLink to="/login">User</RouterLink>
      <RouterLink to="/user">회원가입?</RouterLink>
    </nav>
  </header>

  <div id="container">
    <RouterView />
  </div>
</template>

<style scoped lang="scss">
header {
  position: fixed;
  height: 80px;
  padding: 10px;
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
    a {
      &.router-link-active {
        font-weight: bold;
        color: var(--color-red);
      }
    }
  }
}
</style>
