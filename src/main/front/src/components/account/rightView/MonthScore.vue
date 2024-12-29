<script setup>
import { useAccountStore } from '@/stores/account/accountStore'
import { formatCurrencyKRW } from '@/lib/account/accountLib'
const accountStore = useAccountStore()
</script>
<template>
  <div class="account-left">
    <div class="account-fixed">
      <div class="account-divide">
        <div class="first">
          <div class="tit">소득</div>
          <div>{{ formatCurrencyKRW(accountStore.monthSum.plus) }}</div>
        </div>
        <div class="second">
          <div class="tit">지출</div>
          <div>{{ formatCurrencyKRW(accountStore.monthSum.minus) }}</div>
        </div>
      </div>
    </div>
    <div class="account-fixed">
      <div class="account-divide">
        <div class="first">
          <div class="tit">고정 소득</div>
          <div>{{ formatCurrencyKRW(accountStore.monthFixedSum.plus) }}</div>
        </div>
        <div class="second">
          <div class="tit">고정 지출</div>
          <div>{{ formatCurrencyKRW(accountStore.monthFixedSum.minus) }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
