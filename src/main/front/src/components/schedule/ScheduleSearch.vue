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
</template>
<script>
export default {
  emits: ['searchResult'],
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
      searchResults: [],
    }
  },
  methods: {
    initYears() {
      const currentYear = new Date().getFullYear();
      const endYear = currentYear + 10;

  for (let year = currentYear; year <= endYear; year++) {
    this.years.push(year);
      }
    },
    async fnSearch() {
      let url = '';

      if (this.form.searchType === 'range' && this.form.startTime && this.form.endTime) {
        const startTime = new Date(this.form.startTime);
        const endTime = new Date(this.form.endTime);


        if (isNaN(startTime.getTime()) || isNaN(endTime.getTime())) {
          alert('시작 날짜 또는 끝 날짜가 올바르지 않습니다. 유효한 날짜 형식을 입력해 주세요.');
          return; // Stop the function execution if dates are invalid
        }

        if (startTime > endTime) {
          alert('끝나는 시간이 시작 시간보다 이전입니다. 날짜를 확인해 주세요.');
          return; // Stop the function execution if start date is greater than end date
        }

        url = `schedule/date/range?startTime=${startTime.toISOString()}&endTime=${endTime.toISOString()}`;
      } else if (this.form.searchType === 'title' && this.form.title) {
        url = `schedule/title/${this.form.title}`;
      }

      if (url) {
        try {
          const { data } = await this.$axios.get(url);
          this.$emit('searchResult', data);
        } catch (error) {
          console.error('오류 :', error);
        }
      } else {
        console.log('검색할 조건을 입력하세요.');
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
