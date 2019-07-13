package dao;

import entity.Problem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DButils;

import java.sql.SQLException;
import java.util.List;

public class ProblemDao {
    public List<Problem> getProblemList() throws SQLException {
        QueryRunner qr=new QueryRunner(DButils.getDataSource());
        String sql="select * from problem_set where status=0 and username is null or trim(username)=''";
        List<Problem> list=qr.query(sql,new BeanListHandler<Problem>(Problem.class));
        return list;
    }
    public boolean addProblem(int id) throws SQLException {
        QueryRunner qr=new QueryRunner(DButils.getDataSource());
        String sql="update problem_set set status=1 where id=?";
        int num=qr.update(sql,new Object[]{id});
        return num>0;
    }
    public boolean setName(int id,String date,String username) throws SQLException {
        QueryRunner qr=new QueryRunner(DButils.getDataSource());
        String sql="update problem_set set username=?,date=? where id=?";
        int num=qr.update(sql,new Object[]{username,date,id});
        return num>0;
    }
    public List<Problem> findByName(String username,String date) throws SQLException {
        QueryRunner qr=new QueryRunner(DButils.getDataSource());
        String sql="select * from problem_set where username=? and date=?";
        List<Problem>list=qr.query(sql,new BeanListHandler<Problem>(Problem.class),new Object[]{username,date});
        return list;
    }
    public List<Problem> findByLast(String username) throws SQLException {
        QueryRunner qr=new QueryRunner(DButils.getDataSource());
        String sql="select * from problem_set where username=? and status=0";
        List<Problem> list=qr.query(sql,new BeanListHandler<Problem>(Problem.class),new Object[]{username});
        return list;
    }
}
