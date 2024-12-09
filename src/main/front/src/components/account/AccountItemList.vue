<template>
  <div>
    <div class="list-container">
      <AccountItemRow v-for="(account, index) in list" :key="index" :account="account" />
    </div>
  </div>
</template>
<script>
import AccountItemRow from './AccountItemRow.vue'
export default {
  components: {
    AccountItemRow,
  },
  props: {
    date: String,
  },
  data() {
    const dateStore = useDateStore()
    return {
      dateStore,
      categoryArray,
      updateRowNum: null,
      updateForm: {},
      list: [],
    }
  },
  watch: {
    'dateStore.selectedDate': 'fetchDataList',
  },
  computed: {
    selectedDate() {
      return this.dateStore.selectedDate
    },
  },
  methods: {
    async fetchDataList() {
      const date = this.selectedDate || this.$dayjs().format('YYYY-MM-DD')
      const url = `account/items/date/${date}`
      const { data } = await this.$axios.get(url)
      this.list = data
    },
    fnUpdateInit(item) {
      this.updateRowNum = item.id
      this.updateForm = {
        category: item.category,
        title: item.title,
        amount: Math.abs(item.amount),
        description: item.description,
        amountValue: item.amount > 0 ? 'plus' : 'minus',
      }
    },
    async fnUpdate() {
      const url = `account/items/${this.updateRowNum}`
      const form = this.updateForm
      const body = {
        category: form.category,
        title: form.title,
        description: form.description,
        amount: form.amountValue === 'plus' ? form.amount : -form.amount,
      }
      const { data } = await this.$axios.patch(url, body)
      if (data) {
        this.updateRowNum = null
        this.updateForm = null
        this.$emit('refresh')
      }
    },
    async fnDeleteItem(itemId) {
      if (!confirm('진짜 삭제?')) {
        return
      }
      const url = `account/items/${itemId}`
      const { data } = await this.$axios.delete(url)
      if (data) {
        this.$emit('refresh')
      } else {
        alert('뭔가 잘못되었으니 수정해라')
      }
    },
    fnUpdateCancel() {
      this.updateRowNum = null
      this.updateForm.amountValue = ''
    },
  },
  mounted() {
    this.fetchDataList()
  },
}
</script>
<style scoped>
.list-container {
  .list-item {
    display: flex;
    margin: 10px 0;
    height: 70px;
    border-bottom: 1px solid #eee;
    box-shadow: 5px 5px 3px #666;
    .category {
      width: 120px;
      align-content: center;
      justify-items: center;
    }
    .amount {
      width: 220px;
      align-content: center;
      justify-items: start;
      font-weight: bold;
    }
    .title {
      width: 200px;
      font-size: 20px;
      align-content: center;
      justify-items: start;
    }
    .buttonBox {
      align-content: center;
      justify-items: center;
      .updateBtn {
        color: #fff;
        border: none;
        border-radius: 4px;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        transition:
          background-color 0.3s ease,
          transform 0.2s ease,
          box-shadow 0.2s ease;
        height: 40px;
        width: 100px;

        /* 기본 그림자 효과 */
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

        &:hover {
          background-color: #2980b9;
          box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
        }

        &:active {
          background-color: #1c6ba0;
          transform: translateY(2px);
          box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        &:focus {
          outline: none;
        }
      }
    }
  }
}
</style>
