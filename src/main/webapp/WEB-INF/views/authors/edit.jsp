<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../fragments/header.jsp"/>

<h2>Edit Author</h2>

<c:if test="${not empty errorMessage}">
    <div class="error"><c:out value="${errorMessage}"/></div>
</c:if>

<form:form modelAttribute="author" method="post" action="${pageContext.request.contextPath}/authors/${author.id}">
    <div>
        <label for="name">Name</label>
        <form:input path="name" id="name"/>
        <form:errors path="name" cssClass="field-error" element="div"/>
    </div>
    <div>
        <label for="email">Email</label>
        <form:input path="email" id="email"/>
        <form:errors path="email" cssClass="field-error" element="div"/>
    </div>
    <div>
        <label for="nationality">Nationality</label>
        <form:input path="nationality" id="nationality"/>
    </div>
    <div class="actions">
        <button class="btn" type="submit">Update Author</button>
        <a class="btn btn-secondary" href="<c:url value='/authors'/>">Cancel</a>
    </div>
</form:form>

<jsp:include page="../fragments/footer.jsp"/>
