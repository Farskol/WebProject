<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/discount_create.jsp"/>
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
    <fmt:message key="discountAdmin.edit" var="editDiscount"/>
    <fmt:message key="discountAdmin.discountPage" var="discountPage"/>
    <fmt:message key="discountAdmin.createDiscountPage" var="createDiscount"/>
    <fmt:message key="discountEdit.nameOfDiscount" var="nameOfDiscount"/>
    <fmt:message key="discountAdmin.discountValue" var="discountValue"/>
    <fmt:message key="discountEdit.invalidNameOfDiscount" var="invName"/>
    <fmt:message key="discountEdit.invalidValueOfDiscount" var="invValue"/>
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
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=sign_out'">${sign_out}</button>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <h4 class="col-md-4 offset-md-4 text-center my-3"></h4>
        <form class="col-md-4 offset-md-4 needs-validation" name="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="create_discount">

            <div class="form-group col-auto my-3">
                <label for="nameOfDiscount">${nameOfDiscount}</label>
                <input type="text" class="form-control" id="nameOfDiscount" placeholder="${nameOfDiscount}" name="discount_name" required
                       pattern="^[A-Za-zА-Яа-я0-9_\.\,\:\s-]{3,50}$"/>
                <c:if test="${invalid_discount_name}">
                    <div class="invalid-feedback-backend" style="color: red">
                            ${invName}
                    </div>
                </c:if>
            </div>

            <div class="form-group col-auto my-3">
                <label for="valueOfDiscount">${discountValue}</label>
                <input type="text" class="form-control" id="valueOfDiscount" placeholder="${discountValue}" name="discount_value" required
                       pattern="^[0-9]{1,2}$">
                <c:if test="${invalid_discount_value}">
                    <div class="invalid-feedback-backend" style="color: red">
                            ${invValue}
                    </div>
                </c:if>
            </div>
            <button type="submit" class="btn btn-success my-3">${createDiscount}</button>

        </form>
    </div>
</div>

</body>
</html>
