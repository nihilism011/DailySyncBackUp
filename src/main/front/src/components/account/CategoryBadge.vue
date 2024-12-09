<template>
  <div class="category-badge" :style="badgeStyle">
    <span>{{ categoryName }}</span>
  </div>
</template>

<script>
import { categories } from '@/constants/accountCategory.js'
export default {
  props: {
    category: {
      type: String,
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
  data() {
    return {
      categories,
    }
  },
  computed: {
    categoryData() {
      return this.categories[this.category] || {}
    },
    badgeStyle() {
      const sizeStyles = this.getSizeStyles(this.size)
      return {
        backgroundColor: this.categoryData.color || '#000000',
        color: 'white',
        ...sizeStyles, // 크기 스타일을 동적으로 적용
      }
    },
    categoryName() {
      return this.categoryData.name || ''
    },
  },
  methods: {
    getSizeStyles(size) {
      switch (size) {
        case 'small':
          return {
            width: '40px',
            height: '20px',
            fontSize: '10px',
            lineHeight: '20px',
            borderRadius: '8px',
          }
        case 'medium':
          return {
            width: '80px',
            height: '50px',
            fontSize: '16px',
            lineHeight: '50px',
            borderRadius: '12px',
          }
        case 'large':
          return {
            width: '100px',
            height: '60px',
            fontSize: '18px',
            lineHeight: '60px',
            borderRadius: '16px',
          }
        default:
          return {
            width: '80px',
            height: '40px',
            fontSize: '12px',
            lineHeight: '40px',
            borderRadius: '8px',
          }
      }
    },
  },
}
</script>

<style lang="scss">
.category-badge {
  text-align: center;
  font-weight: bold;
}
</style>
