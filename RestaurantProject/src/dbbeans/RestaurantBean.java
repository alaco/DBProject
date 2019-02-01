//Allie LaCompte 5270100
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;

public class RestaurantBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    
    private String restID;
    
    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setID(String restID) {
        this.restID = restID;
    }

    public String getID() {
        return restID;
    }
    public String getRestName() {
    	String restName = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT re.name AS name "+
					"FROM project.restaurant re WHERE re.restaurantid = '" + restID + "';");
        } catch (Exception e) {
                    System.out.println("Cant read Restaurant table");
                }
        try {
            if (rs.next()) {
                restName = rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return restName;
    }
    
    public boolean deleteItem(String item) {
    	connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
			st.executeUpdate("DELETE FROM project.menuitem mi " +
					"WHERE mi.itemid = '" + item + "' AND mi.restaurantid = '"+restID+"';");
        } catch (Exception e) {
                    System.out.println("Can't delete item");
                    return false;
                }
        System.out.println("Item deleted");
        return true;
    }
    
    public String getMenuItemList() {
    	String menuItemList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT mi.name AS name, mi.itemid AS id "+
					"FROM project.menuitem mi WHERE mi.restaurantid = '" + restID + "';");
        } catch (Exception e) {
                    System.out.println("Cant read Menu Item table");
                }
        try {
            String name;
            String id;
            while (rs.next()) {
                name = rs.getString("name");
                id = rs.getString("id");
                menuItemList +="<option value="+id+">"+name+"</option>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return menuItemList;
    }
}