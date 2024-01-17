<html lang="en">
<head>
  <link rel="stylesheet" href="/authors.css", type="text/css">
</head>
<body>
<div class="container">
    <h2>AUTHORS</h2>
    <ul class="responsive-table">
        <li class="table-header">
            <div class="col col-1">ID</div>
            <div class="col col-2">NAME</div>
            <div class="col col-3">BOOKS</div>
        </li>
        <#list authors as author>
            <li class="table-row">
                <div class="col col-1">${author.id}</div>
                <div class="col col-2">${author.name} ${author.surname}</div>
                <div class="col col-3">
                <#list author.books as book>
                <div>${book.name}</div>
                <#else>
                <div>NO BOOKS</div>
                </#list>
                </div>
            </li>
        </#list>
    </ul>
</div>
</body>
</html>