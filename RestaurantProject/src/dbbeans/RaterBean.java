//Allie LaCompte - modification of java bean provided in lab
//DB Project W18
package dbbeans;

import connection.DataAccess;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class RaterBean {

    private Connection connection;
    private DataAccess dataaccess;
    private Statement st;
    private ResultSet rs;
    
    // Current user information
    private String userName;
    private int userid;
    
    public void setDataAccess(DataAccess db) {
        dataaccess = db;
    }
    
    public void setName(String name) {
        this.userName = name.trim();
    }

    public String getName() {
        return userName;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public int existsRaterName(String name, DataAccess db) {
        int id = -1;
        connection = db.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT u.userid AS userid FROM project.rater u " +
                "WHERE name = '" + name.trim() + "'");
            if (rs.next()) {
                id = rs.getInt("userid");
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Cant read Rater table");
        }
        return id;
    }
    
    public void setRater(String name, int id, DataAccess db) {
        setName(name.trim());
        setUserid(id);
        setDataAccess(db);
        System.out.println("USERID: " + userid);
    }

    public int insertRater(String name, String email, String type, DataAccess db) {
        connection = db.getConnection();
        int id = -1;

        try {
            st = connection.createStatement();

            rs = st.executeQuery("SELECT max(userid) AS id FROM project.rater");
            id = rs.next() ? rs.getInt(1) + 1 : 1000;

            System.out.println("USERID: " + id);

            st.executeUpdate("INSERT INTO project.rater " +
                " (userid,name,email,type) VALUES (" + id + ",'" + name.trim().replaceAll("'", "''") + "','" + email.trim().replaceAll("'", "''") + "','" + type + "')");
            System.out.println("Inserted into rater table succesfully!");

            setName(name);
            setUserid(id);
            setDataAccess(db);
            System.out.println("USERID: " + userid);
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Can't insert into rater table");
        }
        return id;
    }
    
    public boolean deleteUser() {
    	connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
			st.executeUpdate("DELETE FROM project.rater ra WHERE ra.userid = '"+userid+"';");
        } catch (Exception e) {
                    System.out.println("Can't delete rater");
                    return false;
                }
        System.out.println("User deleted");
        return true;
    }
    
    public void insertRating(String restaurant, String price, String food, String mood, String staff, String comments) {
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            comments = (comments.isEmpty()) ? "NULL" : "'"+comments.trim().replaceAll("'", "''")+"'";

        	st.executeUpdate("INSERT INTO project.rating (userid, date_time, price, food, mood, staff, comments, restaurantid) " +
        			"VALUES (" + userid + ",'" + date + "','" + price + "','"+ food + "','" + 
        			mood + "','"+ staff + "'," + comments + ",'" + restaurant + "');");

            System.out.println("Inserted into Rating table succesfully!");
            
            rs.close();
            st.close();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Can't insert into rating table");
        }
    }
    
    public int insertRestaurant(String name, String type, String url, String opendy, String opendm, String manager, String phone, String addr, String openh, String closeh) {
        connection = dataaccess.getConnection();
        int id = -1;

        try {
            st = connection.createStatement();

            rs = st.executeQuery("SELECT max(re.restaurantid) AS id FROM project.restaurant re");
            id = rs.next() ? rs.getInt(1) + 1 : 1000;

            System.out.println("NEW RESTAURANT ID: " + id);

            url = (url.isEmpty()) ? "NULL" : "'"+url.trim().replaceAll("'", "''")+"'";

            System.out.println(name.trim().replaceAll("'", "''"));
        	st.executeUpdate("INSERT INTO project.restaurant (restaurantid, name, type, url) " +
        			"VALUES (" + id + ",'" + name.trim().replaceAll("'", "''") + "','" + 
        			type.trim().replaceAll("'", "''") + "'," + url + ");");

            System.out.println("Inserted into restaurant table succesfully!");
            
            rs.close();
            st.close();
            insertLocation(id, opendy, opendm, manager, phone, addr, openh, closeh);
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Can't insert into restaurant table");
        }
        return id;
    }
    
    public void insertLocation(int resID, String opendy, String opendm, String manager, String phone, String addr, String openh, String closeh) {
    	connection = dataaccess.getConnection();
        int locID = -1;

        try {
            st = connection.createStatement();

            rs = st.executeQuery("SELECT max(l.locationid) AS id FROM project.location l");
            locID = rs.next() ? rs.getInt(1) + 1 : 1000;
            
            String opend;
            opend = (opendy.isEmpty() || opendm.isEmpty()) ? "NULL" : "'"+opendy+"-"+opendm+"-01'";

            manager = (manager.isEmpty()) ? "NULL" : "'"+manager.trim().replaceAll("'", "''")+"'";
            
        	st.executeUpdate("INSERT INTO project.location (locationid, first_open_date, manager_name, phone_number, " +
        			"street_address, hour_open, hour_close, restaurantid) VALUES (" +
        			locID + "," + opend + "," + manager + ",'" + phone + "','" + addr.trim().replaceAll("'", "''") + "','" + 
        			openh + "','" + closeh + "'," + resID +");");
            
        	System.out.println("NEW LOCATION ID: " + locID);
            System.out.println("Inserted into location table succesfully!");
            
            rs.close();
            st.close();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Can't insert into location table");
        }
    }
    
    public boolean deleteRestaurant(String id) {
    	connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
			st.executeUpdate("DELETE FROM project.restaurant re WHERE re.restaurantid = '"+id+"';");
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Can't delete restaurant");
            return false;
                }
        System.out.println("Restaurant deleted");
        return true;
    }
    
    public void insertItem(String restaurant, String item, String type, String category, String description, String price) {
        connection = dataaccess.getConnection();
        int id = -1;

        try {
            st = connection.createStatement();

            rs = st.executeQuery("SELECT max(itemid) AS id FROM project.menuitem");
            id = rs.next() ? rs.getInt(1) + 1 : 1000;

            System.out.println("New item id: " + id);
            
            category = (category == null) ? "NULL" : "'"+category+"'";
            description = (description.isEmpty()) ? "NULL" : "'"+description.trim().replaceAll("'", "''")+"'";

            st.executeUpdate("INSERT INTO project.menuitem " +
                "(itemid,name,type,category,description,price,restaurantid) "+
            	"VALUES (" + id + ",'" + item.trim().replaceAll("'", "''") + "','" + type + "'," + category + 
            	"," + description + ",'" + price + "','" + restaurant + "');");
            System.out.println("Inserted into Menuitem table succesfully!");

            rs.close();
            st.close();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Can't insert into Menuitem table");
        }
    }

    public String getRaterList() {
    	String raterList = "";
            connection = dataaccess.getConnection();

            try {
                st = connection.createStatement();
                rs = st.executeQuery("SELECT name, type FROM project.rater");
            } catch (Exception e) {
                System.out.println("Cant read Rater table");
            }
            try {
                String name;
                String type;
                while (rs.next()) {
                    name = rs.getString("name");
                    type = rs.getString("type");
                    raterList += "<tr><td>" +
                        name +
                        "</td><td>" +
                        type +
                        "</td></tr>";
                }
            } catch (Exception e) {
                System.out.println("Error creating table " + e);
            }
        return raterList;
    }  
    public String getRatingsList() {
        String ratingsList = "";
            connection = dataaccess.getConnection();

            try {
                st = connection.createStatement();
                rs = st.executeQuery("SELECT * " +
                "FROM project.rating ra " +
                "INNER JOIN project.restaurant re ON ra.restaurantid = re.restaurantid " +
                "WHERE userID = '" + userid + "'" +
                "ORDER BY ra.date_time;");
            } catch (Exception e) {
                System.out.println("Cant reada table(s) " + e);
            }
            try {
                String datetime;
                String price;
                String food;
                String mood;
                String staff;
                String comments;
                String restName;

                while (rs.next()) {
                    datetime = rs.getString("date_time");
                    price = rs.getString("price");
                    if (price == null) {
                        price = "n/a";
                    }
                    restName = rs.getString("name");
                    food = rs.getString("food");
                    if (food == null) {
                        food = "n/a";
                    }
                    mood = rs.getString("mood");
                    if (mood == null) {
                        mood = "n/a";
                    }
                    staff = rs.getString("staff");
                    if (staff == null) {
                        staff = "n/a";
                    }
                    comments = rs.getString("comments");
                    if (comments == null) {
                        comments = "n/a";
                    }

                    ratingsList += "<tr><td>" +
                        datetime +
                        "</td><td>" +
                        restName +
                        "</td><td>" +
                        price +
                        "</td><td>" +
                        food +
                        "</td><td>" +
                        mood +
                        "</td><td>" +
                        staff +
                        "</td><td>" +
                        comments +
                        "</td></tr>";
                }
            } catch (Exception e) {
                System.out.println("Error creating table " + e);
            }
        return ratingsList;
    }
    
    public String getQeList() {
    	String qeList="";
        connection = dataaccess.getConnection();
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT re.type AS type, mi.category AS category, CAST (AVG(mi.price) AS NUMERIC(4,2)) AS avgPrice " + 
                "FROM project.restaurant re " +
                "INNER JOIN project.menuitem mi ON re.restaurantid = mi.restaurantid " +
                "GROUP BY re.type, mi.category HAVING mi.category IS NOT NULL " +
                "ORDER BY re.type;");
            
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        
        try {
            String type;
            String category;
            String avgPrice;
            String prevType = "";
            String categories = "";
            String avgPrices = "";
            
            while (rs.next()) {
                type = rs.getString("type");
                category = rs.getString("category");
                avgPrice = "$"+rs.getString("avgPrice");
                if (!type.equals(prevType)) {
                    if (!prevType.isEmpty()) {
                    	qeList += "<tr><td>" +
                    			prevType +
                    			"</td><td>" +
                    			categories +
                    			"</td><td>" +
                    			avgPrices +
                    			"</td></tr>";
                    }
                    prevType = type;
                    categories = category;
                    avgPrices = avgPrice;
                }else {
                    categories += "<br>" + category;
                    avgPrices += "<br>" + avgPrice;
                }
            }
            qeList += "<tr><td>" +
            		prevType +
	                "</td><td>" +
	                categories +
	                "</td><td>" +
	                avgPrices +
	                "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
    	return qeList;
    }

    public String getQfList() {
        String qfList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT re.name AS restaurant, u.name AS rater, u.reputation AS rep, COUNT(*) AS number "+
				"FROM project.restaurant re "+
				"INNER JOIN project.rating ra ON re.restaurantid = ra.restaurantid " +
				"INNER JOIN project.rater u ON ra.userid = u.userid " +
				"GROUP BY re.name, u.name, u.reputation " +
				"ORDER BY re.name, u.reputation DESC;");
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
            String restaurant;
            String rater;
            String rep;
            String number;
            String prevRest = "";
            String raters = "";
            String reps = "";
            String numbers = "";

            while (rs.next()) {
                restaurant = rs.getString("restaurant");
                rater = rs.getString("rater");
                rep = rs.getString("rep");
                number = rs.getString("number");
                if (!restaurant.equals(prevRest)) {
                    if (!prevRest.isEmpty()) {
                        qfList += "<tr><td>" +
                            prevRest +
                            "</td><td>" +
                            raters +
                            "</td><td>" +
                            reps +
                            "</td><td>" +
                            numbers +
                            "</td></tr>";
                    }

                    prevRest = restaurant;
                    raters = rater;
                    reps = rep;
                    numbers = number;
                } else {
                    raters += "<br>" + rater;
                    reps += "<br>" + rep;
                    numbers += "<br>" + number;
                }
            }
            qfList += "<tr><td>" +
                prevRest +
                "</td><td>" +
                raters +
                "</td><td>" +
                reps +
                "</td><td>" +
                numbers +
                "</td></tr>";
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return qfList;
    }
  
    public String getQkList() {
        String kList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT u.name AS rater, u.join_date AS jdate, u.reputation AS rep, " + 
            		"(ra.food + ra.mood) AS overall, ra.date_time AS rdate, re.name AS restaurant " +
            		"FROM project.rater u "+
            		"INNER JOIN project.rating ra ON u.userid = ra.userid " + 
            		"INNER JOIN project.restaurant re ON ra.restaurantid = re.restaurantid " +
            		"WHERE (ra.food + ra.mood) = ( " + 
            		"SELECT MAX(ra2.food + ra2.mood) " + 
            		"FROM project.rating ra2) " +
            		"ORDER BY u.name;"); 
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
        	String rater;
        	String jdate;
            String rep;
            String rdate;
            String restaurant;
            
            String prevRater = "";
            String prevJoin = "";
            String prevRep = "";
            String rdates = "";
            String restaurants ="";

            while (rs.next()) {
                rater = rs.getString("rater");
                jdate = rs.getString("jdate");
                rep = rs.getString("rep");
                rdate = rs.getString("rdate");
                restaurant = rs.getString("restaurant");
      
                if (!rater.equals(prevRater)) {
                    if (!prevRater.isEmpty()) {
                        kList += "<tr><td>" +
                        	prevRater +
                            "</td><td>" +
                            prevJoin +
                            "</td><td>" +
                            prevRep +
                            "</td><td>" +
                            rdates+
                            "</td><td>" +
                            restaurants+
                            "</td></tr>";
                    }

                    prevRater = rater;
                    prevJoin = jdate;
                    prevRep = rep;
                    rdates = rdate;
                    restaurants = restaurant;
                }
                else {
                    rdates += "<br>" + rdate;
                    restaurants += "<br>" + restaurant;
                }
            }
            kList += "<tr><td>" +
                	prevRater +
                    "</td><td>" +
                    prevJoin +
                    "</td><td>" +
                    prevRep +
                    "</td><td>" +
                    rdates+
                    "</td><td>" +
                    restaurants+
                    "</td></tr>";        
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return kList;
    }
    
    public String getQlList() {
        String lList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT DISTINCT u.name AS rater, u.reputation AS rep, " + 
            		"(ra.food + ra.mood) AS overall, ra.date_time AS rdate, re.name AS restaurant " +
            		"FROM project.rater u "+
            		"INNER JOIN project.rating ra ON u.userid = ra.userid " + 
            		"INNER JOIN project.restaurant re ON ra.restaurantid = re.restaurantid " +
            		"WHERE (ra.food + ra.mood) = ( " + 
            		"SELECT MAX(ra2.food + ra2.mood) " + 
            		"FROM project.rating ra2) " +
            		"ORDER BY u.name;"); 
        } catch (Exception e) {
            System.out.println("Cant read table(s) " + e);
        }
        try {
        	String rater;
            String rep;
            String rdate;
            String restaurant;
            
            String prevRater = "";
            String prevRep = "";
            String rdates = "";
            String restaurants ="";

            while (rs.next()) {
                rater = rs.getString("rater");
                rep = rs.getString("rep");
                rdate = rs.getString("rdate");
                restaurant = rs.getString("restaurant");
      
                if (!rater.equals(prevRater)) {
                    if (!prevRater.isEmpty()) {
                        lList += "<tr><td>" +
                        	prevRater +
                            "</td><td>" +
                            prevRep +
                            "</td><td>" +
                            rdates+
                            "</td><td>" +
                            restaurants+
                            "</td></tr>";
                    }

                    prevRater = rater;
                    prevRep = rep;
                    rdates = rdate;
                    restaurants = restaurant;
                }
                else {
                    rdates += "<br>" + rdate;
                    restaurants += "<br>" + restaurant;
                }
            }
            lList += "<tr><td>" +
                	prevRater +
                    "</td><td>" +
                    prevRep +
                    "</td><td>" +
                    rdates+
                    "</td><td>" +
                    restaurants+
                    "</td></tr>";        
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return lList;
    }
    
    public String getTopRating() {
        String topRating = "";
        connection = dataaccess.getConnection();
        try {
            st = connection.createStatement();
            rs = st.executeQuery("SELECT MAX(ra.food + ra.mood) AS top FROM project.rating ra; "); 
        } catch (Exception e) {
            System.out.println("Cant read Rating table " + e);
        }
        try {
            if (rs.next()) {
            	topRating = rs.getString("top");
            }
        }
        catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return topRating;
    }

    public String getRaterOptionList() {
    	String raterOptionList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT u.name AS name, u.userid AS id FROM project.rater u ORDER BY u.name;");
        } catch (Exception e) {
                    System.out.println("Cant read Rater table");
                }
        try {
            String name;
            String id;
            while (rs.next()) {
                name = rs.getString("name");
                id = rs.getString("id");
                raterOptionList +="<option value="+id+">"+name+"</option>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return raterOptionList;
    } 
    
    public String getRaterOptionList2() {
    	String raterOptionList2 = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT DISTINCT u.name AS name FROM project.rater u ORDER BY u.name;");
        } catch (Exception e) {
                    System.out.println("Cant read Rater table");
                }
        try {
            String name;
            while (rs.next()) {
                name = rs.getString("name");
                raterOptionList2 +="<option value="+name+">"+name+"</option>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return raterOptionList2;
    } 
    
    public String getReOptionList() {
    	String reOptionList = "";
        connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT re.name AS name, re.restaurantid AS id FROM project.restaurant re ORDER by re.name;");
        } catch (Exception e) {
                    System.out.println("Cant read Restaurant table");
                }
        try {
            String name;
            String id;
            while (rs.next()) {
                name = rs.getString("name");
                id = rs.getString("id");
                reOptionList +="<option value="+id+">"+name+"</option>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return reOptionList;
    } 
    
    public String getTypeOptionList() {
    	String typeOptionList = "";
    	connection = dataaccess.getConnection();

        try {
            st = connection.createStatement();

			rs = st.executeQuery("SELECT DISTINCT re.type AS type FROM project.restaurant re ORDER by re.type;");
        } catch (Exception e) {
                    System.out.println("Cant read Rater table");
                }
        try {
            String type;
            while (rs.next()) {
                type = rs.getString("type");
                typeOptionList +="<option value="+type+">"+type+"</option>";
            }
        } catch (Exception e) {
            System.out.println("Error creating table " + e);
        }
        return typeOptionList;
    }
    
    public String getHourOptionList() {
    	String hourOptionList = "";
    	for(int i=1; i<13; i++) {
    		String aHour = i+":00am";
    		String bHour = i+":30am";
    		hourOptionList +="<option value="+aHour+">"+aHour+"</option>";
    		hourOptionList +="<option value="+bHour+">"+bHour+"</option>";
    	}
    	for(int i=1; i<13; i++) {
    		String aHour = i+":00pm";
    		String bHour = i+":30pm";
    		hourOptionList +="<option value="+aHour+">"+aHour+"</option>";
    		hourOptionList +="<option value="+bHour+">"+bHour+"</option>";
    	}
        return hourOptionList;
    }
    
    public String getYearOptionList() {
    	String yearOptionList = "";
    	for(int i=2018; i>1899; i--) {
    		yearOptionList +="<option value="+i+">"+i+"</option>";
    	}
        return yearOptionList;
    }
    
    public String getMonthOptionList() {
    	String monthOptionList = "<option value=01>Jan</option>" +
    			"<option value=02>Feb</option>" +
    			"<option value=03>Mar</option>" +
    			"<option value=04>Apr</option>" +
    			"<option value=05>May</option>" +
    			"<option value=06>Jun</option>" +
    			"<option value=07>Jul</option>" +
    			"<option value=08>Aug</option>" +
    			"<option value=09>Sep</option>" +
    			"<option value=10>Oct</option>" +
    			"<option value=11>Nov</option>" +
    			"<option value=12>Dec</option>";
    	
        return monthOptionList;
    }

    public String getYearIndexList() {
    	String yearIndexList = "";
    	for(int i=2018; i > 2000; i--) {
    		yearIndexList += "<option value="+i+">"+i+"</option>";
    	}
    	return yearIndexList;
    }
    
    public String getDollarList() {
    	String dollarList = "";
    	for(int i=0; i < 101; i++) {
    		dollarList += "<option value="+i+">"+i+"</option>";
    	}
    	return dollarList;
    }
    public String getCentList() {
    	String centList = "";
    	for(int i=0; i < 100; i++) {
    		String cents;
    		if(i < 10) {
    			cents = ".0"+i;
    		}
    		else {
    			cents = "."+i;
    		}
    		centList += "<option value="+cents+">"+cents+"</option>";
    	}
    	return centList;
    }
    public String getRatingValueList() {
    	String valueList = "";
    	for(double d=1; d<5.5; d=d+0.5) {
    		valueList += "<option value="+d+">"+d+"</option>";
    	}
    	return valueList;
    }
}