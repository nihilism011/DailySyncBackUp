<template>
  <div class="left left-container">
    <LeftView />
  </div>
  <div class="right right-container">
    <RightView />
  </div>
</template>
<script>
import RightView from '@/components/account/layout/RightView.vue'
import { useDateStore } from '@/stores/account/dateStore'
import LeftView from '@/components/account/layout/LeftView.vue'
export default {
  components: {
    RightView,
    LeftView,
  },
  setup() {
    const dateStore = useDateStore()
    return { dateStore }
  },
  data() {
    return {
      dateList: [],
      fixedList: [],
      viewUpdatePopup: false,
    }
  },
  methods: {},
}
</script>
<style scoped>
.left-container {
  display: flex;
  flex-direction: column;
}
.right-container {
  display: flex;
  flex-direction: column;
}
.insert-btn {
  align-self: center;
  margin: 10px;
  width: 500px;
  font-size: 20px;
  font-weight: bold;
  background-color: white;
  border: 3px solid black;
  height: 50px;
  border-radius: 10px;
  transition:
    background-color 0.3s ease,
    color 0.3s ease,
    transform 0.3s ease,
    box-shadow 0.2s ease;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
  &:hover {
    background-color: black;
    color: white;
    border-color: rgb(26, 26, 25);
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
  }
}
</style>
