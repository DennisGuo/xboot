import { get,post, put ,del} from "./http"

/**
 * 获取验证码
 * @returns 
 */
export const getCaptcha = ()=>{
  const url = '/api/user/captcha?_t='+new Date().getTime()
  return get(url)
}
/**
 * 获取当前登录用户
 * @returns 
 */
export const getMe = ()=>{
  return get(`/api/user/me`)
}
/**
 * 登录
 * @param {*} vo 
 * @returns 
 */
export const login = (vo)=>{
  const url = `/api/user/login`
  return post(url,vo)
}
/**
 * 获取当前用户的菜单
 * @param {*} vo 
 * @returns 
 */
export const getMeMenu = ()=>{
  const url = `/api/user/me/menu?tree=true`
  return get(url)
}
/**
 * 获取当前用户的角色
 * @returns 
 */
export const getMeRoles = ()=>{
  const url = `/api/user/me/roles`
  return get(url)
}
/**
 * 退出登录
 * @returns 
 */
export const logout = ()=>{
  return get(`/api/user/logout`)
}
/**
 * 保存或者更新用户
 * @param {*} data 
 * @returns 
 */
export const saveUser = (data)=>{
  const url = `/api/user`
  return post(url,data)
}
/**
 * 修改个人的密码
 * @param {*} data 
 * @returns 
 */
export const changeMePassword = (data)=>{
  return put(`/api/user/me/password`,data)
}
/**
 * 列表查询用户
 * @param {*} params 
 * @returns 
 */
export const listUser = (params)=>{
  const url = `/api/user/list`
  return get(url,params)
}
/**
 * 删除用户
 * @param {*} id 
 * @returns 
 */
export const removeUser = (id)=>{
  const url = `/api/user/${id}`
  return del(url)
}