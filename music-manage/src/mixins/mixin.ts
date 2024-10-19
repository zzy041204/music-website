import { getCurrentInstance, ref } from "vue";
import { LocationQueryRaw, useRouter, useRoute } from "vue-router"; // 引入 useRouter 和 useRoute
import { RouterName } from "@/enums";
import { ElMessage } from "element-plus"; // 引入 ElMessage
import { useStore } from "vuex"; // 引入 Vuex

interface routerOptions {
  path?: string;
  query?: LocationQueryRaw;
}

export default function () {
  const store = useStore(); // 使用 Vuex 的 store
  const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);
  const router = useRouter(); // 使用 useRouter
  const route = useRoute(); // 使用 useRoute

  function changeSex(value) {
    if (value === 0) {
      return "女";
    } else if (value === 1) {
      return "男";
    } else if (value === 2) {
      return "组合";
    } else if (value === 3) {
      return "不明";
    } else if (value === "男" || value === "女") {
      return value;
    }
  }

  function beforeImgUpload(file) {
    const ltCode = 2;
    const isLt2M = file.size / 1024 / 1024 < ltCode;
    const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

    if (!isExistFileType) {
      ElMessage.error(`图片只支持 ${uploadTypes.value.join("、")} 格式!`); // 直接调用 ElMessage.error
    }
    if (!isLt2M) {
      ElMessage.error(`上传头像图片大小不能超过 ${ltCode}MB!`); // 直接调用 ElMessage.error
    }

    return isExistFileType && isLt2M;
  }

  function beforeSongUpload(file) {
    const ltCode = 10;
    const isLt10M = file.size / 1024 / 1024 < ltCode;
    const extension = file.name.endsWith(".mp3");

    if (!extension) {
      ElMessage.error("上传文件只能是mp3格式！"); // 直接调用 ElMessage.error
    }
    if (!isLt10M) {
      ElMessage.error(`上传文件大小不能超过 ${ltCode}MB!`); // 直接调用 ElMessage.error
    }

    return extension && isLt10M;
  }

  // 路由管理
  function routerManager(routerName: string | number, options: routerOptions) {
    switch (routerName) {
      case RouterName.Song:
      case RouterName.ListSong:
      case RouterName.Comment:
      case RouterName.Consumer:
      case RouterName.Collect:
        router.push({ path: options.path, query: options.query }); // 修改位置
        break;
      case RouterName.Home:
      case RouterName.SignIn:
      case RouterName.SignOut:
      case RouterName.Info:
      case RouterName.Singer:
      case RouterName.SongList:
      case RouterName.Error:
      default:
        router.push({ path: options.path }); // 修改位置
        break;
    }
  }

  function goBack(step = -1) {
    router.go(step); // 修改位置
  }

  return { changeSex, routerManager, goBack, beforeImgUpload, beforeSongUpload };
}
