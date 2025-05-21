<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thank You for Your Order</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="icon" href="favicon_io/favicon.ico" type="image/x-icon">
    <link rel="icon" type="image/png" sizes="32x32" href="favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="favicon_io/favicon-16x16.png">
    <link rel="apple-touch-icon" sizes="180x180" href="favicon_io/apple-touch-icon.png">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            background: url('truck2.jpg') no-repeat center center fixed; /* Replace with your background image URL */
            background-size: cover;
        }
        .overlay {
            position: fixed; /* Ensures the overlay covers the viewport */
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent overlay */
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: rgba(255, 255, 255, 0.9);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            max-width: 500px;
            width: 100%;
            text-align: center;
            position: relative; /* For positioning the logo */
        }
        .logo {
            max-height: 50px; /* Adjust size as needed */
            height: auto; /* Maintain aspect ratio */
            margin-right: 15px; /* Space between logo and text */
        }
        .header-container {
            display: flex;
            align-items: center; /* Vertically center-align items */
            margin-bottom: 20px;
        }
        h2 {
            color: #333;
            margin: 0; /* Remove default margins */
            padding: 0; /* Remove default padding */
        }
        p {
            color: #555;
            margin-bottom: 30px;
        }
        .btn-group {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        .btn-group .btn {
            width: 48%;
            margin-bottom: 10px;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #4e555b;
        }
    </style>
</head>
<body>
    <div class="overlay">
        <div class="container">
            <div class="header-container">
                <img src="logo.png" alt="Logo" class="logo"> <!-- Replace with your logo URL -->
                <h2>Thank You for Placing Your Order!</h2>
            </div>
            <p>Your order has been successfully placed.</p>
            <div class="btn-group">
                <a href="trackOrder.jsp" class="btn btn-primary">Track Your Order</a>
                <a href="dashboard.html" class="btn btn-secondary">Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>