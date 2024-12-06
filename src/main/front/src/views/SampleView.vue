<template>
  <div class="sample">
    <div>{{ day }}</div>
    <div>
      {{ 'http://localhost:8080/api/' }}
      <input type="text" v-model="inputApiPath" @keyup.enter="fnRequest(inputApiPath)" />
      <button @click="fnRequest(inputApiPath)">요청</button>
    </div>
    <div>응답 결과</div>
    <div>{{ result }}</div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      result: '',
      inputApiPath: '',
      day: '',
    }
  },
  methods: {
    async fnRequest(inputApiPath) {
      const res = await this.$axios.get(inputApiPath)
      if (res.status) {
        this.result = res.data
      } else {
        console.log('에러메세지 :', res.message)
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss')
  },
}
</script>
<style>
@media (min-width: 1024px) {
  .sample {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
