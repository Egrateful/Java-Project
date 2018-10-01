<%@page import="java.util.List"%>
<%@page import="uuu.resort.domain.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8" info="產品清單"%>
<jsp:useBean id="service" class="uuu.resort.model.ProductService" />
<%    
    String search = request.getParameter("search");
    List<Product> list = null;
    //ProductService service = new ProductService(); //local variable
    if(search==null || (search=search.trim()).length()==0){    
        list = service.getAll();
    }else{
        list = service.getByName(search);
    }
%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo()%>" />
</jsp:include>
<div id="article">
    <form style="text-align: right" >
        <input type="search" name="search" placeholder="請輸入產品代號或部分名稱..." style="width: 85%" value="${param.search}">
        <input type="submit" value="查詢">
    </form>            
    <div id="product_div">
        <ul>
            <% if (list != null && list.size() > 0) {
                    for (Product p : list) {
            %>
            <li class="product_item">
                <h5><%= p.getName()%></h5>
                <div>
                    <a class="product_photo" href="product.html?p_id=1">
                        <img src="<%= p.getUrl()==null?"images/phone.png":p.getUrl()%>">
                    </a>                    
                    <p>
                        <%if(p instanceof Outlet){%>
                        原價: <%= ((Outlet)p).getListPrice()%><br>
                        折扣: <%= ((Outlet)p).getDiscount()%> <br>
                        <%}%>
                        售價: <%= p.getUnitPrice()%> 
                    </p>                    
                    <a href="${pageContext.request.contextPath}/add_cart.do?pid=<%= p.getId()%>">
                        <img style="width:30px" src="images/cart.png" alt="加入購物車" title="加入購物車"/>
                    </a>
                </div>
            </li>
            <%      }
               }%>
        </ul>
    </div>
</div>
<%@include file="/WEB-INF/subviews/footer.jsp" %>

