<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/personalArea.jsp"/>
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
    <fmt:message key="main.language" var="languge"/>
    <fmt:message key="personalArea.personalArea" var="personalArea"/>
    <fmt:message key="basket.date" var="inWhatDate"/>
    <fmt:message key="ordersAdmin.orderStatus" var="orderStatus"/>
    <fmt:message key="clientExtra.totalPrice" var="totalPrice"/>
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
                <button type="button" class="btn btn-success" onclick="window.location.href='jsp/pages/login.jsp'">${sign_in}</button>
            </div>
        </c:if>
        <c:if test="${not empty client}">
            <div class="d-flex">
                <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=sign_out'">${sign_out}</button>
            </div>
        </c:if>
    </div>
</nav>


<div class="row align-items-center">
    <table class="table table-striped d-flex">
        <tr class="table-success">
            <th class="table-success">${inWhatDate}</th>
            <th class="table-success">${orderStatus}</th>
            <th class="table-success">${totalPrice}</th>
            <th class="table-success"></th>
            <th class="table-success"></th>
        </tr>
        <c:forEach var="order" items="${client_orders}">
            <tr class="table-success">
                <td class="table-success">${order.inWhatDate}</td>
                <td class="table-success">${order.orderStatus}</td>
                <td class="table-success">${order.totalPrice*client.discount.value}</td>
                <td class="table-success">
                    <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="delete_order"/>
                        <input type="hidden" name="order_id" value="${order.orderId}"/>
                        <button type="submit" class="btn btn-success">${deleteOrder}</button>
                    </form>
                </td>
                <td class="table-success">
                    <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="order_extra"/>
                        <input type="hidden" name="order_id" value="${order.orderId}"/>
                        <button type="submit" class="btn btn-success">${extra}</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
