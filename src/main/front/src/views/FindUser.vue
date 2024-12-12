<template>
  <div>
    <input class="name2" v-model="name" placeholder="이름을 입력하세요" />
    <input class="email2" v-model="email" placeholder="이메일을 입력하세요" />
    <button @click="fnAPassword">비밀번호 찾기</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      email: '',
    }
  },
  methods: {
    async fnAPassword() {
      if (!this.name || !this.email) {
        alert('이름과 이메일을 입력해주세요.')
        return
      }

      try {
        const response = await this.$axios.post('/api/user/findPassword', {
          name: this.name,
          email: this.email,
        })

        if (response.data) {
          alert(`비밀번호: ${response.data}`)
        } else {
          alert('해당 이름과 이메일로 등록된 사용자를 찾을 수 없습니다.')
        }
      } catch (error) {
        console.error('Error:', error)
        alert('서버와 통신 중 오류가 발생했습니다. 다시 시도해주세요.')
      }
    },
  },
}
</script>
