<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.excilys.formation.projet.DAO.*"%>
<%@ page import="com.excilys.formation.projet.OM.*"%>
<%@ page import="com.excilys.formation.projet.services.*"%>

<section id="main">
	<h1 id="homeTitle"><%=CountDataService.CountComputers() %> Computers found</h1>
	
	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	
	<div id="actions">
		<form action="SelectDataServlet" method="GET">
		<input type="search" id="dispbox" name="display"
					value="" placeholder="10">
				<input type="submit" id="dispsubmit"
					value="Display"
					class="btn primary">
		<a class="btn success" id="add" href="SelectComputerServlet">Add Computer</a>
	</div>
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th><a class="btn">Computer Name
						<input type="search" id="searchbox" name="search"
							value="" placeholder="Search name">
						<input type="submit" id="searchsubmit"
							value="Filter by name"
							class="btn primary">
					</a></th>
					<th><a class="btn">Introduced Date</a></th>
					<th><a class="btn">Discontinued Date</a></th>
					<th><a class="btn">Company
						<input type="search" id="dispbox" name="company"
							value="" placeholder="Search Company Name">
						<input type="submit" id="compsubmit"
							value="Filter by company"
							class="btn primary">
					</a></th>
				</tr>
			</thead>
				
		</form>
			<tbody>
			
			<c:choose>
				<c:when test="${computers=='1'}">
					<p>No computers were founded</p>
				</c:when>
				<c:otherwise>
				<c:forEach var="comp" items="${computers}">
					<tr>
						<td><a href="SelectComputerServlet?computer=${comp.id}">${comp.name }</a></td>
						<td><fmt:formatDate value="${comp.introduced}" pattern="yyyy/MM/dd"/></td>
						<td><fmt:formatDate value="${comp.discontinued}" pattern="yyyy/MM/dd"/></td>
						<td>${comp.company.name }</td>
					</tr>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
</section>


<jsp:include page="../include/footer.jsp" />
