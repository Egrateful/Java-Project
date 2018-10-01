<%@page contentType="text/html" pageEncoding="UTF-8" info="結帳作業" import="uuu.totalbuy.domain.*"%>
<%
    Customer user = (Customer) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    } else {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        cart.setCustomer(user);
%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo()%>" />
</jsp:include>
<div id="article">
    <form method="POST" action="">
        <% if (cart != null && cart.size() > 0){ %>
        <ul>
            <% for (Product p : cart.getKeySet()) { %>
            <li><%= p.getId()%>, <%= p.getName()%>,                 
                <% if(p instanceof Outlet){ %>
                    原價:<%= ((Outlet)p).getListPrice()%>, 折扣: <%= ((Outlet)p).getDiscount()%>,
                <%}%>
                售價: <%= p.getUnitPrice()%>, 
                數量: <input type="number" min="1" max="5" value="<%= cart.getQuantity(p)%>" readonly>                
            </li>
            <% } %>
            <% if (user instanceof VIP) { %>
                <li>原來總金額:<%= cart.getTotalAmout()%></li>
            <% } %>            
            <li>實際總金額:<span id="totalAmount"><%= cart.getVIPTotalAmout()%></span></li>                    
            <li>
                <label for="payment_type">付款方式:</label>
                <select id="payment_type" name="payment_type" required onchange="changeHandler()">
                    <option value="">請選擇...</option>
                    <% for(PaymentType paymentType:PaymentType.values()){%>
                    <option value="<%= paymentType.ordinal() %>">
                        <%= paymentType%>
                    </option>
                    <%}%>
                </select>
                <script src="${pageContext.request.contextPath}/js/pst.js" ></script>    
                <label for="shipping_type">貨運方式:</label>
                <select id="shipping_type" name="shipping_type" required onchange="totalAmountHandler()">
                    <option value="">請選擇...</option>
                </select>
            </li>
            <li>含運總金額:<span id="finalTotalAmount"><%= cart.getVIPTotalAmout()%></span></li>    
            <li>
                <fieldset>
                    <legend>收件人:</legend>
                    <label for="receiver">姓名: </label><input id="receiver" name="receiver" placeholder="收件人姓名" required=""><br>
                    <label for="receiver_phone">電話: </label><input id="receiver_phone" name="receiver_phone" placeholder="收件人電話" required=""><br>
                    <label for="receiver_email">電郵: </label><input id="receiver_email" name="receiver_email" placeholder="收件人電郵" required=""><br>
                    <label for="receiver_address">地址: </label><input id="receiver_address" name="receiver_address" placeholder="收件人地址" required=""><br>                    
                </fieldset>
            </li>
        </ul>
        <% } %>
        <div>
            <span style="float:left"><input type="button" onclick="go_shopping()" value="繼續購物"></span>
            <span style="float:right"><input type="submit" value="送出訂單"></span>
        </div>
    </form>  
</div>
<%@include file="/WEB-INF/subviews/footer.jsp" %>
<% } %>