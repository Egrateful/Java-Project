<%-- 
    Document   : register_ok
    Created on : 2016/3/22, 下午 03:58:29
    Author     : PattyTai
--%>

<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8" info="註冊成功"%>
<jsp:useBean id="customer"  type="uuu.totalbuy.domain.Customer" scope="request"/>
<%
    //Customer customer = (Customer)request.getAttribute("customer");
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%= this.getServletInfo() %></title>
    </head>
    <body>
        <h1><%= application.getInitParameter("app-name")%></h1>        
        <h3><%= this.getServletInfo() %>: </h3>
        姓名:<%= customer.getName()%>
        電郵:<jsp:getProperty name="customer" property="email" />
        地址:${requestScope.customer.address}
    </body>
</html>
