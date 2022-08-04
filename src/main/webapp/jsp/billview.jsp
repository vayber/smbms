<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
<div class="right">
     <div class="location">
         <strong>你现在所在的位置是:</strong>
         <span>订单管理页面 >> 信息查看</span>
     </div>
     <div class="providerView">
         <table border="5" cellspacing="5" cellpadding="8"  style="width: 100%">
             <tr>
                 <th>订单ID：</th>
                 <td style="width: 35%">${bill.id }</td>
                 <th>订单编码：</th>
                 <td style="width: 35%">${bill.billCode} </td>
             </tr>
             <tr>
                 <th>商品名称：</th>
                 <td >${bill.productName }</td>
                 <th>商品描述：</th>
                 <td >${bill.productDesc} </td>
             </tr>
             <tr>
                 <th>商品数量：</th>
                 <td >${bill.productCount }</td>
                 <th>商品单位：</th>
                 <td >${bill.productUnit} </td>
             </tr>
             <tr>
                 <th>总价格：</th>
                 <td >${bill.totalPrice }</td>
                 <th>是否支付：</th>
                 <td >${bill.isPayments} </td>
             </tr>
             <tr>
                 <th>供应商ID：</th>
                 <td >${bill.providerId}</td>
                 <th>供应商：</th>
                 <td >${bill.proName} </td>
             </tr>
             <tr>
                 <th>createdBy：</th>
                 <td >${bill.createdBy}</td>
                 <th>creationDate：</th>
                 <td >${bill.creationDate} </td>
             </tr>
             <tr>
                 <th>modifyBy：</th>
                 <td >${bill.modifyBy}</td>
                 <th>modifyDate：</th>
                 <td >${bill.modifyDate} </td>
             </tr>

         </table>

		<div class="providerAddBtn">
         	<input type="button" id="back" name="back" value="返回" >
        </div>
     </div>
 </div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billview.js"></script>