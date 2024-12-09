<template>
    <div class="left">
        {{ dailyLsit }}
    </div>
    <div class="right meal-type">
        <WeekendList 
            :fullList="fullList"
            :day="day"
            :week="week"
        />
        <DayList 
            :fullList="fullList" 
            :day="day"
            :categoryList="categoryList"
            @fnRequest="fnRequest"
        />
        <button @click="popupOpen = !popupOpen" class="btn-default">식단 등록</button>
    </div>
    <div class="popup-box type-modal" v-if="popupOpen">
        <div class="popup-cont">
            <div class="popup-tit-wrap">
                <h2 class="tit">식단 등록</h2>
            </div>
            <div class="meal-add-wrap">
                <div class="icon-box">
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">아이콘 선택</p>
                        </div>
                        <div class="bot-box">
                            <button class="icon-btn" :class="icon" :title="icon" @click="iconSelected = !iconSelected">아이콘선택</button>
                            <IconCheckbox v-if="iconSelected"
                                :icon="icon" 
                                :iconList="iconList"
                                @iconSelected="handleValueSelected"
                            />
                        </div>
                    </div>
                </div>
                <div class="category-box">
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">카테고리 선택</p>
                        </div>
                        <div class="bot-box">
                            <div class="select-box">
                                <select id="category" v-model="category">
                                    <option value="" hidden>카테고리 선택</option>
                                    <option v-for="item in categoryList" :value="item.ename">{{ item.name }}</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tit-box">
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">제목</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="foodName">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="nutrient-box">
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">칼로리</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="kcalories">
                                <span class="unit">kcal</span>
                            </div>
                        </div>
                    </div>
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">나트륨</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="sodium">
                                <span class="unit">mg</span>
                            </div>
                        </div>
                    </div>
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">탄수화물</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="carbs">
                                <span class="unit">g</span>
                            </div>
                        </div>
                    </div>
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">단백질</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="protein">
                                <span class="unit">g</span>
                            </div>
                        </div>
                    </div>
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">지방</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="fat">
                                <span class="unit">g</span>
                            </div>
                        </div>
                    </div>
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">당</p>
                        </div>
                        <div class="bot-box">
                            <div class="ip-box">
                                <input type="text" v-model="sugar">
                                <span class="unit">g</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="description-box">
                    <div class="ip-list">
                        <div class="tit-box">
                            <p class="tit">기타 설명</p>
                        </div>
                        <div class="bot-box">
                            <div class="text-box">
                                <textarea v-model="description"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="favorite-box">
                    <div class="ip-chk-txt">
                        <input type="checkbox" id="favorite" v-model="isFavorite">
                        <label for="favorite">즐겨찾기 추가</label>
                    </div>
                </div>
            </div>
            <div class="pop-btn-wrap">
                <button class="btn-default submit" @click="fnSubmit">등록</button>
                <button class="btn-default cancel" @click="popupOpen = false">취소</button>
            </div>
        </div>
        <div class="dimmed" @click="popupOpen = false">dim</div>
    </div>
</template>
<script>
import WeekendList from "@/components/meal/WeekendList.vue";
import DayList from "@/components/meal/DayList.vue";
import IconCheckbox from "@/components/meal/IconCheckbox.vue"
import { mealCategory } from "@/constants/mealCategory.js"
import { iconCategory } from "@/constants/iconCategory.js"
export default {
  components: {
    WeekendList,
    DayList,
    IconCheckbox,
  },
  data() {
    return {
      dailyLsit: [],
      fullList: [],
      day: "",
      week: [],
      popupOpen: false,
      icon: "",
      iconSelected: false,
      category: "",
      foodName: "",
      description: "",
      kcalories: 0,
      sugar: 0,
      protein: 0,
      fat: 0,
      carbs: 0,
      sodium: 0,
      isFavorite: false,
      categoryList: mealCategory,
      iconList: iconCategory
    }
  },
  methods: {
    async fnRequest(inputDay) {
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      let day = inputDay.split('-')[2];
      //day = '03';
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
    },
    handleValueSelected(value) {
        this.icon = value;
    },
    async fnSubmit() {
        console.log(this.day);
        const param = [
            {
                icon: this.icon,
                category: this.category,
                foodName: this.foodName,
                description: this.description,
                kcalories: this.kcalories,
                sugar: this.sugar,
                protein: this.protein,
                fat: this.fat,
                carbs: this.carbs,
                sodium: this.sodium,
                isFavorite: this.isFavorite,
                date: this.day
            }
        ]
        const submit = await this.$axios.post(`meal/add`, param);
        console.log(submit);
        if(submit.status) {
            alert("등록 되었습니다.");
            this.fnRequest(this.$dayjs().format('YYYY-MM-DD'));
            this.popupOpen = false;
        } else {
            alert(submit.message);
        }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD');
    this.fnRequest(this.day);
  }
}
</script>
<style lang="scss">
</style>
