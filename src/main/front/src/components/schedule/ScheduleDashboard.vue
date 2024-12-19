<template>
    <div class="popup-cont">
      <RouterLink to="/schedule" style="text-align: right;">Schedule 이동하기 >> </RouterLink>
      <div class="schedule-items">
        <div class="item" v-for="(item, index) in filteredItems" :key="index">
            <div class="item-info">
              <div class="item-tit">{{ item.title }}</div>
              <div class="item-start">{{ formatDate(item.startTime) }}</div>
              <div class="item-end">{{ formatDate(item.endTime) }}</div>
              <div class="item-des">{{ item.description }}</div>
            </div>
        </div>
      </div>
    </div>
</template>
<script>
import { RouterLink } from 'vue-router'
export default {
    components: { RouterLink },
        data() {
        return {
            items: [],
        };
        },
    props: {
      selectedDate: {
        type: String,
        default: '',
      },
    },
    computed: {
      filteredItems() {
        const selectedDate = this.$dayjs(this.selectedDate).startOf('day');
        const endOfDay = selectedDate.endOf('day');

        return this.items.filter(item => {
          const itemStart = this.$dayjs(item.startTime);
          const itemEnd = this.$dayjs(item.endTime);

          return (itemStart.isBefore(endOfDay) && itemEnd.isAfter(selectedDate));
        });
    },
            },
    methods: {
        navigateToSchedule() {
            this.$router.push({ name: 'SchedulePage' });
        },
        formatDate(date) {
            return this.$dayjs(date).format('YYYY-MM-DD A hh:mm'); 
            },
        async fetchItems() {
          const today = this.$dayjs();
          const year = today.year();
          const month = today.month() + 1;

          const startOfMonth = today.startOf('month').format('YYYY-MM-DD[T]HH:mm:ss');
          const endOfMonth = today.endOf('month').format('YYYY-MM-DD[T]HH:mm:ss');

          try {
            const response = await this.$axios.get(`schedule/userId/scheduleList/${year}/${month}`, {
              params: { startOfMonth, endOfMonth }
            });

            if (response.status && response.data.length > 0) {
              this.items = response.data.map(item => ({
                id: item.id,
                title: item.title,
                startTime: item.startTime,
                endTime: item.endTime,
                description: item.description,
              }));
            } else {
              console.log('해당 월에 일정이 없습니다.');
              this.items = [];
            }
          } catch (error) {
            console.error('Error fetching items:', error);
            this.items = [];
          }
      },
    },
    mounted() {
      this.fetchItems(); 
    },
  };
</script>
  
<style lang="scss" scoped>
.schedule-items {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px; 
  overflow-y: auto;
  max-height: 500px; 
  
  .item {
    display: flex;
    flex-direction: column; 
    padding: 15px;
    background-color: #f9f9f9; 
   
    &:not(:last-child) {
      margin-bottom: 10px; 

    .item-info {
      display: flex;
      flex-direction: column; 
      gap: 5px; 
      
      .item-tit {
        font-size: 18px;
        font-weight: bold;
        color: #333; 
      }
      
      .item-start,
      .item-end {
        font-size: 14px;
        color: #555; 
      }

      .item-des {
        font-size: 14px;
        color: #555; 
      }
    }
  }
 }
}
</style>
  
  