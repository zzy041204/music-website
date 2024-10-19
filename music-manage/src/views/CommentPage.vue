<script lang="ts">
import { defineComponent, watch, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";  // 导入 useRoute
import { HttpManager } from "@/api";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";
import { getCurrentInstance } from "vue"; // 确保导入 getCurrentInstance

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const store = useStore();
    const route = useRoute();  // 使用 useRoute 获取路由对象

    const tableData = ref([]); // 记录歌曲，用于显示
    const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
    const breadcrumbList = computed(() => store.getters.breadcrumbList);

    const searchWord = ref(""); // 记录输入框输入的内容
    watch(searchWord, () => {
      if (searchWord.value === "") {
        tableData.value = tempDate.value;
      } else {
        tableData.value = [];
        for (let item of tempDate.value) {
          if (item.username.includes(searchWord.value)) {
            tableData.value.push(item);
          }
        }
      }
    });

    getData();

    // 获取评论
    async function getData() {
      tableData.value = [];
      tempDate.value = [];
      let promise = null;

      // 直接使用 route.query
      if (route.query.type == "0") {
        promise = HttpManager.getCommentOfSongId(route.query.id);
      } else if (route.query.type == "1") {
        promise = HttpManager.getCommentOfSongListId(route.query.id);
      }

      // 确保 promise 被解析
      if (promise) {
        const res = await promise;
        for (let item of (res as ResponseBody).data) {
          getUsers(item.userId, item);
        }
      }
    }

    async function getUsers(id, item) {
      const result = (await HttpManager.getUserOfId(id)) as ResponseBody;
      item.username = result.data[0].username;
      tableData.value.push(item);
      tempDate.value.push(item);
    }

    /**
     * 删除
     */
    const idx = ref(-1); // 记录当前要删除的行
    const multipleSelection = ref([]); // 记录当前要删除的列表
    const delVisible = ref(false); // 显示删除框

    async function confirm() {
      const result = (await HttpManager.deleteComment(idx.value)) as ResponseBody;

      // 使用 getCurrentInstance 来获取当前实例并调用 $message
      const { appContext } = getCurrentInstance(); // 获取应用上下文
      const message = appContext.config.globalProperties.$message; // 访问全局属性中的 $message

      message({
        message: result.message,
        type: result.type,
      });

      if (result.success) getData();
      delVisible.value = false;
    }

    function deleteRow(id) {
      idx.value = id;
      delVisible.value = true;
    }

    function handleSelectionChange(val) {
      multipleSelection.value = val;
    }

    function deleteAll() {
      for (let item of multipleSelection.value) {
        deleteRow(item.id);
        confirm();
      }
      multipleSelection.value = [];
    }

    return {
      searchWord,
      tableData,
      delVisible,
      breadcrumbList,
      deleteAll,
      handleSelectionChange,
      deleteRow,
      confirm,
    };
  },
});
</script>
