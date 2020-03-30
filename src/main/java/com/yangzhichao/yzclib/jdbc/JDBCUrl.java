package com.yangzhichao.yzclib.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description
 * @Author liumc <liumingchao@cecdat.com>
 * @Since 2019/11/7$
 */
public class JDBCUrl {
    static {
        try {
            // 加载驱动
          //  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          //  Class.forName("com.mysql.jdbc.Driver");
         //   Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("org.postgresql.Driver");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

}
