<?php
include('Config.php');
if(isset($_POST['save']))
{
    $sql = "INSERT INTO `employees`(`userName`, `pin`, `firstName`, `lastName`, `authorityLevel`, `pos`, `status`) VALUES ('".$_POST["userName"]."','".$_POST["pin"]."','".$_POST["firstName"]."','".$_POST["lastName"]."','".$_POST["authorityLevel"]."','".$_POST["pos"]."','".$_POST["status"]."')";


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

    <title>Add Employee</title>


</head>
<body>
    <div style="position: absolute; left: 45%; top: 25%;">
        <form method="post" style="background: rgb(0, 120, 86); width: 105%; height: 105%;">
            <label id="first"> User Name:</label><br/>
            <input type="text" name="userName" value=""><br/>

            <label id="first">PIN:</label><br/>
            <input type="password" name="pin"><br/>

            <label id="first">First Name:</label><br/>
            <input type="text" name="firstName"><br/>

            <label id="first">Last Name:</label><br/>
            <input type="text" name="lastName"><br/>

            <label id="first">Authority Level:</label><br/>
            <input type="text" name="authorityLevel"><br/>

            <label id="first">Position:</label><br/>
            <input type="text" name="pos"><br/>

            <label id="first">Status:</label><br/>
            <input type="text" name="status"><br/>

            <button type="submit" name="save">Add Employee</button>


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