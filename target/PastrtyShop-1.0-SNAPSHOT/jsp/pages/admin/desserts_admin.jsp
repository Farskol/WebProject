<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/desserts_admin.jsp"/>
<c:choose>
    <c:when test="${not empty pageLanguage}"> <fmt:setLocale value="${pageLanguage}" scope="session"/></c:when>
    <c:when test="${empty pageLanguage}"> <fmt:setLocale value="${pageLanguage = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:bundle basename="context.language">
    <fmt:message key="main.desserts" var="desserts"/>
    <fmt:message key="main.language" var="languge"/>
    <fmt:message key="login.sign_out" var="sign_out"/>
    <fmt:message key="mainAdmin.Clients" var="clientsPage"/>
    <fmt:message key="mainAdmin.orders" var="ordersPage"/>
    <fmt:message key="dessertAdmin.edit" var="edit"/>
    <fmt:message key="dessertAdmin.create" var="createDessert"/>
    <fmt:message key="discountAdmin.discountPage" var="discountPage"/>
    <fmt:message key="dessertAdmin.delete" var="deleteDessert"/>
    <fmt:message key="stuffingAdmin.stuffingPage" var="stuffingPage"/>
</fmt:bundle>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-success">
    <div class="container">
        <div class="container-fluid">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active text-white" aria-current="page" href="${pageContext.request.contextPath}/jsp/pages/admin/desserts_admin.jsp">${desserts}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/admin/clients_admin.jsp">${clientsPage}</a>
                </li>
                <li class="nav-item">
                <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/admin/orders_admin.jsp">${ordersPage}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/admin/discount_admin.jsp">${discountPage}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/admin/stuffing_admin.jsp">${stuffingPage}</a>
                </li>
            </ul>
        </div>
        <div class="d-flex">
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=change_language'">${languge}</button>
        </div>
        <div class="d-flex">
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/jsp/pages/admin/dessert_create.jsp'">${createDessert}</button>
        </div>
        <div class="d-flex">
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=sign_out'">${sign_out}</button>
        </div>
    </div>
</nav>

<div class="container d-flex flex-wrap justify-content-center">
    <c:forEach var="des" items="${dessert_list}">
        <div class="card m-3 col-sm-4" style="width: 16rem;">
            <img class="card-img-top" src="cake.jpg" alt="Шоколадный чиз">
            <div class="card-body">
                <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="edit_dessert">
                    <input type="hidden" name="id_of_dessert" value="${des.dessertId}">
                    <h5 class="card-title">${des.name}</h5>
                    <p class="card-text">${des.cost}</p>
                    <p class="card-text">${des.description}</p>
                    <p class="card-text">${des.type}</p>
                    <button type="submit" class="btn btn-success">${edit}</button>
                </form>

                <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="delete_dessert">
                    <input type="hidden" name="id_of_dessert" value="${des.dessertId}">
                    <button type="submit" class="btn btn-success">${deleteDessert}</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
