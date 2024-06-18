
import { useGlobalStore } from '@/store/global';

const Auth = {
  created: (el, binding) => {
    const global = useGlobalStore()
    global.hasAuth(binding.value).then(rs => {
      if (!rs) {
        el.remove();
      }
    })
  },
}

export default Auth;