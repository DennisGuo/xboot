import { get,post,del } from "./http"

/**
 * 获取机构部门树
 * @param {*} params 
 * @returns 
 */
export const getGroupTree = ()=>{
  const url = `/api/group/tree`
  return get(url)
}
/**
 * 删除机构部门
 * @param {*} id 
 * @returns 
 */
export const removeGroup = (id)=>{
  const url = `/api/group/${id}`
  return del(url)
}
/**
 * ID查询部门机构
 * @param {*} id 
 * @returns 
 */
export const getGroup = (id)=>{
  const url = `/api/group/${id}`
  return get(url)
}
/**
 * 保存或者更新机构部门
 * @param {*} data 
 * @returns 
 */
export const saveGroup = (data)=>{
  const url = `/api/group`
  return post(url,data)
}
/**
 * 删除机构部门用户
 * @param {*} id  
 * @param {*} userIds 用户ID数组
 * @returns 
 */
export const removeGroupUser = (id,userIds)=>{
  const url = `/api/group/${id}/user?userIds=${userIds.join(',')}`
  return del(url)
}
/**
 * 添加机构部门用户
 * @param {*} id 
 * @param {*} userIds 
 * @returns 
 */
export const saveGroupUser = (id,userIds)=>{
  const url = `/api/group/${id}/user`
  return post(url,userIds)
}