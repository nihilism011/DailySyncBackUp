<template>
  <div class="container">
    <div class="left-record">
      <div class="title">개인 설정</div>
    </div>
    <div class="left-record">
      <div class="record-title">이름</div>
      <input type="text" v-model="userInput.userName" />
    </div>
    <div class="left-record">라디오 박스 수정해야함.</div>
    <div class="left-record">
      <div class="record-title">성별</div>
      <input type="text" v-model="userInput.gender" />
    </div>
    <div class="left-record">
      <div class="record-title">몸무게</div>
      <input type="number" v-model="userInput.weight" />
    </div>
    <div class="left-record">
      <div class="record-title">키</div>
      <input type="number" v-model="userInput.height" />
    </div>
    <div class="left-record">
      <div class="record-title">권장 칼로리</div>
      <input type="number" v-model="userInput.recommendCal" />
    </div>
    <div v-if="updateData.length != 0" class="left-record">
      <button @click="updateUserInfo">수정</button>
      <button @click="fetchData">원래대로</button>
    </div>
    <button @click="visiblePasswordPopup = true">비밀번호 수정</button>
    <PasswordChangePopup v-if="visiblePasswordPopup" @close="visiblePasswordPopup = false" />
  </div>
</template>
<script>
import PasswordChangePopup from './PasswordChangePopup.vue'
export default {
  components: {
    PasswordChangePopup,
  },
  data() {
    return {
      userSettingData: {},
      userInput: {},
      visiblePasswordPopup: false,
    }
  },
  computed: {
    updateData() {
      const input = this.userInput
      const data = this.userSettingData
      const updateColumn = []
      if (input.userName != data.userName) {
        updateColumn.push('userName')
      }
      if (input.height != data.height) {
        updateColumn.push('height')
      }
      if (input.recommendCal != data.recommendCal) {
        updateColumn.push('recommendCal')
      }
      if (input.gender != data.gender) {
        updateColumn.push('gender')
      }
      if (input.weight != data.weight) {
        updateColumn.push('weight')
      }
      return updateColumn
    },
  },
  watch: {
    'userInput.weight': function () {
      if (!this.userInput.weight) this.userInput.weight = null
    },
    'userInput.height': function () {
      if (!this.userInput.height) this.userInput.height = null
    },
    'userInput.recommendCal': function () {
      if (!this.userInput.recommendCal) this.userInput.recommendCal = null
    },
  },
  methods: {
    async updateUserInfo() {
      const url = 'user/info'
      if (this.userInput.userName.length < 2) {
        alert('이름은 2자 이상')
        return
      }
      if (this.userInput.gender != 'F' && this.userInput.gender != 'M') {
        alert('성별은 F 나 M 적을것. 이 메세지도 라디오박스로 바꾸면 수정해야함.')
        return
      }
      const { data } = await this.$axios.put(url, this.userInput)
      if (data) {
        this.fetchData()
      }
    },
    async fetchData() {
      const userInfoUrl = 'user/info'
      const { data } = await this.$axios.get(userInfoUrl)
      console.log(data)
      this.userInput = { ...data }
      this.userSettingData = data
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>
<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.left-record {
  display: flex;
  input {
    width: 100px;
  }
}
.record-title {
  width: 150px;
}
.title {
  margin-bottom: 20px;
}
</style>
