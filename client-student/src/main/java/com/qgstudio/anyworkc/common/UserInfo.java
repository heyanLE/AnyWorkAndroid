package com.qgstudio.anyworkc.common;

import com.qgstudio.anyworkc.data.model.User;
import com.qgstudio.anyworkc.utils.DataBaseUtil;

import java.util.List;

public class UserInfo {

    /**
     * 从数据库中读取用户登录信息
     *
     * @return User
     */
    public static User getUser() {
        List<User> users = DataBaseUtil.getHelper().queryAll(User.class);
        if (users != null) {
            if (users.size() <= 0) {
                return null;
            }
            return users.get(users.size() - 1);
        } else {
            return null;
        }
    }
}
