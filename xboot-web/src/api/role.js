import { get,post,del } from "./http"

/**
 * 列表查询角色
 * @param {*} params 
 * @returns 
 */
export const listRole = (params)=>{
  const url = `/api/role/list`
  return get(url,params)
}
/**
 * 删除角色
 * @param {*} id 
 * @returns 
 */
export const removeRole = (id)=>{
  const url = `/api/role/${id}`
  return del(url)
}
/**
 * 保存或者更新角色
 * @param {*} data 
 * @returns 
 */
export const saveRole = (data)=>{
  const url = `/api/role`
  return post(url,data)
}
/**
 * 删除角色用户
 * @param {*} id 角色ID
 * @param {*} userIds 用户ID数组
 * @returns 
 */
export const removeRoleUser = (id,userIds)=>{
  const url = `/api/role/${id}/user?userIds=${userIds.join(',')}`
  return del(url)
}
/**
 * 添加角色用户
 * @param {*} id 
 * @param {*} userIds 
 * @returns 
 */
export const saveRoleUser = (id,userIds)=>{
  const url = `/api/role/${id}/user`
  return post(url,userIds)
}
/**
 * 保存角色配置菜单
 * @param {*} id 
 * @param {*} menuIds 
 * @returns 
 */
export const saveRoleMenu = (id,menuIds)=>{
  const url = `/api/role/${id}/menu`
  return post(url,menuIds)
}

/**
 * 获取角色的菜单ID列表
 * @param {*} id 
 * @returns 
 */
export const getRoleMenuList = (id)=>{
  return get(`/api/role/${id}/menu/ids`)
}