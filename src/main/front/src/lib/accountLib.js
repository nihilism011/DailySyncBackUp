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

export const isValid = (value, fieldName) => {
  if (fieldName === 'amount') {
    // 금액 유효성 검사
    if (!value || value <= 0) {
      alert('금액을 올바르게 입력하세요.')
      return false
    }
  } else if (!value || value.trim() === '') {
    // 다른 항목들에 대한 유효성 검사
    switch (fieldName) {
      case 'title':
        alert('내역을 입력하세요.')
        break
      case 'description':
        alert('메모를 입력하세요.')
        break
      default:
        alert(`${fieldName}을 입력하세요.`)
        break
    }
    return false
  }
  return true
}
