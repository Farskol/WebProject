<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="counter" value="0" scope="page"/>
<c:set var="currentPage" scope="session" value="jsp/pages/basket.jsp"/>
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
    <fmt:message key="registration.second_name" var="second_name"/>
    <fmt:message key="registration.your_second_name" var="your_second_name"/>
    <fmt:message key="registration.phone_number" var="phone_number"/>
    <fmt:message key="basket.checkout" var="checkout"/>
    <fmt:message key="registration.invalid_first_name" var="inv_first_name"/>
    <fmt:message key="registration.invalid_second_name" var="inv_second_name"/>
    <fmt:message key="registration.invalid_phone_number" var="inv_phone_number"/>
    <fmt:message key="basket.name" var="des_name"/>
    <fmt:message key="basket.cost" var="cost"/>
    <fmt:message key="basket.number" var="number"/>
    <fmt:message key="basket.kg" var="kg"/>
    <fmt:message key="basket.date" var="date"/>
    <fmt:message key="basket.delete_dessert" var="deleteDesert"/>
    <fmt:message key="main.language" var="languge"/>
    <fmt:message key="basket.empty" var="basketIsEmpty"/>
    <fmt:message key="personalArea.personalArea" var="personalArea"/>
</fmt:bundle>

<html>
<head>
    <style>
        .hidden-cell {
            display: none;
        }
    </style>
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

<div class="container-fluid d-flex">
<c:if test="${not empty list_of_desserts}">
    <div class="col-md-4">
        <form class="col-md-9 offset-md-2 needs-validation" name="orderForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="create_order">
            <div class="form-group col-auto my-3">
                <c:if test="${empty client}">
                <label for="first_name">${name}</label>
                <input type="text" class="form-control" id="first_name" placeholder="${your_name}" name = "first_name" required
                       pattern="^[A-Za-zА-Яа-я]{3,50}$">
                </c:if>
                <c:if test="${invalid_first_name}">
                <div class="invalid-feedback-backend" style="color: red">
                        ${inv_first_name}
                </div>
                </c:if>
            </div>
            <div class="form-group col-auto my-3">
                <c:if test="${empty client}">
                <label for="second_name">${second_name}</label>
                <input type="text" class="form-control" id="second_name" placeholder="${your_second_name}" name="second_name"
                       pattern="^[A-Za-zА-Яа-я]{3,50}$">
                </c:if>
                <c:if test="${invalid_second_name}">
                <div class="invalid-feedback-backend" style="color: red">
                        ${inv_second_name}
                </div>
                </c:if>
            </div>
            <div class="form-group my-3">
                <c:if test="${empty client}">
                <label for="phoneNumber">${phone_number}</label>
                <input type="text" class="form-control" id="phoneNumber" placeholder="${phone_number}" name = "phone_number" required
                       pattern="(29|25|44)\d{7}">
                </c:if>
                <c:if test="${invalid_phone_number}">
                <div class="invalid-feedback-backend" style="   color: red">
                        ${inv_phone_number}
                </div>
                </c:if>
            </div>
            <div class="form-group my-3">
                <label for="date">${date}</label>
                <input type="date" id="date" class="form-control" name="date" required>
            </div>
            <button type="submit" class="btn btn-success my-3">${checkout}</button>
        </form>
    </div>
</c:if>

<c:if test="${empty list_of_desserts}">
    <div class="container">
        <div class="row align-items-center">
            <div class="display-4 col-md-8 offset-md-3">
                    ${basketIsEmpty}
            </div>
        </div>
    </div>
</c:if>

    <div class="col-md-8">
        <c:if test="${not empty list_of_desserts}">
        <div class="align-items-center">
            <table class="table table-striped">
                <tr class="table-success">
                    <th class="table-success" style="width:50%">${des_name}</th>
                    <th class="table-success">${cost}</th>
                    <th class="table-success">${number}</th>
                    <th class="table-success"></th>
                </tr>
                <c:forEach var="ordDes" items="${list_of_desserts}">
                <c:if test="${not empty ordDes.dessert}">
                <tr class="table-success">
                    <td class="table-success">${ordDes.dessert.name}</td>
                    <td class="table-success">${ordDes.dessert.cost*ordDes.dessertCount}</td>
                    <td class="table-success">${ordDes.dessertCount}</td>
                    <td class="table-success">
                        <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="delete_dessert_in_list">
                            <input type="hidden" name="count" value=${counter}>
                            <button type="submit" class="btn btn-success">${deleteDesert}</button>
                        </form>
                    </td>
                    <td class="hidden-cell">
                            ${counter = counter+1}
                    </td>
                </tr>
                </c:if>
                <c:if test="${not empty ordDes.cake}">
                <tr class="table-success">
                    <td class="table-success">${ordDes.cake.stuffing.stuffing}</td>
                    <td class="table-success">${ordDes.cake.cost*ordDes.cake.weight}</td>
                    <td class="table-success">${ordDes.cake.weight} ${kg}</td>
                    <td class="table-success">
                        <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="delete_dessert_in_list">
                            <input type="hidden" name="count" value=${counter}>
                            <button type="submit" class="btn btn-success">${deleteDesert}</button>
                        </form>
                    </td>
                    <td class="hidden-cell">
                            ${counter = counter+1}
                    </td>
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
