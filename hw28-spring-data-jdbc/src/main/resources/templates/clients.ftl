<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <#--<link href="/static/bootstrap.min.css" rel="stylesheet">-->
        <link href="/static/bootstrap.min.css" rel="stylesheet">

        <title>Hello, world!</title>
    </head>
    <body>
        <div class="container">
            <h1>Clients</h1>
            <h2>Clients list</h2>
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
                        <td><#list client.phones as phone>${phone.number}<#sep>, </#sep></#list></td>
                    </tr>
                </#list>
                </tbody>
            </table>

            <h2>Add a client</h2>
            <form method="post" action="/add">

                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="mb-3">
                                <label for="name" class="form-label">Name</label>
                                <input type="text" class="form-control" id="name" name="name">
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-control" id="address" name="address">
                            </div>

                        </div>
                        <div class="col">

                            <div class="mb-3">
                                <label for="phone1" class="form-label">Phone 1</label>
                                <input type="text" class="form-control" id="phone1" name="phones">
                            </div>
                            <div class="mb-3">
                                <label for="phone2" class="form-label">Phone 2</label>
                                <input type="text" class="form-control" id="phone2" name="phones">
                            </div>
                            <div class="mb-3">
                                <label for="phone3" class="form-label">Phone 3</label>
                                <input type="text" class="form-control" id="phone3" name="phones">
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </body>
</html>