<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="/static/bootstrap.min.css" rel="stylesheet">

        <title>Clients!</title>
    </head>
    <body>
        <div class="container">
            <h1>Clients</h1>

            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item active" aria-current="page">Home</li>
                </ol>
            </nav>

            <h2>Clients list</h2>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Phones</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <#list clients as client>
                    <tr>
                        <td scope="row">${client.id}</td>
                        <td>${client.name}</td>
                        <td>${client.address.street}</td>
                        <td><#list client.phones as phone>${phone.number}<#sep>, </#sep></#list></td>
                        <td>
                            <a href="/edit/${client.id}">Edit</a>
                            <a href="/remove/${client.id}">Remove</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>

            <a href="/add" class="btn btn-primary">Add a client</a>
        </div>
    </body>
</html>