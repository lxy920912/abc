package org.connect;

import org.check.MysqlHandle;

/**
 * Created by lixiaoyan on 2017/4/12.
 */
public class Main {

    public static void main(String[] args) {
        MysqlHandle mysqlHandle = new MysqlHandle();
        mysqlHandle.closeConnect();
    }
}
