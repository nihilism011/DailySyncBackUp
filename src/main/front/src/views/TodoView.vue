<template>
  <div class="container">
    <div class="left left-container">
      <div class="left-top">
        <DateSelector v-model="selectedDate" />
      </div>
      <div class="left-bottom">
       
      </div>
    </div>
    <div class="right">
      <div class="right-left">
        
      </div>
      <div class="right-middle">
        
        <TodoGroup @updateSelectedGroup="selectedGroup = $event" />
      </div>
      <div class="right-right">
       
        <TodoItem :selectedGroup="selectedGroup" />
      </div>
    </div>
  </div>
</template>

<script>
import DateSelector from '@/components/common/DateSelector.vue';
import TodoGroup from '@/components/todo/TodoGroup.vue';
import TodoItem from '@/components/todo/TodoItem.vue';

export default {
  components: {
    DateSelector,
    TodoGroup,
    TodoItem,
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'), 
      selectedGroup: null, 
      dateList: [],
      fixedList: [],
    };
  },
  watch: {
    selectedDate(newDate) {
      this.fnInit(newDate);
    },
  },
  methods: {
    fnInit(date) {
      this.fnGetFixedItemListByMonth(date);
    },
    async fnGetFixedItemListByMonth(date) {
      const year = this.$dayjs(date).get('year');
      const month = this.$dayjs(date).get('month') + 1;
      const url = `account/items/fixed/${year}/${month}`;
      const { data } = await this.$axios.get(url);
      this.fixedList = data;
    },
  },
  mounted() {
    this.fnInit(this.selectedDate);
  },
};
</script>

<style scoped>
.container {
  display: flex;
  width: 100%;
  height: 100vh; 
}

.left-container {
  width: 30%; 
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.left-top {
  flex: 1;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.left-bottom {
  flex: 1;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.right {
  width: 70%;
  display: flex;
  justify-content: space-between;
}

.right-left {
  width: 40%; 
  padding: 20px;
  background-color: lightgray; 
}

.right-middle {
  width: 30%;
  padding: 20px;
  background-color: lightgreen; 
}

.right-right {
  width: 30%;
  padding: 20px;
  background-color: lightblue;
}
</style>
