<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" scope="session" value="jsp/pages/desserts.jsp"/>
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
    <fmt:message key="desserts.add" var="add_dessert"/>
    <fmt:message key="main.language" var="languge"/>
    <fmt:message key="personalArea.personalArea" var="personalArea"/>
</fmt:bundle>
    <!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Десерты</title>
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
    <div class="container d-flex flex-wrap justify-content-center">
        <c:forEach var="des" items="${dessert_list}">
            <form name="addDessert" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="add_dessert">
                <input type="hidden" name="id_of_dessert" value="${des.dessertId}">
                <div class= "card m-3 col-sm-4" style="width: 16rem;">
                    <img class="card-img-top" src="cake.jpg" alt="Шоколадный чиз">
                    <div class="card-body">
                        <h5 class="card-title">${des.name}</h5>
                        <p class="card-text">${des.cost}</p>
                        <p class="card-text">${des.description}</p>
                        <p class="card-text">${des.type}</p>
                        <div class="qty mt-5">
                            <input type="number" class="count" name="dessert_count" value="1">
                        </div>
                        <button type="submit" class="btn btn-success">${add_dessert}</button>
                    </div>
                </div>
            </form>
        </c:forEach>
    </div>
    </body>
    </html>