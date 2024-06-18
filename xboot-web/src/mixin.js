import { Modal } from 'ant-design-vue';
import { KEY_AC_TOKEN, KEY_EX_TIME, KEY_RF_TOKEN } from './common/const';

/**
 * 从数组中筛选出符合条件的
 * @param {*} arr 
 * @param {*} filter 
 * @returns 
 */
export const findIdxInArr = (arr,filter)=>{
  for(let i=0;i<arr.length;i++){
    if(filter(arr[i])){
      return i;
    }
  }
  return -1;
}
/**
 * 从数组中筛选出符合条件的
 * @param {*} arr 
 * @param {*} filter 
 * @returns 
 */
export const findItemInArr = (arr,filter)=>{
  const idx = findIdxInArr(arr,filter);
  if(idx >= 0){
    return arr[idx]
  }
  return null
}
// 从树形数组中筛选出符合条件的
export const findItemInTree = (arr,filter)=>{
  for(let i=0;i<arr.length;i++){
    const it = arr[i]
    if(filter(it)){
      return it;
    }
    if(it.children && it.children.length > 0){
      let rs = findItemInTree(it.children,filter)
      if(rs){
        return rs
      }
    }
  }
  return null
}

export const confirm = (msg,callback,cancelCall) =>{
  Modal.confirm({
    title: '请确认',    
    content: msg,
    onOk() {
      callback(true);
    },
    onCancel() {
      cancelCall && cancelCall()
    },
  });
}

/**
 * 保存token信息
 * @param {*} res 
 */
export const saveToken = (res) => {
  const { accessToken, refreshToken, expiredIn } = res;
  window.localStorage.setItem(KEY_AC_TOKEN, accessToken);
  window.localStorage.setItem(KEY_RF_TOKEN, refreshToken);
  window.localStorage.setItem(
    KEY_EX_TIME,
    new Date().getTime() + expiredIn * 1000
  );
};


/**
 * 遍历树形数组
 */
export const loopTree = (arr,func)=>{
  for(let i=0;i<arr.length;i++){
    const it = arr[i];
    func(it);
    if(it.children && it.children.length>0){
      loopTree(it.children,func);
    }
  }
}