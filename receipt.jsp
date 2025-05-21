<!DOCTYPE html>
<html>
<head>
    <title>Booking Receipt</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="icon" href="favicon_io/favicon.ico" type="image/x-icon">
    <link rel="icon" type="image/png" sizes="32x32" href="favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="favicon_io/favicon-16x16.png">
    <link rel="apple-touch-icon" sizes="180x180" href="favicon_io/apple-touch-icon.png">
    <style>
        body {
            font-family: 'Roboto', Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: url('truck2.jpg') no-repeat center center/cover; /* Background image */
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            position: relative;
            max-width: 800px;
            margin: 20px;
            padding: 20px;
            background: rgba(255, 255, 255, 0.9); /* Semi-transparent white background */
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .header {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .header img {
            height: 40px; /* Adjust logo size */
            margin-right: 15px; /* Space between logo and title */
        }

        h2 {
            margin: 0; /* Remove default margin */
            font-weight: 700;
            color: #333;
        }

        table {
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background: #f1f1f1;
            font-weight: 500;
        }

        td {
            background: #fafafa;
        }

        .btn {
            margin-right: 10px;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <img src="logo.png" alt="Company Logo"> <!-- Replace with your logo file -->
            <h2>Booking Receipt</h2>
        </div>
        <table class="table table-striped table-bordered">
            <tr><th>Company</th><td>${company}</td></tr>
            <tr><th>Cost (&#8377;)</th><td>${cost}</td></tr>
            <tr><th>From Location</th><td>${fromLocation}</td></tr>
            <tr><th>To Location</th><td>${toLocation}</td></tr>
        </table>
        <a href="index.html" class="btn btn-primary">Back to Home</a>
        <a href="thankYou.jsp" class="btn btn-success">Track Your Order</a>
        <button class="btn btn-success" onclick="window.print()">Print Receipt</button>
    </div>
</body>
</html>
