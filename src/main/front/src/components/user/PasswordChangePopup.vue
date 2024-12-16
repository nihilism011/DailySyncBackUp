<template>
  <div>
    <div v-if="!passwordCheck">
      <div>비밀번호 확인</div>
      <input type="password" v-model="inputPassword" @keyup.enter="checkPassword" />
      <button @click="checkPassword">비밀번호 확인</button>
      <button @click="$emit('close')">취소</button>
    </div>
    <div v-if="passwordCheck">
      <div>변경할 비밀번호</div>
      <input type="password" v-model="inputPassword" @keyup.enter="changePassword" />
      <button @click="changePassword">비밀번호 변경</button>
      <button @click="$emit('close')">취소</button>
    </div>
  </div>
</template>
<script>
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  setup() {
    const refreshStore = useRefreshStore()
    return { refreshStore }
  },
  data() {
    return {
      passwordCheck: false,
      inputPassword: '',
    }
  },
  methods: {
    async checkPassword() {
      const url = '/user/password/check'
      const { data } = await this.$axios.post(url, { password: this.inputPassword })
      if (data) {
        this.inputPassword = ''
        this.passwordCheck = true
      } else {
        alert('비밀번호가 틀렸습니다.')
      }
    },
    async changePassword() {
      const password = this.inputPassword
      const url = 'user/password'
      const res = await this.$axios.patch(url, { password: password })
      if (res.data) {
        alert('비밀번호 변경이 완료 되었습니다.')
        this.inputPassword = ''
        this.passwordCheck = false
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
