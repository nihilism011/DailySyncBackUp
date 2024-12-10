import axiosInstance from '@/plugins/axios'

export const numToWon = (money) => {
  let won = new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW',
  })
  return won.format(Math.abs(money))
}

const REST_API = 'account/items'

export const deleteAccountItem = async (id) => {
  const url = `${REST_API}/${id}`
  const { data } = await axiosInstance.delete(url)
  return data
}

export const updateAccountItem = async (id, requestBody) => {
  const url = `${REST_API}/${id}`
  const { data } = await axiosInstance.patch(url, requestBody)
  return data
}

export const createAccountItem = async (requestBody) => {
  const url = REST_API
  const { data } = await axiosInstance.post(url, requestBody)
  return data
}
