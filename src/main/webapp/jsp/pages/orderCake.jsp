<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/orderCake.jsp"/>
<c:choose>
    <c:when test="${not empty pageLanguage}"> <fmt:setLocale value="${pageLanguage}" scope="session"/></c:when>
    <c:when test="${empty pageLanguage}"> <fmt:setLocale value="${pageLanguage = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:bundle basename="context.language">
    <fmt:message key="login.sign_in" var="sign_in"/>
    <fmt:message key="main.desserts" var="desserts"/>
    <fmt:message key="main.order_a_cake" var="order_cake"/>
    <fmt:message key="main.basket" var="basket"/>
    <fmt:message key="login.sign_out" var="sign_out"/>
    <fmt:message key="registration.name" var="name"/>
    <fmt:message key="registration.your_name" var="your_name"/>
    <fmt:message key="basket.checkout" var="checkout"/>
    <fmt:message key="orderCake.weight" var="weight"/>
    <fmt:message key="orderCake.description" var="description"/>
    <fmt:message key="orderCake.stuffing" var="stuffing"/>
    <fmt:message key="desserts.add" var="add_dessert"/>
    <fmt:message key="main.language" var="languge"/>
    <fmt:message key="orderCake.title" var="title"/>
    <fmt:message key="orderCake.globalCost" var="globalCost"/>
    <fmt:message key="personalArea.personalArea" var="personalArea"/>
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
                    <a class="nav-link active text-white" aria-current="page" href="${pageContext.request.contextPath}/jsp/pages/desserts.jsp">${desserts}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/orderCake.jsp">${order_cake}</a>
                </li>
                <li class="nav-item d-flex">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/basket.jsp">${basket}</a>
                </li>
                <c:if test="${not empty client}">
                    <li class="nav-item d-flex">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/jsp/pages/personalArea.jsp">${personalArea}</a>
                    </li>
                </c:if>
            </ul>
        </div>

        <div class="d-flex">
            <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=change_language'">${languge}</button>
        </div>

        <c:if test="${empty client}">
            <div class="d-flex">
                <button type="button" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/jsp/pages/login.jsp'">${sign_in}</button>
            </div>
        </c:if>
        <c:if test="${not empty client}">
            <div class="d-flex">
                <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=sign_out'">${sign_out}</button>
            </div>
        </c:if>
    </div>
</nav>

<div class="container">
    <div class="row align-items-center col-md-6 offset-md-3">
        <h1>${title}</h1>
        <h5>${globalCost}</h5>
        <form class="row g-3" name="orderForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="add_cake">
            <textarea class="form-control" style="height: 150px" id="descriptionCakeTextarea" placeholder="${description}" name ="description"></textarea>
            <div class="col-md-4 position-relative">
                <label for="validationTooltip03" class="form-label">${weight}</label>
                <input type="number" class="form-control" id="validationTooltip03" name="weight" value="1">
            </div>
            <div class="col-md-8 position-relative">
                <label for="stuffing" class="form-label">${stuffing}</label>
                <select class="form-select" id="stuffing" name="stuffing">
                    <c:forEach var="stuf" items="${stuffing_list}">
                        <option value=${stuf.stuffingId}>${stuf.stuffing}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-12 mt-4">
                <button class="btn btn-success" type="submit">${add_dessert}</button>
            </div>
            <hr>
            <c:forEach var="stuf" items="${stuffing_list}">
                <p>${stuf.stuffing} - ${stuf.description}</p>
            </c:forEach>
        </form>
    </div>
</div>

</body>
</html>
