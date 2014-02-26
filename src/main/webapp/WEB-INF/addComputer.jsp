<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.excilys.formation.projet.dao.*"%>
<%@ page import="com.excilys.formation.projet.om.*"%>
<section id="main">

	<div style="margin-top:90px"><h1 id="homeTitle">Computer</h1></div>

	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	<c:choose>
		<c:when test="${answer==0}">
			<h1>Add Computer</h1>
			<form class="myForm" action="AddComputer" method="POST">
				<input type="hidden" name="mode" value="add"/>
		</c:when>
		
		<c:otherwise>
			<h1>Edit Computer</h1>
			
			<form action="DelComputer" method="POST">
				<input type="hidden" name="computer" value="${computer.id }"/>
				<input type="submit" value="Delete" onclick="return confirm('You are about to delete this computer, are you sure?')" class="btn btn-danger">
			</form>
			
			<form class="myForm" action="EditComputer" method="POST">
				<input type="text" name="comp_id" value="${computer.id }" readonly/>
		</c:otherwise>
	</c:choose>	
				<fieldset>
					<div class="clearfix">
						<label for="name">Computer name:</label>
						<div class="input">
							<input type="text" name="name" data-validation="length" data-validation-length="1-255" value="${computer.name }"/>
							<span class="help-inline">Required</span>
						</div>
					</div>
			
					<div class="clearfix">
						<label for="introduced">Introduced date:</label>
						<div class="input">
							<input type="date" name="introduced" data-validation-optional="true" data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.introduced}" pattern="yyyy-MM-dd"/>>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued">Discontinued date:</label>
						<div class="input">
							<input type="date" name="discontinued" data-validation-optional="true" data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.discontinued}" pattern="yyyy-MM-dd"/>>
							<span class="help-inline">YYYY-MM-DD</span>
					</div>
					</div>
					<div class="clearfix">
						<label for="company">Company Name:</label>
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
					<input type="submit" value="Edit" class="btn primary">
					<a href="Display" class="btn">Cancel</a>
					
				</div>
			</form>
		
</section>
<script>
	$.validate();
</script>	
<jsp:include page="../include/footer.jsp" />