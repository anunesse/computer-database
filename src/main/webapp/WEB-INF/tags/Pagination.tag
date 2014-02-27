<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="elementsByPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="numberOfPages" required="true" type="java.lang.Integer"%>
<%@ attribute name="search" required="true" type="java.lang.String"%>
<%@ attribute name="orderField" required="true" type="java.lang.String"%>
<%@ attribute name="orderDirection" required="true" type="java.lang.String"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<ul class="pagination">
<c:if test="${ page == 1 }">
	<li class="disabled">
		<a href="Display?page=1&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}"><spring:message code="pagination.first"/></a>
	</li>
	<li class="disabled">
		<a>&laquo;</a>
	</li>
</c:if>
<c:if test="${ page > 1 }">
	<li>
		<a href="Display?page=1&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}"><spring:message code="pagination.first"/></a>
	</li>
	<li class="prev">
		<a href="Display?page=${page-1}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}">&laquo;</a>
	</li>
</c:if>

<c:forEach var="entry" begin="${(page - 5 < 1) ? 1 : page - 5}" end="${(page + 5 > numberOfPages) ? numberOfPages : page + 5}">
	<c:if test="${entry == page}">
		<li class="active">
			<a href="Display?page=${entry}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}">${entry}</a>
		</li>
	</c:if>
	
	<c:if test="${entry != page}">
		<li class="current">
			<a href="Display?page=${entry}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}">${entry}</a>
		</li>
	</c:if>
</c:forEach>

<c:if test="${ page == numberOfPages }">
	<li class="disabled">
		<a>&raquo;</a>
	</li>
	<li class="disabled">
		<a href="Display?page=${numberOfPages}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}"><spring:message code="pagination.last"/></a>
	</li>
</c:if>
<c:if test="${ page != numberOfPages }">
	<li class="next">
		<a href="Display?page=${page+1}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}">&raquo;</a>
	</li>
	<li>
		<a href="Display?page=${numberOfPages}&search=${search}&display=${elementsByPage}&orderField=${orderField}&order=${orderDirection}"><spring:message code="pagination.last"/></a>
	</li>
</c:if>

</ul>