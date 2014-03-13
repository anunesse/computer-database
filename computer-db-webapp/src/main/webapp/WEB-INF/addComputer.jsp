<jsp:include page="../include/header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="at" %>

<section id="main" class="container theme-showcase clearfix">

	<div style="margin-top:90px"></div>

	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>

	<h1><spring:message code="label.add"/></h1>

	<form:form role="form" class="myForm" commandName="computerDTO" action="AddComputer" method="POST">
	<fieldset>
		<div class="form-group">
		<spring:bind path="name">
			<form:label path="name"><spring:message code="label.table.header.computer"/> :</form:label>
			<div class="input">
				<form:input type="text" path="name" data-validation="length" data-validation-length="1-255" value="${computer.name }"/>
				<form:errors path="name" cssClass="error"></form:errors>
				<span class="help-inline"><spring:message code="label.form.name"/></span>
			</div>
		</spring:bind>
		</div>
		
		<div class="form-group">
		<spring:bind path="introduced">
			<label for="introduced"><spring:message code="label.table.header.introduced"/> :</label>
			<div class="input">
				<form:input class="datepicker" type="text" path="introduced"/>
				<form:errors path="introduced" cssClass="error"></form:errors>
				<span class="help-inline"><spring:message code="label.form.date"/></span>
			</div>
		</spring:bind>
		</div>
		
		<div class="form-group">
		<spring:bind path="discontinued">
			<label for="discontinued"><spring:message code="label.table.header.discontinued"/> :</label>
			<div class="input">
				<form:input class="datepicker" type="date" path="discontinued"/>
				<form:errors path="discontinued" cssClass="error"></form:errors>
				<span class="help-inline"><spring:message code="label.form.date"/></span>
			</div>
		</spring:bind>
		</div>
		
		<div class="form-group">
			<label for="company"><spring:message code="label.table.header.company"/> :</label>
				<div class="input">
				<form:select path="company">
					<option value="0">Unknown</option>
					<c:forEach var="comp" items="${options}">
						<option value="${comp.id }">${comp.name }</option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		
	</fieldset>
	
	<div class="form-group">
		<button type="submit" class="btn primary"><spring:message code="label.buttonadd"/></button>
		<a href="Display" class="btn"><spring:message code="label.buttoncancel"/></a>
	</div>
			
	</form:form>
</section>
<script>
	$.validate();
</script>	
<jsp:include page="../include/footer.jsp" />