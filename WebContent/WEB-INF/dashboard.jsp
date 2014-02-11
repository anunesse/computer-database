<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.excilys.formation.projet.DAO.*"%>
<%@ page import="com.excilys.formation.projet.OM.*"%>

<section id="main">
	<h1 id="homeTitle">456 Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		
		<form action="SelectDataServlet" method="GET">
			<input type="search" id="dispbox" name="display"
				value="" placeholder="10">
			<input type="submit" id="dispsubmit"
				value="Display"
				class="btn primary">
		</form>
		
		<a class="btn success" id="add" href="SelectComputerServlet">Add Computer</a>
	</div>
	
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<th>Discontinued Date</th>
					<th>Company</th>
				</tr>
			</thead>
			<tbody>
			
			<c:choose>
				<c:when test="${computers=='1'}">
					<p>No computers were founded</p>
				</c:when>
				<c:otherwise>
				<c:forEach var="comp" items="${computers}">
					<tr>
						<td><a href="SelectComputerServlet?computer=${comp.id}">${comp.name }</a></td>
						<td>${comp.introduced }</td>
						<td>${comp.discontinued }</td>
						<td>${comp.company.name }</td>
					</tr>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
</section>


<jsp:include page="../include/footer.jsp" />
