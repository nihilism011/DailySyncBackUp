<template>
  <div class="left left-container">
    <div class="left-record">
      <div class="title">개인 설정</div>
    </div>
    <div class="left-record">
      <div class="record-title">이름</div>
      <input type="text" v-model="userInput.userName" />
    </div>
    <div class="left-record">
      <div class="record-title">성별</div>
      <input type="text" />
    </div>
    <div class="left-record">
      <div class="record-title">몸무게</div>
      <input type="number" />
    </div>
    <div class="left-record">
      <div class="record-title">키</div>
      <input type="number" />
    </div>
    <div class="left-record">
      <div class="record-title">권장 칼로리</div>
      <input type="number" />
    </div>
  </div>
  <div class="right right-container"></div>
</template>
<script>
export default {
  data() {
    return {
      userSettingData: {},
      userInput: {},
      updateData: {},
    }
  },
  methods: {
    async fetchData() {
      const userInfoUrl = 'user/info'
      const { data } = await this.$axios.get(userInfoUrl)
      console.log(data)
      this.userInput = data
      this.userSettingData = data
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>
<style lang="scss" scoped>
.left-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.left-record {
  display: flex;
}
.record-title {
  width: 150px;
}
.title {
  margin-bottom: 20px;
}
</style>
