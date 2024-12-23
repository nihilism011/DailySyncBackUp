<template>
  <div class="dash-wrap">
    <div class="dash-tit-box">
      <p class="tit">
        <span>{{ isToday ? '오늘의 진행률' : `${selectedDate}의 진행률` }}</span> <br />
        {{ Math.round(todayCompletionRate) }}%
      </p>
    </div>
    <div class="dash-item-wrap">
      <template v-if="items != null || items != undefined">
        <div class="dash-item wrap" v-for="(item, index) in items" :key="index">
          <div class="tit">{{ item.title }}</div>
          <template v-if="item.status === 'new'">
            <span class="date">{{ formatTimeWithoutSeconds(item.checkedTime) }}</span>
          </template>
          <template v-else>
            <div class="full-date">
              <span>생성일 : {{ formatDateTime(item.createdAt) }}</span>
              <span>완료일 : {{ formatDateTime(item.checkedTime) }}</span>
            </div>
          </template>
        </div>
      </template>
      <template v-else>
        <div class="dash-item none"><span>등록한 Todo가 없어요</span></div>
      </template>
    </div>
    <RouterLink to="/todo" class="dash-link" title="Todo 이동">Todo</RouterLink>
  </div>
</template>

<script>
import { RouterLink } from 'vue-router'
export default {
  components: { RouterLink },
  data() {
    return {
      items: [],
      todayCompletionRate: null,
      isToday: false,
    }
  },
  props: {
    selectedDate: {
      type: String,
      default: '',
    },
  },
  watch: {
    selectedDate(newVal) {
      if (newVal) {
        this.fnMySelectList()
        this.fnDayList(newVal)
      }
    },
  },
  methods: {
    // Todo 페이지로 이동하는 메소드
    navigateToTodo() {
      this.$router.push({ name: 'TodoPage' })
    },

    async fnDayList(selectedDate) {
      const today = new Date()
      const year = selectedDate.split('-')[0]
      const month = selectedDate.split('-')[1]

      this.isToday = selectedDate === today.toISOString().split('T')[0]

      const daily = await this.$axios.get(`todo/list/${year}/${month}`)
      if (daily.status) {
        const todoCounts = daily.data.todoCounts

        const todayData = todoCounts.find((item) => item.date === selectedDate)
        if (todayData) {
          this.todayCompletionRate = todayData.completionRate
        } else {
          this.todayCompletionRate = 0
        }
      }
    },

    async fnMySelectList() {
      if (!this.selectedDate) return
      const url = `todo/list/${this.selectedDate}`
      try {
        const { data } = await this.$axios.get(url)
        this.items = data
      } catch (error) {
        console.error('데이터 가져오기 실패:', error)
      }
    },

    formatTimeWithoutSeconds(time) {
      if (!time) return ''
      const date = new Date(time)
      return `${date.getHours()}시 ${date.getMinutes()}분`
    },

    formatDateTime(dateTime) {
      if (!dateTime) return ''
      const date = new Date(dateTime)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}시${minutes}분`
    },
  },
  mounted() {
    if (this.selectedDate) {
      this.fnMySelectList()
      this.fnDayList(this.selectedDate)
    }
  },
}
</script>

<style lang="scss" scoped></style>
