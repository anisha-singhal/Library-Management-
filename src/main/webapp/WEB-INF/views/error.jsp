<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="fragments/header.jsp"/>

<h2>Something went wrong</h2>
<div class="error">
    <c:out value="${errorMessage}"/>
</div>
<a class="btn" href="<c:url value='/'/>">Back to Home</a>

<jsp:include page="fragments/footer.jsp"/>
