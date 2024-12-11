<template>
  <div class="popup-box type-modal" v-if="recomState">
    <div class="popup-cont">
      <div class="popup-tit-wrap">
        <h2 class="tit">추천 및 즐겨찾기</h2>
      </div>
      <div class="tab-default">
        <button
          class="tab-btn"
          :class="showChange == true ? 'active' : ''"
          @click="fnShowChange(true)"
        >
          추천
        </button>
        <button
          class="tab-btn"
          :class="showChange == false ? 'active' : ''"
          @click="fnShowChange(false)"
        >
          즐겨찾기
        </button>
      </div>
      <div class="recom-wrap">
        <template v-for="items in showChange ? recommandList : favoriteList">
          <div class="recom-box" v-for="item in items">
            <button @click="fnAdd(item)" class="list-btn">
              <span class="icon-box">
                <span class="icon" :class="item.icon">{{ item.icon }}</span>
              </span>
              <span class="info-box">
                <span>제목: {{ item.foodName }}</span>
                <span>카테고리: {{ getMealName(item.category) }}</span>
                <span>칼로리: {{ item.kcalories }}kcal</span>
                <span>나트륨: {{ item.sodium }}mg</span>
                <span>탄수화물: {{ item.carbs }}g</span>
                <span>단백질: {{ item.protein }}g</span>
                <span>지방: {{ item.fat }}g</span>
                <span>당: {{ item.sugar }}g</span>
                <span>설명: {{ item.description }}</span>
              </span>
            </button>
            <button @click="fnRemove(item.id)" v-if="!showChange" class="remove-btn" title="삭제">
              삭제
            </button>
          </div>
        </template>
      </div>
      <div class="pop-btn-wrap">
        <button class="btn-default cancel" @click="popupClsoe">취소</button>
      </div>
    </div>
    <div class="dimmed" @click="popupClsoe">dim</div>
  </div>
</template>
<script>
export default {
  props: {
    categoryList: {
      type: Object,
    },
    recomState: {
      type: Boolean,
    },
  },
  data() {
    return {
      recommandList: [],
      favoriteList: [],
      showChange: true,
    }
  },
  methods: {
    async fnRecomList() {
      const recommand = await this.$axios.get(`meal/recommand`)
      if (recommand.status) {
        this.recommandList = recommand.data
      } else {
        console.log(recommand.message)
      }

      const favorite = await this.$axios.get(`meal/favorite`)
      if (favorite.status) {
        this.favoriteList = favorite.data
      } else {
        console.log(favorite.message)
      }
    },
    fnShowChange(state) {
      this.showChange = state
    },
    fnAdd(item) {
      const { id, ...itemWithoutId } = item
      let showChange = this.showChange
      this.$emit('recomInfo', [itemWithoutId, showChange])
      this.$emit('recomClose')
    },
    getMealName(category) {
      return this.categoryList[category]?.name || 'empty'
    },
    popupClsoe() {
      this.$emit('recomClose')
    },
    async fnRemove(id) {
      console.log(id)
      if (confirm('삭제하시겠습니까?')) {
        const remove = await this.$axios.delete(`meal/delete/${id}`)
        if (remove.status) {
          this.fnRecomList()
        }
      }
    },
  },
  mounted() {
    this.fnRecomList()
  },
}
</script>
<style lang="scss" scoped>
.recom {
  &-wrap {
    height: 260px;
    margin-top: 20px;
    overflow-y: auto;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3) inset;
  }
  &-box {
    display: flex;
    align-items: center;
    border-bottom: 1px solid var(--color-contrastyE5);
    transition: all 0.2s;
    padding: 15px;
    gap: 10px;
    .list-btn {
      border: none;
      width: 100%;
      display: flex;
      align-items: center;
      gap: 0 10px;
    }
    .remove-btn {
      min-width: 35px;
      height: 35px;
      font-size: 0;
      background: url('@/assets/images/ico/ico_remove.png') center / 20px no-repeat #fff;
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
    .info-box {
      display: flex;
      flex-wrap: wrap;
      gap: 5px 10px;
    }
    &:hover {
      background-color: var(--color-contrastyE5);
    }
  }
}
</style>
