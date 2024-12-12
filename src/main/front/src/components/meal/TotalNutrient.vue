<template>
  <!-- {{ todayNutrient }} -->
  <div class="nutrient-wrap">
    <div class="nutrient-tit-wrap">
      <p class="nutrient-tit">오늘 섭취 영양소(총합)</p>
    </div>
    <div class="nutrient-box">
      <span class="nutrient-item" v-for="(val, key) in Object(todayNutrient)">
        <span>{{ nameChange(key, 0) }}</span> : {{ val }}{{ nameChange(key, 1) }}
      </span>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    todayNutrient: {
      type: Object,
    },
  },
  data() {
    return {
      nutrientName: {
        kcalories: ['칼로리', 'kcal'],
        sodium: ['나트륨', 'mg'],
        carbs: ['탄수화물', 'g'],
        protein: ['단백질', 'g'],
        fat: ['지방', 'g'],
        sugar: ['당', 'g'],
      },
    }
  },
  methods: {
    nameChange(name, idx) {
      return this.nutrientName[name][idx]
    },
  },
  mounted() {},
}
</script>
<style lang="scss" scoped>
.nutrient {
  &-wrap {
    margin-top: 30px;
    box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    padding: 10px 20px 20px;
  }
  &-box {
    display: flex;
    align-items: center;
    gap: 8px 0;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  &-item {
    flex: 0 1 50%;
    color: var(--color-contrasty54);
    span {
      font-weight: bold;
      color: var(--color-contrasty2);
    }
  }
  &-tit {
    font-size: 18px;
    font-weight: bold;
    &-wrap {
      margin-bottom: 10px;
    }
  }
}
</style>
