<template>
  <div class="left">
    {{ dailyLsit }}
  </div>
  <div class="right meal-type">
    <WeekendList :fullList="fullList" :day="day" :week="week" />
    <DayList
      :popupState="popupState"
      :fullList="fullList"
      :day="day"
      :categoryList="categoryList"
      @openPopup="openPopup"
      @fnRequest="fnRequest"
    />
    <button @click="openPopup([])" class="btn-default">식단 등록</button>
    <MealModal
      :popupState="popupState"
      :day="day"
      :categoryList="categoryList"
      :iconList="iconList"
      :editInfo="editInfo"
      @closePopup="popupState = false"
      @fnRequest="fnRequest"
    />
  </div>
</template>
<script>
import WeekendList from '@/components/meal/WeekendList.vue'
import DayList from '@/components/meal/DayList.vue'
import MealModal from '@/components/meal/MealModal.vue'
import { mealCategory } from '@/constants/mealCategory.js'
import { iconCategory } from '@/constants/iconCategory.js'

export default {
  components: {
    WeekendList,
    DayList,
    MealModal,
  },
  data() {
    return {
      dailyLsit: [],
      fullList: [],
      day: '',
      week: [],
      popupState: false,
      categoryList: mealCategory,
      iconList: iconCategory,
      editInfo: [],
    }
  },
  methods: {
    async fnRequest(inputDay) {
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      let day = inputDay.split('-')[2]
      //day = '03';
      const daily = await this.$axios.get(`meal/mealDayList/${year}/${month}`)
      const full = await this.$axios.get(`meal/mealList/${year}/${month}`)
      if (daily.status) {
        this.dailyLsit = daily.data
      } else {
        console.log(daily.message)
      }
      if (full.status) {
        this.fullList = full.data.mealList
      } else {
        console.log(full.message)
      }
      await this.fnDayClick(year, month, day)
    },
    async fnDayClick(year, month, day) {
      let firstDay = new Date(year, month - 1, 1).getDay()
      let lastDay = new Date(year, month, 0).getDate()
      let toDay = new Date(year, month - 1, day).getDate()
      let thisWeek = parseInt((firstDay - 1 + toDay) / 7)
      this.week = new Array(7).fill(0)
      for (let i = 0; i < 7; i++) {
        let check = thisWeek === 0 ? i - (firstDay - 1) : i + 7 * thisWeek - firstDay + 1
        this.week[i] =
          check > lastDay || check < 1
            ? { 0: 0 }
            : { [`${year}-${month}-${check.toString().padStart(2, '0')}`]: check } // 날짜가 유효하지 않으면 0 처리
      }
      this.day = `${year}-${month}-${day}`
    },
    openPopup(itemInfo) {
      this.editInfo = itemInfo
      this.popupState = true
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnRequest(this.day)
  },
}
</script>
<style lang="scss"></style>
