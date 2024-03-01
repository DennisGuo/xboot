import { del, get,postForm, put } from "./http"

/**
 * 批量上传
 * @param {*} files 
 * @returns 
 */
export const uploadFiles = (files)=>{
  return new Promise(async (resolve,reject)=>{
    const arr=[]
    for(let i=0;i<files.length;i++){
      const file = files[i]
      const res = await upload(file)
      if(res.data){
        arr.push(res.data)
        if(files.length == arr.length){
          resolve(arr)
        }
      }else{
        reject('上传失败')
        break;
      }
    }
  })
}

/**
 * 上传附件
 * @param {*} file 
 * @param {*} path 
 * @returns 
 */
export const upload = (file,path)=>{
  let url = '/api/attach/upload'
  if(path){
    url += '?path=' + path
  }
  const data = new FormData()
  data.append('file',file)

  return postForm(url,data)
}

/**
 * 根据附件ID获取文件的URL
 * @param {*} id 
 * @returns 
 */
export const getFile = (id)=>{
  return `/api/attach/file/${id}`
}
/**
 * 删除附件
 * @param {*} id 
 * @returns 
 */
export const delFile = id=>{
  return del(`/api/attach/${id}`)
}