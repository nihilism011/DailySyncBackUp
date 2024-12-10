<template>
  <div class="meal-icon-box">
    <!-- {{ iconList }} -->
    <template v-for="(item, key, index) in iconList" :key="key">
      <input
        type="radio"
        name="icon"
        :id="'icon' + index"
        :checked="icon == item.ename"
        :value="item.ename"
        v-model="selectedOption"
        @change="fnIconCheck"
      />
      <label :for="'icon' + index" :class="item.ename" :title="item.ename">{{ item.name }}</label>
    </template>
  </div>
</template>
<script>
export default {
  props: {
    icon: {
      type: String,
    },
    iconList: {
      type: Object,
    },
  },
  data() {
    return {
      selectedOption: '',
    }
  },
  methods: {
    fnIconCheck() {
      this.$emit('iconSelected', this.selectedOption)
    },
  },
}
</script>

<style lang="scss" scoped>
.meal-icon-box {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  width: 300px;
  border: 1px solid var(--color-contrastyC);
  border-radius: 4px;
  padding: 10px 5px 10px 15px;
  background: var(--base-white);
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 10;
  input {
    display: none;
    &:checked + label {
      background-color: var(--color-contrastyC);
    }
  }
  label {
    width: 60px;
    height: 60px;
    border-radius: 4px;
    border: 1px solid var(--color-contrastyC);
    font-size: 0;
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
      }
    }
  }
}
</style>
