package test1.transaction;

import org.testng.annotations.Test;
import test1.utils.JDBCUtils;

import java.sql.Connection;

/**
 * @author wanjun
 * @create 2022-05-10 16:11
 */
public class ConnectionTest {
    @Test
    public void testGetConnection() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }
}
