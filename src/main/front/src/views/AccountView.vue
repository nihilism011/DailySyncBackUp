<template>
  <div class="left left-container">
    <div class="left-top">
      <DateSelector v-model="selectedDate" />
    </div>
    <div class="left-bottom">
      <div>{{ fixedList }}</div>
    </div>
  </div>
  <div class="right"><ItemList :date="selectedDate" /></div>
</template>
<script>
import DateSelector from '@/components/common/DateSelector.vue'
import ItemList from '@/components/account/ItemList.vue'
export default {
  components: {
    DateSelector,
    ItemList,
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'),
      dateList: [],
      fixedList: [],
    }
  },
  watch: {
    selectedDate(newDate) {
      this.fnInit(newDate)
    },
  },
  methods: {
    fnInit(date) {
      this.fnGetFixedItemListByMonth(date)
    },
    async fnGetFixedItemListByMonth(date) {
      const year = this.$dayjs(date).get('year')
      const month = this.$dayjs(date).get('month') + 1
      const url = `account/items/fixed/${year}/${month}`
      const { data } = await this.$axios.get(url)
      this.fixedList = data
    },
  },
  mounted() {
    this.fnInit(this.selectedDate)
  },
}
</script>
<style scoped>
.left-container {
  display: flex;
  flex-direction: column;
  .left-top {
    align-content: center;
    justify-items: center;
    height: 50%;
    padding: 20px;
  }
  .left-bottom {
    height: 50%;
    padding: 20px;
  }
}
</style>
