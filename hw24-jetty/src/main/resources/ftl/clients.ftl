<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
<#--    <link href="/static/bootstrap.min.css" rel="stylesheet">-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>
        <h1>Clients</h1>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Address</th>
                <th scope="col">Phones</th>
            </tr>
            </thead>
            <tbody>
            <#list clients as client>
                <tr>
                    <td scope="row">${client.id}</td>
                    <td>${client.name}</td>
                    <td>${client.address.street}</td>
                    <td><#list client.phones as phone>${phone.number}<#sep>, </#list></td>
                </tr>
            </#list>
            </tbody>
        </table>

    </body>
</html>