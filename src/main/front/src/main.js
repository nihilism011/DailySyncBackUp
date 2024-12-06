import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axiosInstance from './plugins/axios'
import App from './App.vue'
import router from './router'
import dayjs from 'dayjs'

const app = createApp(App)

app.use(createPinia())
app.use(router)
// Axios를 전역 속성으로 등록
app.config.globalProperties.$axios = axiosInstance
app.config.globalProperties.$dayjs = dayjs
app.mount('#app')
