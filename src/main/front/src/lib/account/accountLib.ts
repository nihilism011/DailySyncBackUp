export const formatCurrencyKRW = (money: number) => {
  let won = new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW',
  })
  return won.format(money)
}
