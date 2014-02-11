<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.excilys.formation.projet.DAO.*"%>
<%@ page import="com.excilys.formation.projet.OM.*"%>
<section id="main">

	
	<c:choose>
		<c:when test="${computer=='1'}">
			<h1>Add Computer</h1>
			<form action="SelectComputerServlet" method="POST">
				<input type="hidden" name="mode" value="add"/>
				<fieldset>
					<div class="clearfix">
						<label for="name">Computer name:</label>
						<div class="input">
							<input type="text" name="name"/>
							<span class="help-inline">Required</span>
						</div>
					</div>
			
					<div class="clearfix">
						<label for="introduced">Introduced date:</label>
						<div class="input">
							<input type="date" name="introduced"/>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued">Discontinued date:</label>
						<div class="input">
							<input type="date" name="discontinued"/>
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
				<input type="hidden" name="mode" value="edit"/>
				<input type="text" name="id" value="${computer.id }" readonly/>
				<fieldset>
					<div class="clearfix">
						<label for="name">Computer name:</label>
						<div class="input">
							<input type="text" name="name" value="${computer.name }"/>
							<span class="help-inline">Required</span>
						</div>
					</div>
			
					<div class="clearfix">
						<label for="introduced">Introduced date:</label>
						<div class="input">
							<input type="date" name="introduced" value="${computer.introduced }"/>
							<span class="help-inline">YYYY-MM-DD</span>
						</div>
					</div>
					<div class="clearfix">
						<label for="discontinued">Discontinued date:</label>
						<div class="input">
							<input type="date" name="discontinued" value="${computer.discontinued }"/>
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
					or <a href="SelectDataServlet" class="btn">Cancel</a>
				</div>
			</form>
		</c:otherwise>
	</c:choose>
</section>

<jsp:include page="../include/footer.jsp" />