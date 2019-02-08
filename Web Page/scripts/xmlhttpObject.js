var urlPrefix = "http://192.168.15.14:8090/";
var newHTML = "";
var oldHtml = "";

	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	function updateData(){
	    xmlhttp.onreadystatechange=function(){
	        if (xmlhttp.readyState==4 && xmlhttp.status==200){
	            newHTML = xmlhttp.responseText;
	            if(oldHtml == ""){
	                oldHtml = newHTML;
	                updateStatusSideBar();
	                updateStatusMainPage();
	            }
	            else if(newHTML != oldHtml && oldHtml != ""){
	                oldHtml = newHTML;
	                updateStatusSideBar();
	                updateStatusMainPage();
	            }
	        }
	    };
	    xmlhttp.open("GET", urlPrefix, true);
	    xmlhttp.send();  
	}

	//now call the function
    if(!document.hidden){
        var handler = setInterval(updateData, 1000);
    }