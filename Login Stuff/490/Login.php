<?php
include('Config.php');
session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
    // username and password sent from form

    $myusername = mysqli_real_escape_string($db,$_POST['userName']);
    $mypassword = mysqli_real_escape_string($db,$_POST['PIN']);

    $sql = "SELECT userName FROM `employees` WHERE userName = '$myusername' AND pin = '$mypassword'";
    $result = mysqli_query($db,$sql);
    $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
    $active = $row['active'];

    $count = mysqli_num_rows($result);

    // If result matched $myusername and $mypassword, table row must be 1 row

    if($count == 1) {
        $_SESSION['login_user'] = $myusername;

        header("location: ABC.html");
    }else {
        $error = "Your Login Name or Password is invalid";
    }
}
?>
<html>

<head>

    <title>Login Page</title>
    <link rel="stylesheet" href="styles.css">
    <style type = "text/css">
        body {
            font-family:Arial, Helvetica, sans-serif;
            font-size:14px;

            background-image: url("grocery.png");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: 65%;

        }
        label {
            font-weight:bold;
            width:100px;
            font-size:14px;
        }
        .box {
            border:#666666 solid 1px;
        }
    </style>

</head>
<h1>Employee Login Page</h1>



<div align = "center">
    <div style = "width:25%; border: solid 1px #333333; background: white" align = "left">
        <div style = "background-color:#333333; color:#FFFFFF; padding:3px;"><b>Login</b></div>

        <div style = "margin:30px">

            <form action = "" method = "post">
                <label>UserName:</label><br>
                <input type = "text" name = "userName" class = "box"/><br /><br />

                <label>PIN:</label><br>
                <input type = "password" name = "PIN" class = "box" /><br /><br />
                <input type = "submit" value = " Submit "/><br />
            </form>

            <div style = "font-size:11px; color: red; margin-top:10px">
                    <?php
                        echo $error;
                    ?>
            </div>

        </div>

    </div>

</div>


</html>