<template>
  <!-- {{ todayNutrient }} -->
  <div class="nutrient-wrap">
    <div class="nutrient-tit-wrap">
      <p class="nutrient-tit">오늘 섭취 영양소(총합)</p>
      <p class="nutrient-kcal" :class="aci > 0 ? 'minus' : 'plus'">
        <template v-if="bmrState"
          ><RouterLink to="/userInfo">키와 몸무게를 설정해주세요</RouterLink></template
        >
        <template v-else>
          <span class="info">
            <template v-if="iPbmr == null"><b>권장</b>: {{ bmr }}kcal</template>
            <template v-else
              ><b>입력</b>: {{ iPbmr }}kcal<span>(<b>권장</b>: {{ bmr }}kcal)</span></template
            >
          </span>
          <span class="aci"
            >{{ aci > 0 ? '섭취 가능' : '초과 섭취' }}: {{ Math.abs(aci) }} Kcal</span
          >
        </template>
      </p>
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
  watch: {
    todayNutrient() {
      this.fnUserInfo()
    },
  },
  data() {
    return {
      bmrState: false,
      bmr: 0,
      iPbmr: '',
      aci: 0,
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
    async fnUserInfo() {
      const userInfo = await this.$axios.get('user/info')
      this.fnBmr(userInfo.data)
    },
    fnBmr(data) {
      if (data.height == null || data.weight == null) {
        this.bmrState = true
        return
      }
      this.bmr = 10 * data.weight + 6.25 * data.height + (data.gender === 'M' ? -5 : -161)
      this.iPbmr = data.recommendCal
      this.aci = (this.iPbmr ?? this.bmr) - this.todayNutrient.kcalories
    },
    nameChange(name, idx) {
      return this.nutrientName[name][idx]
    },
  },
  mounted() {
    this.fnUserInfo()
  },
}
</script>
<style lang="scss" scoped>
.nutrient {
  &-wrap {
    margin-top: 30px;
    box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    padding: 15px 20px 20px;
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
    text-align: center;
    &-wrap {
      margin-bottom: 20px;
      padding-bottom: 12px;
      border-bottom: 1px solid var(--color-contrastyC);
    }
  }
  &-kcal {
    display: flex;
    justify-content: space-evenly;
    margin-top: 5px;
    .info {
      b {
        font-weight: bold;
      }
      span {
        color: var(--color-contrastyA);
        margin-left: 6px;
        font-size: 12px;
      }
    }
    .aci {
      font-weight: bold;
    }
    &.minus {
      .aci {
        color: var(--color-blue);
      }
    }
    &.plus {
      .aci {
        color: var(--color-red);
      }
    }
  }
}
</style>
