import { get,post, put } from "./http"

/**
 * 列表查询用户
 * @param {*} params 
 * @returns 
 */
export const listRole = (params)=>{
  const url = `/api/role/list`
  return get(url,params)
}