//Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class QHBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String userID;
    private int version;

    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setUserID(String userID) {
        this.userID = userID; 
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    public int getVersion() {
    	return version;
    }
    
    public String getUserName() {
    	String userName = "";
    	connection = dataaccess.getConnection();
    	
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT u.name AS name FROM project.rater u WHERE u.userid = '" + userID + "';");
        } catch (Exception e) {
            System.out.println("Cant read Rater table " + e);
        }
        try {
            while (rs.next()) {
                userName = rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return userName;
    }

    public String getQhList() {
    	if(version == 0) {
    		return getQhaList();
    	}
    	if(version == 1) {
    		return getQhbList();
    	}
    	else {
    		return getQhcList();
    	}
    }

    public String getQhaList() {
        String qhList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT re.name AS restaurant, l.first_open_date AS open " +
                "FROM project.restaurant re " +
            	"INNER JOIN project.location l on re.restaurantid = l.restaurantid " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
            	"WHERE ra.staff < ( " +
                "SELECT MIN(ra2.staff) " +
            	"FROM project.rating ra2 " +
                "INNER JOIN project.rater u ON ra2.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
                "ORDER BY re.name, l.first_open_date;");

        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String open;
            String prevRest = "";
            String openList = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                open = rs.getString("open");
                if(open == null) {
                	open = "n/a";
                }
                if (!restaurant.equals(prevRest)) {
                    if (!prevRest.isEmpty()) {
                        qhList += "<tr><td>" +
                            prevRest +
                            "</td><td>" +
                            openList +
                            "</td></tr>";
                    }

                    prevRest = restaurant;
                    openList = open;
                } else {
                    openList += "<br>" + open;
                }
            }
            qhList += "<tr><td>" +
                    prevRest +
                    "</td><td>" +
                    openList +
                    "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qhList;
    }
    
    public String getQhbList() {
        String qhList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT re.name AS restaurant, l.first_open_date AS open, ra.date_time AS date " + 
                "FROM project.restaurant re " +
            	"INNER JOIN project.location l on re.restaurantid = l.restaurantid " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
            	"WHERE ra.staff < ( " +
                "SELECT MIN(ra2.staff) " +
            	"FROM project.rating ra2 " +
                "INNER JOIN project.rater u ON ra2.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
                "ORDER BY ra.date_time, re.name;");

        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String open;
            String date;
            String prevRest = "";
            String prevDate = "";
            String openList = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                date = rs.getString("date");
                open = rs.getString("open");
                if(open == null) {
                	open = "n/a";
                }
                if (!restaurant.equals(prevRest) || !date.equals(prevDate)) {
                    if (!prevRest.isEmpty()) {
                        qhList += "<tr><td>" +
                            prevDate +
                            "</td><td>" +
                            prevRest +
                            "</td><td>" +
                            openList +
                            "</td></tr>";
                    }
                    prevRest = restaurant;
                    prevDate = date;
                    openList = open;
                } else {
                    openList += "<br>" + open;
                }
            }
            qhList += "<tr><td>" +
                    prevDate +
                    "</td><td>" +
                    prevRest +
                    "</td><td>" +
                    openList +
                    "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qhList;
    }

    public String getQhcList() {
        String qhList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT re.name AS restaurant, l.first_open_date AS open, ra.date_time AS date " + 
                "FROM project.restaurant re " +
            	"INNER JOIN project.location l on re.restaurantid = l.restaurantid " +
                "INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
            	"WHERE ra.staff < ( " +
                "SELECT MIN(ra2.price) " +
            	"FROM project.rating ra2 " +
                "INNER JOIN project.rater u ON ra2.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
                "AND ra.staff < ( " +
            	"SELECT MIN(ra3.food) " +
                "FROM project.rating ra3 " +
            	"INNER JOIN project.rater u ON ra3.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
            	"AND ra.staff < ( " +
            	"SELECT MIN(ra4.mood) " +
                "FROM project.rating ra4 " +
            	"INNER JOIN project.rater u ON ra4.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
            	"AND ra.staff < ( " +
            	"SELECT MIN(ra5.staff) " +
                "FROM project.rating ra5 " +
            	"INNER JOIN project.rater u ON ra5.userid = u.userid " +
            	"WHERE u.userid = '" + userID + "') " +
                "ORDER BY ra.date_time, re.name;");

        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String open;
            String date;
            String prevRest = "";
            String prevDate = "";
            String openList = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                date = rs.getString("date");
                open = rs.getString("open");
                if(open == null) {
                	open = "n/a";
                }
                if (!restaurant.equals(prevRest) || !date.equals(prevDate)) {
                    if (!prevRest.isEmpty()) {
                        qhList += "<tr><td>" +
                            prevDate +
                            "</td><td>" +
                            prevRest +
                            "</td><td>" +
                            openList +
                            "</td></tr>";
                    }
                    prevRest = restaurant;
                    prevDate = date;
                    openList = open;
                } else {
                    openList += "<br>" + open;
                }
            }
            qhList += "<tr><td>" +
                    prevDate +
                    "</td><td>" +
                    prevRest +
                    "</td><td>" +
                    openList +
                    "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qhList;
    }
}