<template>
  <div class="page-content">
     <PageBreadcrumb />
    <div class="padding bg-white mb flex-row">
      <div class="flex flex-1 flex-row gap-4">
        <a-radio-group v-model:value="searchState.type" button-style="solid">
          <a-radio-button :value="null">全部</a-radio-button>
          <a-radio-button
            v-for="key in Object.keys(menuTypeDict)"
            :key="key"
            :value="key"
          >
            {{ menuTypeDict[key] }}
          </a-radio-button>
        </a-radio-group>
        <a-input
          placeholder="名称|编码|路径"
          :allowClear="true"
          v-model:value="searchState.keyword"
          style="width: 180px"
        >
          <template #suffix>
            <SearchOutlined />
          </template>
        </a-input>
      </div>
      <a-space>
        <span class="flex flex-row gap-2 items-center mr-4">
          <a-switch v-model:checked="expandAll" />
          <span v-if="expandAll">折叠全部</span>
          <span v-else>展开全部</span>
        </span>
        <a-button type="primary" @click="toAdd" v-auth="`menu-add`">
          新增
        </a-button>
        <a-button @click="refresh">刷新</a-button>
      </a-space>
    </div>
    <a-table
      :loading="loading"
      :columns="columns"
      :data-source="filtered"
      :row-key="(r) => r.id"
      :defaultExpandAllRows="true"
      v-model:expandedRowKeys="expandedRowKeys"
      :rowClassName="
        (_record, idx) => (idx % 2 == 1 ? 'bg-blue-100' : 'bg-white')
      "
      :pagination="false"
      bordered
    >
      <template #bodyCell="{ column, record, index }">
        <template v-if="column.key === 'type'">
          <a-tag :color="menuTypeColor[record.type]">{{
            menuTypeDict[record.type]
          }}</a-tag>
        </template>
        <template v-if="column.key === 'icon'">
          <span v-if="record.icon">
            <component :is="record.icon" />
          </span>
        </template>
        <template v-if="column.key === 'hidden'">
          <a-switch :checked="record.hidden" />
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="handleEdit(record)" v-auth="`menu-edit`">编辑</a>
            <a
              @click="handleRemove(record)"
              class="text-red"
              v-auth="`menu-delete`"
              >删除</a
            >
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
  <MenuDrawer
    v-if="modal == 'menu'"
    :item="crtItem"
    @close="onCancel"
    @saved="onSaved"
  />
</template>

<script setup>
import { computed, onMounted, reactive, ref, toRaw, watch } from "vue";
import { message } from "ant-design-vue";
import {
  getMenuTree,
  removeMenu,
  menuTypeDict,
  menuTypeColor
} from "@/api/menu";
import MenuDrawer from "@/components/MenuDrawer.vue";
import { confirm, loopTree } from "@/mixin";
import { SearchOutlined } from "@ant-design/icons-vue";
import cloneDeep from "lodash/cloneDeep";

const searchState = reactive({
  type: null,
  keyword: null
});
const modal = ref(null);
const crtItem = ref(null);
const loading = ref(false);
const expandAll = ref(false);
const rows = ref([]);
const expandedRowKeys = ref([]);
const columns = [
  {
    title: "名称",
    dataIndex: "name",
    width: 220
  },
  {
    title: "类型",
    key: "type",
    width: 80
  },
  {
    title: "编码",
    dataIndex: "code",
    width: 180
  },
  {
    title: "路径",
    dataIndex: "path"
  },
  {
    title: "组件",
    dataIndex: "component"
  },
  {
    title: "图标",
    key: "icon",
    width: 80,
    align: "center"
  },
  {
    title: "隐藏",
    key: "hidden",
    width: 80,
    align: "center"
  },
  {
    title: "排序",
    dataIndex: "sort",
    width: 80,
    align: "center"
  },
  {
    title: "操作",
    key: "action",
    width: 100
  }
];

watch(()=>expandAll.value,(v)=>{
  let rs = []
  if(v){
    loopTree(rows.value,(item)=>{
      if(item.children && item.children.length > 0){
        rs.push(item.id)
      }
    })
  }
  expandedRowKeys.value = rs
})

const filtered = computed(() => {
  let arr = cloneDeep(rows.value);
  if (searchState.type) {
    let rs = [];
    loopTree(arr, (item) => {
      if (item.type == searchState.type) {
        delete item.children;
        rs.push(item);
      }
    });
    return rs;
  }
  if (searchState.keyword) {
    let rs = [];
    const key = searchState.keyword.toLowerCase();
    loopTree(arr, (item) => {
      if (
        item.name?.toLowerCase()?.includes(key) ||
        item.code?.toLowerCase()?.includes(key) ||
        item.path?.toLowerCase()?.includes(key)
      ) {
        // return true
        const it = cloneDeep(item)
        delete it.children;
        rs.push(it);
      }
    });
    return rs;
  }
  return arr;
});

onMounted(() => {
  load();
});

const load = async () => {
  loading.value = true;
  const res = await getMenuTree();
  loading.value = false;
  if (res.data) {
    rows.value = res.data || [];
    expandedRowKeys.value = rows.value
      .filter((i) => i.type == 0 && i.children && i.children.length > 0)
      .map((i) => i.id);
  }
};
const toAdd = () => {
  crtItem.value = null;
  modal.value = "menu";
};
const refresh = () => {
  load();
};
const onCancel = () => {
  modal.value = null;
};

const handleEdit = (item) => {
  crtItem.value = item;
  modal.value = "menu";
};
// 删除
const handleRemove = (item) => {
  confirm(`您确定要删除【${item.name}】吗？`, async () => {
    const res = await removeMenu(item.id);
    if (res.data) {
      message.success("删除成功");
      load();
    }
  });
};
const onSaved = () => {
  onCancel();
  if (crtItem.value) {
    refresh();
  } else {
    load(1);
  }
};
</script>

<style lang="less" scoped></style>
