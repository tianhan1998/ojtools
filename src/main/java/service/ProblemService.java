package service;

import dao.ProblemDao;
import entity.Problem;

import java.sql.SQLException;
import java.util.List;

public class ProblemService {
    ProblemDao pd=new ProblemDao();
    public List<Problem> getProblemList() throws SQLException {
        return pd.getProblemList();
    }
    public boolean addProblem(int id) throws SQLException {
        return pd.addProblem(id);
    }
    public List<Problem> findByName(String date, String username) throws SQLException {
        return pd.findByName(username,date);
    }
    public boolean setName(String date,String username,int id) throws SQLException {
        return pd.setName(id,date,username);
    }
    public List<Problem> findByLast(String username) throws SQLException {
        return pd.findByLast(username);
    }
}
