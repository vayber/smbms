<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户修改页面</span>
        </div>
        <div class="providerAdd">

        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/management.do?method=modify">
			<input type="hidden" name="id" id="id" value="${user.id }"/>
            <input type="hidden" name="modifyBy" value="${userSession.id}">
			 <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName" value="${user.userName }"> 
					<font color="red"></font>
             </div>
			 <div>
                    <label >用户性别：</label>
                    <select name="gender" id="gender">
						<c:choose>
							<c:when test="${user.userGender == '男' }">
								<option value="2" selected="selected">男</option>
					    		<option value="1">女</option>
							</c:when>
							<c:otherwise>
								<option value="2">男</option>
					    		<option value="1" selected="selected">女</option>
							</c:otherwise>
						</c:choose>
					 </select>
             </div>
			 <div>
                    <label for="age">年龄：</label>
                 <input type="text" name="age" id="age" value="${user.age}">
                    <font color="red"></font>
              </div>
			
		       <div>
                    <label for="phone">用户电话：</label>
                    <input type="text" name="phone" id="phone" value="${user.phone }">
                    <font color="red"></font>
               </div>
                <div>
                    <label for="address">用户地址：</label>
                    <input type="text" name="address" id="address" value="${user.address }">
                </div>
				<div>
                    <label >用户角色：</label>
                    <!-- 列出所有的角色分类 -->

                    <select name="userRole">
                        <option value="0"
                                <c:if test="${user.userRole == '0'}">
                                    selected
                                </c:if>
                        >--请选择--</option>
                        <option value="1"
                                <c:if test="${user.userRole == '1'}">
                                    selected
                                </c:if>
                        >系统管理员</option>
                        <option value="2"
                                <c:if test="${user.userRole == '2'}">
                                    selected
                                </c:if>
                        >经理</option>
                        <option value="3"
                                <c:if test="${user.userRole == '3'}">
                                    selected
                                </c:if>
                        >普通员工</option>
                    </select>
                </div>
			 <div class="providerAddBtn">
                    <input type="submit" name="save" id="save" value="提交" />
                    <input type="button" id="back" name="back" value="返回"/>
                </div>
            </form>
        </div>
    </div>
</section>

<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/usermodify.js"></script>
