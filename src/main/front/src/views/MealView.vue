<template lang="">
    <div class="left">
        {{ dailyLsit }}
    </div>
    <div class="right meal-type">
        <div class="weekend-wrap">
            <div class="weekend-box" v-for="weekList in week">
                <template v-for="(days, date) in weekList">
                    <template v-if="days != 0">
                        <div class="weekend">
                            <span class="date">{{ days }}</span>
                            <template v-if="fullList[`${date}`]">
                                <button @click="fnDayClick(date.split('-')[0], date.split('-')[1], date.split('-')[2])">
                                    <template v-for="item in fullList[`${date}`]">
                                        <span>{{ item.foodName }}</span>
                                    </template>
                                </button>
                            </template>
                        </div>
                    </template>
                    <template v-else>
                        <div class="weekend none"></div>
                    </template>
                </template>
            </div>
        </div>
        <!-- {{ fullList }} -->
        <!-- {{ fullList[`${day}`] }} -->
        <div class="daily-wrap">
            <template v-if="fullList[`${day}`] && fullList[`${day}`].length > 0">
                <div class="daily-box">
                    <template v-for="item in fullList[`${day}`]">
                        <div class="daily">
                            <strong>{{ item.foodName }}</strong> - {{ item.description }}<br />
                            Calories: {{ item.kcalories }} kcal = Protein: {{ item.protein }}g<br />
                            Fat: {{ item.fat }}g = Carbs: {{ item.carbs }}g<br>
                            Sodium: {{ item.sodium }}g = Sugar: {{ item.sugar }}g<br>
                            Category: {{ item.category }} = Icon: {{ item.icon }}<br>
                        </div>
                    </template>
                </div>
            </template>
            <template v-else>
                <div class="daily-box none"><span>식단을 등록해주세요.</span></div>
            </template>
        </div>
        <button @click="!popupOpen" class="btn-default">식단 등록</button>
    </div>
    <div class="popup-box type-modal" v-if="popupOpen">
        <div class="popup-cont">
            <div class="popup-tit-wrap">
                <h2 class="tit">식단 등록</h2>
            </div>
            <div class="pop-btn-wrap">
                <button class="btn-default submit">등록</button>
                <button class="btn-default cancel">취소</button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  components: {
  },
  data() {
    return {
      dailyLsit: '',
      fullList: '',
      day: "",
      week: [],
      popupOpen: true
    }
  },
  methods: {
    async fnRequest(year, month, day) {
      const daily = await this.$axios.get(`meal/mealDayList/${year}/${month}`);
      const full = await this.$axios.get(`meal/mealList/${year}/${month}`);
      if (daily.status) {
        this.dailyLsit = daily.data
      } else {
        console.log(daily.message)
      }
      if (full.status) {
        this.fullList = full.data.mealList
      } else {
        console.log(full.message)
      }
      await this.fnDayClick(year, month, day);
    },
    async fnDayClick(year, month, day) {
        let firstDay = new Date(year, month-1, 1).getDay();
        let lastDay = new Date(year, month, 0).getDate();
        let toDay = new Date(year, month-1, day).getDate();
        let thisWeek = parseInt(((firstDay - 1) + toDay) / 7);
        this.week = new Array(7).fill(0);
        for (let i = 0; i < 7; i++) {
            let check = (thisWeek === 0) 
                ? i - (firstDay - 1) 
                : i + (7 * thisWeek) - firstDay + 1;            
                this.week[i] = (check > lastDay || check < 1) ? {0 : 0} : { [`${year}-${month}-${check.toString().padStart(2, '0')}`]: check}; // 날짜가 유효하지 않으면 0 처리
        }
        this.day = `${year}-${month}-${day}`;
    }
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD');
    let year = this.day.split('-')[0];
    let month = this.day.split('-')[1];
    //month = 11;
    let day = this.day.split('-')[2];
    //day = '09';
    this.fnRequest(year, month, day);
  }
}
</script>
<style lang="scss">
    .weekend {
        position: relative;
        aspect-ratio: 1/1;
        padding: 20px;
        &-wrap {
            display: flex; width: 100%;
            padding: 10px; border-radius: 10px; overflow: hidden;
            box-shadow: 5px 5px 10px 0 rgba(0, 0, 0, 0.1);
        }
        &-box {
            width: calc(100% / 7);
        }
        .date {
            position: absolute; top: 10px; left: 10px;
        }
        button {
            height: 100%; width: 100%;
            border: 0; vertical-align: top;
        }
    }

    .daily {
        flex: 0 1 calc(50% - 10px); border: 1px solid var(--color-contrastyE5);
        padding: 20px; border-radius: 6px;
        &-wrap {
            margin: 40px 0;
            width: 100%; flex-grow: 1; padding: 20px; 
            border-radius: 10px; overflow-y: scroll;
            box-shadow: 5px 5px 10px 0 rgba(0, 0, 0, 0.1);
        }
        &-box {
            display: flex; flex-wrap: wrap; gap: 20px; width: 100%;
            justify-content: space-between;
            &.none {
                height: 100%; align-items: center; justify-content: center;
                span {
                    background: url('@/assets/images/no_list_icon.png') no-repeat top center; padding-top: 74px;
                    font-size: 18px; font-weight: bold; color: var(--color-contrastyA)
                }
            }
        }
    }
</style>
