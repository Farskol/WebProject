<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/orders_extra.jsp"/>
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
    <fmt:message key="basket.name" var="des_name"/>
    <fmt:message key="basket.cost" var="cost"/>
    <fmt:message key="basket.number" var="number"/>
    <fmt:message key="basket.kg" var="kg"/>
    <fmt:message key="basket.date" var="date"/>
    <fmt:message key="registration.name" var="name"/>
    <fmt:message key="registration.second_name" var="secondName"/>
    <fmt:message key="registration.phone_number" var="phoneNumber"/>
    <fmt:message key="basket.cost" var="totalPrice"/>
    <fmt:message key="basket.date" var="inWhatDate"/>
    <fmt:message key="ordersAdmin.orderStatus" var="orderStatus"/>
    <fmt:message key="orderExtra.orderDate" var="orderDate"/>
    <fmt:message key="orderExtra.changeOrderStatus" var="changeOrderStatus"/>
    <fmt:message key="orderExtra.processed" var="processed"/>
    <fmt:message key="orderExtra.preparing" var="preparing"/>
    <fmt:message key="orderExtra.ready" var="ready"/>
    <fmt:message key="discountAdmin.discountPage" var="discountPage"/>
    <fmt:message key="stuffingAdmin.stuffingPage" var="stuffingPage"/>
    <fmt:message key="dessertEdit.description" var="descriotion"/>
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
        <div class="card-body">
            <p class="card-text">${name}: ${order.firstName}</p>
            <p class="card-text">${secondName}: ${order.secondName}</p>
            <p class="card-text">${phoneNumber}: +37529${order.phoneNumber}</p>
            <p class="card-text">${totalPrice}: ${order.totalPrice}</p>
            <p class="card-text">${inWhatDate}: ${order.inWhatDate}</p>
            <p class="card-text">${orderStatus}: ${order.orderStatus}</p>
            <p class="card-text">${orderDate}: ${order.orderDate}</p>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change_order_status"/>
                <label for="orderStatus" class="form-label">${orderStatus}</label>
                <select class="form-select" id="orderStatus" name="order_status">
                    <option value="PROCESSED">${processed}</option>
                    <option value="PREPARING">${preparing}</option>
                    <option value="READY">${ready}</option>
                </select>
                <button type="submit" class="btn btn-success">${changeOrderStatus}</button>
            </form>
        </div>
    </div>

    <div class="col-md-8">
        <c:if test="${not empty order_dessert_list}">
            <div class="align-items-center">
                <table class="table table-striped">
                    <tr class="table-success">
                        <th class="table-success" style="width:50%">${des_name}</th>
                        <th class="table-success">${descriotion}</th>
                        <th class="table-success">${cost}</th>
                        <th class="table-success">${number}</th>
                    </tr>
                    <c:forEach var="ordDes" items="${order_dessert_list}">
                        <c:if test="${not empty ordDes.dessert}">
                            <tr class="table-success">
                                <td class="table-success">${ordDes.dessert.name}</td>
                                <td class="table-success">${ordDes.dessert.description}</td>
                                <td class="table-success">${ordDes.dessert.cost*ordDes.dessertCount}</td>
                                <td class="table-success">${ordDes.dessertCount}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty ordDes.cake}">
                            <tr class="table-success">
                                <td class="table-success">${ordDes.cake.stuffing.stuffing}</td>
                                <td class="table-success">${ordDes.cake.designDescription}</td>
                                <td class="table-success">${ordDes.cake.cost*ordDes.cake.weight}</td>
                                <td class="table-success">${ordDes.cake.weight} ${kg}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>

</div>
</body>

</html>
