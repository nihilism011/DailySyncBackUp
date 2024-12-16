<template>
  <div class="left left-container">
    <Calendar />
    <FixedItemList />
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
import AccountItemList from '@/components/account/rightView/AccountItemList.vue'
import DateScore from '@/components/account/rightView/DateScore.vue'
import UpdatePopup from '@/components/account/UpdatePopup.vue'
import FixedItemList from '@/components/account/leftView/FixedItemList.vue'
import Calendar from '@/components/account/leftView/accountCalender.vue'
export default {
  components: {
    AccountItemList,
    FixedItemList,
    DateScore,
    UpdatePopup,
    Calendar,
  },
  setup() {
    const dateStore = useDateStore()
    return { dateStore }
  },
  data() {
    return {
      dateList: [],
      fixedList: [],
      viewUpdatePopup: false,
    }
  },
  methods: {},
}
</script>
<style scoped>
.left-container {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .left-top {
    height: 65%;
  }
  .left-bottom {
    height: 30%;
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
