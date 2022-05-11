package test2;

import org.junit.jupiter.api.Test;
import test1.transaction.Client;
import test1.utils.JDBCUtils;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author wanjun
 * @create 2022-05-11 10:33
 */
public class CustomerDAOImplTest {
    private CustomerDAOImpl dao = new CustomerDAOImpl();

    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Client cust = new Client(2, "于小飞", "xiaofei@126.com");
            dao.insert(conn, cust);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }

    }

    @Test
    public void testDeleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            dao.deleteById(conn, 2);


            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

    @Test
    public void testUpdateConnectionCustomer() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Client cust = new Client(2,"贝多芬","beiduofen@126.com");
            dao.update(conn, cust);


            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

    @Test
    public void testGetCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Client cust = dao.getCustomerById(conn, 1);
            System.out.println(cust);


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

    @Test
    public void testGetAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            List<Client> list = dao.getAll(conn);
            list.forEach(System.out::println);


            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

    @Test
    public void testGetCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Long count = dao.getCount(conn);

            System.out.println("表中的记录数为：" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

    @Test
    public void testGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Date maxBirth = dao.getMaxBirth(conn);

            System.out.println("最大的生日为：" + maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);

        }
    }

}
