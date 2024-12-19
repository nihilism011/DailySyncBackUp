<template>
  <div>
    <DateScore />
    <AccountItemList />
  </div>
</template>
<script>
import DateScore from '../leftView/DateScore.vue'
import AccountItemList from '../leftView/AccountItemList.vue'
export default {
  components: {
    DateScore,
    AccountItemList,
  },
}
</script>
<style lang="scss" scoped></style>
