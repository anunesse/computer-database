<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section id="main">
	<h1 id="homeTitle">Logs</h1>
	
	<table>
	<thead>
		<tr><th>Date</th><th>Type</th><th>Description</th></tr>	
	</thead>
	<tbody>
			<c:forEach var="log" items="${pageData.results}">
				<tr>
					<td><fmt:formatDate value="${log.operationDate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${log.operationType}</td>
					<td>${log.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</section>

<jsp:include page="../include/footer.jsp" />