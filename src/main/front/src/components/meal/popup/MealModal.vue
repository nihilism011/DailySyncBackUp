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
                  :class="nutrient.icon"
                  :title="nutrient.icon ? nutrient.icon : '아이콘 선택'"
                  @click="iconSelected = !iconSelected"
                >
                  아이콘선택
                </button>
                <IconCheckbox
                  v-if="iconSelected"
                  :icon="nutrient.icon"
                  :iconList="iconList"
                  @iconSelected="valueChange"
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
                  <select id="category" v-model="nutrient.category">
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
                  <button
                    @click="((this.recomState = true), (this.iconSelected = false))"
                    class="btn-default"
                  >
                    추천 및 즐겨찾기
                  </button>
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
                <input type="text" v-model="nutrient.foodName" />
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
                <input type="number" v-model="nutrient.kcalories" />
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
                <input type="number" v-model="nutrient.sodium" />
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
                <input type="number" v-model="nutrient.carbs" />
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
                <input type="number" v-model="nutrient.protein" />
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
                <input type="number" v-model="nutrient.fat" />
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
                <input type="number" v-model="nutrient.sugar" />
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
                <textarea v-model="nutrient.description"></textarea>
              </div>
            </div>
          </div>
        </div>
        <template v-if="!editMode && recomToggle">
          <div class="favorite-box">
            <div class="ip-chk-txt">
              <input type="checkbox" id="favorite" v-model="nutrient.isFavorite" />
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
    ref="recomRefresh"
    :categoryList="categoryList"
    :recomState="recomState"
    @recomClose="recomState = false"
    @recomInfo="fnRecom"
    @recomAdd="recomRefresh"
  />
</template>
<script>
import { useNutrientStore } from '@/stores/nutrientStore'
import IconCheckbox from '@/components/meal/popup/IconCheckbox.vue'
import RecomModal from '@/components/meal/popup/RecomModal.vue'
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
    fnDayList: {
      type: Function,
    },
  },
  emits: ['closePopup', 'fnMealList', 'fnDayList'],
  components: {
    IconCheckbox,
    RecomModal,
  },
  data() {
    return {
      nutrientStore: useNutrientStore(),
      iconSelected: false,
      isFavorite: false,
      editMode: false,
      recomState: false,
      recomToggle: true,
    }
  },
  computed: {
    nutrient() {
      const nutrientData = this.nutrientStore.nutrient
      nutrientData.date = this.day
      return nutrientData
    },
    isEditMode() {
      return this.nutrientStore.editMode
    },
  },
  watch: {
    editInfo() {
      if (Object.keys(this.editInfo).length > 0) {
        this.nutrientStore.setNutrient(this.editInfo)
        this.editMode = true
      } else {
        this.nutrientStore.resetNutrient()
        this.editMode = false
      }
    },
  },
  methods: {
    async fnSubmit() {
      let check = this.valuCheck()
      if (!check[1]) {
        alert(check[0])
        return
      }
      const { id, ...param } = this.nutrient
      const submit = await this.$axios.post(`meal/add`, [param])
      if (submit.status) {
        alert('등록 되었습니다.')
        this.$emit('fnMealList', this.day)
        this.$emit('fnDayList', this.day)
        if (param.isFavorite) {
          this.recomRefresh()
        }
        this.popupClsoe()
      } else {
        alert(submit.message)
      }
    },
    async fnEdit() {
      const param = this.nutrient
      const edit = await this.$axios.patch(`meal/edit`, param)
      if (edit.status) {
        alert('수정 되었습니다.')
        this.$emit('fnMealList', this.day)
        this.popupClsoe()
      } else {
        alert(edit.message)
      }
    },
    valuCheck() {
      let message, boolType
      const messaeIp = ['카테고리를 선택해주세요', '제목을 입력해주세요', '칼로리를 입력해주세요']
      if (this.nutrient.category == '') {
        message = messaeIp[0]
        boolType = false
      } else if (this.nutrient.foodName == '') {
        message = messaeIp[1]
        boolType = false
      } else if (this.nutrient.kcalories == '') {
        message = messaeIp[2]
        boolType = false
      } else {
        boolType = true
      }
      return [message, boolType]
    },
    valueChange(value) {
      this.nutrient.icon = value
      this.iconSelected = false
    },
    popupClsoe() {
      this.recomToggle = true
      this.iconSelected = false
      this.nutrientStore.setNutrient(this.editInfo)
      this.$emit('closePopup')
    },
    fnRecom(item) {
      Object.keys(this.nutrient).forEach((key) => {
        if (key in item[0]) {
          this.nutrient[key] = item[0][key]
        }
      })
      if (item[1]) {
        this.recomToggle = true
      } else {
        this.recomToggle = false
        this.isFavorite = false
      }
    },
    recomRefresh() {
      this.$refs.recomRefresh.fnFavorite()
    },
  },
  mounted() {},
}
</script>
<style lang="scss"></style>
