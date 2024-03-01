// 封装统一的请求方法
import router from '../router'
import { message } from 'ant-design-vue'
import { KEY_AC_TOKEN,KEY_RF_TOKEN, KEY_EX_TIME ,REFRESH_TIME} from '../common/const';
import { saveToken } from '../mixin'

/**
 * 处理token
 * @param {*} params
 * @returns
 */
const wrapOptions = (params) => {
  let headers = {};
  if (params.headers) {
    headers = { ...params.headers };
  }
  const ls = window.localStorage;
  const key = ls.getItem(KEY_AC_TOKEN);
  if (key) {
    headers['Authorization'] = `Bearer ${key}`;
  }
  
  return {
    ...params,
    headers
  };
};


const tryRefreshToken = async ()=>{
  const ls = window.localStorage;
  const exp = ls.getItem(KEY_EX_TIME)
  if(exp && exp - new Date().getTime() < REFRESH_TIME ){
    const refreshToken = ls.getItem(KEY_RF_TOKEN);
    if(refreshToken){
      const res = await fetch('/api/user/token/refresh?token=' + refreshToken)
      if(res.status == 200 ){
        const json = await res.json() 
        saveToken(json.data)
      }
    }
  }
}

const send = async (url, params = {}) => {
  // 判读是否执行刷新
  tryRefreshToken()

  const opts = wrapOptions(params);
  try {    

    const res = await fetch(url, opts);
    const status = res.status;
    if (status == 200) {
      if(params.responseType == 'blob'){
        return res.blob();
      }
      const json =  await res.json();
      if(json.status != 200 && json.message){
        message.warn(json.message)
      }
      return new Promise(resolve=>resolve(json))
    } else if (status == 403) {
      
      if(url.indexOf('/token/refresh') < 0){
        // 尝试读取 refresh token 
        
      }
      if(router.currentRoute.value.name != 'login'){
        router.push({ name: "login", query: { redirect: router.currentRoute.value.path } });
      }
      return Promise.reject(''+status);
    }else {
      console.log('其他异常状态',status)
      router.push({ name: "error", query: { status } });
      return Promise.reject(''+status);
    }
  } catch (e) {}
};

// Define function to make GET requests
export async function get(url,params=null) {
  let arr = []
  if(params){
    for(const key in params){
      let val = params[key]
      if(val != undefined && val !== ''){
        arr.push(`${key}=${val}`)
      }
    }
  }
  let furl = url
  if(arr.length > 0){
    furl += (url.indexOf('?') >= 0 ? '&' : '?') + arr.join('&')
  }
  return await send(furl);
}
export async function getBlob(url) {
  return await send(url,{
    responseType: "blob"
  });
}

// Define function to make POST requests
export async function post(url, data) {
  return await send(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });
}
// Define function to make POST requests
export async function postForm(url, data) {
  return await send(url, {
    method: "POST",
    body: data
  });
}

// Define function to make PUT requests
export async function put(url, data) {
  const options = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },    
  }
  if(data){
    options.body = JSON.stringify(data)
  }
  return await send(url, options);
}

// Define function to make DELETE requests
export async function del(url) {
  return await send(url, { method: "DELETE" });
}
