package test3;

import test1.transaction.Client;
import test2.BaseDAO;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author wanjun
 * @create 2022-05-11 22:19
 */
public class ClientDaoImpl extends BaseDAO implements ClientDao{

    @Override
    public void insert(Connection conn, Client cust) {
        String sql = "insert into client(name,type,client_id)values(?,?,?)";
        update(conn, sql,cust.getName(),cust.getType(),cust.getClient());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from client where client_id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Client cust) {
        String sql = "update client set name = ?,type = ? where client_id = ?";
        update(conn, sql,cust.getName(),cust.getType(),cust.getClient());
    }

    @Override
    public Client getCustomerById(Connection conn, int id) {
        String sql = "select client_id as client,name,type from client where client_id = ?";
        Client customer = getInstance(conn,Client.class, sql,id);
        return customer;
    }

    @Override
    public List<Client> getAll(Connection conn) {
        String sql = "select client_id as client,name,type from client";
        List<Client> list = getForList(conn, Client.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from client";
        return getValue(conn, sql);
    }

    @Override
    public int getMaxBirth(Connection conn) {
        String sql = "select max(client_id) from client";
        return getValue(conn, sql);
    }
}
