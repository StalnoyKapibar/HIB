<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Create</title>
</head>
<body>

${message}



<form action="/admin/add" method="POST">
    <h2>Create</h2>
    Логин:
    <input type="text" name="username" size="30">
    Пароль:
    <input type="text" name="password" size="30">
    Роль:
    <select name="roleId" id="">
        <c:forEach var="role" items="${roleList}">
            <option value="${role.id}">${role.role}</option>
        </c:forEach>
    </select>

    <input type="submit" value="ОK"/>
</form>



Создание формы
<form action="/admin/role" method="post">
    <input type="text" name="roleName" size="30">
    <input type="submit" value="ОK"/>
</form>

<sec:authorize access="isAuthenticated()">
    <h4><a href="/logout">Выйти</a></h4>
</sec:authorize>
</body>
</html>