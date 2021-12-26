<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<div style="text-align: center;">
  <h1>Вход в систему</h1><br>
  <form method="post" action="/login">
    <input type="text" required placeholder="Логин" name="login"><br>
    <input type="password" required placeholder="Пароль" name="password"><br><br>
    <%
      Object str = request.getAttribute("Error");
      if (str != null) {
        out.print(str);
      }
    %>
    <p><input class="button" type="submit" value="Войти"></p>
  </form>
</div>
</body>
</html>