<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <link rel="stylesheet" href="../css/bootstrap.min.css"/>
  <script src="../js/bootstrap.min.js"></script>
  <title>Reco</title>
  <br>
  <img src="<%=request.getContextPath()%>/resources/images/logo.png" />
</head>
<body>
<h1>Reco</h1>

<form id="search" action="mainServlet" method="post">
  <input type="radio" name="category" value="All" checked/>All
  <input type="radio" name="category" value="Movies"/>Movies
  <input type="radio" name="category" value="Books"/>Books
  <input type="radio" name="category" value="Music"/>Music
  <br>
  <input type="text" name="input" size="30"/>
  <input type="submit" id="btnSearch" value="search"/>
</form>

<form id="results">
  <table>
    <tr>
      <th>#</th>
      <th>Name</th>
      <th>Genre</th>
      <th>Price</th>
      <th>Ranking</th>
      <th>Image</th>
    </tr>
    <c:forEach items="${titlesList}" var="title" varStatus="status">
      <tr>
        <td>${status.index + 1}</td>
        <td><a href=${urlList[status.index]}>${title}</a></td>
        <td>${genreList[status.index]}</td>
        <td>${priceList[status.index]}</td>
        <td>${rankingList[status.index]}</td>
        <td><img src="${imageList[status.index]}"></td>
      </tr>
    </c:forEach>
  </table>
</form>
</body>
</html>
