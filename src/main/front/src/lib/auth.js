import axiosInstance from '@/plugins/axios'
import dayjs from 'dayjs'
export const logout = () => {
  localStorage.removeItem('jwtToken')
  sessionStorage.removeItem('jwtToken')
}
export const refreshToken = async (token) => {
  const payLoad = window.atob(token.split('.')[1])
  const payLoadObj = JSON.parse(payLoad)
  const iat = dayjs(payLoadObj.iat * 1000).format('YYYY-MM-DD')
  const today = dayjs().format('YYYY-MM-DD')
  if (iat != today) {
    const url = 'token/refresh'
    const res = await axiosInstance.post(url, { token: token })
    if (!res.status) {
      alert('세션이 만료되었습니다.')
      logout()
      return false
    } else {
      if (localStorage.getItem('jwtToken')) {
        localStorage.setItem('jwtToken', res.data)
      } else {
        sessionStorage.setItem('jwtToken', res.data)
      }
      return true
    }
  } else {
    return true
  }
}
