<template>
  <div class="left left-container">
    <div class="left-top">
      <DateSelector v-model="selectedDate" />
    </div>
    <div class="left-bottom">
      <div>{{ fixedList }}</div>
    </div>
  </div>
  <div class="right">
    <div class="right-container">
      <ItemList :date="selectedDate" ref="itemListRef" />
      <button class="insert-btn" @click="fnInsertView">
        {{ !createMode ? '가계부 항목 추가' : '추가 취소' }}
      </button>
      <CreateItemForm @createItem="fnInsertView" v-if="createMode" date="selectedDate" />
    </div>
  </div>
</template>
<script>
import DateSelector from '@/components/common/DateSelector.vue'
import ItemList from '@/components/account/ItemList.vue'
import CreateItemForm from '@/components/account/CreateItemForm.vue'
export default {
  components: {
    DateSelector,
    CreateItemForm,
    ItemList,
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'),
      dateList: [],
      fixedList: [],
      createMode: false,
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
    fnInsertView() {
      if (this.createMode) {
        this.$refs.itemListRef.fnGetItemListByDate(this.selectedDate)
      }
      this.createMode = !this.createMode
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
.right-container {
  display: flex;
  flex-direction: column;
}
.insert-btn {
  align-self: center;
  margin: 10px;
  width: 500px;
  font-size: 20px;
  font-weight: bold;
  background-color: white;
  border: 3px solid black;
  height: 50px;
  border-radius: 10px;
  transition:
    background-color 0.3s ease,
    color 0.3s ease,
    transform 0.3s ease,
    box-shadow 0.2s ease;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
  &:hover {
    background-color: black;
    color: white;
    border-color: rgb(26, 26, 25);
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
  }
}
</style>
