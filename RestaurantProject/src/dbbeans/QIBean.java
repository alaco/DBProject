//Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class QIBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String type;

    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public String getTopRating() {
        String topRating = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("Select MAX(ra.food) AS top " +
                "FROM project.restaurant re " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
                "WHERE re.type = '" + type +"' ");
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            if (rs.next()) {
                topRating = rs.getString("top");
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return topRating;
    }
 
    public String getQiList() {
        String qiList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("Select re.name AS restaurant, u.name AS rater " +
                "FROM project.restaurant re " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
                "INNER JOIN project.rater u ON ra.userid = u.userid " +
                "WHERE re.type = '" + type +"' AND ra.food = ( " +
                "SELECT MAX(ra2.food) " +
                "FROM project.restaurant re2 " +
                "INNER JOIN project.rating ra2 ON re2.restaurantid = ra2.restaurantid " +
                "WHERE re2.type = '" + type +"' )"+
                "ORDER BY re.name");
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String rater;
            String prevRest = "";
            String raters = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                rater = rs.getString("rater");
                if (!restaurant.equals(prevRest)) {
                    if (!prevRest.isEmpty()) {
                        qiList += "<tr><td>" +
                            prevRest +
                            "</td><td>" +
                            raters +
                            "</td></tr>";
                    }
                    prevRest = restaurant;
                    raters = rater;
                } else {
                    raters += "<br>" + rater;
                }
            }
            qiList += "<tr><td>" +
                prevRest +
                "</td><td>" +
                raters +
                "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qiList;
    }
}