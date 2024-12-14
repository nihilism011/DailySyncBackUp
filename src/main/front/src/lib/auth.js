import { useRouter } from 'vue-router'
import axiosInstance from '@/plugins/axios'
export const logout = () => {
  localStorage.removeItem('jwtToken')
  sessionStorage.removeItem('jwtToken')
  const router = useRouter()
  router.push('/login')
}
export const refreshToken = async (token) => {
  console.log('토큰 -> ', token)
  const url = 'token/refresh'
  const res = await axiosInstance.post(url, { token: token })
  console.log('새로운 토큰 -> ', res)
}
