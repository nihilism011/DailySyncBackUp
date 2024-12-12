<template>
  <div class="daily-wrap">
    <template v-if="fullList[`${day}`] && fullList[`${day}`].length > 0">
      <div class="daily-box">
        <template v-for="item in fullList[`${day}`].slice().sort((a, b) => a.id - b.id)">
          <div class="daily">
            <div class="icon-box">
              <i class="icon" :class="item.icon">이미지</i>
            </div>
            <div class="txt-box">
              <div class="tit-box">
                <p class="category">{{ getMealName(item.category) }}</p>
                <strong class="tit">{{ item.foodName }}</strong>
              </div>
              <div class="info-box">
                <p class="info"><span class="tit">칼로리:&nbsp;</span>{{ item.kcalories }}kcal</p>
                <p class="info"><span class="tit">나트륨:&nbsp;</span>{{ item.sodium }}mg</p>
                <p class="info"><span class="tit">탄수화물:&nbsp;</span>{{ item.carbs }}g</p>
                <p class="info"><span class="tit">단백질:&nbsp;</span>{{ item.protein }} g</p>
                <p class="info"><span class="tit">지방:&nbsp;</span>{{ item.fat }}g</p>
                <p class="info"><span class="tit">당:&nbsp;</span>{{ item.sugar }}g</p>
              </div>
              <div class="desc-box">{{ item.description }}</div>
            </div>
            <div class="btn-box">
              <button @click="fnEdit(item)" class="edit-btn" title="수정">수정</button>
              <button @click="fnRemove(item.id)" class="remove-btn" title="삭제">삭제</button>
            </div>
          </div>
        </template>
      </div>
    </template>
    <template v-else>
      <div class="daily-box none"><span>식단을 등록해주세요.</span></div>
    </template>
  </div>
</template>
<script>
export default {
  props: {
    fullList: {
      type: Object,
    },
    categoryList: {
      type: Object,
    },
    day: {
      type: String,
    },
    popupOpen: {
      type: Boolean,
    },
  },
  emits: ['openPopup', 'closePopup', 'fnMealList', 'fnDayList'],
  data() {
    return {}
  },
  methods: {
    async fnRemove(id) {
      if (confirm('삭제하시겠습니까?')) {
        const remove = await this.$axios.delete(`meal/delete/${id}`)
        if (remove.status) {
          this.$emit('fnMealList', this.day)
          this.$emit('fnDayList', this.day)
        }
      }
    },
    fnEdit(id) {
      this.$emit('openPopup', id)
    },
    getMealName(category) {
      return this.categoryList[category]?.name || 'empty'
    },
  },
}
</script>
<style lang="scss" scoped>
.daily {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 0 20px;
  flex: 0 1 calc(50% - 10px);
  border: 1px solid var(--color-contrastyE5);
  padding: 20px;
  border-radius: 6px;
  &-wrap {
    width: 100%;
    flex-grow: 1;
    padding: 20px;
    border-radius: 10px;
    overflow-y: scroll;
    box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
  }
  &-box {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    width: 100%;
    justify-content: space-between;
    &.none {
      height: 100%;
      align-items: center;
      justify-content: center;
      span {
        background: url('@/assets/images/no_list_icon.png') no-repeat top center;
        padding-top: 74px;
        font-size: 18px;
        font-weight: bold;
        color: var(--color-contrastyA);
      }
    }
  }
  .icon-box {
    display: flex;
    min-width: 60px;
    height: 60px;
    font-size: 0;
    border-radius: 4px;
    .icon {
      width: 100%;
      height: 100%;
      background: no-repeat center;
      background: center / 40px no-repeat;
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
  }
  .txt-box {
    width: calc(100% - 80px);
    .tit-box {
      display: flex;
      gap: 0 14px;
      align-items: center;
      .category {
        padding: 5px 10px;
        font-size: 14px;
        border-radius: 4px;
        background-color: var(--color-contrastyA);
        color: var(--base-white);
      }
      .tit {
        font-size: 20px;
      }
    }
    .info-box {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      margin-top: 20px;
      gap: 5px;
      width: 100%;
      .info {
        flex: 0 1 calc(50% - 2.5px);
        font-size: 14px;
        .tit {
          font-weight: bold;
        }
      }
    }
    .desc-box {
      margin-top: 20px;
      line-height: 1.4;
    }
  }
  .btn-box {
    display: flex;
    align-items: center;
    gap: 0 4px;
    button {
      min-width: 35px;
      height: 35px;
      font-size: 0;
      background: center / 20px no-repeat #fff;
      &.edit-btn {
        background-image: url('@/assets/images/ico/ico_edit.png');
      }
      &.remove-btn {
        background-image: url('@/assets/images/ico/ico_remove.png');
      }
    }
  }
}
</style>
