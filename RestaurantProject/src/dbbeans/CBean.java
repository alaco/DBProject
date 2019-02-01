//Created by Linh and modified by Allie LaCompte
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class CBean {
	    private Connection connection;
	    private DataAccess dataaccess;
	    private Statement st;
	    private ResultSet rs;
	    private String type;
	    
	    public void setDataAccess(DataAccess db) {
	        dataaccess = db;
	    }

	    public void setType(String value) {
	        type = value;
	    }

	    public String getType() {
	        return type;
	    }
	    
	    public String getQcList() {
	    	String qcList="";
	        connection = dataaccess.getConnection();
	        
	        try {
	            st = connection.createStatement();
	            rs = st.executeQuery("SELECT re.name restaurant, l.manager_name AS manager, l.first_open_date AS firstOpen " +
	                "FROM project.restaurant re " +
	            	"INNER JOIN project.location l ON re.restaurantid = l.restaurantid " +
	                "WHERE re.type = '"+ type +"';");
	        } catch (Exception e) {
	            System.out.println("Cant read table(s) " + e);
	        }
	        try {
	        	String restaurant;
	            String manager;
	            String firstOpen;

	            String prevRestaurant = "";
	            String managers = "";
	            String firstOpenDates = "";
	           

	            while (rs.next()) {
	            	restaurant = rs.getString("restaurant");
	                manager = rs.getString("manager");
	                if(manager == null) {
	                	manager = "n/a";
	                }
	                firstOpen = rs.getString("firstOpen");
	                if(firstOpen == null) {
	                	firstOpen = "n/a";
	                }
	                if (!restaurant.equals(prevRestaurant)) {
	                    if (!prevRestaurant.isEmpty()) {
	                        qcList += "<tr><td>" +
	                        		prevRestaurant +
	                        		"</td><td>" +
	                        		managers + 
	                        		"</td><td>" +
	                        		firstOpenDates +
	                        		"</td></tr>";
	                    }

	                    prevRestaurant = restaurant;
	                    managers = manager;
	                    firstOpenDates = firstOpen;
	                } else {
	                	managers += "<br>" + manager;
	                    firstOpenDates += "<br>" + firstOpen;
	                }
	            }
	            qcList += "<tr><td>" +
                        prevRestaurant +
                       "</td><td>" +
                       managers +
                        "</td><td>" +
                       firstOpenDates + 
                       "</td></tr>";
	        } catch (Exception e) {
	            System.out.println("Error creating table " + e);
	        }
	    	return qcList;
	    }
}
