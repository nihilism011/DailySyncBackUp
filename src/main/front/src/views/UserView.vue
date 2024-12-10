<template>
  <div class="form-container">
    <div class="form-group">
      <label for="userName">아이디</label>
      <input type="text" id="userName" v-model="userName" placeholder="아이디를 입력하세요" />
    </div>

    <div class="form-group">
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="password" placeholder="비밀번호를 입력하세요" />
    </div>

    <div class="form-group">
      <label for="name">이름</label>
      <input type="text" id="name" v-model="name" placeholder="이름을 입력하세요" />
    </div>

    <div class="form-group">
      <label>성별</label>
      <div class="gender-options">
        <label>
          <input type="radio" v-model="gender" value="M" />
          남자
        </label>
        <label>
          <input type="radio" v-model="gender" value="F" />
          여자
        </label>
      </div>
    </div>

    <div class="form-group">
      <label for="email">이메일</label>
      <input type="email" id="email" v-model="email" placeholder="이메일을 입력하세요" />
    </div>

    <button @click="signUp" class="submit-btn">가입!</button>
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
/* 전체 폼 스타일 */
.form-container {
  max-width: 600px;
  height: 600px;
  margin: 50px auto;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

/* 각 입력 항목 그룹 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 14px;
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
}

.form-group input {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #fff;
  margin-top: 5px;
  box-sizing: border-box;
}

.form-group input:focus {
  border-color: #007bff;
  outline: none;
}

.gender-options {
  display: flex;
  gap: 25px;
}

.gender-options label {
  font-size: 16px;
  color: #333;
}

.gender-options input {
  margin-right: 8px;
}

/* 버튼 스타일 */
.submit-btn {
  width: 100%;
  padding: 14px;
  font-size: 18px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #0056b3;
}

.submit-btn:focus {
  outline: none;
}
</style>
