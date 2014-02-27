<jsp:include page="../include/header.jsp" />

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="at" %>

<section id="main">

	<div style="margin-top:90px"></div>

	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
		<spring:message code="label.edit"/>
		
		<form action="DelComputer" method="POST">
			<input type="hidden" name="computer" value="${computer.id }"/>
			<button type="submit" onclick="return confirm('You are about to delete this computer, are you sure?')" class="btn btn-danger">
				<spring:message code="label.buttondelete"/>
			</button>
		</form>
	
		<form:form role="form" class="myForm" commandName="computer" action="EditComputer" method="POST">

			<input type="text" name="comp_id" value="${computer.id }" readonly/>
			<fieldset>
			<div class="form-group">
				<label for="name"><spring:message code="label.table.header.computer"/> :</label>
				<div class="input">
					<form:input type="text" data-validation="length" data-validation-length="1-255" path="name"/>
					<span class="help-inline">Required</span>
				</div>
			</div>
			
			<div class="form-group">
				<label for="introduced"><spring:message code="label.table.header.introduced"/> :</label>
				<div class="input">
					<form:input type="text" path="introduced"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="discontinued"><spring:message code="label.table.header.discontinued"/> :</label>
				<div class="input">
					<form:input type="text" path="discontinued"/>
					<span class="help-inline">YYYY-MM-DD</span>
			</div>
			</div>
			<div class="form-group">
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