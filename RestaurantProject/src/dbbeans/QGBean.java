//Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class QGBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String month;
    private String year;

    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public String getDate() {
        String monthString = "";
        switch (month) {
            case "01":
                monthString = "January";
                break;
            case "02":
                monthString = "February";
                break;
            case "03":
                monthString = "March";
                break;
            case "04":
                monthString = "April";
                break;
            case "05":
                monthString = "May";
                break;
            case "06":
                monthString = "June";
                break;
            case "07":
                monthString = "July";
                break;
            case "08":
                monthString = "August";
                break;
            case "09":
                monthString = "September";
                break;
            case "10":
                monthString = "October";
                break;
            case "11":
                monthString = "November";
                break;
            case "12":
                monthString = "December";
                break;
        }
        return monthString + " " + year;
    }

    public String getQgList() {
        String qgList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery(" SELECT re.name as restaurant, re.type as type, l.phone_number as phone " +
                "FROM project.restaurant re, project.location l " +
                "WHERE re.restaurantid = l.restaurantid AND re.restaurantid NOT IN ( " +
                "SELECT ra.restaurantid " +
                "FROM project.rating ra " +
                "WHERE CAST (ra.date_time AS VARCHAR) LIKE '%" + year + "-" + month + "%') " +
                "ORDER BY re.name;");
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String type;
            String phone;
            String prevType = "";
            String prevRest = "";
            String phones = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                type = rs.getString("type");
                phone = rs.getString("phone");
                if (!restaurant.equals(prevRest)) {
                    if (!prevRest.isEmpty()) {
                        qgList += "<tr><td>" +
                            prevRest +
                            "</td><td>" +
                            prevType +
                            "</td><td>" +
                            phones +
                            "</td></tr>";
                    }

                    prevRest = restaurant;
                    prevType = type;
                    phones = phone;
                } else {
                    phones += "<br>" + phone;
                }
            }
            qgList += "<tr><td>" +
                prevRest +
                "</td><td>" +
                prevType +
                "</td><td>" +
                phones +
                "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qgList;
    }
}