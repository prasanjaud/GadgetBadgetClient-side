//hide the alerts
$(document).ready(function(){ 
	if ($("#alertSuccess").text().trim() == "") { 
		$("#alertSuccess").hide(); 
	} 
	$("#alertDanger").hide(); }
);

//Form validation
function validationProductForm(){
	//code
	if($("#code").val().trim()==""){
		return "Insert the product code.";
	}
	
	//name
	if($("#name").val().trim() == ""){
		return "Insert the product name";
	}
	
	//price
	if($("#price").val().trim()==""){
		return "Insert a numeric value for product price";
	}
	
	//description
	if($("#description").val().trim() == ""){
		return "Insert the product description";
	}
	
	return true;
}

//Save
$(document).on("click", "#bntSave", function(event){
	//clear alerts
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertDanger").text("");
	$("#alertDanger").hide();
	
	//form validation
	var status = validationProductForm();
	if(status != true){
		$("#alertDanger").text(status);
		$("#alertDanger").show();
		return;	
	}
	
	//if valid
	var type=($("#hidID").val()=="") ? "post" : "put";
	$.ajax({
		url : "ProductApi",
		type : type,
		data : $("#formProduct").serialize(),
		dataType : "text",
		complete : function(response, status){
			onItemSaveComplete(response.responseText, status);
		}
		
	});	
});

function onProductSaveComplete(response, status){
	
	if(status == "success"){
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#colProduct").html(resultSet.data);
			
		}else if(resultSet.status.trim() == "error"){
			$("#alertDanger").text(resultSet.data)
			$("#alertDanger").show();
		}
	}else if(status == "error"){
		$("#alertDanger").text("Error while saving.");
		$("#alertDanger").show();
		
	}else{
		$("#alertDanger").text("Unknown error while saving.");
		$("#alertDanger").show();
	}
	$("#hidID").val("");
	$("#formProduct")[0].reset();
}

//update
$(document).on("click",".btnUpdate", function(event){
	$("#hidID").val($(this).data("productid"));
	$("#code").val($(this).closest("tr").find('td:eq(1)').text());
	$("#name").val($(this).closest("tr").find('td:eq(2)').text());
	$("#price").val($(this).closest("tr").find('td:eq(3)').text());
	$("#description").val($(this).closest("tr").find('td:eq(4)').text());
	
});

//Delete
$(document).on("click", ".btnRemove", function(event){
	$.ajax({
		url : "ProductApi",
		type : "DELETE",
		data : "id="+$(this).data("productid"),
		dataType : "text",
		complete : function(respnose, status){
			onItemDeleteComplete(respnose.responseText, status);
		}
			
	});
});

function onProductDeleteComplete(response, status){
	
	if(status == "success"){
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			
			$("#colProduct").html(resultSet.data);
		}else if(resultSet.status.trim() == "error"){
			$("#alertDanger").text(resultSet.data)
			$("#alertDanger").show();
		}
	}else if(status == "error"){
		$("#alertDanger").text("Error while deleting.");
		$("#alertDanger").show();
	}else{
		$("#alertDanger").text("Unknown error while deleting.");
		$("#alertDanger").show();
	}
	$("#formProduct")[0].reset();
}