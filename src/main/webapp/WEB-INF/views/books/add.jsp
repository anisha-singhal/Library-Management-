<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../fragments/header.jsp"/>

<h2>Add Book</h2>

<c:if test="${not empty errorMessage}">
    <div class="error"><c:out value="${errorMessage}"/></div>
</c:if>

<form:form modelAttribute="book" method="post" action="${pageContext.request.contextPath}/books">
    <div>
        <label for="title">Title</label>
        <form:input path="title" id="title"/>
        <form:errors path="title" cssClass="field-error" element="div"/>
    </div>
    <div>
        <label for="isbn">ISBN</label>
        <form:input path="isbn" id="isbn"/>
        <form:errors path="isbn" cssClass="field-error" element="div"/>
    </div>
    <div>
        <label for="price">Price</label>
        <form:input path="price" id="price" type="number" step="0.01"/>
        <form:errors path="price" cssClass="field-error" element="div"/>
    </div>
    <div>
        <label for="publishedYear">Published Year</label>
        <form:input path="publishedYear" id="publishedYear" type="number"/>
    </div>
    <div>
        <label for="authorId">Author</label>
        <select id="authorId" name="authorId" required>
            <option value="">-- Select an author --</option>
            <c:forEach var="a" items="${authors}">
                <option value="${a.id}"><c:out value="${a.name}"/> (<c:out value="${a.email}"/>)</option>
            </c:forEach>
        </select>
    </div>
    <div class="actions">
        <button class="btn" type="submit">Save Book</button>
        <a class="btn btn-secondary" href="<c:url value='/books'/>">Cancel</a>
    </div>
</form:form>

<jsp:include page="../fragments/footer.jsp"/>
