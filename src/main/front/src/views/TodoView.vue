<template>
  <div class="container">
    <div class="left left-container">
      <Calendar />
      <TodoView/>

    </div>
    <div></div>
    <div class="right">
      <div class="right-left">
        <TodoList/>
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
import TodoGroup from '@/components/todo/TodoGroup.vue';
import TodoItem from '@/components/todo/TodoItem.vue';
import TodoList from '@/components/todo/TodoList.vue';
import Calendar from '@/components/todo/TodoCalender.vue';
import TodoView from '@/components/todo/TodoDays.vue'

export default {
  components: {
    TodoGroup,
    TodoItem,
    TodoList,
    Calendar,
    TodoView


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
    async test() {
      const url = `todo/autoTest`;
      await this.$axios.post(url);
    },
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
    this.test();
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
  width: 55%; 
  padding: 20px;
  background-color: lightgray; 
}

.right-middle {
  width: 15%;
  padding: 20px;
  background-color: lightgreen; 
}

.right-right {
  width: 30%;
  padding: 20px;
  background-color: lightblue;
}
</style>
