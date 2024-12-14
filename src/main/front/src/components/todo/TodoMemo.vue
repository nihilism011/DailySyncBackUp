<template>
  <div class="memo-container">
    <h2>메모장</h2>
    <textarea
      v-model="Memo"
      @input="updateContents"
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
      MemoId: '',
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
        return;
      }
      
      const userId = 5;
      if (this.MemoId) {
        const url = `todo/memo/${userId}/${this.MemoId}`;
        await this.$axios.put(url, {
          contents: this.Memo
        });
      } else {
        const url = `todo/memo/${userId}`;
        await this.$axios.post(url, {
          contents: this.Memo
        });  
      }
      await this.getMemo(); 
    },

   
   
    updateContents() {
   
      if (this.Memo.trim()) {
        this.SaveMemo();
      }
    },
  },

  mounted() {
    this.getMemo();
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
  height: 200px;
  padding: 10px;
  margin-top: 20px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}

</style>
