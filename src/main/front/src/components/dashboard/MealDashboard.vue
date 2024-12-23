<template>
  <div class="dash-wrap">
    <div class="dash-tit-box">
      <p class="tit"><span>오늘의 칼로리</span> <br />{{ kcalories }}Kcal</p>
    </div>
    <div class="dash-item-wrap">
      <template v-if="mealList != null || mealList != undefined">
        <div class="dash-item" v-for="item in mealList">
          <p class="dash-icon" :class="item.icon">{{ item.icon }}</p>
          <p class="tit">{{ item.foodName }}</p>
          <p class="category">{{ this.mealCategory[item.category].name }}</p>
          <p class="kcal">{{ item.kcalories }}Kcal</p>
        </div>
      </template>
      <template v-else>
        <div class="dash-item none"><span>등록한 식단이 없어요</span></div>
      </template>
    </div>
    <RouterLink to="/meal" class="dash-link" title="식단으로 이동">식단</RouterLink>
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
      kcalories: 0,
    }
  },
  methods: {
    async fnGetList() {
      const day = this.selectedDate.split('-')
      const { data } = await this.$axios.get(`meal/mealList/${day[0]}/${day[1]}`)
      this.mealList = data.mealList[this.selectedDate]
      this.kcalories = 0
      if (this.mealList?.length != undefined) {
        for (let i in this.mealList) {
          this.kcalories += this.mealList[i].kcalories
        }
      }
    },
  },
  mounted() {
    this.fnGetList()
  },
}
</script>

<style lang="scss" scoped></style>
