import { Modal } from 'ant-design-vue';

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
    return arr[index]
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

export const confirm = (msg,callback) =>{
  Modal.confirm({
    title: '请确认',    
    content: msg,
    onOk() {
      callback(true);
    },
    onCancel() {
      
    },
  });
}