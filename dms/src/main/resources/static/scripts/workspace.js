var files_url = "../getFiles"
var delete_url = "../deleteFile"
var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

function getFiles(){
	$.ajax({
		 url: files_url,
		 method: "GET",
		 success: function(data){
			 $(".filesTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".filesTable").append(`<tr>
                               <td><span class="font-weight-bold">`+data[i].fileName+`</span></td>
                               <td><span class="font-weight-bold">`+data[i].date+`</span></td>
                               <td><button type="button" id=`+data[i].id+` class="btn btn-info btn-sm invbutton" data-toggle="modal" data-target="#exampleModalR">Keywords</button></td>           
                               <td><button type="button" id=`+data[i].id+` class="btn btn-success btn-sm dowbutton" data-toggle="modal" data-target="#exampleModalR">Open/Download</button></td>  	
                               <td><button type="button" id=`+data[i].id+` class="btn btn-warning btn-sm sendbutton" data-toggle="modal" data-target="#exampleModalR">Send</button></td>
                               <td><button onclick="deleteFile(this)" name=`+data[i].id+` type="button" class="btn btn-danger btn-sm" >Delete</button></td>
                           </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting files!");
		 }
	});
}

$(document).on('click','.sendbutton',function(e){
	var fileId = $(this).attr("id");
	//console.log("ID: " + fileId);
	sessionStorage.setItem('selectedfileID',fileId);
    window.open('sendFile.html','','height=350,width=350,left=550,top=90');
});


function deleteFile(obj){
	$(document).on("click",obj,function(e) {
		e.preventDefault();	
		$.ajax({
			method : 'GET',
			url : "../deleteFile/"+obj.name,
			success : function(data){
				console.log("success!");
				location.href = 'workspace.html';
			},
			error: function(){
				console.log("error");
			}
		});
		
	});
}

$(document).on('click','.invbutton',function(e){
	//var resId = $("#inputKW").attr("name");
	//var p = $('#inputKW').val();
	//console.log(p);
	var fileId = $(this).attr("id");
	//console.log("ID: " + fileId);
	sessionStorage.setItem('selectedfileID',fileId);
    window.open('keyword.html','','height=350,width=350,left=550,top=90');
});


$(document).on('click','.dowbutton',function(e){
	var fileId = $(this).attr("id");
	location.href = "http://localhost:8085/downloadFile/"+fileId;
});

$(document).on('click','#searchFiles',function(e){
	e.preventDefault();
	var name = $('#fileName').val();
	var keyword = $('#fileKW').val();
	if(name == ''){
		name = "nema";
	}
	if(keyword ==''){
		keyword="nema";
	}
	//console.log("ime: "+name+"kljuc: "+keyword);
	
	$.ajax({
		 url: "../searchFiles/"+name+"/"+keyword,
		 method: "GET",
		 success: function(data){
			 $(".filesTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".filesTable").append(`<tr>
                         <td><span class="font-weight-bold">`+data[i].fileName+`</span></td>
                         <td><span class="font-weight-bold">`+data[i].date+`</span></td>
                         <td><button type="button" id=`+data[i].id+` class="btn btn-info btn-sm invbutton" data-toggle="modal" data-target="#exampleModalR">Keywords</button></td>           
                         <td><button type="button" id=`+data[i].id+` class="btn btn-success btn-sm dowbutton" data-toggle="modal" data-target="#exampleModalR">Open/Download</button></td>  	
                         <td><button type="button" id=`+data[i].id+` class="btn btn-warning btn-sm sendbutton" data-toggle="modal" data-target="#exampleModalR">Send</button></td>
                         <td><button onclick="deleteFile(this)" name=`+data[i].id+` type="button" class="btn btn-danger btn-sm" >Delete</button></td>
                     </tr>`);
			 }
				 
		 },
		 error: function(){
			 alert("Error searching files");
		 }
	});
});

