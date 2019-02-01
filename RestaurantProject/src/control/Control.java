//Allie LaCompte
//DB Project W18
package control;

import connection.DataAccess;
import dbbeans.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Control extends HttpServlet {
	
    private DataAccess db;

    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String raterName = (String) request.getParameter("userName");
        RequestDispatcher rd;

        if (raterName.trim().isEmpty()) {
            rd = this.getServletContext().getRequestDispatcher("/index.jsp");
            request.setAttribute("error", "Error: Please enter a valid username");
        } else {
            db = new DataAccess();
            db.openConnection();

            RaterBean raterbean = new RaterBean();

            int userid = raterbean.existsRaterName(raterName, db);
            if (userid == -1) {
                db.closeConsult();

                rd = this.getServletContext().getRequestDispatcher("/index.jsp");
                request.setAttribute("error", "Error: Username does not exist");
            } else {
                raterbean.setRater(raterName, userid, db);

                s.setAttribute("raterbean", raterbean);
                s.setAttribute("db", db);
                s.setAttribute("key", "000");
                s.setMaxInactiveInterval(1000);

                db.closeConsult();

                rd = this.getServletContext().getRequestDispatcher("/menu.jsp");
            }
        }
        rd.forward(request, response);
    }

    private void processSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String raterName = ((String) request.getParameter("newName")).replaceAll("\\s","").replaceAll("'", "''");
        String email = ((String) request.getParameter("email")).replaceAll("\\s","");

        RequestDispatcher rd;

        if (raterName.isEmpty()) {
            rd = this.getServletContext().getRequestDispatcher("/index.jsp");
            request.setAttribute("error", "Error: Please enter a valid username");
        } else {
            db = new DataAccess();
            db.openConnection();

            RaterBean raterbean = new RaterBean();

            int userid = raterbean.existsRaterName(raterName, db);
            if (userid != -1) {
                db.closeConsult();

                rd = this.getServletContext().getRequestDispatcher("/index.jsp");
                request.setAttribute("error", "Error: Username already exists");
            } else {
                String type = (String) request.getParameter("userType");
                raterbean.insertRater(raterName, email, type, db);

                s.setAttribute("raterbean", raterbean);
                s.setAttribute("db", db);
                s.setAttribute("key", "000");
                s.setMaxInactiveInterval(1000);

                db.closeConsult();

                rd = this.getServletContext().getRequestDispatcher("/menu.jsp");
            }
        }

        rd.forward(request, response);
    }
    
    private void processDelUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        RequestDispatcher rd;

        RaterBean raterbean = (RaterBean) s.getAttribute("raterbean");
        raterbean.deleteUser();

        rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
    
    private void processRateRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("restaurant");
        String price = (String) request.getParameter("price");
        String food = (String) request.getParameter("food");
        String mood = (String) request.getParameter("mood");
        String staff = (String) request.getParameter("staff");
        String comments = (String) request.getParameter("comments");

        RequestDispatcher rd;

        RaterBean raterbean = (RaterBean) s.getAttribute("raterbean");
        raterbean.insertRating(restaurant, price, food, mood, staff, comments);

        rd = this.getServletContext().getRequestDispatcher("/rateRestaurant.jsp");
        rd.forward(request, response);
    }
    
    private void processAddRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("reName");
        String type = (String) request.getParameter("type");
        String url = (String) request.getParameter("url");
        String opendy = (String) request.getParameter("opendY");
        String opendm = (String) request.getParameter("opendM");
        String manager = (String) request.getParameter("manager");
        String phone = (String) request.getParameter("phone");
        String addr = (String) request.getParameter("addr");
        String openh = (String) request.getParameter("openh");
        String closeh = (String) request.getParameter("closeh");

        RequestDispatcher rd;

        RaterBean raterbean = (RaterBean) s.getAttribute("raterbean");
        raterbean.insertRestaurant(restaurant, type, url, opendy, opendm, manager, phone, addr, openh, closeh);

        rd = this.getServletContext().getRequestDispatcher("/addRestaurant.jsp");
        rd.forward(request, response);
    }
    
    private void processAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("restaurant");
        String item = (String) request.getParameter("item");
        String type = (String) request.getParameter("type");
        String category = (String) request.getParameter("category");
        String description = (String) request.getParameter("description");
        String dollars = (String) request.getParameter("dollars");
        String cents = (String) request.getParameter("cents");
        String price = dollars+cents;

        RequestDispatcher rd;

        RaterBean raterbean = (RaterBean) s.getAttribute("raterbean");
        raterbean.insertItem(restaurant, item, type, category, description, price);

        rd = this.getServletContext().getRequestDispatcher("/addMenuItem.jsp");
        rd.forward(request, response);
    }
    
    private void processDelRestaurant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("restaurant");
        RequestDispatcher rd;

        RaterBean raterbean = (RaterBean) s.getAttribute("raterbean");
        raterbean.deleteRestaurant(restaurant);

        rd = this.getServletContext().getRequestDispatcher("/menu.jsp");
        rd.forward(request, response);
    }
    
    private void processDelItemReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("restaurant");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        RestaurantBean restbean = new RestaurantBean();
        restbean.setDataAccess(db);
        restbean.setID(restaurant);

        s.setAttribute("restbean", restbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/delMenuItem.jsp");
        rd.forward(request, response);
    }
    
    private void processDelItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String item = (String) request.getParameter("item");
        RequestDispatcher rd;

        RestaurantBean restbean = (RestaurantBean) s.getAttribute("restbean");
        restbean.deleteItem(item);

        rd = this.getServletContext().getRequestDispatcher("/delMenuItem.jsp");
        rd.forward(request, response);
    }
	
      private void processQA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String resId = (String) request.getParameter("restaurant");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        ABDBean qabean = new ABDBean();
        qabean.setDataAccess(db);
        qabean.setResId(resId);

        s.setAttribute("qabean", qabean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qA.jsp");
        rd.forward(request, response);
    }
	
     private void processQB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String restaurant = (String) request.getParameter("restaurant");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        ABDBean qbbean = new ABDBean();
        qbbean.setDataAccess(db);
        qbbean.setResId(restaurant);

        s.setAttribute("qbbean", qbbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qB.jsp");
        rd.forward(request, response);
    }
	
     private void processQC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String type = (String) request.getParameter("type");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        CBean qcbean = new CBean();
        qcbean.setDataAccess(db);
        qcbean.setType(type);
 
        s.setAttribute("qcbean", qcbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qC.jsp");
        rd.forward(request, response);
    }
    
    private void processQD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String resId = (String) request.getParameter("restaurant");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        ABDBean qdbean = new ABDBean();
        qdbean.setDataAccess(db);
        qdbean.setResId(resId);

        s.setAttribute("qdbean", qdbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qD.jsp");
        rd.forward(request, response);
    }

    private void processQG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String month = (String) request.getParameter("month");
        String year = (String) request.getParameter("year");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        QGBean qgbean = new QGBean();
        qgbean.setDataAccess(db);
        qgbean.setMonth(month);
        qgbean.setYear(year);

        s.setAttribute("qgbean", qgbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qG.jsp");
        rd.forward(request, response);
    }
    
    private void processQH(HttpServletRequest request, HttpServletResponse response, int version) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String id = (String) request.getParameter("userh");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        QHBean qhbean = new QHBean();
        qhbean.setDataAccess(db);
        qhbean.setUserID(id);
        qhbean.setVersion(version);

        s.setAttribute("qhbean", qhbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();
        if(version == 0) {
        	rd = this.getServletContext().getRequestDispatcher("/qHa.jsp");
        }
        else {
        	rd = this.getServletContext().getRequestDispatcher("/qHbc.jsp");
        }

        rd.forward(request, response);
    }
    
    private void processQI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String type = (String) request.getParameter("type");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        QIBean qibean = new QIBean();
        qibean.setDataAccess(db);
        qibean.setType(type);

        s.setAttribute("qibean", qibean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qI.jsp");
        rd.forward(request, response);
    }
    
    private void processQJ(HttpServletRequest request, HttpServletResponse response, int version) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String type = (String) request.getParameter("type");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        QJBean qjbean = new QJBean();
        qjbean.setDataAccess(db);
        qjbean.setType(type);
        qjbean.setVersion(version);

        s.setAttribute("qjbean", qjbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();
       
        rd = this.getServletContext().getRequestDispatcher("/qJ.jsp");
        rd.forward(request, response);
    }
    
    private void processQM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String userName = (String) request.getParameter("user");
        RequestDispatcher rd;

        db = new DataAccess();
        db.openConnection();

        QMBean qmbean = new QMBean();
        qmbean.setDataAccess(db);
        qmbean.setUserName(userName);

        s.setAttribute("qmbean", qmbean);
        s.setAttribute("db", db);
        s.setAttribute("key", "000");
        s.setMaxInactiveInterval(1000);

        db.closeConsult();

        rd = this.getServletContext().getRequestDispatcher("/qM.jsp");
        rd.forward(request, response);
    }
    
    
   
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("loginBttn") != null) {
            System.out.println("Attempting to login");
            processLogin(req, resp);
        } 
        else if (req.getParameter("signupBttn") != null) {
        	System.out.println("Attempting to signup");
            processSignup(req, resp);
         } 
        else if (req.getParameter("userDelBttn") != null) {
        	System.out.println("Attempting to delete account");
        	processDelUser(req, resp);
        }
        else if (req.getParameter("rateRestBttn") != null) {
        	System.out.println("Attempting to insert rating");
        	processRateRestaurant(req, resp);
        }
        else if (req.getParameter("reAddBttn") != null) {
            System.out.println("Attempting to add restaurant");
            processAddRestaurant(req, resp);
        }
        else if (req.getParameter("reDelBttn") != null) {
            System.out.println("Attempting to delete restaurant");
            processDelRestaurant(req, resp);
        }
        else if(req.getParameter("itemAddBttn") != null) {
        	System.out.println("Attempting to add item");
        	processAddItem(req, resp);
        }
        else if (req.getParameter("miDelBttn") != null) {
        	System.out.println("Processing delete item request");
        	processDelItemReq(req, resp);
        }
        else if (req.getParameter("delItemBttn") != null) {
        	System.out.println("Attempting to delete menu item");
        	processDelItem(req, resp);
        }
        else if (req.getParameter("qABttn") != null) {
            System.out.println("Attempting Q.A");
            processQA(req, resp);
        } 
        else if (req.getParameter("qBBttn") != null) {
            System.out.println("Attempting Q.B");
            processQB(req, resp);
        } 
        else if (req.getParameter("qCBttn") != null) {
            System.out.println("Attempting Q.C");
            processQC(req, resp);
        } 
        else if (req.getParameter("qDBttn") != null) {
            System.out.println("Attempting Q.D");
            processQD(req, resp);
        } 
        else if (req.getParameter("qGBttn") != null) {
            System.out.println("Attempting Q.G");
            processQG(req, resp);
        
        }else if (req.getParameter("qHaBttn") != null) {
        	System.out.println("Attempting Q.Ha");
        	processQH(req, resp, 0);
        }
        else if (req.getParameter("qHbBttn") != null) {
        	System.out.println("Attempting Q.Hb");
        	processQH(req, resp, 1);
        }
        else if (req.getParameter("qHcBttn") != null) {
        	System.out.println("Attempting Q.Hc");
        	processQH(req, resp, 2);
        }
        else if(req.getParameter("qIBttn") != null) {
        	System.out.println("Attempting Q.I");
        	processQI(req, resp);
        }
        else if(req.getParameter("qJaBttn") != null) {
        	System.out.println("Attempting Q.Ja");
        	processQJ(req, resp, 0);
        }
        else if(req.getParameter("qJbBttn") != null) {
        	System.out.println("Attempting Q.Jb");
        	processQJ(req, resp, 1);
        }
        else if(req.getParameter("qMBttn") != null) {
        	System.out.println("Attempting Q.M");
        	processQM(req, resp);
        }
    }

    public void destroy() {
        super.destroy();
        db.closeConsult();
        db.closeConnection();
    }
}
