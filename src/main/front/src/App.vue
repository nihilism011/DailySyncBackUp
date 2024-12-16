<script setup>
import { RouterLink, RouterView, useRoute } from 'vue-router'
import MainHeader from './components/Layout/MainHeader.vue'

const route = useRoute()
</script>

<template>
  <MainHeader v-if="route.path !== '/login' && route.path !== '/signup'" />
  <div id="container">
    <RouterView />
  </div>
</template>

<style scoped lang="scss"></style>
