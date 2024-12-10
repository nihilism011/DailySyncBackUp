<template>
  <div class="left left-container">
    <div class="left-top">
      <DateSelector v-model="selectedDate" />
    </div>
    <div class="left-bottom">
    <ScheduleList/>
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch/>
  </div> 
</template>
<script>
import ScheduleList from '@/components/Schedule/ScheduleList.vue'
import ScheduleSearch from '@/components/Schedule/ScheduleSearch.vue'
import DateSelector from '@/components/common/DateSelector.vue'
export default {
  components: {
    DateSelector,
    ScheduleList,
    ScheduleSearch
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'), 
      selectedGroup: null, 
      dateList: [],
      fixedList: [],
    };
  },
  watch: {
    selectedDate(newDate) {
      this.fnInit(newDate);
    },
  },
  methods: {
     fnInit(date) {
      this.fnGetFixedItemListByMonth(date);
    },
    async fnGetFixedItemListByMonth(date) {
      const year = this.$dayjs(date).get('year');
      const month = this.$dayjs(date).get('month') + 1;
      const url = `account/items/fixed/${year}/${month}`;
      const { data } = await this.$axios.get(url);
      this.fixedList = data;
    },
  },
  mounted() {
   
  },
}
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
