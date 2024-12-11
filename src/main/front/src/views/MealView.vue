<template>
  <div class="left">
    {{ dailyLsit }}
    <Calendar :dailyLsit="dailyLsit" @fnMealList="fnMealList" @fnDayList="fnDayList" />
    <TotalNutrient :todayNutrient="todayNutrient" />
  </div>
  <div class="right meal-type">
    <WeekendList :fullList="fullList" :day="day" :week="week" />
    <button @click="openPopup([])" class="btn-default mg40">식단 등록</button>
    <DayList
      :popupState="popupState"
      :fullList="fullList"
      :day="day"
      :categoryList="categoryList"
      @openPopup="openPopup"
      @fnMealList="fnMealList"
      @fnDayList="fnDayList"
    />
    <MealModal
      :popupState="popupState"
      :day="day"
      :categoryList="categoryList"
      :iconList="iconList"
      :editInfo="editInfo"
      @closePopup="popupState = false"
      @fnMealList="fnMealList"
      @fnDayList="fnDayList"
    />
  </div>
</template>
<script>
import WeekendList from '@/components/meal/WeekendList.vue'
import DayList from '@/components/meal/DayList.vue'
import MealModal from '@/components/meal/MealModal.vue'
import { mealCategory } from '@/constants/mealCategory.js'
import { iconCategory } from '@/constants/iconCategory.js'
import Calendar from '@/components/meal/Calendar.vue'
import TotalNutrient from '@/components/meal/TotalNutrient.vue'

export default {
  components: {
    WeekendList,
    DayList,
    MealModal,
    Calendar,
    TotalNutrient,
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
      todayNutrient: {
        kcalories: 0,
        sodium: 0,
        carbs: 0,
        protein: 0,
        fat: 0,
        sugar: 0,
      },
    }
  },
  methods: {
    async fnMealList(inputDay) {
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      let day = inputDay.split('-')[2]
      const full = await this.$axios.get(`meal/mealList/${year}/${month}`)
      if (full.status) {
        this.fullList = full.data.mealList
      } else {
        console.log(full.message)
      }
      await this.fnDayClick(year, month, day)
    },
    async fnDayList(inputDay) {
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      const daily = await this.$axios.get(`meal/mealDayList/${year}/${month}`)
      if (daily.status) {
        this.dailyLsit = daily.data.mealDay.map((item) => ({
          title: `${item.cnt}`, // cnt를 title로 사용
          date: item.date, // 해당 날짜로 설정
        }))
      } else {
        console.log(daily.message)
      }
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
      this.fnNutrient(this.day)
    },
    fnNutrient(day) {
      this.todayNutrient = {
        kcalories: 0,
        sodium: 0,
        carbs: 0,
        protein: 0,
        fat: 0,
        sugar: 0,
      }
      if(this.fullList[day] != undefined) {
        Object.keys(this.todayNutrient).map((key) => {
          return this.fullList[day].filter((item) => {
            if (key in item) {
              this.todayNutrient[key] += item[key]
            }
          })          
        })
      }
    },
    openPopup(itemInfo) {
      this.editInfo = itemInfo
      this.popupState = true
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnMealList(this.day)
  },
}
</script>
<style lang="scss"></style>
