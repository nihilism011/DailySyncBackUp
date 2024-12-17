<template>
  <div class="date-selector">
    <div class="select-container">
      <!-- Year Input -->
      <div class="input-container">
        <input
          v-model="year"
          type="number"
          min="2000"
          max="2024"
          placeholder="Year"
          class="input-box"
        />
        <div class="input-buttons">
          <button @click="increase('year')" class="btn adjust-btn">+</button>
          <button @click="decrease('year')" class="btn adjust-btn">-</button>
        </div>
      </div>

      <!-- Month Input -->
      <div class="input-container">
        <input
          v-model="month"
          type="number"
          min="1"
          max="12"
          placeholder="Month"
          class="input-box"
        />
        <div class="input-buttons">
          <button @click="increase('month')" class="btn adjust-btn">+</button>
          <button @click="decrease('month')" class="btn adjust-btn">-</button>
        </div>
      </div>

      <!-- Date Input -->
      <div class="input-container">
        <input v-model="date" type="number" min="1" max="31" placeholder="Date" class="input-box" />
        <div class="input-buttons">
          <button @click="increase('date')" class="btn adjust-btn">+</button>
          <button @click="decrease('date')" class="btn adjust-btn">-</button>
        </div>
      </div>

      <button @click="updateDate" class="submit-btn">선택</button>
    </div>

    <div class="selected-date">
      <div>선택된 날짜:</div>
      <div class="date">{{ dateStore.selectedDate }}</div>
    </div>
  </div>
</template>

<script>
import { useDateStore } from '@/stores/dateStore'
export default {
  props: {
    modelValue: {
      type: String,
      default: '',
    },
  },
  data() {
    const dateStore = useDateStore()
    return {
      dateStore,
      year: '',
      month: '',
      date: '',
    }
  },
  methods: {
    dateInit() {
      const today = this.$dayjs(this.dateStore.selectedDate)
      this.year = today.get('year')
      this.month = today.get('month') + 1
      this.date = today.get('date')
    },
    increase(field) {
      if (field === 'year') {
        if (this.year < 2024) this.year++
      } else if (field === 'month') {
        if (this.month < 12) this.month++
      } else if (field === 'date') {
        if (this.date < 31) this.date++
      }
    },
    decrease(field) {
      if (field === 'year') {
        if (this.year > 2000) this.year--
      } else if (field === 'month') {
        if (this.month > 1) this.month--
      } else if (field === 'date') {
        if (this.date > 1) this.date--
      }
    },
    updateDate() {
      const year = this.year
      const month = this.month > 9 ? this.month : '0' + this.month
      const date = this.date > 9 ? this.date : '0' + this.date
      const selectDate = `${year}-${month}-${date}`

      this.dateStore.setSelectedDate(selectDate)
    },
  },
  mounted() {
    this.dateInit()
  },
}
</script>

<style lang="scss">
.date-selector {
  font-family: 'Arial', sans-serif;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  .select-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;

    .input-container {
      display: flex;
      align-items: center;
      flex-grow: 1;
      margin-right: 10px;
      position: relative;

      .input-box {
        font-size: 16px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        background-color: #fafafa;
        transition: border-color 0.3s ease;
        width: 100%;
        height: 40px; /* 높이 동일하게 설정 */
        text-align: center;

        &:focus {
          border-color: #3498db;
          outline: none;
        }

        &::placeholder {
          color: #aaa;
        }
      }

      .input-buttons {
        position: absolute;
        right: 0px;
        top: 0;
        display: flex;
        flex-direction: column;
        justify-content: center;

        .btn {
          background-color: white;
          border: 1px solid #ccc;
          font-size: 14px;
          width: 25px;
          height: 20px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          margin: 0;

          &:hover {
            background-color: #3498db;
            color: white;
          }

          &:focus {
            outline: none;
          }
        }
      }
    }

    .submit-btn {
      background-color: #3498db;
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

  .selected-date {
    margin-top: 20px;
    font-size: 16px;
    color: #333;

    .date {
      font-weight: bold;
      font-size: 18px;
      color: #2c3e50;
    }
  }
}
</style>
