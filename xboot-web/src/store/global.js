import { defineStore } from "pinia";

export const useGlobalStore = defineStore("global", () => {


  const config = window._CONFIG || {}
  const site = config.site || {}

   return {
    site,
   }
})