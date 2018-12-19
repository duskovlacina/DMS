var islogged_url = "../api/users/isLoggedIn"
var logout_url = "../api/users/logout"

function createCV(data) {
	var id = data.id;
	//alert(id);
	sessionStorage.setItem('createVenueType',id);
	window.location.href = "createVenue.html";
}	
	
function generateNavbar(){
	 $.ajax({
		 url: islogged_url,
		 method: "GET",
		 success: function(data){
			 var user = data;
			 if(user.email!=null){
				 $(".navitems").empty();
				 $(".navitems").append(`<li class="nav-item active">
	               
	                <a class="navbar-brand" href="workspace.html">Document Management System (DMS)</a>
	                <!--  <a class="nav-link" href="index.html">Home
	                  <span class="sr-only">(current)</span>
	                </a> -->
	              <li class="nav-item">
	                <a class="nav-link" href="index.html">Home</a>
	              </li>  
	              <!-- 
	              <li class="nav-item">
	                <a class="nav-link" href="upload.html">Upload</a>
	              </li>
	              -->
	              <li class="nav-item">
	                <a class="nav-link" href="uploadForm.html">Upload</a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="userpage.html">User `+user.name+` signed in</a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="index.html" onclick="logout()">Sign out</a>
	              </li>`);
			 }else{
			    	$(".navitems").empty();
			    	$(".navitems").append(`<li class="nav-item active">
			                <a class="nav-link" href="#">Home
			                  <span class="sr-only">(current)</span>
			                </a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="#">About</a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="register.html">Register</a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="signin.html">Sign in</a>
			              </li>`);
			 }
	        
	    },
	    error:function(data){
	    	
	    }
	 });
}

function logout(){
	$.ajax({
		 url: logout_url,
		 method: "GET",
		 success: function(){
			 sessionStorage.removeItem('loggedUser');
		 }
	});
}