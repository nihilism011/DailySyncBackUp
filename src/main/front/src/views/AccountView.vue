<template>
  <div class="left left-container">
    <div class="left-top">
      <AccountDateSelector v-model="date" />
    </div>
    <div class="left-bottom"><FixedItemList /></div>
  </div>
  <div class="right right-container">
    <DateScore ref="dateScoreRef" />
    <AccountItemList />
    <button @click="viewUpdatePopup = true" class="insert-btn">가계부 항목 추가</button>
    <UpdatePopup v-if="viewUpdatePopup" @close="viewUpdatePopup = false" action="create" />
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import AccountDateSelector from '@/components/account/leftView/AccountDateSelector.vue'
import AccountItemList from '@/components/account/rightView/AccountItemList.vue'
import DateScore from '@/components/account/rightView/DateScore.vue'
import UpdatePopup from '@/components/account/UpdatePopup.vue'
import FixedItemList from '@/components/account/leftView/FixedItemList.vue'
export default {
  components: {
    AccountDateSelector,
    AccountItemList,
    FixedItemList,
    DateScore,
    UpdatePopup,
  },
  data() {
    const dateStore = useDateStore()
    return {
      dateStore,
      date: '',
      dateList: [],
      fixedList: [],
      viewUpdatePopup: false,
    }
  },
  methods: {},
  mounted() {
    this.date = this.dateStore.selectedDate
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
