<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
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
  <br>
  1) <a href=${urlList[0]}>${itemsList[0]}</a>
  <p>
  2) <a href=${urlList[1]}>${itemsList[1]}</a>
  <p>
  3) <a href=${urlList[2]}>${itemsList[2]}</a>
  <p>
  4) <a href=${urlList[3]}>${itemsList[3]}</a>
  <p>
  5) <a href=${urlList[4]}>${itemsList[4]}</a>
  <p>
  6) <a href=${urlList[5]}>${itemsList[5]}</a>
  <p>
  7) <a href=${urlList[6]}>${itemsList[6]}</a>
  <p>
  8) <a href=${urlList[7]}>${itemsList[7]}</a>
  <p>
  9) <a href=${urlList[8]}>${itemsList[8]}</a>
  <p>
  10) <a href=${urlList[9]}>${itemsList[9]}</a>


  </body>
</html>
