<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../fragments/header.jsp"/>

<h2>Authors</h2>
<div class="actions">
    <a class="btn" href="<c:url value='/authors/new'/>">+ Add Author</a>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Nationality</th>
        <th>#Books</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${authors}">
        <tr>
            <td>${a.id}</td>
            <td><c:out value="${a.name}"/></td>
            <td><c:out value="${a.email}"/></td>
            <td><c:out value="${a.nationality}"/></td>
            <td><span class="badge">${a.books.size()}</span></td>
            <td>
                <a class="btn btn-small btn-secondary" href="<c:url value='/authors/${a.id}/edit'/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty authors}">
        <tr><td colspan="6">No authors found.</td></tr>
    </c:if>
    </tbody>
</table>

<jsp:include page="../fragments/footer.jsp"/>
