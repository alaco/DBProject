//Created by Linh and modified by Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class ABDBean {
    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    private String resId;
    
    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }

    public String getResName() {
    	String resName = "";
    	connection = dataaccess.getConnection();
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT re.name AS name FROM project.restaurant re WHERE re.restaurantid = '" + resId + "';");
        } catch (Exception e) {
            System.out.println("Cant read Restaurant table " + e);
        }
        try {
            while (rs.next()) {
                resName = rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return resName;
    }

    public void setResId(String value) {
        resId = value;
    }

    public String getResId() {
        return resId;
    }
    
    public String getQaList() {
    	String qaList="";
        connection = dataaccess.getConnection();
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT * " +
                "FROM project.restaurant re "+
            	"INNER JOIN project.location l ON re.restaurantid = l.restaurantid " +
                "WHERE re.restaurantid = '" + resId + "';");
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        
        try {
            String type = "";
            String url = "";
            String manager;
            String firstOpen;
            String address;
            String phone;
            String hours;
            
            String managers = "";
            String firstOpens = "";
            String addresses ="";
            String phones = "";
            String hoursList = "";
            
            boolean first = true;
            
            while (rs.next()) {
                type = rs.getString("type");
                url = rs.getString("url");
                if(url == null) {
                	url = "n/a";
                }
                manager = rs.getString("manager_name");
                if(manager == null) {
                	manager = "n/a";
                }
                firstOpen = rs.getString("first_open_date");
                if(firstOpen == null) {
                	firstOpen = "n/a";
                }
                address = rs.getString("street_address");
                phone = rs.getString("phone_number");
                if(phone.length() == 10) {
                	phone = "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-"+phone.substring(7, 10);
                }
                String[] openH = rs.getString("hour_open").split(":");
                String[] closeH = rs.getString("hour_close").split(":");
                hours = openH[0] + ":" + openH[1] + " to " + closeH[0] + ":" + closeH[1];
                
                if(!first) {
                	managers += "<br>";
                	firstOpens += "<br>";
                	addresses += "<br>";
                	phones += "<br>";
                	hoursList +="<br>";	
                }
                	managers += manager;
                	firstOpens += firstOpen;
                	addresses += address;
                	phones += phone;
                	hoursList += hours;
                	
                	first = false;
        	}
                qaList += "<tr><td>" +
                		type +
	                    "</td><td>" +
	                    url +
	                    "</td><td>" +
	                    managers +
	                    "</td><td>" +
	                    firstOpens +
	                    "</td><td>" +
	                    addresses +
	                    "</td><td>" +
	                    phones +
	                    "</td><td>" +
	                    hoursList +
	                    "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qaList;
    }
    
    public String getQbList() {
    	String qbList="";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT mi.name AS item, mi.price AS price, mi.category AS category " +
                "FROM project.menuitem mi " +
                "WHERE mi.restaurantid ='" + resId + "' " +
                "ORDER BY mi.category, mi.name;"
                );
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        
        try {
            String item = "";
            String price = "";
            String category = "";

            while (rs.next()) {
                item = rs.getString("item");
                price = rs.getString("price");
                category = rs.getString("category");
                if(category == null) {
                	category = "n/a";
                }
                qbList += "<tr><td>" +
                        item +
                        "</td><td>" +
                        category + 
                        "</td><td>" +
                        price +
                        "</td></tr>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        
        return qbList;
    }
  
    public String getQdList() {
    	String qdList="";
        connection = dataaccess.getConnection();
        
        try { 
            st = connection.createStatement();
            rs = st.executeQuery("SELECT mi.name AS item, mi.price AS price, l.manager_name AS manager, l.hour_open AS open, " +
                "l.hour_close AS close, re.url AS url " +
                "FROM project.menuitem mi " +
                "INNER JOIN project.restaurant re ON mi.restaurantid = re.restaurantid " + 
                "INNER JOIN project.location l ON re.restaurantid = l.restaurantid " + 
                "WHERE mi.price = ( " + 
                "SELECT MAX(mi2.price) " + 
                "FROM project.menuitem mi2 " + 
                "WHERE mi2.restaurantid = '"+resId+"') " + 
                "AND mi.restaurantid = '"+resId+"' " +
                "ORDER BY mi.name;");

        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        
        try {
            String item;
            String price;
            String manager;
            String hours;
            String url;

            String prevItem="";
            String prevPrice="";
            String managers="";
            String hoursList="";
            String prevURL="";

            while (rs.next()) {
            	
                item = rs.getString("item");
                price = "$"+rs.getString("price");
                manager = rs.getString("manager");
                if(manager == null) {
                	manager = "n/a";
                }
                String[] openH = rs.getString("open").split(":");
                String[] closeH = rs.getString("close").split(":");
                hours = openH[0] + ":" + openH[1] + " to " + closeH[0] + ":" + closeH[1];
                url = rs.getString("url");
                if(url == null) {
                	url = "n/a";
                }

                if (!item.equals(prevItem)) {
                    if (!prevItem.isEmpty()) {
                        qdList += "<tr><td>" +
                            prevItem +
                            "</td><td>" +
                             prevPrice +
                            "</td><td>" +
                             managers +
                             "</td><td>" +
                             hoursList +
                             "</td><td>" +
                             prevURL +
                            "</td></tr>";
                    }
                    prevItem = item;
                    prevPrice = price;
                    managers = manager;
                    hoursList = hours;
                    prevURL = url;
                } 
                else {
                	managers += "<br>" + manager;
                    hoursList += "<br>" + hours;
                }
            }
            qdList += "<tr><td>" +
                    prevItem +
                    "</td><td>" +
                     prevPrice +
                    "</td><td>" +
                     managers +
                     "</td><td>" +
                     hoursList +
                     "</td><td>" +
                     prevURL +
                    "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
    	return qdList;
    }
}