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
			<input type="search" id="search" name="search" value="${pageData.search }">
			<button type="submit" class="btn btn-primary"><spring:message code="label.buttonsearch"/></button>
		</form>
	</div>

	<c:if test="${not empty pageData.pageNumber && pageData.recordsOnThisPage>0}">
		<at:Pagination orderDirection="${pageData.orderDirection}"
			elementsByPage="${pageData.recordsOnThisPage}"
			search="${pageData.search }" page="${pageData.pageNumber }"
			orderField="${pageData.resultsOrderedBy}"
			numberOfPages="${pageData.numberOfPages}"></at:Pagination>
	</c:if>
	
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-sm-1">
						<c:choose>
							<c:when test="${pageData.resultsOrderedBy == 'computer.name' }">
									<c:if test="${pageData.orderDirection == 'DESC' }">
										<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.name" display="${pageData.recordsOnThisPage}" ><spring:message code="label.table.header.computer"/></at:Link>
									</c:if>
									<c:if test="${pageData.orderDirection == 'ASC' }">
										<at:Link order="DESC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.name" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.computer"/></at:Link>
									</c:if>
							</c:when>
							<c:otherwise>
								<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
										field="computer.name" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.computer"/></at:Link>
							</c:otherwise>
						</c:choose>
					</th>
					<th class="col-sm-1">
						<c:choose>
							<c:when test="${pageData.resultsOrderedBy == 'computer.introduced' }">
									<c:if test="${pageData.orderDirection == 'DESC' }">
										<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.introduced" display="${pageData.recordsOnThisPage}" ><spring:message code="label.table.header.introduced"/></at:Link>
									</c:if>
									<c:if test="${pageData.orderDirection == 'ASC' }">
										<at:Link order="DESC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.introduced" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.introduced"/></at:Link>
									</c:if>
							</c:when>
							<c:otherwise>
								<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
										field="computer.introduced" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.introduced"/></at:Link>
							</c:otherwise>
						</c:choose>
					</th>
					<th class="col-sm-1">
						<c:choose>
							<c:when test="${pageData.resultsOrderedBy == 'computer.discontinued' }">
									<c:if test="${pageData.orderDirection == 'DESC' }">
										<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.discontinued" display="${pageData.recordsOnThisPage}" ><spring:message code="label.table.header.discontinued"/></at:Link>
									</c:if>
									<c:if test="${pageData.orderDirection == 'ASC' }">
										<at:Link order="DESC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.discontinued" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.discontinued"/></at:Link>
									</c:if>
							</c:when>
							<c:otherwise>
								<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
										field="computer.discontinued" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.discontinued"/></at:Link>
							</c:otherwise>
						</c:choose>
					</th>
					<th class="col-sm-1">
						<c:choose>
							<c:when test="${pageData.resultsOrderedBy == 'computer.company.name' }">
									<c:if test="${pageData.orderDirection == 'DESC' }">
										<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.company.name" display="${pageData.recordsOnThisPage}" ><spring:message code="label.table.header.company"/></at:Link>
									</c:if>
									<c:if test="${pageData.orderDirection == 'ASC' }">
										<at:Link order="DESC" search="${pageData.search}" page="${pageData.pageNumber}"
											field="computer.company.name" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.company"/></at:Link>
									</c:if>
							</c:when>
							<c:otherwise>
								<at:Link order="ASC" search="${pageData.search}" page="${pageData.pageNumber}"
										field="computer.company.name" display="${pageData.recordsOnThisPage}"><spring:message code="label.table.header.company"/></at:Link>
							</c:otherwise>
						</c:choose>
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
						<td><joda:format value="${comp.introduced}" style="M-"/></td>
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
