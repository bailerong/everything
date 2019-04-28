package com.lele.everything.Core.dao.FileIndexDaoImpl;

import com.lele.everything.Core.Condition;
import com.lele.everything.Core.FileType;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.dao.DataSourceFactory;
import com.lele.everything.Core.dao.FileIndexDao;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileIndexDaoImpl implements FileIndexDao {
    private final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Thing thing) {
        //准备连接
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection =this.dataSource.getConnection();
            String sql = ("insert into file_index(name,path,depth,file_type) values (?,?,?,?)");
            statement = connection.prepareStatement(sql);
            statement.setString(1, thing.getName());
            statement.setString(2, thing.getPath());
            statement.setInt(3, thing.getDepth());
            statement.setString(4, thing.getFileType().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResultSet resultSet=null;
releaseResource(resultSet,statement,connection);
        }

    }

    @Override
    public void delete(Thing thing) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection =this.dataSource.getConnection();
            String sql = ("delete from thing where path=?");
            statement = connection.prepareStatement(sql);
            statement.setString(1, thing.getPath());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResultSet resultSet=null;
            releaseResource(resultSet,statement,connection);
        }

    }

    @Override
    public List<Thing> search(Condition condition) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Thing> things = new ArrayList<>();
        try {
            connection = this.dataSource.getConnection();

            //name我们希望是模糊匹配，进行我们的文件名称查询，用到函数like
            //filetype：我们不需要模糊匹配，正常匹配就可以
            //查询的时候，我们希望深度是才能够小到大，
            //第一，我们需要进行深度的排序
            //第二，我们的
            //limit
            //orderbyAsc
            //拼接不要用加号，一定要用我们的StringBuilder
            //线程安全，这里会不会出现多线程访问？

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select name,path,depth,file_type from file_index");


            if (condition.getFileType() != null) {
                FileType fileType=FileType.lookupByName(condition.getName());
                sqlBuilder.append(" and file_type= '")
                        .append(condition.getFileType().toUpperCase()).append("'");

            }
            //注意，你是要前模糊还是后模糊，还是前后模糊
            sqlBuilder.append(" where ").append(" name like '%").
                    append(condition.getName()).append("%'");
            //limit order必选，必须要有
            sqlBuilder.append(" order by depth ").
                    append(condition.getOrderByAsc() ? " asc " : " desc ");
            sqlBuilder.append(" limit ").append(condition.getLimit())
                    .append(" offset 0 ");
            String str = sqlBuilder.toString();
            System.out.println(str);
            statement = connection.prepareStatement(str);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //数据库中的行记录-->java中的对象thing
                Thing thing = new Thing();
                thing.setName(resultSet.getString("name"));
                thing.setPath(resultSet.getString("path"));
                thing.setDepth(resultSet.getInt("depth"));
                String fileType = resultSet.getString("file_type");
                thing.setFileType(FileType.lookupByName(fileType));
                things.add(thing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

releaseResource(resultSet,statement,connection);
        }

        return things;
    }

    //解决内部代码大量重复的问题：重构
    private void releaseResource(ResultSet resultSet,
                                 PreparedStatement statement, Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        File file=new File("");
    }

}
/*
* 至此，我们的代码就全部写完了
* */