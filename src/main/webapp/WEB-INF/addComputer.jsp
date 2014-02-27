<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="at" %>

<section id="main">

	<div style="margin-top:90px"></div>

	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>

	<h1><spring:message code="label.add"/></h1>

	<form:form role="form" class="myForm" commandName="computer" action="AddComputer" method="POST">

		<fieldset>
		<div class="form-group">
			<label for="name"><spring:message code="label.table.header.computer"/> :</label>
			<div class="input">
				<form:input type="text" path="name" data-validation="length" data-validation-length="1-255" value="${computer.name }"/>
				<form:errors path="name" cssClass="error"></form:errors>
				<span class="help-inline">Required</span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="introduced"><spring:message code="label.table.header.introduced"/> :</label>
			<div class="input">
				<form:input class="datepicker" type="date" path="introduced"
					data-validation-optional="true"
					data-validation="date" data-validation-format="yyyy-mm-dd"/>
				<form:errors path="introduced" cssClass="error"></form:errors>
				<span class="help-inline">YYYY-MM-DD</span>
			</div>
		</div>
		<div class="form-group">
			<label for="discontinued"><spring:message code="label.table.header.discontinued"/> :</label>
			<div class="input">
				<form:input class="datepicker" type="date" path="discontinued"
					data-validation-optional="true"
					data-validation="date" data-validation-format="yyyy-mm-dd"/>
				<form:errors path="discontinued" cssClass="error"></form:errors>
				<span class="help-inline">YYYY-MM-DD</span>
		</div>
		</div>
			<div class="form-group">
				<label for="company"><spring:message code="label.table.header.company"/> :</label>
					<div class="input">
					<form:select path="company">
						<option value="">Unknown</option>
						<c:forEach var="comp" items="${options}">
							<option value="${comp.id }">${comp.name }</option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			</fieldset>
			<div class="form-group">
				<button type="submit" class="btn primary"><spring:message code="label.buttonedit"/></button>
				<a href="Display" class="btn"><spring:message code="label.buttoncancel"/></a>
			</div>
	</form:form>
</section>
<script>
	$.validate();
</script>	
<jsp:include page="../include/footer.jsp" />