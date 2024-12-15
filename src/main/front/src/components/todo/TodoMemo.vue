<template>
  <div class="memo-container">
    <h2>메모장</h2>
    <textarea
      v-model="Memo"
      @input="handleInput"
      placeholder="메모를 작성하세요..."
      class="memo-textarea"
    ></textarea>
  </div>
</template>

<script>
export default {
  data() {
    return { 
      Memo: "",
      MemoId: "",
      saveTimer: null, 
    };
  },

  methods: {
    async getMemo() {
      const userId = 5;
      const url = `todo/memo/${userId}`;
      try {
        const { data } = await this.$axios.get(url);
        if (data && data.id) {
          this.Memo = data.contents;
          this.MemoId = data.id;
        }
      } catch (error) {
        console.log(error);
        this.Memo = "";
        this.MemoId = "";
      }
    },

    async SaveMemo() {
      if (!this.Memo.trim()) {
        this.Memo = " ";
      }

      const userId = 5;
      if (this.MemoId) {
        const url = `todo/memo/${userId}/${this.MemoId}`;
        await this.$axios.put(url, {
          contents: this.Memo,
        });
      } else {
        const url = `todo/memo/${userId}`;
        await this.$axios.post(url, {
          contents: this.Memo,
        });
      }
      await this.getMemo();
    },

    handleInput() {
      if (this.saveTimer) {
        clearTimeout(this.saveTimer);
      }

      this.saveTimer = setTimeout(() => {
        this.SaveMemo();
      }, 1000);
    },
  },

  mounted() {
    this.getMemo();
  },
  beforeUnmount() { 
    if (this.saveTimer) {
      clearTimeout(this.saveTimer);
    }
  },
};
</script>

<style scoped>
.memo-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.memo-textarea {
  width: 100%;
  height: 300%;
  padding: 10px;
  margin-top: 20px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}
</style>
