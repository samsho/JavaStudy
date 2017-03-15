package com.jdk.advance.reflect.jdbc;

import java.util.List;

/**
 * ClassName: Test
 * Desc:
 * Date： 2017/3/15
 * Created：shaom
 */
public class Test {

    public static void main(String[] args) throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> users = userDao.getALL(User.class);
        System.out.println(users);

        List<User> userList = userDao.get();
        System.out.println(userList);

    }
}
