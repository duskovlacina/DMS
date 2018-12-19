kw_url="../api/keywords/addKW"

$(document).on('submit','.form-kw', function(e) {
	e.preventDefault();
	$.ajax({
		type : 'POST',
		url : kw_url+"/"+sessionStorage.getItem('selectedfileID'),
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			console.log("usao u success");
			if(data != null){
			location.href="keyword.html"
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown+"add kw");
		}
	});
});


function formToJSON() {
	return JSON.stringify({
    "name":$('#inputKW').val()
	});
}

function loadKW() {
	$.ajax({
		type : 'GET',
		url : "../"+sessionStorage.getItem('selectedfileID')+"/kw",
		success : function(data) {
			for(let i=0;i<data.length;i++) {
				$("#keywordDiv").append(`<tr>
                        <td>
                        <span class="font-weight-bold">`+data[i].name+`</span>
                     </td>
                 </tr>`)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown+"add kw");
		}
	});
	
}