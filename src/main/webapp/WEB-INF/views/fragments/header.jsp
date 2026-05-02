<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>${pageTitle != null ? pageTitle : 'Library Management'}</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<header>
    <h1>Library Management System</h1>
</header>
<nav>
    <a href="<c:url value='/'/>">Home</a>
    <a href="<c:url value='/authors'/>">Authors</a>
    <a href="<c:url value='/books'/>">Books</a>
</nav>
<main>
