<template>
  <div class="form-wrap">
    <div class="form-container">
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="userName">아이디</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="text" id="userName" v-model="userName" placeholder="아이디를 입력하세요" />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="password">비밀번호</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input
              type="password"
              id="password"
              v-model="password"
              placeholder="비밀번호를 입력하세요"
            />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="name">이름</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="text" id="name" v-model="name" placeholder="이름을 입력하세요" />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit">성별</p>
        </div>
        <div class="bot-box">
          <div class="ip-ra-txt">
            <input type="radio" v-model="gender" value="M" id="M" />
            <label for="M"> 남자 </label>
            <input type="radio" v-model="gender" value="F" id="F" />
            <label for="F"> 여자 </label>
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <p class="tit"><label for="email">이메일</label></p>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="email" id="email" v-model="email" placeholder="이메일을 입력하세요" />
          </div>
        </div>
      </div>
      <button @click="signUp" class="btn-default">가입하기</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userName: '',
      password: '',
      name: '',
      gender: '',
      email: '',
    }
  },
  methods: {
    async signUp() {
      const url = 'user'
      const requestBody = {
        userName: this.userName,
        password: this.password,
        name: this.name,
        gender: this.gender,
        email: this.email,
      }
      const { data } = await this.$axios.post(url, requestBody)
      if (data) {
        alert('가입 완료.')
        this.userName = ''
        this.password = ''
        this.name = ''
        this.gender = ''
        this.email = ''
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.form {
  &-wrap {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    .btn-default {
      margin-top: 30px;
      width: 100%;
    }
  }
  &-container {
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
    border-radius: 10px;
    width: 500px;
    padding: 20px 30px 30px;
    margin-top: 80px;
  }
}
</style>
