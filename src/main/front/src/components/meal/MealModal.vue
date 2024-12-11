<template>
  <div class="popup-box type-modal" v-if="popupState">
    <div class="popup-cont">
      <div class="popup-tit-wrap">
        <h2 class="tit">식단 등록</h2>
      </div>
      <div class="meal-add-wrap">
        <div class="meal-top-wrap">
          <div class="icon-box">
            <div class="ip-list">
              <div class="tit-box">
                <p class="tit">아이콘 선택</p>
              </div>
              <div class="bot-box">
                <button
                  class="icon-btn"
                  :class="icon"
                  :title="icon"
                  @click="iconSelected = !iconSelected"
                >
                  아이콘선택
                </button>
                <IconCheckbox
                  v-if="iconSelected"
                  :icon="icon"
                  :iconList="iconList"
                  @iconSelected="valueChecge"
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
                    <option v-for="item in categoryList" :value="item.ename">
                      {{ item.name }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <template v-if="!editMode">
            <div class="recom-box">
              <div class="ip-list">
                <div class="tit-box">
                  <p class="tit">추천 및 즐겨찾기</p>
                </div>
                <div class="bot-box">
                  <button @click="openRecom" class="btn-default">추천 및 즐겨찾기</button>
                </div>
              </div>
            </div>
          </template>
        </div>
        <div class="tit-box">
          <div class="ip-list">
            <div class="tit-box">
              <p class="tit">제목</p>
            </div>
            <div class="bot-box">
              <div class="ip-box">
                <input type="text" v-model="foodName" />
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
                <input type="number" v-model="kcalories" />
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
                <input type="number" v-model="sodium" />
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
                <input type="number" v-model="carbs" />
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
                <input type="number" v-model="protein" />
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
                <input type="number" v-model="fat" />
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
                <input type="number" v-model="sugar" />
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
        <template v-if="!editMode && recomToggle">
          <div class="favorite-box">
            <div class="ip-chk-txt">
              <input type="checkbox" id="favorite" v-model="isFavorite" />
              <label for="favorite">즐겨찾기 추가</label>
            </div>
          </div>
        </template>
      </div>
      <div class="pop-btn-wrap">
        <template v-if="editMode">
          <button class="btn-default submit" @click="fnEdit">수정</button>
        </template>
        <template v-else>
          <button class="btn-default submit" @click="fnSubmit">등록</button>
        </template>
        <button class="btn-default cancel" @click="popupClsoe">취소</button>
      </div>
    </div>
    <div class="dimmed" @click="popupClsoe">dim</div>
  </div>
  <RecomModal
    :categoryList="categoryList"
    :recomState="recomState"
    @recomClose="recomState = false"
    @recomInfo="fnRecom"
  />
</template>
<script>
import IconCheckbox from '@/components/meal/IconCheckbox.vue'
import RecomModal from '@/components/meal/RecomModal.vue'
export default {
  props: {
    popupState: {
      type: Boolean,
    },
    categoryList: {
      type: Object,
    },
    iconList: {
      type: Object,
    },
    editInfo: {
      type: Object,
    },
    day: {
      type: String,
    },
  },
  emits: ['closePopup', 'fnMealList'],
  components: {
    IconCheckbox,
    RecomModal,
  },
  data() {
    return {
      id: '',
      icon: '',
      iconSelected: false,
      category: '',
      foodName: '',
      description: '',
      kcalories: 0,
      sugar: 0,
      protein: 0,
      fat: 0,
      carbs: 0,
      sodium: 0,
      isFavorite: false,
      editMode: false,
      recomState: false,
      recomToggle: true,
    }
  },
  watch: {
    editInfo() {
      if (Object.keys(this.editInfo).length > 0) {
        this.editMode = true
        Object.keys(this.$data).forEach((key) => {
          if (key in this.editInfo) {
            this[key] = this.editInfo[key]
          }
        })
      } else {
        this.editMode = false
        this.resetToDefault()
      }
    },
  },
  methods: {
    async fnSubmit() {
      const param = this.fnParam()
      const submit = await this.$axios.post(`meal/add`, param)
      if (submit.status) {
        alert('등록 되었습니다.')
        this.$emit('fnMealList', this.$dayjs().format('YYYY-MM-DD'))
        this.popupClsoe()
      } else {
        alert(submit.message)
      }
    },
    async fnEdit() {
      const param = this.fnParam()
      const edit = await this.$axios.patch(`meal/edit`, param)
      if (edit.status) {
        alert('수정 되었습니다.')
        this.$emit('fnMealList', this.$dayjs().format('YYYY-MM-DD'))
        this.popupClsoe()
      } else {
        alert(edit.message)
      }
    },
    valueChecge(value) {
      this.icon = value
      this.iconSelected = false
    },
    popupClsoe() {
      this.$emit('closePopup')
    },
    resetToDefault() {
      this.id = ''
      this.icon = ''
      this.iconSelected = false
      this.category = ''
      this.foodName = ''
      this.description = ''
      this.kcalories = 0
      this.sugar = 0
      this.protein = 0
      this.fat = 0
      this.carbs = 0
      this.sodium = 0
      this.isFavorite = false
      this.editMode = false
    },
    fnParam() {
      if (this.id == null || this.id == '') {
        return [this.paramInfo()]
      } else {
        return this.paramInfo()
      }
    },
    paramInfo() {
      return {
        id: this.id,
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
        date: this.day,
      }
    },
    fnRecom(item) {
      Object.keys(this.$data).forEach((key) => {
        if (key in item[0]) {
          this[key] = item[0][key]
        }
      })
      if (item[1]) {
        this.recomToggle = true
      } else {
        this.recomToggle = false
        this.isFavorite = false
      }
    },
    openRecom() {
      this.recomState = true
    },
    closeRecom() {
      this.recomState = false
    },
  },
  mounted() {},
}
</script>
<style lang="scss"></style>
