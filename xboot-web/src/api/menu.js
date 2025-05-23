import { del, get,post, put } from "./http"

export const menuTypeDict = {
  0: '模块',
  1: '菜单',
  2: '页面',
  3: '操作'
}
export const menuTypeColor = {
  0: 'blue',
  1: 'green',
  2: 'orange',
  3: 'gray'
}

/**
 * 查询菜单树
 * @param {*} params 
 * @returns 
 */
export const getMenuTree= (params)=>{
  const url = `/api/menu/tree`
  return get(url,params)
}
/**
 * 列表查询菜单
 * @param {*} params 
 * @returns 
 */
export const listMenu = (params)=>{
  const url = `/api/menu/list`
  return get(url,params)
}
/**
 * 删除菜单
 * @param {*} id 
 * @returns 
 */
export const removeMenu = (id)=>{
  const url = `/api/menu/${id}`
  return del(url)
}
/**
 * 保存或者更新菜单
 * @param {*} data 
 * @returns 
 */
export const saveMenu = (data)=>{
  const url = `/api/menu`
  return post(url,data)
}