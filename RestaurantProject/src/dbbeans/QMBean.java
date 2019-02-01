//Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class QMBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String userName;

    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setUserName(String userName) {
        this.userName = userName; 
    }
    
    public String getUserName() {
        return userName; 
    }
    
    public String getQmList() {
        String qmList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT u.name AS rater, u.email AS email " +
                "FROM project.rater u " +
            	"INNER JOIN project.rating ra ON u.userid = ra.userid, " +
                "(SELECT u2.userid, MIN(ra2.price + ra2.food + ra2.mood + ra2.staff) as overall " +
            	"FROM project.rating ra2 " +
                "INNER JOIN project.rater AS u2 ON ra2.userid = u2.userid " +
                "WHERE u2.name = '"+userName+"' " +
                "GROUP BY u2.userid) AS test " +
                "WHERE (ra.price + ra.food + ra.mood + ra.staff) < test.overall " + 
                "ORDER BY u.name;");

        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String rater;
            String email;
            
            while (rs.next()) {
                rater = rs.getString("rater");
                email = rs.getString("email");
                
                qmList += "<tr><td>" +
                    rater +
                    "</td><td>" +
                    email +
                    "</td></tr>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qmList;
    }
}