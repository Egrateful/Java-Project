<%-- 
    Document   : dump
    Created on : 2016/3/21, 下午 03:57:14
    Author     : PattyTai
--%>

<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dump</title>
        <style>
            table{width: 100%}
        </style>
    </head>
    <body>        
        context-path: <%= request.getContextPath()%><br>
        Request URL: <%= request.getRequestURL()%><br>
        Protocol: <%= request.getProtocol()%><br>
        Remote Addr: <%= request.getRemoteAddr()%><br>
        Server Name <%= request.getServerName()%><br>
        Server Port: <%= request.getServerPort()%><br>
        <%
            Enumeration<String> names = request.getHeaderNames();
        %>
        <table>
            <caption>Request Headers List</caption>
            <tr><th>Name</th><th>Value</th></tr>
            <% while (names.hasMoreElements()) {
                String name = names.nextElement();
            %>    
            <tr><td><%= name%></td><td><%= request.getHeader(name)%></td></tr>
            <% } %>
        </table>
        <br>
        <hr>
        <br>
        <%
            Enumeration<String> paramNames = request.getParameterNames();
        %>
        <table>
            <caption>Request Parameters List</caption>
            <tr><th>Name</th><th>Value</th></tr>
            <% while (paramNames.hasMoreElements()) {
                String name = paramNames.nextElement();
            %>    
            <tr><td><%= name%></td><td><%= request.getParameter(name)%></td></tr>
            <% } %>
        </table>        
        <br>
        <hr>
        <br>
        session id: <%= session.getId() %><br>
        session Creation Time: <%= session.getCreationTime() %><br>
        session Last Accessed Time: <%= session.getLastAccessedTime() %><br>
        session Max Inactive Interval <%= session.getMaxInactiveInterval() %><br>
        <br>
        <hr>
        <br>
        Context Path: <%=  application.getContextPath() %><br>
        Real Path: <%=  application.getRealPath("/") %><br>
    </body>
</html>
