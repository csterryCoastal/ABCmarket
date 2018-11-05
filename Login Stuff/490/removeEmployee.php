<?php
include('Config.php');
if(isset($_POST['delete']))
{
    $user = $_POST['userName'];
    $first = $_POST['firstName'];
    $last = $_POST['lastName'];

    $sql = "Delete FROM `employees` WHERE userName = '$user' AND firstName = '$first' AND lastName = '$last'";

    $result = mysqli_query($db,$sql);
}

?>
<!Doctype html>
<html>
<head>

    <style>

        body{
            background: rgb(0, 120, 86) url("manager.png") no-repeat fixed center;
            background-size: 75%;
        }

        .cancel{

            border: 2px solid black;
            background-color: red;
            color: black;
            padding: 10px 18px;
            margin: 8px 0;
            cursor: pointer;
            width: auto;
        }
    </style>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="$1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Remove Employee</title>


</head>
<body>
<div style="position: absolute; left: 45%; top: 25%;">
    <form method="post" style="background: rgb(0, 120, 86); width: 105%; height: 105%;">
        <label id="first"> User Name:</label><br/>
        <input type="text" name="userName"><br/>

        <label id="first"> First Name:</label><br/>
        <input type="text" name="firstName"><br/>

        <label id="first"> Last Name:</label><br/>
        <input type="text" name="lastName"><br/>


        <button style="text-align: center; background-color: red; position: relative; left: 13%;" type="submit" name="delete">Remove Employee</button>


    </form>
</div>

</body>
<div style="text-align: center; position: absolute; bottom: 0;left: 45%">
    <a href = "ABC.html">
        <button class="cancel">Sign Out of Manager</button>
    </a>
    <br>

    <a href="Manager.html">
        <button class="cancel">Return to Manager Page</button>
    </a>
</div>
</html>