<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/admin/clients_extra.jsp"/>
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
    <fmt:message key="registration.name" var="firstName"/>
    <fmt:message key="basket.date" var="inWhatDate"/>
    <fmt:message key="registration.phone_number" var="phoneNumber"/>
    <fmt:message key="ordersAdmin.orderStatus" var="orderStatus"/>
    <fmt:message key="ordersAdmin.deleteOrder" var="deleteOrder"/>
    <fmt:message key="ordersAdmin.extra" var="extra"/>
    <fmt:message key="login.login" var="login"/>
    <fmt:message key="registration.second_name" var="second_name"/>
    <fmt:message key="registration.email" var="email"/>
    <fmt:message key="clientAdmin.clientRole" var="role"/>
    <fmt:message key="clientAdmin.clientStatus" var="clientStatus"/>
    <fmt:message key="clientExtra.discount" var="discount"/>
    <fmt:message key="clientExtra.admin" var="adminRole"/>
    <fmt:message key="clientExtra.client" var="clientRole"/>
    <fmt:message key="clientExtra.changeClientRole" var="changeRole"/>
    <fmt:message key="clientExtra.changeClientDiscount" var="changeDiscount"/>
    <fmt:message key="clientExtra.noDiscount" var="noDiscount"/>
    <fmt:message key="clientExtra.ban" var="ban"/>
    <fmt:message key="clientExtra.totalPrice" var="totalPrice"/>
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
        <div class="card-body">
            <p class="card-text">${login}: ${client_extra.login}</p>
            <p class="card-text">${firstName}: ${client_extra.firstName}</p>
            <p class="card-text">${second_name}: ${client_extra.secondName}</p>
            <p class="card-text">${email}: ${client_extra.email}</p>
            <p class="card-text">${phoneNumber}: ${client_extra.phoneNumber}</p>
            <p class="card-text">${role}: ${client_extra.role}</p>
            <p class="card-text">${clientStatus}: ${client_extra.status}</p>
            <p class="card-text">${discount}: ${client_extra.discount.nameOfDiscount}</p>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change_client_role"/>
                <select class="form-select" id="clientRole" name="client_role">
                    <option value="ADMIN">${adminRole}</option>
                    <option value="CLIENT">${clientRole}</option>
                </select>
                <button type="submit" class="btn btn-success">${changeRole}</button>
            </form>

            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change_client_discount"/>
                <select class="form-select" id="clientDiscount" name="client_discount">
                    <option value="">${noDiscount}</option>
                    <c:forEach var="clientDiscount" items="${discount_list}">
                        <option value="${clientDiscount.discountId}">${clientDiscount.nameOfDiscount}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-success">${changeDiscount}</button>
            </form>

            <div class="d-flex">
                <button type="submit" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=change_client_banned'">${ban}</button>
            </div>
        </div>
    </div>

    <div class="row align-items-center">
                <table class="table table-striped d-flex">
                    <tr class="table-success">
                        <th class="table-success">${inWhatDate}</th>
                        <th class="table-success">${firstName}</th>
                        <th class="table-success">${phoneNumber}</th>
                        <th class="table-success">${orderStatus}</th>
                        <th class="table-success">${totalPrice}</th>
                <th class="table-success"></th>
                <th class="table-success"></th>
            </tr>
            <c:forEach var="order" items="${order_list}">
                <tr class="table-success">
                    <td class="table-success">${order.inWhatDate}</td>
                    <td class="table-success">${order.firstName}</td>
                    <td class="table-success">${order.phoneNumber}</td>
                    <td class="table-success">${order.orderStatus}</td>
                    <td class="table-success">${order.totalPrice*client_extra.discount.value}</td>
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

</div>

</body>
</html>
