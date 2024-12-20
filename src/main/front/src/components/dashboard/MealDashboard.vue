<template>
  <div class="dash-wrap">
    <div class="dash-tit-box">
      <p class="tit"><span>오늘의 칼로리</span> <br />{{ kcalories }}Kcal</p>
    </div>
    <div class="dash-item-wrap">
      <template v-if="mealList != null || mealList != undefined">
        <div class="dash-item" v-for="item in mealList">
          <p class="dash-icon" :class="item.icon">{{ item.icon }}</p>
          <p class="tit">{{ item.foodName }}</p>
          <p class="category">{{ this.mealCategory[item.category].name }}</p>
          <p class="kcal">{{ item.kcalories }}Kcal</p>
        </div>
      </template>
      <template v-else>
        <div class="dash-item none"><span>등록한 식단이 없어요</span></div>
      </template>
    </div>
  </div>
</template>

<script>
import { mealCategory } from '@/constants/mealCategory'

export default {
  props: {
    selectedDate: String,
  },
  watch: {
    selectedDate() {
      this.fnGetList()
    },
  },
  data() {
    return {
      mealList: [],
      mealCategory,
      kcalories: 0,
    }
  },
  methods: {
    async fnGetList() {
      const day = this.selectedDate.split('-')
      const { data } = await this.$axios.get(`meal/mealList/${day[0]}/${day[1]}`)
      this.mealList = data.mealList[this.selectedDate]
      this.kcalories = 0
      if (this.mealList?.length != undefined) {
        for (let i in this.mealList) {
          this.kcalories += this.mealList[i].kcalories
        }
      }
    },
  },
  mounted() {
    this.fnGetList()
  },
}
</script>

<style lang="scss" scoped>
.dash {
  &-item {
    display: flex;
    align-items: center;
    min-height: 60px;
    padding: 10px 5px;
    gap: 0 8px;
    font-size: 14px;
    border-bottom: 1px dashed var(--color-contrastyC);
    &-wrap {
      overflow-y: auto;
      flex-grow: 1;
      max-height: 590px;
      border-top: 1px solid var(--color-contrastyC);
      border-bottom: 1px solid var(--color-contrastyC);
    }
    &:last-of-type {
      border-bottom: 0;
    }
    .tit {
      flex-grow: 1;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
    }
    .category {
      min-width: 40px;
    }
    .kcal {
      min-width: 70px;
    }
    &.none {
      height: 100%;
      justify-content: center;
      color: var(--color-contrastyA);
      font-weight: bold;
      font-size: 16px;
      span {
        padding-top: 62px;
        background: url('@/assets/images/no_list_icon.png') top center / 60px no-repeat;
      }
    }
  }
  &-icon {
    width: 40px;
    min-width: 40px;
    height: 40px;
    font-size: 0;
    border-radius: 4px;
    background: url('@/assets/images/ico/icon_default.png') center / 30px no-repeat;
    $iconClass: (
      bread,
      burger,
      cake,
      chicken,
      egg,
      fish,
      fruit,
      icecream,
      milk,
      noodle,
      pizza,
      rice,
      sausage,
      snack,
      steak
    );
    @each $icon in $iconClass {
      &.#{$icon} {
        background-image: url('@/assets/images/ico/icon_#{$icon}.png');
        font-size: 0;
      }
    }
  }
  &-tit-box {
    .tit {
      font-size: 32px;
      text-align: center;
      font-weight: bold;
      span {
        font-size: 16px;
      }
    }
  }
  &-wrap {
    display: flex;
    flex-direction: column;
    height: 100%;
    gap: 14px 0;
  }
}
</style>
