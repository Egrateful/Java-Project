<%@page import="uuu.resort.domain.Customer"%>
<%@page import="uuu.resort.domain.Room"%>
<%@page import="uuu.resort.domain.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8" info="檢視購物車"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo()%>" />
</jsp:include>
<div id="article">    
    <form method="POST" action="update_cart.do">
        <%
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart != null && cart.size() > 0) {
                Customer user = (Customer) session.getAttribute("user");
                if (user != null) {
                    cart.setCustomer(user);
                }
        %>
        <ul>
            <% for (Room p : cart.getKeySet()) {%>
            <li><%= p.getId()%>, <%= p.getName()%>, 
                <%= p.getUnitPrice()%>, 
                數量: <input type="number" min="1" max="5" name="quantity_<%=p.getId()%>" value="<%= cart.getQuantity(p)%>" required>
                刪除: <input type="checkbox" name="delete_<%=p.getId()%>">
            </li>
            <%}%>
            <li>總金額:<%= cart.getTotalAmout()%></li>
                <%if (cart.getCustomer() != null) {%>
            <li>VIP總金額:<%= cart.getVIPTotalAmout()%></li>        
                <%}%>
        </ul>
        <%} else {%>    
        <p>購物車是空的!</p>
        <%}%>
        <div>
            <span style="float:left">
                <input type="button" onclick="go_shopping()" value="繼續購物"></span>
            <span style="float:right">
                <input type="submit" value="修改購物車">
                <input type="button" value="確認結帳"
                       onclick="location.href='<%= request.getContextPath()%>/user/check_out.jsp';" >
            </span>
        </div>
    </form>    
    <script>
        function go_shopping() {
            location.href = "products_list.jsp";
        }
    </script>
</div>

<%@include file="/WEB-INF/subviews/footer.jsp" %>