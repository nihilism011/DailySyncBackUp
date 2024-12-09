export const numToWon = (money) => {
  let won = new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW',
  })
  return won.format(Math.abs(money))
}
