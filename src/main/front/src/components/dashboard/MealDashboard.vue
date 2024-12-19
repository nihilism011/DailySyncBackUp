<template>
  <div class="dash-wrap">
    <div class="dash-item-wrap">
      <template v-if="mealList != null || mealList != undefined">
        <div class="dash-item" v-for="item in mealList">
          <p class="dash-icon" :class="item.icon">{{ item.icon }}</p>
          <p class="tit">{{ item.foodName }}</p>
          <p class="category">{{ this.mealCategory[item.category].name }}</p>
          <p class="kcal">{{ item.kcalories }}</p>
        </div>
      </template>
      <template v-else>
        <div class="dash-item none">일정이없어요</div>
      </template>
    </div>
  </div>
</template>

<script>
import { mealCategory } from '@/constants/mealCategory'

export default {
  props: {
    selectedDate: String,
  },
  watch: {
    selectedDate() {
      this.fnGetList()
    },
  },
  data() {
    return {
      mealList: [],
      mealCategory,
    }
  },
  methods: {
    async fnGetList() {
      const day = this.selectedDate.split('-')
      const { data } = await this.$axios.get(`meal/mealList/${day[0]}/${day[1]}`)
      this.mealList = data.mealList[this.selectedDate]
      console.log(this.mealList)
    },
  },
  mounted() {
    this.fnGetList()
  },
}
</script>

<style lang="scss" scoped>
.dash {
  &-item {
    display: flex;
    align-items: center;
    min-height: 60px;
    padding: 10px 5px;
    &-wrap {
      overflow: hidden;
    }
  }
}
</style>
