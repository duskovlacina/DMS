send_url="../sendFile"

$(document).on('submit','.form-send', function(e) {
	e.preventDefault();
	//var email = $("#inputEmail").val();
	//var sub = $("#inputSub").val();
	//console.log(email + "    " sub);
	$.ajax({
		type : 'POST',
		url : send_url+"/"+sessionStorage.getItem('selectedfileID'),
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			console.log("usao u seend");
			
			location.href="sendFile.html"
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown+"send error");
		}
	});
});


function formToJSON() {
	return JSON.stringify({
    "email":$('#inputEmail').val(),
    "subject":$('#inputSub').val()
	});
}