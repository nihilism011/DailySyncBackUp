<template>
  <div class="search-wrap">
    <div class="select-box">
      <select v-model="form.searchType">
        <option value="" hidden>검색조건선택</option>
        <!-- <option value="year">연도</option> -->
        <!-- <option value="yearMonth">월</option> -->
        <option value="title">제목</option>
        <option value="range">기간설정</option>
      </select>
    </div>
    <!-- <template v-if="form.searchType === 'year'">
      <div class="select-box">
        <select v-model="form.year">
          <option v-for="(year, index) in years" :key="index" :value="year">{{ year }}</option>
        </select>
      </div>
    </template> -->
    <!-- <template v-if="form.searchType === 'yearMonth'">
      <div class="select-box">
        <select v-model="form.year">
          <option v-for="(year, index) in years" :key="index" :value="year">{{ year }}</option>
        </select>
        <select v-model="form.month">
          <option v-for="(month, index) in months" :value="month" :key="index">
            {{ month }}
          </option>
        </select>
      </div>
    </template> -->
    <template v-if="form.searchType === 'range'">
      <div class="ip-box">
        <input type="date" v-model="form.startTime" placeholder="시작일을 입력하세요" />
      </div>
      <div class="ip-box">
        <input type="date" v-model="form.endTime" placeholder="종료일을 입력하세요" />
      </div>
    </template>
    <template v-if="form.searchType === 'title'">
      <div class="ip-box">
        <input id="title" type="text" v-model="form.title" placeholder="제목을 입력하세요" />
      </div>
    </template>
    <button class="search-btn" @click="fnSearch" :disabled="form.searchType == ''">검색</button>
  </div>
  <ScheduleModal
    v-if="popupState"
    :popupState="popupState"
    :searchResults="searchResults"
    @closePopup="popupState = false"
  />
</template>

<script>
import ScheduleModal from './ScheduleModal.vue'
export default {
  components: {
    ScheduleModal,
  },
  data() {
    return {
      form: {
        searchType: '',
        title: '',
        year: '2024',
        month: '12',
        startTime: '',
        endTime: '',
      },
      list: [],
      years: [],
      months: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      popupState: false,
    }
  },
  methods: {
    initYears() {
      const currentYear = new Date().getFullYear()
      const startYear = currentYear - 14
      for (let year = startYear; year <= currentYear; year++) {
        this.years.push(year)
      }
    },
    async fnSearch() {
      let url = ''
      if (this.form.searchType === 'year' && this.form.year) {
        url = `schedule/date/${this.form.year}`
      } else if (this.form.searchType === 'yearMonth' && this.form.year && this.form.month) {
        url = `schedule/date/${this.form.year}/${this.form.month}`
      } else if (this.form.searchType === 'range' && this.form.startTime && this.form.endTime) {
        const startTime = this.form.startTime
        const endTime = this.form.endTime
        url = `schedule/date/range?startTime=${startTime}&endTime=${endTime}`
      } else if (this.form.searchType === 'title' && this.form.title) {
        url = `schedule/title/${this.form.title}`
      }

      if (url) {
        try {
          const { data } = await this.$axios.get(url)
          this.list = data
          this.searchResults = data // 검색된 데이터를 모달로 전달
          this.popupState = true // 모달 열기
        } catch (error) {
          console.error('오류 :', error)
        }
      } else {
        console.log('검색할 조건을 입력하세요.')
      }
    },
    formatDate(date) {
      if (!date) return ''
      const formattedDate = new Date(date)
      return formattedDate.toLocaleDateString()
    },
  },
  mounted() {
    this.initYears()
  },
}
</script>

<style lang="scss" scoped>
.search {
  &-wrap {
    display: flex;
    gap: 0 6px;
    margin-bottom: 20px;
    select + select {
      margin-left: 6px;
    }
  }
  &-btn {
    font-size: 0;
    border-radius: 4px;
    border: 1px solid var(--color-contrastyC);
    width: 34px;
    height: 34px;
    background: url('@/assets/images/ico/ico_search.png') center / 26px no-repeat;
    &:disabled {
      background-image: url('@/assets/images/ico/ico_search2.png');
      background-color: var(--color-contrastyE);
      border-color: var(--color-contrastyE);
    }
  }
}
</style>
