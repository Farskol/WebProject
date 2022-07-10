<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/clients_admin.jsp"/>
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
    <fmt:message key="login.login" var="login"/>
    <fmt:message key="registration.name" var="firstName"/>
    <fmt:message key="registration.phone_number" var="phoneNumber"/>
    <fmt:message key="clientAdmin.clientRole" var="clientRole"/>
    <fmt:message key="clientAdmin.clientStatus" var="clientStatus"/>
    <fmt:message key="clientAdmin.deleteClient" var="deleteClient"/>
    <fmt:message key="ordersAdmin.extra" var="extra"/>
    <fmt:message key="discountAdmin.discountPage" var="discountPage"/>
    <fmt:message key="stuffingAdmin.stuffingPage" var="stuffingPage"/>
</fmt:bundle>
<html>
<head>
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
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=sign_out'">${sign_out}</button>
        </div>
    </div>
</nav>

<div class="row align-items-center">
    <table class="table table-striped d-flex">
        <tr class="table-success">
            <th class="table-success">${login}</th>
            <th class="table-success">${firstName}</th>
            <th class="table-success">${phoneNumber}</th>
            <th class="table-success">${clientRole}</th>
            <th class="table-success">${clientStatus}</th>
            <th class="table-success"></th>
            <th class="table-success"></th>
        </tr>
        <c:forEach var="client" items="${client_list}">
            <tr class="table-success">
                <td class="table-success">${client.login}</td>
                <td class="table-success">${client.firstName}</td>
                <td class="table-success">${client.phoneNumber}</td>
                <td class="table-success">${client.role}</td>
                <td class="table-success">${client.status}</td>
                <td class="table-success">
                    <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="delete_client"/>
                        <input type="hidden" name="client_id" value="${client.clientId}"/>
                        <button type="submit" class="btn btn-success">${deleteClient}</button>
                    </form>
                </td>
                <td class="table-success">
                    <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="client_extra"/>
                        <input type="hidden" name="client_id" value="${client.clientId}"/>
                        <button type="submit" class="btn btn-success">${extra}</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
