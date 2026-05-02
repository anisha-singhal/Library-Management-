<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../fragments/header.jsp"/>

<h2>Books (Joined with Author)</h2>
<p style="color:#718096;font-size:13px;">
    This list is fetched using a custom JPQL inner-join query in the repository layer.
</p>

<div class="actions">
    <a class="btn" href="<c:url value='/books/new'/>">+ Add Book</a>
</div>

<table>
    <thead>
    <tr>
        <th>Book ID</th>
        <th>Title</th>
        <th>ISBN</th>
        <th>Year</th>
        <th>Price</th>
        <th>Author</th>
        <th>Nationality</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="r" items="${rows}">
        <tr>
            <td>${r.bookId}</td>
            <td><c:out value="${r.title}"/></td>
            <td><c:out value="${r.isbn}"/></td>
            <td>${r.publishedYear}</td>
            <td><fmt:formatNumber value="${r.price}" type="currency" currencySymbol="₹"/></td>
            <td><c:out value="${r.authorName}"/></td>
            <td><span class="badge"><c:out value="${r.authorNationality}"/></span></td>
            <td>
                <a class="btn btn-small btn-secondary" href="<c:url value='/books/${r.bookId}/edit'/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty rows}">
        <tr><td colspan="8">No books found.</td></tr>
    </c:if>
    </tbody>
</table>

<jsp:include page="../fragments/footer.jsp"/>
