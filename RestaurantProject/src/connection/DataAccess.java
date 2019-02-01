//Provided in lab and modified by Allie LaCompte
//DB Project W18
package connection;

import java.sql.*;
import java.util.ResourceBundle;

public class DataAccess {

    private Connection connection;
    private Statement st;
    private ResultSet rs;
    private ResourceBundle reader;

    public Connection getConnection() {
        return connection;
    }

    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        	
            reader = ResourceBundle.getBundle("database");
           
        	String url = reader.getString("url");
        	String username = reader.getString("username");
        	String password = reader.getString("password");
               	
            connection = DriverManager.getConnection(url, username, password);
            
            System.out.println("Connection Established");
        } catch (Exception e) {
            System.out.println("No connection established: " + e.toString());
        }
    }

    public boolean next() {
        try {
            return (rs.next());
        } catch (Exception e) {
            System.out.println("Error moving to the next one");
            return false;
        }
    }

    public String getField(String name) {
        try {
            return (rs.getString(name));
        } catch (Exception e) {
            System.out.println("Error getting the field");
            return "";
        }
    }

    public void closeConsult() {
        try {
            rs.close();
            st.close();
        } catch (Exception e) {}
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {}
    }
}