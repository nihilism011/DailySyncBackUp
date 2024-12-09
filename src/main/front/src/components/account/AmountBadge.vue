<template>
  <div class="amount-badge" :style="badgeStyle">
    <div :style="badgeTypeStyle">{{ amountType }}</div>
    <div :style="badgeMoneyStyle">{{ money }}</div>
  </div>
</template>

<script>
export default {
  props: {
    amount: {
      type: Number,
      required: true,
    },
    size: {
      type: String,
      default: 'medium',
      validator(value) {
        return ['small', 'medium', 'large'].includes(value)
      },
    },
  },
  computed: {
    money() {
      let won = new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW',
      })
      return won.format(Math.abs(this.amount))
    },
    amountType() {
      return this.amount > 0 ? '수입' : '지출'
    },
    badgeStyle() {
      return this.getBadgeSizeStyles(this.size)
    },
    badgeTypeStyle() {
      return this.getTypeStyle(this.amount)
    },
    badgeMoneyStyle() {
      return this.getMoneyStyle()
    },
  },
  methods: {
    // 배지의 기본 크기 스타일만 조정
    getBadgeSizeStyles(size) {
      const sizeStyles = {
        small: {
          width: '120px',
          height: '20px',
          fontSize: 10,
          lineHeight: '20px',
          borderRadius: '8px',
        },
        medium: {
          width: '200px',
          height: '50px',
          fontSize: 16,
          lineHeight: '50px',
          borderRadius: '12px',
        },
        large: {
          width: '250px',
          height: '60px',
          fontSize: 18,
          lineHeight: '60px',
          borderRadius: '16px',
        },
      }

      return sizeStyles[size] || sizeStyles.medium
    },

    // 타입별 색상 설정
    getTypeStyle(amount) {
      const color = amount > 0 ? '#E85167' : '#515EE8'
      return {
        backgroundColor: color,
        color: 'white',
        width: '25%', // 배지에 맞게 너비를 100%로 설정
        height: '100%', // 배지에 맞게 높이를 100%로 설정
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        fontSize: `${this.getFontSizeRatio()}px`, // 비율로 폰트 크기 조정
        borderRadius: 'inherit', // 배지의 borderRadius 그대로 사용
      }
    },

    // 금액 스타일 비율로 조정
    getMoneyStyle() {
      return {
        width: '60%', // 배지에 맞게 너비를 100%로 설정
        height: '100%', // 배지에 맞게 높이를 100%로 설정
        display: 'flex',
        justifyContent: 'start',
        alignItems: 'center',
        paddingLeft: '20px',
        fontSize: `${this.getFontSizeRatio() * 1.2}px`, // 조금 더 큰 폰트 크기
        borderRadius: 'inherit', // 배지의 borderRadius 그대로 사용
      }
    },

    // 사이즈에 따라 폰트 크기 비율을 반환
    getFontSizeRatio() {
      switch (this.size) {
        case 'small':
          return 10
        case 'medium':
          return 16
        case 'large':
          return 18
        default:
          return 16
      }
    },
  },
}
</script>

<style lang="scss">
.amount-badge {
  display: flex;
  align-content: center;
  justify-items: start;
}
</style>
