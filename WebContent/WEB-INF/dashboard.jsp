<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="Page" uri="/WEB-INF/tld/LinkDescriptor.tld"%>

<section id="main">
	<h1 id="homeTitle"> Computers found</h1>
	
	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	<c:url var="searchUri" value="/searchResults.html?s=la&page=##" />
	<!-- <paginator:display maxLinks="10" currPage="${page}" totalPages="${totalPages}" uri="${searchUri}" /> 
	<paginator:display maxLinks="10" currPage="4" totalPages="10"/>-->
	
	<div id="actions">
		<form action="SelectDataServlet" method="GET">
			<input type="search" id="dispbox" name="display" value="${display }" placeholder="20">
			<input type="submit" id="dispsubmit" value="Displayed by page" class="btn primary">
		<a class="btn success" id="add" href="SelectComputerServlet">Add Computer</a>
	</div>
	
	<c:if test="${not empty currentPage}">
	<Page:LinkDescriptor maxLinks="10" currPage="${currentPage}" totalPages="10" uri="3" />
		<input type="hidden" name="page" value="${currentPage}">
	</c:if>
	
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th><a class="btn">Computer Name
						<input type="search" id="searchbox" name="search"
							value="${search }" placeholder="Search name">
						<input type="submit" id="searchsubmit"
							value="Filter by name"
							class="btn primary">
					</a></th>
					<th><a class="btn">Introduced Date</a></th>
					<th><a class="btn">Discontinued Date</a></th>
					<th><a class="btn">Company
						<input type="search" id="dispbox" name="company"
							value="${company }" placeholder="Search Company Name">
						<input type="submit" id="compsubmit"
							value="Filter by company"
							class="btn primary">
					</a></th>
				</tr>
			</thead>
				
		</form>
			<tbody>

			<c:choose>
				<c:when test="${answer==0}">
					<p>No computers were found.</p>
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
