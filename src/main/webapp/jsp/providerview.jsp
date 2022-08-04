<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面 >> 信息查看</span>
        </div>
        <div class="providerView">

            <table border="5" cellspacing="5" cellpadding="8"  style="width: 100%">
                <tr>
                    <th>供应商ID：</th>
                    <td style="width: 35%">${provider.id }</td>
                    <th>供应商编码：</th>
                    <td style="width: 35%">${provider.proCode} </td>
                </tr>
                <tr>
                    <th>供应商名称：</th>
                    <td >${provider.proName }</td>
                    <th>供应商描述：</th>
                    <td >${provider.proDesc} </td>
                </tr>
                <tr>
                    <th>联系人：</th>
                    <td >${provider.proContact }</td>
                    <th>联系电话：</th>
                    <td >${provider.proPhone} </td>
                </tr>
                <tr>
                    <th>地址：</th>
                    <td >${provider.proAddress }</td>
                    <th>传真：</th>
                    <td >${provider.proFax} </td>
                </tr>
                <tr>
                    <th>createdBy：</th>
                    <td >${provider.createdBy}</td>
                    <th>creationDate：</th>
                    <td >${provider.creationDate} </td>
                </tr>
                <tr>
                    <th>modifyBy：</th>
                    <td >${provider.modifyBy}</td>
                    <th>modifyDate：</th>
                    <td >${provider.modifyDate} </td>
                </tr>

            </table>


			<div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>
    </div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/providerview.js"></script>
