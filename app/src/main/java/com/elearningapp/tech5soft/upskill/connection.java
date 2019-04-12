package com.elearningapp.tech5soft.upskill;

import android.util.Log;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class connection {
    public Statement stmt;
    public Connection con=null;
    public ResultSet rs;
    String Connection_url="jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12280802\",\"sql12280802\", \"VU94u8Xp9C";
    public void connection() throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12280802","sql12280802","VU94u8Xp9C");
            stmt= (Statement) con.createStatement();
        } catch (Exception e1) {
            Log.d("Exception",e1.getMessage());
        }

    }

    public void insert() {

        String query="select * from Data";

        try{
            stmt.execute(query);
            rs=stmt.executeQuery(query);
            while (rs.next())
            {
                String data=rs.getString(1);
                Log.d("Data",data);
            }

        }catch(Exception e){
            Log.d("Exception",e.getMessage());
        }
        finally
        {
            try
            {
                if(rs!=null)
                    rs.close();

                if(stmt!=null)
                    stmt.close();

                if(con!=null)
                    con.close();
            }
            catch(Exception ex){}
        }
    }

}
