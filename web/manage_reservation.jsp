<%-- 
    Document   : order_history
    Created on : 2016/4/6, 下午 05:45:22
    Author     : PattyTai
--%>

<%@page import="uuu.totalbuy.model.OrderService"%>
<%@page import="uuu.totalbuy.domain.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" info="歷史訂單"%>
<%
    Customer user = (Customer) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    
    OrderService service = new OrderService();
    List<Order> list = service.getOrderHistoryByCustomer(user);
%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo() %>" />
</jsp:include>
<div id="article">
    <%  
    if(list!=null && list.size()>0){
        out.println(list);
    }
    %>
</div>
<%@include file="/WEB-INF/subviews/footer.jsp" %>        
    
