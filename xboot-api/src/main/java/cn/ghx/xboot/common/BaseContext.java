package cn.ghx.xboot.common;

import cn.ghx.xboot.user.User;

import java.util.Objects;

public class BaseContext {

    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static String getUserId() {
        User user = getUser();
        if(user != null ) return user.getId();
        return null;
    }
    public static User getUser() {

        if (Objects.nonNull(threadLocal.get())) {
            return threadLocal.get();

        }
        return null;
    }

    public static void setUser(User user) {
        threadLocal.set(user);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
