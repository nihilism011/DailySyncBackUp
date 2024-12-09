import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SampleView from '@/views/SampleView.vue'

import TodoView from '@/views/TodoView.vue'
import AccountView from '@/views/AccountView.vue'
import MealView from '@/views/MealView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/sample',
      name: 'sample',
      component: SampleView,
    },
    {
      path: '/todo',
      name: 'todo',
      component: TodoView,

    },
    {
      path: '/account',
      name: 'account',
      component: AccountView,
    },
    {
      path: '/meal',
      name: 'meal',
      component: MealView,
    },
  ],
})

export default router
