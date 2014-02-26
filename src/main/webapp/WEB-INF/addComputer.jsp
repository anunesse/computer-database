<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.excilys.formation.projet.dao.*"%>
<%@ page import="com.excilys.formation.projet.om.*"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<section id="main">

	<div style="margin-top:90px"></div>

	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	<c:choose>
		<c:when test="${answer==0}">
			<h1><spring:message code="label.add"/></h1>
			<form class="myForm" action="AddComputer" method="POST">
				<input type="hidden" name="mode" value="add"/>
		</c:when>
		
		<c:otherwise>
			<h1><spring:message code="label.edit"/></h1>
			
			<form action="DelComputer" method="POST">
				<input type="hidden" name="computer" value="${computer.id }"/>
				<button type="submit" onclick="return confirm('You are about to delete this computer, are you sure?')" class="btn btn-danger">
					<spring:message code="label.buttondelete"/>
				</button>
				<!-- <input type="submit" value="Delete" onclick="return confirm('You are about to delete this computer, are you sure?')" class="btn btn-danger">-->
			</form>
			
			<form class="myForm" action="EditComputer" method="POST">
				<input type="text" name="comp_id" value="${computer.id }" readonly/>
		</c:otherwise>
	</c:choose>	
				<fieldset>
					<div class="clearfix">
						<label for="name"><spring:message code="label.table.header.computer"/> :</label>
						<div class="input">
							<input type="text" name="name" data-validation="length" data-validation-length="1-255" value="${computer.name }"/>
							<span class="help-inline">Required</span>
						</div>
					</div>
			
					<div class="clearfix">
						<label for="introduced"><spring:message code="label.table.header.introduced"/> :</label>
						<div class="input">
							<input type="date" name="introduced" data-validation-optional="true" data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.introduced}" pattern="yyyy-MM-dd"/>>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued"><spring:message code="label.table.header.discontinued"/> :</label>
						<div class="input">
							<input type="date" name="discontinued" data-validation-optional="true" data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.discontinued}" pattern="yyyy-MM-dd"/>>
							<span class="help-inline">YYYY-MM-DD</span>
					</div>
					</div>
					<div class="clearfix">
						<label for="company"><spring:message code="label.table.header.company"/> :</label>
						<div class="input">
							<select name="company">
								<option value="${computer.company.id }">${computer.company.name }</option>
								<c:forEach var="comp" items="${options}">
									<option value="${comp.id }">${comp.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</fieldset>
				<div class="actions">
					<button type="submit" class="btn primary"><spring:message code="label.buttonedit"/></button>
					<a href="Display" class="btn"><spring:message code="label.buttoncancel"/></a>
				</div>
			</form>
		
</section>
<script>
	$.validate();
</script>	
<jsp:include page="../include/footer.jsp" />