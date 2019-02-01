<!DOCTYPE HTML>
<!--Provided in lab
DB Project W18 -->
<% session = request.getSession(false);
   session.invalidate();
   response.sendRedirect("index.jsp");
%>