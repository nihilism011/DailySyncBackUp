<template>
  <div>
    {{ accountList }}
  </div>
</template>
<script>
export default {
  props: {
    accountDate: String,
  },
  data() {
    return {}
  },
  methods: {
    fnSearchAccountItemsList(accountDate) {
      const dayjsDate = this.$dayjs(accountDate)
      const year = dayjsDate.get('year')
      const month = dayjsDate.get('month')
      const date = dayjsDate.get('date')
      const url = `api/account/items/date/${year}/${month}/${date}`
      console.log(url)
    },
  },
  mounted() {},
}
</script>
<style scoped></style>
