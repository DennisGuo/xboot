import { del, get,post, put } from "./http"

 
/**
 * 列表查询字典
 * @param {*} params 
 * @returns 
 */
export const listDict = (params)=>{
  const url = `/api/dict/list`
  return get(url,params)
}
/**
 * 删除字典
 * @param {*} id 
 * @returns 
 */
export const removeDict = (id)=>{
  const url = `/api/dict/${id}`
  return del(url)
}
/**
 * 保存或者更新字典
 * @param {*} data 
 * @returns 
 */
export const saveDict= (data)=>{
  const url = `/api/dict`
  return post(url,data)
}