import { defineStore } from 'pinia'

export const useNutrientStore = defineStore('nutrientStore', {
  state: () => ({
    nutrient: {
      id: '',
      icon: '',
      category: '',
      foodName: '',
      description: '',
      date: '',
      kcalories: 0,
      sugar: 0,
      protein: 0,
      fat: 0,
      carbs: 0,
      sodium: 0,
      isFavorite: false,
    },
    editMode: false,
  }),
  actions: {
    setNutrient(data) {
      this.nutrient = { ...data }
    },
    resetNutrient() {
      this.nutrient = {
        id: null,
        icon: '',
        category: '',
        foodName: '',
        description: '',
        date: '',
        kcalories: 0,
        sugar: 0,
        protein: 0,
        fat: 0,
        carbs: 0,
        sodium: 0,
        isFavorite: false,
      }
    },
    setEditMode(edit) {
      this.editMode = edit
    },
  },
  getters: {
    getNutrient(state) {
      return state.nutrient
    },
    isEditMode(state) {
      return state.editMode
    },
  },
})
