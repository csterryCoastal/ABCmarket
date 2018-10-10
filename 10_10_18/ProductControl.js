

//alert("HI$
$(document).ready(function()
{
	alert("Ready");
document.getElementById("sSales").onclick = function(){alert("9");


    alert("ERROR");
        $.ajax({url: "localhost:8080/webserv1/resources/490/FUNCTION.js",type:"GET", dataType: "script", 
		success:function(result)
		{
			alert("success");
		$("#p_setSales").html(result);
		},
		error: function(xhr)
		{
			$("p_setSales").text("Error");
		}
		
		
		});



}
});
//Use the document.getElementbyId(



