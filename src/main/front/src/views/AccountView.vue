<template>
  <div class="container">
    <ItemList account-date="" />
  </div>
</template>
<script>
import ItemList from '@/components/account/ItemList.vue'
export default {
  components: {
    ItemList,
  },

  data() {
    return {
      date: '2024-12-04',
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style scoped></style>
