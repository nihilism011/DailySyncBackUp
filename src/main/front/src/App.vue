<script setup>
import { RouterLink, RouterView } from 'vue-router'
import MainHeader from './components/Layout/MainHeader.vue'
</script>

<template>
  <MainHeader />
  <div id="container">
    <RouterView />
  </div>
</template>

<style scoped lang="scss"></style>
