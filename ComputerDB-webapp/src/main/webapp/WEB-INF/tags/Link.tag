<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="java.lang.String"%>
<%@ attribute name="search" required="true" type="java.lang.String"%>
<%@ attribute name="display" required="true" type="java.lang.Integer"%>
<%@ attribute name="field" required="true" type="java.lang.String"%>
<%@ attribute name="order" required="true" type="java.lang.String"%>

<a href="Display?page=${page}
	&search=${search}
	&display=${display}
	&orderField=${field }
	&order=${order}" class="btn">
	<jsp:doBody/>
</a>