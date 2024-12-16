<template>
  <div class="todo-text-wrap">
    <p class="memo-title">메모장</p>
    <div class="text-box">
      <textarea
        v-model="Memo"
        @input="handleInput"
        placeholder="메모를 작성하세요..."
        class="memo-textarea"
      ></textarea>
    </div>
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
      const url = `todo/memo`;
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
      if (this.MemoId) {
        const url = `todo/memo/${this.MemoId}`;
        await this.$axios.put(url, {
          contents: this.Memo,
        });
      } else {
        const url = `todo/memo`;
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
.todo-text-wrap {
  margin-top: 40px;
  padding: 20px 20px 30px;
  box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
  border-radius: 10px;
}
.memo-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 14px;
}
textarea {
  height: 250px;
}
</style>