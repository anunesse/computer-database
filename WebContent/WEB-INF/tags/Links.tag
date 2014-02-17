<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ attribute name="resultsOrderedBy" required="true" type="java.lang.String"%>
<%@ attribute name="orderDirection" required="true" type="java.lang.String"%>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageSize" required="true" type="java.lang.Integer"%>
<%@ attribute name="recordsOnThisPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="totalNumberOfRecords" required="true" type="java.lang.Integer"%>
<%@ attribute name="numberOfPages" required="true" type="java.lang.Integer"%>
 
<ul class="pagination">
 
<c:if test="${ pageNumber == 1 }">
	<li class="prev disabled"><h:Link pageNumber="1" resultsOrderedBy="${resultsOrderedBy }" value="&larr;"></h:Link></li>
</c:if>
<c:if test="${ pageNumber > 1 }">
	<li class="prev ">&larr;</li>
</c:if>


<c:forEach var="entry" begin="${(page - 5 < 1) ? 1 :  page - 5}" end="${(page + 5 > totalPages) ? totalPages :  page + 5}">
	
	<li class="current">${page }</li>
</c:forEach>
 
<c:if test="${ pageNumber == pageSize }">
	<li class="next disabled"><a href="">&rarr;</a></li>
</c:if>
<c:if test="${ pageNumber != pageSize }">
	<li class="next ">&rarr;</li>
</c:if>
 
</ul>