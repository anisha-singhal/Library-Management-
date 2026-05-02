<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="fragments/header.jsp"/>

<div class="hero">
    <h2>Welcome</h2>
    <p>This Spring Boot + JSP application demonstrates managing two related entities &mdash;
        <strong>Authors</strong> and <strong>Books</strong> &mdash; with a one-to-many relationship.
        You can create, list, and update records using JPA, Spring MVC, and JSP views.</p>

    <div class="cards">
        <div class="card">
            <h3>Authors</h3>
            <p>View, add, and edit author profiles.</p>
            <a class="btn btn-small" href="<c:url value='/authors'/>">Manage Authors</a>
        </div>
        <div class="card">
            <h3>Books</h3>
            <p>List books joined with their author, add new books, and update existing ones.</p>
            <a class="btn btn-small" href="<c:url value='/books'/>">Manage Books</a>
        </div>
        <div class="card">
            <h3>H2 Console</h3>
            <p>Inspect the in-memory database directly.</p>
            <a class="btn btn-small btn-secondary" href="<c:url value='/h2-console'/>">Open Console</a>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
