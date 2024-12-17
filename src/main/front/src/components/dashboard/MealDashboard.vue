<template>
  <div class="dash-wrap">
    <template v-if="mealList != null || mealList != undefined">
      <div class="dash-item-wrap">
        <div class="dash-item" v-for="item in mealList">
          {{ item.icon }}
          {{ item.foodName }}
          {{ getMealName(item.category) }}
          {{ item.kcalories }}
          {{ item.description }}
        </div>
      </div>
    </template>
    <template v-else>일정이없어요</template>
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
    getMealName(data) {
      console.log(data)
      return this.mealCategory[data].name || 'empty'
    },
  },
  mounted() {
    this.fnGetList()
  },
}
</script>

<style lang="scss" scoped></style>
