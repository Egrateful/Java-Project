<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html" info="歡迎" %>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="<%= this.getServletInfo()%>" />
</jsp:include>
<article>
<!--    <div class="classArea">classArea
        </div>classArea end
    -->
	<div class="wrap"><!--wrap-->
<!--            <div class="albumBox">albumBox-->
            <div style="max-width: 1000px; margin: 10px auto;" class="bx-wrapper">
            <div style="width: 100%; overflow: hidden; position: relative; height: 336px;" class="bx-viewport">
<!--                <ul style="width: auto; position: relative;" id="album" class="album clearfix">album-->
                    <ul style="width: auto; position: absolute;" id="album" class="album clearfix">
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic1.jpg" alt="pic_b01"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: list-item;"><img src="images/pic2.jpg" alt="jpg"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic3.jpg" alt="-2"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic4.jpg" alt="jpg"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic5.jpg" alt="001"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic6.jpg" alt="05"></li>
                    <li style="float: none; list-style: outside none none; position: absolute; width: 800px; z-index: 0; display: none;"><img src="images/pic7.jpg" alt="-2"></li>
                </ul></div>
                <div class="bx-controls bx-has-controls-direction">
                    <div class="bx-controls-direction"><a class="bx-prev" href="">Prev</a>
                <a class="bx-next" href="">Next</a></div></div></div>
   
<!--        </div>albumBox end-->
        
        <div class="textEditor"><!--textEditor-->
        	<div style="text-align: center"><strong class="title">清境高山歐風城堡 逸境山林渡假會館 </strong><br>
穿過翠湖小徑 櫻花與落羽松圍繞庄園<br>
遠眺山谷間的碧湖水色 夕照光暉映照山嵐雲海<br>
這裡有清境農場最美麗的絕高視野<br>
<br>
延續幾世紀的歐洲貴族生活 為何讓人如此憧憬？<br>
<span style="color: #008000"><strong>君士坦丁</strong></span>巴洛克宮廷城堡的華麗古典<br>
<span style="color: #008000"><strong>義式鄉村</strong></span>莊園別墅的優雅精緻<br>
<span style="display:none;">綠意的<strong><font color="#008000">歐洲</font></strong><span style="color: #008000"><strong>庭園</strong></span><br></span>
將歐洲貴族名媛數百年來的奢華考究濃縮在這一刻<br>
讓貼心適切的待客之道<br>
與尊貴大氣的建築風格融為一體<br>
開啟在清境逸境山林會館的美好時光</div>        </div><!--textEditor end-->
        
    </div><!--wrap end-->	

</article><!--article end-->
<%@include file="/WEB-INF/subviews/footer.jsp" %>