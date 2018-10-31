var count=1;

document.getElementById("coupon").onclick = function(){
	alert("A");
	//$("#table").html("");
	
};
document.getElementById("1").onclick = function(){alert("1");};
document.getElementById("2").onclick = function(){alert("2");};
document.getElementById("3").onclick = function(){alert("3");};
document.getElementById("4").onclick = function(){alert("4");};
document.getElementById("5").onclick = function(){alert("5");};
document.getElementById("6").onclick = function(){alert("6");};
document.getElementById("7").onclick = function(){alert("7");};
document.getElementById("8").onclick = function(){alert("8");};
document.getElementById("9").onclick = function(){alert("9");};


document.getElementById("cancel").onclick = function()
{

for(var count1=1;count1<count;count1++)
{
var itemVal1= "receipt"+count1+"Item";
var quantval1= "receipt"+count1+"Quantity";
var priceVal1= "receipt"+count1+"Price";
//$("#receipt1Quantity").text("NewValue");
//$("#receipt2Price").text("NewValue");
$("#"+itemVal1+"").text(" ");
$("#"+quantval1+"").text(" ");
$("#"+priceVal1).text(" ");
//alert(count1);

}
count=0// reset the adding counter
//alert(count);

};
document.getElementById("2").onclick = function(){alert("2");};
document.getElementById("3").onclick = function(){alert("3");};
document.getElementById("4").onclick = function(){alert("4");};
document.getElementById("5").onclick = function(){alert("5");};
document.getElementById("6").onclick = function(){alert("6");};
document.getElementById("7").onclick = function(){alert("7");};
document.getElementById("8").onclick = function(){alert("8");};
document.getElementById("subtotal").onclick = function(){
	alert("Total");
	alert(count);
var itemVal= "receipt"+count+"Item";
var quantval= "receipt"+count+"Quantity";
var priceVal= "receipt"+count+"Price";
//$("#receipt1Quantity").text("NewValue");
//$("#receipt2Price").text("NewValue");
$("#"+itemVal+"").text("NewValue");
$("#"+quantval+"").text("NewValue");
$("#"+priceVal).text("NewValue");
count++;
if(count>21)
{count=1;
}

};