import { del, get,post, put } from "./http"

 
/**
 * 列表查询配置
 * @param {*} params 
 * @returns 
 */
export const listSetting = (params)=>{
  const url = `/api/setting/list`
  return get(url,params)
}
/**
 * 删除配置
 * @param {*} id 
 * @returns 
 */
export const removeSetting = (id)=>{
  const url = `/api/setting/${id}`
  return del(url)
}
/**
 * 保存或者更新配置
 * @param {*} data 
 * @returns 
 */
export const saveSetting = (data)=>{
  const url = `/api/setting`
  return post(url,data)
}