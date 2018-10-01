<%@page import="uuu.totalbuy.domain.BloodType"%>
<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" info="會員註冊"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo()%>" />
</jsp:include>
<div id="article">
    <%        
        List<String> errors = (List<String>) request.getAttribute("errors");
        if (errors != null && errors.size() > 0) {
    %>
    <ul>
        <%for (String msg : errors) {%>
        <li><%=msg%></li>
            <%}%>
    </ul>    
    <%
        }
    %>   
     <div  class="signin">
    <form method="POST" action="register.do">
        <p>
            <label for="userid">會員帳號:</label>
            <input type="text" id="userid" name="id" placeholder="請輸入帳號" pattern="[A-Za-z][12]\d{8}" required
                   value="<%= request.getParameter("id") == null ? "" : request.getParameter("id")%>">
        </p>
        <p>
            <label for="name">會員姓名:</label>
            <input type="text" id="name" name="name" placeholder="請輸入姓名" required
                   value="<%= request.getMethod().equalsIgnoreCase("post") ? request.getParameter("name") : ""%>" >            
        </p>
        <p>
            <label for="pwd1">會員密碼:</label>
            <input type="password" id="pwd1" name="password1" minlength="6" maxlength="20" placeholder="請輸入密碼" required>            
        </p>
        <p>
            <label for="pwd2">確認密碼:</label>
            <input type="password" id="pwd2" name="password2" minlength="6" maxlength="20" placeholder="請輸入確認密碼" required>            
        </p>
        <p>              
            <label>會員性別:</label>
            <input type="radio" id="male" name="gender" value='<%= Customer.MALE%>' required 
                   <%= request.getParameter("gender") != null && request.getParameter("gender").charAt(0) == Customer.MALE ? "checked" : ""%>
                   ><label for="male">男</label>        
            <input type="radio" id="female" name="gender" value="<%= Customer.FEMALE%>" required
                   <%= request.getParameter("gender") != null && request.getParameter("gender").charAt(0) == Customer.FEMALE ? "checked" : ""%>
                   ><label for="female">女</label>        
        </p>
        <p>              
            <label for="email">電子郵件:</label>
            <input type="email" id="email" name="email" required
                   value="<%= request.getMethod().equalsIgnoreCase("post") ? request.getParameter("email") : ""%>">
        </p>
        <p>              
            <label for="birthday">出生日期:</label>
            <input type="date" id="birthday" name="birthday" 
                   value="<%= request.getMethod().equalsIgnoreCase("post") ? request.getParameter("birthday") : ""%>">
        </p>            
        <p>              
            <label for="phone">聯絡電話:</label>
            <input type="tel" id="phone" name="phone" 
                   value="<%= request.getMethod().equalsIgnoreCase("post") ? request.getParameter("phone") : ""%>">
        </p>
        <p>              
            <label for="address">聯絡地址:</label>
            <input type="text" id="address" name="address" 
                   value="<%= request.getMethod().equalsIgnoreCase("post") ? request.getParameter("address") : ""%>">
        </p>
        <p>              
            <label>婚姻狀況:</label>
            <input type="checkbox" id="married" name="married" 
                   <%= request.getParameter("married") != null ? "checked" : ""%>>
            <label for="married">已婚</label>
        </p>            
        <p>              
            <label for="blood_type">血型:</label>
            <select id="blood_type" name="blood_type">
                <option value="">請選擇...</option>
                <% for (BloodType bType : BloodType.values()) {%>
                <option value="<%= bType.name()%>" 
                        <%= bType.name().equals(request.getParameter("blood_type")) ? "selected" : ""%>
                        ><%= bType.toString()%></option>
                <%}%>
            </select>
        </p>        
        <p>
            <img src="images/reg_check_code.jpg" alt="" id='check_image'><a href="javascript:refresh()">更新圖片</a><br>
            <label for="check_code">驗證碼:</label>
            <input type="text" id="check_code" name="check_code" placeholder="請輸入驗證碼" required       
                   value="${param.check_code}">
            <script>
                function refresh() {
                    var image = document.getElementById("check_image");
                    image.src = "images/reg_check_code.jpg?get=" + new Date();
                }
            </script>
        </p>
        <input type="submit" value="加入會員">
    </form>
    </div>
</div>
<%@include file="/WEB-INF/subviews/footer.jsp" %>

