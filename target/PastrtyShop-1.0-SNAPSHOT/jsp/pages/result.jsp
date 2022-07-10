<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row align-items-center col-md-6 offset-md-3">
        <h1>Торты на заказ</h1>
        <h5>Цена за 1 кг - 30 р.</h5>
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
            <p>Бананы-кокосы - очень вкусный торт</p>
            <p>Апельсиновый рай - еще один очень вкусный торт</p>
        </form>
    </div>
</div>
</body>
</html>
