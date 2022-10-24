package dao;

import com.mysql.cj.xdevapi.AddStatement;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Map;

public class UserDao {
    private final DataSource dataSource;
    private final JdbcContext jdbcContext;


    public UserDao(DataSource dataSource) {
        this.jdbcContext = new JdbcContext(dataSource);
        this.dataSource = dataSource;
    }

//    public UserDao(ConnectionMaker connectionMaker) {
//        this.connectionMaker = connectionMaker;
//    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            ps = stmt.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException e ) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public int getCount() throws SQLException, ClassNotFoundException {

        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM user");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        pstmt.close();
        conn.close();
        return count;
    }

//    public void executeSql(String sql) throws SQLException {
//
//        jdbcContextWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                return c.prepareStatement(sql);
//            }
//        });
//    }
    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcContext.executeSql("delete from user");

    }
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = dataSource.getConnection();
////            pstmt = conn.prepareStatement("DELETE FROM user");
////            pstmt.executeUpdate();
//            //DeteleAllStrategy 사용하기
//            pstmt = new DeleteAllStrategy().makePreparedStatement(conn);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally { //error가 발생해도 실행되는 블럭
//            if( pstmt != null){
//                try{
//                    pstmt.close();
//                } catch (SQLException e){
//                }
//            }
//            if(conn != null){
//                try{
//                    conn.close();
//                } catch (SQLException e){
//                }
//            }
//        }
//    }


    public void add(User user) throws SQLException{
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = null;
                ps = c.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?);");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });

//        StatementStrategy st = new AddStrategy(user);
//        jdbcContextWithStatementStrategy(st);
    }
//    public void add(User user) throws SQLException, ClassNotFoundException {
//
//
//        Connection conn = connectionMaker.getConnection();
//
//        PreparedStatement ps = conn.prepareStatement("INSERT INTO user(id,name,password)" +
//                "VALUES(?,?,?);");
//
//        ps.setString(1, user.getId());
//        ps.setString(2, user.getName());
//        ps.setString(3, user.getPassword());
//
//        ps.executeUpdate();
//        ps.close();
//        conn.close();
//    }

    public User Select(String id) throws ClassNotFoundException, SQLException {
        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT id,name,password FROM user WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        User user = null;
        //결과 값이 없을 때 exception 처리
        if(rs.next()) {
            user = new User(rs.getString("id")
                    , rs.getString("name"), rs.getString("password"));
        }
            //리소스 반납
            rs.close();
            ps.close();
            conn.close();
            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

    }
}
