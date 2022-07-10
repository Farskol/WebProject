<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/dessert_edit.jsp"/>
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
    <fmt:message key="dessertEdit.change" var="change"/>
    <fmt:message key="dessertEdit.dessertName" var="dessertName"/>
    <fmt:message key="dessertEdit.description" var="dessertDescription"/>
    <fmt:message key="dessertEdit.type" var="typeOfDessert"/>
    <fmt:message key="dessertEdit.cost" var="costOfDessert"/>
    <fmt:message key="dessertCost.invalidCost" var="invCost"/>
    <fmt:message key="dessertEdit.EditFormNull" var="formIsNull"/>
    <fmt:message key="discountAdmin.discountPage" var="discountPage"/>
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

<div class="d-flex mx-5">
    <div class="card m-3 col-sm-4" style="width: 16rem;">
        <img class="card-img-top" src="cake.jpg" alt="Шоколадный чиз">
        <div class="card-body">
            <h5 class="card-title">${dessert.name}</h5>
            <p class="card-text">${dessert.cost}</p>
            <p class="card-text">${dessert.description}</p>
            <p class="card-text">${dessert.type}</p>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <h4 class="col-md-4 offset-md-4 text-center my-3"></h4>
            <form class="col-md-4 offset-md-4 needs-validation" name="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="edit_dessert_parameters">
                <div class="form-group col-auto my-3">
                    <label for="nameOfDessert">${dessertName}</label>
                    <input type="text" class="form-control" id="nameOfDessert"  placeholder="${dessertName}" name="dessert_name"
                           pattern="^[A-Za-zА-Яа-я0-9_-]{3,30}$"/>
                    <c:if test="${invalid_dessert_name}">
                        <div class="invalid-feedback-backend" style="color: red">
                                ${invDesName}
                        </div>
                    </c:if>
                </div>
                <div class="form-group col-auto my-3">
                    <label for="description">${dessertDescription}</label>
                    <textarea type="text" class="form-control" id="description" placeholder="${dessertDescription}" name="dessert_description"
                              pattern="^[A-Za-zА-Яа-я0-9_\.\,\:\s-]{3,1000}$"></textarea>
                    <c:if test="${invalid_dessert_description}">
                        <div class="invalid-feedback-backend" style="color: red">
                                ${invDesDesc}
                        </div>
                    </c:if>
                </div>
                <div class="form-group col-auto my-3">
                    <label for="type">${typeOfDessert}</label>
                    <input type="text" class="form-control" id="type" placeholder="${typeOfDessert}" name="type_of_dessert"
                           pattern="^[A-Za-zА-Яа-я0-9_-]{3,30}$"/>
                    <c:if test="${invalid_dessert_type}">
                        <div class="invalid-feedback-backend" style="color: red">
                                ${invDesType}
                        </div>
                    </c:if>
                </div>
                <div class="form-group col-auto my-3">
                    <label for="cost">${costOfDessert}</label>
                    <input type="text" class="form-control" id="cost" placeholder="${costOfDessert}" name="cost_of_dessert"
                           pattern="^([0-9]{1,6})+(.[0-9]{1,2})?$">
                    <c:if test="${invalid_dessert_cost}">
                        <div class="invalid-feedback-backend" style="color: red">
                                ${invCost}
                        </div>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-success my-3">${change}</button>

                <c:if test="${edit_dessert_form_is_null}">
                    <div class="invalid-feedback-backend" style="color: red">
                            ${formIsNull}
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>


</body>
</html>
