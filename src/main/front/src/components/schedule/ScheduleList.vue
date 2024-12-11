<template>
  <div>
    <h1>{{ message }}</h1>
    <div v-if="data">
      <p><strong>ID:</strong> {{ data.id }}</p>
      <p><strong>Name:</strong> {{ data.name }}</p>
      <p><strong>Description:</strong> {{ data.description }}</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data() {
    return {
      message: '',
      data: null,
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get('http://localhost:8080/api/customResponse')
        this.message = response.data.message
        this.data = response.data.data
      } catch (error) {
        console.error('Error fetching data:', error)
        this.message = '데이터 로드 중 오류가 발생했습니다.'
      }
    },
  },
}
</script>
<style lang="scss" scoped>
h1 {
  color: #4caf50;
}
</style>
