<template>
    <div class="meal-icon-box" >
        <input type="radio" name="icon" id="icon1" value="chicken" v-model="selectedOption" @change="fnIconCheck"><label for="icon1">치킨</label>
        <input type="radio" name="icon" id="icon2" value="pizza" v-model="selectedOption" @change="fnIconCheck"><label for="icon2">피자</label>
        <input type="radio" name="icon" id="icon3" value="egg" v-model="selectedOption" @change="fnIconCheck"><label for="icon3">계란</label>
        <!-- <input type="radio" id="icon" value=""><label for="icon"></label> -->
    </div>
</template>
<script>
export default {
    data() {
        return {
            selectedOption: ''
        }
    },
    methods: {
        fnIconCheck() {
            this.$emit('iconSelected', this.selectedOption);
        }
    }
}
</script>

<style lang="scss">
    .meal-icon-box {
        display: flex;
        label {
            width: 60px; height: 60px; border-radius: 4px;
            border: 1px solid var(--color-contrastyC);
        }
    }    
</style>