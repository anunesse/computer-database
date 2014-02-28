<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<section id="main">
<div style="margin-top:60px" class="row"></div>
	<h1 id="homeTitle">Logs</h1>
	
	<table class="table table-striped">
	<thead>
		<tr><th class="col-sm-1">Date</th><th class="col-sm-1">Type</th><th class="col-sm-1">Description</th></tr>	
	</thead>
	<tbody>
			<c:forEach var="log" items="${pageData.results}">
				<tr>
					<td>
						<joda:format value="${log.operationDate}" style="MM"/>
					</td>
					<td>${log.operationType}</td>
					<td>${log.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</section>

<jsp:include page="../include/footer.jsp" />