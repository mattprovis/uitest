<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Home - UI Test Demo</title>
</head>

<body>

<div id="container" style="width:800px">

  <%@include file="header.jspf"%>

  <div id="main" style="margin: 3em 1em">

    <h2>UI Test Demo</h2>
    <h3>Latest News</h3>
    <div id="news">
      ${latestNews}
    </div>
  </div>

  <%@include file="footer.jspf"%>
</div>
</body>
</html>
