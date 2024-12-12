<template>
  <div class="weekend-wrap">
    <div class="weekend-box" v-for="weekList in week">
      <template v-for="(days, day) in weekList">
        <template v-if="days != 0">
          <div class="weekend">
            <span
              class="date"
              @click="fnDayClick(day.split('-')[0], day.split('-')[1], day.split('-')[2])"
              >{{ days }}</span
            >
            <template v-if="fullList[`${day}`]">
              <button @click="fnDayClick(day.split('-')[0], day.split('-')[1], day.split('-')[2])">
                <template v-for="item in fullList[`${day}`].slice().sort((a, b) => a.id - b.id)">
                  <span>{{ item.foodName }}</span>
                </template>
              </button>
            </template>
          </div>
        </template>
        <template v-else>
          <div class="weekend none"></div>
        </template>
      </template>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    week: {
      type: Array,
    },
    fullList: {
      type: Object,
    },
    day: {
      type: String,
    },
  },
  data() {
    return {}
  },
  methods: {
    async fnDayClick(year, month, day) {
      this.$parent.fnDayClick(year, month, day)
    },
  },
}
</script>
<style lang="scss" scoped>
.weekend {
  position: relative;
  aspect-ratio: 1/1;
  padding: 20px;
  &-wrap {
    display: flex;
    width: 100%;
    padding: 10px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
  }
  &-box {
    width: calc(100% / 7);
  }
  .date {
    position: absolute;
    top: 10px;
    left: 10px;
    cursor: pointer;
  }
  button {
    height: 100%;
    width: 100%;
    border: 0;
    vertical-align: top;
    display: flex;
    flex-direction: column;
    gap: 5px 0;
    align-items: center;
    span {
      font-size: 12px;
      transition: all 0.2s;
    }
  }
}
</style>
