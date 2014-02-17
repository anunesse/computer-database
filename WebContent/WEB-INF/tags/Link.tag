<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="resultsOrderedBy" required="true" type="java.lang.String"%>
<%@ attribute name="value" required="true" type="java.lang.String"%>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer"%>

<c:if test="${pageData.orderDirection=='ASC'}">
	<a class="btn" href="SelectDataServlet?pageNumber=${pageNumber }&orderDir=DESC&orderRes=${resultsOrderedBy}">${value }</a>
</c:if>

<c:if test="${pageData.orderDirection=='DESC'}">
	<a class="btn" href="SelectDataServlet?pageNumber=${pageNumber }&orderDir=ASC&orderRes=${resultsOrderedBy}">${value }</a>
</c:if>

<c:if test="${empty pageData.orderDirection}">
	<a class="btn" href="SelectDataServlet?pageNumber=${pageNumber }&orderDir=DESC&orderRes=${resultsOrderedBy}">${value }</a>
</c:if>