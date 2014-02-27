<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="at" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">
<div style="margin-top:60px" class="row">

	<div class="col-md-8">
		<h1 id="homeTitle">${pageData.totalNumberOfRecords} <spring:message code="label.datafound"/></h1>
	</div>
	<div class="col-md-2">
		<a href="?lang=fr">fr</a>
		<a href="?lang=en">en</a>
		<a href="?lang=de">de</a>
	</div>
	<div class="col-md-2">
		<a class="btn btn-success" id="add" href="GetComputer"><spring:message code="label.buttonaddcomputer"/></a>
	</div>
</div>
	
	<c:if test="${not empty error}">
	   <jsp:include page="../include/info.jsp" />  
	</c:if>
	
	<div id="actions">
		<form action="Display" method="GET">
			<label for="display"><spring:message code="label.display"/></label>
		<c:choose>
			<c:when test="${pageData.recordsOnThisPage > 0}">
				<input type="search" id="dispbox" name="display" value="${pageData.recordsOnThisPage}" placeholder="10, 20, 50, ...">
			</c:when>
			<c:otherwise>
				<input type="search" id="dispbox" name="display" value="" placeholder="10, 20, 50, ...">
			</c:otherwise>
		</c:choose>
			<label for="search"><spring:message code="label.search"/></label>
			<input type="search" id="search" name="search" value="${search }">
			<button type="submit" class="btn btn-primary"><spring:message code="label.buttonsearch"/></button>
		</form>
	</div>

	<c:if test="${not empty pageData.pageNumber && pageData.recordsOnThisPage>0}">
		<at:Pagination orderDirection="${pageData.orderDirection}" elementsByPage="${pageData.recordsOnThisPage}" search="${search }" page="${pageData.pageNumber }" orderField="${pageData.resultsOrderedBy}" numberOfPages="${pageData.numberOfPages}"></at:Pagination>
	</c:if>
	
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-sm-1">
						<a href="Display?page=${pageData.pageNumber}
							&search=${search}&display=${pageData.recordsOnThisPage}
							&orderField=computer
							&order=ASC" class="btn"><spring:message code="label.table.header.computer"/></a>
					</th>
					<th class="col-sm-1">
						<a href="Display?page=${pageData.pageNumber}
							&search=${search}&display=${pageData.recordsOnThisPage}
							&orderField=introduced
							&order=ASC" class="btn"><i class="icon-arrow-down"></i><spring:message code="label.table.header.introduced"/></a>
					</th>
					<th class="col-sm-1">
						<a href="Display?page=${pageData.pageNumber}
							&search=${search}&display=${pageData.recordsOnThisPage}
							&orderField=discontinued
							&order=ASC" class="btn"><i class="icon-arrow-down"></i><spring:message code="label.table.header.discontinued"/></a>
					</th>
					<th class="col-sm-1">
						<a href="Display?page=${pageData.pageNumber}
							&search=${search}&display=${pageData.recordsOnThisPage}
							&orderField=company
							&order=ASC" class="btn"><i class="icon-arrow-down"></i><spring:message code="label.table.header.company"/></a>
					</th>
				</tr>
			</thead>
			<tbody>

			<c:choose>
				<c:when test="${pageData.totalNumberOfRecords==0}">
					<p><spring:message code="error.empty"/></p>
				</c:when>
				<c:otherwise>
				<c:forEach var="comp" items="${pageData.results}">
					<tr>
						<td><a href="GetComputer?computer=${comp.id}">${comp.name }</a></td>
						<td>
							<joda:format value="${comp.introduced}" style="S-"/>
						</td>
						<td><joda:format value="${comp.discontinued}" style="M-"/></td>
						<td>${comp.company.name }</td>
					</tr>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
</section>


<jsp:include page="../include/footer.jsp" />
