<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="Page" uri="/WEB-INF/tld/LinkDescriptor.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<section id="main">
	<h1 id="homeTitle">${pageData.totalNumberOfRecords } Computers found</h1>
	
	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	<!-- pageData
	results
	resultsOrderedBy
	orderDirection
	pageNumber
	pageSize
	recordsOnThisPage
	totalNumberOfRecords
	numberOfPages
	-->
	
	<div id="actions">
		<form action="SelectDataServlet" method="GET">
			<input type="hidden" name="pageNumber" value="${pageData.pageNumber}">
			<input type="hidden" name="resultsOrderedBy" value="${pageData.resultsOrderedBy }">
			<input type="search" id="dispbox" name="recordsOnThisPage" value="${pageData.recordsOnThisPage }" placeholder="20">
			<input type="submit" id="dispsubmit" value="Displayed by page" class="btn primary">
		<a class="btn success" id="add" href="SelectComputerServlet">Add Computer</a>
	</div>
	
	<h:Links numberOfPages="10" orderDirection="ASC" pageNumber="1" pageSize="10" recordsOnThisPage="20" resultsOrderedBy="name" totalNumberOfRecords="574"></h:Links>
	
	
	<c:if test="${not empty pageData.pageNumber}">
	<Page:LinkDescriptor maxLinks="10" currPage="${pageData.pageNumber}" totalPages="10" uri="3" />
		<input type="hidden" name="page" value="${pageData.pageNumber}">
	</c:if>
	
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th>
						<h:Link pageNumber="${pageData.pageNumber}" resultsOrderedBy="computer" value="Computer Name"></h:Link>
						<input type="search" id="searchbox" name="search"
							value="${search }" placeholder="Search name">
						<input type="submit" id="searchsubmit"
							value="Filter by name"
							class="btn primary">
					</th>
					<th><h:Link pageNumber="${pageData.pageNumber}" resultsOrderedBy="introduced" value="Introduced Date"></h:Link></th>
					<th><h:Link pageNumber="${pageData.pageNumber}" resultsOrderedBy="discontinued" value="Discontinued Date"></h:Link></th>
					<th>
						<h:Link pageNumber="${pageData.pageNumber}" resultsOrderedBy="company" value="Company Name"></h:Link>
						<input type="search" id="dispbox" name="company"
							value="${company }" placeholder="Search Company Name">
						<input type="submit" id="compsubmit"
							value="Filter by company"
							class="btn primary">
					</th>
				</tr>
			</thead>
				
		</form>
			<tbody>

			<c:choose>
				<c:when test="${answer==0}">
					<p>No computers were found.</p>
				</c:when>
				<c:otherwise>
				<c:forEach var="comp" items="${pageData.results}">
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
