<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="/static/bootstrap.min.css" rel="stylesheet">

        <title>Edit client</title>
    </head>
    <body>
        <div class="container">
            <h1>Clients</h1>

            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Edit</li>
                </ol>
            </nav>

            <h2>Create a client</h2>
            <form method="post" action="">

                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="mb-3">
                                <label for="name" class="form-label">Name</label>
                                <input type="text" class="form-control" id="name" name="name" value="${client.name}">
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-control" id="address" name="address" value="${client.address.street}">
                            </div>

                        </div>
                        <div class="col">

                            <div class="mb-3">
                                <label for="phone1" class="form-label">Phone 1</label>
                                <input type="text" class="form-control" id="phone1" name="phones" value="${client.phones[0].number}">
                            </div>
                            <div class="mb-3">
                                <label for="phone2" class="form-label">Phone 2</label>
                                <input type="text" class="form-control" id="phone2" name="phones" value="${client.phones[1].number}">
                            </div>
                            <div class="mb-3">
                                <label for="phone3" class="form-label">Phone 3</label>
                                <input type="text" class="form-control" id="phone3" name="phones" value="${client.phones[2].number!}">
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </body>
</html>