<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.excilys.formation.projet.DAO.*"%>
<%@ page import="com.excilys.formation.projet.OM.*"%>
<section id="main">

	<c:choose>
		<c:when test="${computer=='1'}">
			<h1>Add Computer</h1>
			<form class="myForm" action="SelectComputerServlet" method="POST">
				<input type="hidden" name="mode" value="add"/>
				<fieldset>
					<div class="clearfix">
						<label for="name">Computer name:</label>
						<div class="input">
							<input type="text" name="name" data-validation="length" data-validation-length="1-255"/>
							<span class="help-inline">Required</span>
						</div>
					</div>
			
					<div class="clearfix">
						<label for="introduced">Introduced date:</label>
						<div class="input">
							<input type="date" name="introduced"  data-validation="date" data-validation-format="dd/mm/yyyy"/>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued">Discontinued date:</label>
						<div class="input">
							<input type="date" name="discontinued" data-validation="date" data-validation-format="dd/mm/yyyy"/>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="company">Company Name:</label>
						<div class="input">
							<select name="company">
								<c:forEach var="comp" items="${options}">
									<option value="${comp.id }">${comp.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</fieldset>
				<div class="actions">
					<input type="submit" value="Add" class="btn primary">
					or <a href="SelectDataServlet" class="btn">Cancel</a>
				</div>
			</form>
		</c:when>
		
		<c:otherwise>
			<h1>Edit Computer</h1>
			
			<form action="SelectComputerServlet" method="POST">
				<input type="hidden" name="mode" value="del"/>
				<input type="hidden" name="computer" value="${computer.id }"/>
				<input type="submit" value="Delete" class="btn btn-danger">
			</form>
			
			<form class="myForm2" action="SelectComputerServlet" method="POST">
				<input type="hidden" name="mode" value="edit"/>
				<input type="text" name="id" value="${computer.id }" readonly/>
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
							<input type="date" name="introduced"  data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.introduced}" pattern="yyyy-MM-dd"/>>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued">Discontinued date:</label>
						<div class="input">
							<input type="date" name="discontinued" data-validation="date" data-validation-format="yyyy-mm-dd" value=<fmt:formatDate value="${computer.discontinued}" pattern="yyyy-MM-dd"/>>
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
					<a href="SelectDataServlet" class="btn">Cancel</a>
					
				</div>
			</form>
		</c:otherwise>
	</c:choose>	
</section>
<script>
	$.validate();
</script>	
<jsp:include page="../include/footer.jsp" />