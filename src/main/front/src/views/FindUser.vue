<template>
  <div>
    <input class="name" v-model="name" placeholder="이름을 입력하세요" />
    <input class="email" v-model="email" placeholder="이메일을 입력하세요" />
    <button @click="fnPassword">비밀번호 찾기</button>
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
    fnPassword() {
      this.$axios
        .post('/user/findPassword', {
          name: this.name,
          email: this.email,
        })
        .then((response) => {
          if (response.data) {
            alert(`비밀번호: ${response.data}`) // 비밀번호를 찾은 경우
          } else {
            alert('해당 이름과 이메일로 등록된 사용자를 찾을 수 없습니다.')
          }
        })
        .catch((error) => console.error('Error:', error))
    },
  },
  mounted() {},
}
</script>
<style lang="scss" scoped></style>
