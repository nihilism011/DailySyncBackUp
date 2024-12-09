<template>
  <div class="left">
    <div class="sample">
      <div>{{ day }}</div>
      <div>
        {{ 'http://localhost:8080/api/' }}
        <input type="text" v-model="inputApiPath" @keyup.enter="fnRequest(inputApiPath)" />
        <button @click="fnRequest(inputApiPath)">요청</button>
      </div>
      <BoldText value="Hi~everyone" :some="someOne" />
      <div>응답 결과</div>
      <div>{{ result }}</div>
    </div>
  </div>
  <div class="right">우측메뉴</div>
</template>
<script>
import BoldText from '../components/sample/BoldText.vue'
export default {
  components: {
    BoldText,
  },
  data() {
    return {
      result: '',
      inputApiPath: '',
      day: '',
      someOne: { name: 'kkk', age: 12 },
    }
  },
  methods: {
    async fnRequest(inputApiPath) {
      const res = await this.$axios.get(inputApiPath)
      if (res.status) {
        this.result = res.data
      } else {
        console.log(res.message)
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss')
  },
}
</script>
<style></style>
