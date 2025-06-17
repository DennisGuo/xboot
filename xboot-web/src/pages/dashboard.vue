<template>
  <div class="page-content">
    <PageBreadcrumb />
    <div class="stat-bar">
        <div class="item blue">
          <div class="icon"><img :src="`/icons/event.svg`" /></div>
          <div class="info">
            <div class="title">事件总数</div>
            <div class="value">{{ 123 }}</div>
          </div>
        </div>
        <div class="item red">
          <div class="icon"><img :src="`/icons/plot.svg`" /></div>
          <div class="info">
            <div class="title">预案总数</div>
            <div class="value">{{ 456 }}</div>
          </div>
        </div>
        <div class="item green">
          <div class="icon"><img :src="`/icons/user.svg`" /></div>
          <div class="info">
            <div class="title">人员总数</div>
            <div class="value">{{ 789 }}</div>
          </div>
        </div>
        <div class="item yellow">
          <div class="icon"><img :src="`/icons/sms.svg`" /></div>
          <div class="info">
            <div class="title">短信总数</div>
            <div class="value">{{ 666 }}</div>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>

@import url('@/assets/common.less');

.stat-bar {
  .flex-row();
  .gap();

  .item {
    .flex1();
    .flex-row();
    gap: 14px;
    border-radius: 4px;
    box-shadow: 0 0 15px rgba(0, 0, 0, .1);

    color: #fff;
    background: linear-gradient(45deg, #eee, #aaa);
    padding: 20px 10px;

    .info {
      .flex-col();
      gap: 8px;

      .value {
        font-size: 2em;
      }

      .title {
        opacity: .7;
      }
    }

    .icon {
      opacity: .5;

      img {
        width: 54px;
      }
    }

    &.blue {
      background: linear-gradient(45deg, #5274e6, #5a6aff);
    }

    &.red {
      background: linear-gradient(45deg, #e44f58, #b13d44);
    }

    &.green {
      background: linear-gradient(45deg, #00c091, #008d6a);
    }

    &.yellow {
      background: linear-gradient(45deg, #f1b100, #be8b00);
    }
  }
}
</style>