<script lang="ts">
import { defineComponent, ref, reactive, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router"; // 引入useRoute和useRouter
import { useStore } from "vuex";
import mixin from "@/mixins/mixin";
import { Icon, RouterName } from "@/enums";
import { HttpManager } from "@/api";
import { parseLyric } from "@/utils";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const route = useRoute(); // 使用useRoute获取路由信息
    const router = useRouter(); // 使用useRouter进行路由跳转
    const store = useStore();
    const { routerManager, beforeImgUpload, beforeSongUpload } = mixin();

    const tableData = ref([]); // 记录歌曲，用于显示
    const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
    const pageSize = ref(5); // 页数
    const currentPage = ref(1); // 当前页
    const singerId = ref(route.query.id as string); // 从路由获取singerId
    const singerName = ref(route.query.name as string); // 从路由获取singerName
    const toggle = ref(false); // 控制播放图标状态
    const BOFANG = ref(Icon.BOFANG);
    const ZANTING = ref(Icon.ZANTING);
    const breadcrumbList = computed(() => store.getters.breadcrumbList);

    const isPlay = computed(() => store.getters.isPlay); // 播放状态
    const playIcon = computed(() => (isPlay.value ? ZANTING.value : BOFANG.value)); // 播放状态
    // 计算当前表格中的数据
    const data = computed(() => {
      return tableData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
    });

    const searchWord = ref(""); // 记录输入框输入的内容
    watch(searchWord, () => {
      if (searchWord.value === "") {
        tableData.value = tempDate.value;
      } else {
        tableData.value = [];
        for (let item of tempDate.value) {
          if (item.name.includes(searchWord.value)) {
            tableData.value.push(item);
          }
        }
      }
    });

    // 初始化数据
    const init = () => {
      HttpManager.getSongOfSingerId(singerId.value).then((res: any) => {
        tempDate.value = res;
        tableData.value = res;
      });
    };

    // 跳转到评论页，检查RouterName是否有SONG_COMMENT
    const goCommentPage = (id: number) => {
      router.push({
        path: "/song-comment", // 修改为正确的路径，确保项目中有对应的路由
        query: {
          id,
          type: 1,
        },
      });
    };

    return {
      breadcrumbList,
      data,
      centerDialogVisible: ref(false),
      toggle,
      playIcon,
      BOFANG,
      isPlay,
      currentPage,
      pageSize,
      singerId,
      singerName,
      tableData,
      tempDate,
      editVisible: ref(false),
      editForm: reactive({
        name: "",
        introduction: "",
        lyric: "",
      }),
      delVisible: ref(false),
      registerForm: reactive({
        name: "",
        introduction: "",
        lyric: "",
      }),
      searchWord, // 保留唯一的searchWord定义
      init,
      routerManager,
      goCommentPage,
    };
  },
});
</script>
