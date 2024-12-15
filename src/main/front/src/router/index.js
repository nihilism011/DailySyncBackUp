import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SampleView from '@/views/SampleView.vue'
import TodoView from '@/views/TodoView.vue'
import AccountView from '@/views/AccountView.vue'
import MealView from '@/views/MealView.vue'
import ScheduleView from '@/views/ScheduleView.vue'
import SignupView from '@/views/SignupView.vue'
import LoginView from '@/views/LoginView.vue'
import LoginTestPage from '@/views/LoginTestPage.vue'
import { refreshToken } from '@/lib/auth'
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
      meta: { requiresAuth: true },
    },
    {
      path: '/account',
      name: 'account',
      component: AccountView,
      meta: { requiresAuth: true },
    },
    {
      path: '/meal',
      name: 'meal',
      component: MealView,
      meta: { requiresAuth: true },
    },
    {
      path: '/schedule',
      name: 'schedule',
      component: ScheduleView,
      meta: { requiresAuth: true },
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/testPage',
      name: 'testPage',
      component: LoginTestPage,
      meta: { requiresAuth: true },
    },
  ],
})
router.beforeEach(async (to, from, next) => {
  const localToken = localStorage.getItem('jwtToken')
  const sessionToken = sessionStorage.getItem('jwtToken')
  const token = localToken ?? sessionToken // JWT 토큰 가져오기
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    // 인증이 필요한 페이지에 접근할 때, 토큰이 없으면 로그인 페이지로 리다이렉트
    if (!token) {
      alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.')
      next({ name: 'login' })
    } else {
      const check = await refreshToken(token)
      if (check) {
        next()
      } else {
        next({ name: 'login' })
      }
    }
  } else {
    next() // 인증이 필요 없는 페이지는 그대로 진행
  }
})

export default router
