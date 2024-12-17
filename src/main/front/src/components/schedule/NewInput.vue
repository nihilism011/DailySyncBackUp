<template>
  <div>
    <div>제목</div>
    <input type="text" v-model="inputSchedule.title" />
    <div>시작시간</div>
    <input type="text" v-model="inputSchedule.startDate" />
    <div>끝 시간</div>
    <input type="text" v-model="inputSchedule.endDate" />
    <div>설명</div>
    <input type="text" v-model="inputSchedule.description" />
  </div>
</template>
<script>
export default {
  props: {
    schedule: Object,
  },
  data() {
    return {
      inputSchedule: {
        id: '',
        title: '',
        description: '',
        startDate: '',
        endDate: '',
        ...this.schedule,
      },
    }
  },
  methods: {
    async fnSave() {
      const schedule = this.schedule
      let url
      if (schedule.id) {
        url = `업데이트 api 주소`
      } else {
        url = `새로 만드는  api 주소`
      }
      const res = await this.$axios.get(url, schedule)
      if (res.status) {
        alert('완료후 로직')
      }
    },
  },
}
</script>
<style lang=""></style>
