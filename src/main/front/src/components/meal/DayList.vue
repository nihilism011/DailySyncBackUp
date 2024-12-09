<template>
    <div class="daily-wrap">
        <template v-if="fullList[`${day}`] && fullList[`${day}`].length > 0">
            <div class="daily-box">
                <template v-for="item in fullList[`${day}`].slice().sort((a, b) => a.id - b.id)">
                    <div class="daily">
                        <template v-if="editNumber == item.id">
                            바꿀거야
                        </template>
                        <template v-else>
                            <div class="icon-box">
                                <i class="icon" :class="item.icon">이미지</i>
                            </div>
                            <div class="txt-box">
                                <div class="tit-box">
                                    <p class="category">{{ item.category == "BREAKFAST" ? "아침": item.category == "LUNCH" ? "점심" : item.category == "DINNER" ? "저녁" : item.category == "SNACK" ? "간식" : "야식" }}</p>
                                    <strong class="tit">{{ item.foodName }}</strong>
                                </div>
                                <div class="info-box">
                                    <p class="info"><span class="tit">Calories:&nbsp;</span>{{ item.kcalories }}kcal</p>
                                    <p class="info"><span class="tit">Sodium:&nbsp;</span>{{ item.sodium }}mg</p>
                                    <p class="info"><span class="tit">Carbs:&nbsp;</span>{{ item.carbs }}g</p>
                                    <p class="info"><span class="tit">Protein:&nbsp;</span>{{ item.protein }} g</p>
                                    <p class="info"><span class="tit">Fat:&nbsp;</span>{{ item.fat }}g</p>
                                    <p class="info"><span class="tit">Sugar:&nbsp;</span>{{ item.sugar }}g</p>
                                </div>
                                <div class="desc-box">
                                    {{ item.description }}
                                </div>
                            </div>
                        </template>
                        <div class="btn-box">
                            <button @click="fnPatch(item.id)" v-if="editNumber == item.id">저장</button>
                            <button @click="fnEdit(item.id)" v-else>수정</button>
                            <button @click="fnRemove(item.id)">삭제</button>
                        </div>
                    </div>
                </template>
            </div>
        </template>
        <template v-else>
            <div class="daily-box none"><span>식단을 등록해주세요.</span></div>
        </template>
    </div>
</template>
<script>
export default {
    props: {
        fullList: {
            type: Object
        },
        day: {
            type: String
        },
    },
    data() {
        return {
            editNumber: "",
            category: "",
            foodName: "",
            description: "",
            kcalories: 0,
            sugar: 0,
            protein: 0,
            fat: 0,
            carbs: 0,
            sodium: 0,
        }
    },
    methods: {        
        async fnRemove(id) {
            if(confirm("삭제하시겠습니까?")) {
                const remove = await this.$axios.delete(`meal/delete/${id}`);
                if(remove.status) {
                    this.$emit('fnRequest', this.day);
                }
            }
        },
        fnEdit(id) {
            this.editNumber = id;
            console.log(id);
        },
        fnPatch(id) {            
            this.editNumber = "";
            console.log(id);
        }
    }
}
</script>
<style lang="scss">
    .daily {
        display: flex; align-items: flex-start; justify-content: space-between; gap: 0 20px;
        flex: 0 1 calc(50% - 10px); border: 1px solid var(--color-contrastyE5);
        padding: 20px; border-radius: 6px;
        &-wrap {
            margin: 40px 0;
            width: 100%; flex-grow: 1; padding: 20px; 
            border-radius: 10px; overflow-y: scroll;
            box-shadow: 5px 5px 10px 0 rgba(0, 0, 0, 0.1);
        }
        &-box {
            display: flex; flex-wrap: wrap; gap: 20px; width: 100%;
            justify-content: space-between;
            &.none {
                height: 100%; align-items: center; justify-content: center;
                span {
                    background: url('@/assets/images/no_list_icon.png') no-repeat top center; padding-top: 74px;
                    font-size: 18px; font-weight: bold; color: var(--color-contrastyA)
                }
            }
        }
        .icon-box {
            min-width: 60px; height: 60px; font-size: 0; border-radius: 4px;
            .icon {
                width: 100%; height: 100%; background: no-repeat center;
            }
        }
        .txt-box {
            width: calc(100% - 80px);
            .tit-box {
                display: flex; gap: 0 14px; align-items: center;
                .category {
                    padding: 5px 10px; font-size: 14px; border-radius: 4px;
                    background-color: var(--color-contrastyA); color: var(--base-white);
                }
                .tit {
                    font-size: 20px;
                }
            }
            .info-box {
                display: flex; justify-content: space-between; flex-wrap: wrap;
                margin-top: 20px; gap: 5px; width: 100%;
                .info {
                    flex: 0 1 calc(50% - 2.5px); font-size: 14px;
                    .tit {
                        font-weight: bold;
                    }
                }
            }
            .desc-box {
                margin-top: 20px; line-height: 1.4;
            }
        }
    }
</style>