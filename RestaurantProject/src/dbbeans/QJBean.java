//Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class QJBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String type;
    private int version;

    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setType(String type) {
        this.type = type; 
    }
    
    public String getType() {
        return type; 
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    public int getVersion() {
    	return version;
    }
    
    public String getQjList() {
    	if(version == 0) {
    		return getQjaList();
    	}
    	else {
    		return getQjbList();
    	}
    }
    
    public String getQjaList() {
        String qjList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT by_type.type AS type, by_type.overall_average AS overall " +
                "FROM (SELECT re.type, CAST (AVG(per_rating.overall) AS NUMERIC(3,2)) as overall_average " +
            	"FROM project.restaurant re INNER JOIN ( " +
                "SELECT ra.restaurantid, ((ra.price + ra.food + ra.mood + ra.staff) / 4) AS overall " +
            	"FROM project.rating ra) AS  per_rating ON re.restaurantid = per_rating.restaurantid " +
                "GROUP BY re.type) AS by_type " +
            	"WHERE by_type.overall_average > ( " +
                "(SELECT CAST (AVG(per_rating.overall) AS NUMERIC(3,2)) as overall_average " +
            	"FROM project.restaurant re INNER JOIN ( " +
                "SELECT ra.restaurantid, ((ra.price + ra.food + ra.mood + ra.staff) / 4) AS overall " +
            	"FROM project.rating ra) AS  per_rating ON re.restaurantid = per_rating.restaurantid " +
                "WHERE re.type = '"+type+"'));");
            
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String type;
            String avg;

            while (rs.next()) {
                type = rs.getString("type");
                avg = rs.getString("overall");
            	qjList += "<tr><td>" +
                        type +
                        "</td><td>" +
                        avg +
                        "</td></tr>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qjList;
    }
    
    public String getQjbList() {
        String qjList = "";
        connection = dataaccess.getConnection();
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT by_type.type AS type, by_type.number_ratings AS numratings " +
                "FROM (SELECT re.type, COUNT(*) AS number_ratings " +
            	"FROM project.restaurant re " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
                "GROUP BY re.type) AS by_type " +
            	"WHERE by_type.number_ratings > ( " +
                "SELECT COUNT(*) AS number_ratings " +
                "FROM project.restaurant re " +
            	"INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
                "WHERE re.type = '"+type+"');");
            
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String type;
            String numratings;

            while (rs.next()) {
                type = rs.getString("type");
                numratings = rs.getString("numratings");
            	qjList += "<tr><td>" +
                        type +
                        "</td><td>" +
                        numratings +
                        "</td></tr>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qjList;
    }
}