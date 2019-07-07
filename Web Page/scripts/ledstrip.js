var	redLabel = document.getElementById('redLabel');
var	greenLabel = document.getElementById('greenLabel');
var	blueLabel = document.getElementById('blueLabel');
var redSlider = document.getElementById('redSlider');
var greenSlider = document.getElementById('greenSlider');
var blueSlider = document.getElementById('blueSlider');
var imgStatus = document.getElementById('ledStatus');
var c_command = "";
var imgCounter = 0;
var powerStatus;
var animStatus;
var speakerStatus;

redSlider.oninput = function(){
	redLabel.innerHTML = "<h5>Vermelho: "+this.value+"</h5>";
}
greenSlider.oninput = function(){
	greenLabel.innerHTML = "<h5>Verde: "+this.value+"</h5>";
}
blueSlider.oninput = function(){
	blueLabel.innerHTML = "<h5>Azul: "+this.value+"</h5>";
}

redSlider.onchange = function(){
	var c_command = "red:"+((this.value/2)+100);
	executeCommand(String(c_command));
}
greenSlider.onchange = function(){
	var c_command = "green:"+((this.value/2)+100);
	executeCommand(String(c_command));
}
blueSlider.onchange = function(){
	var c_command = "blue:"+((this.value/2)+100);
	executeCommand(String(c_command));
}

function updateStatusMainPage(){
	var redValue = parseFloat(newHTML.substring(newHTML.indexOf("rgbValue")+29, newHTML.indexOf("rgbValue")+32));
	redValue = ((redValue-100)*2);
	document.getElementById('redLabel').innerHTML = "<h5>Vermelho: "+redValue+"</h5>";
	document.getElementById('redSlider').value = redValue;

	var greenValue = parseFloat(newHTML.substring(newHTML.indexOf("rgbValue")+33, newHTML.indexOf("rgbValue")+36));
	greenValue = ((greenValue-100)*2);
	document.getElementById('greenLabel').innerHTML = "<h5>Verde: "+greenValue+"</h5>";
	document.getElementById('greenSlider').value = greenValue;

	var blueValue = parseFloat(newHTML.substring(newHTML.indexOf("rgbValue")+37, newHTML.indexOf("rgbValue")+40));
	blueValue = ((blueValue-100)*2);
	document.getElementById('blueLabel').innerHTML = "<h5>Azul: "+blueValue+"</h5>";
	document.getElementById('blueSlider').value = blueValue;

	if(newHTML.includes("Fita - Ligado")){
		document.getElementById('colorDisplay').style.backgroundColor = 'rgb('+redValue+','+greenValue+','+blueValue+')';
		powerStatus = true;
	}
	else{
		document.getElementById('colorDisplay').style.backgroundColor = 'rgb('+64+','+64+','+64+')';
		powerStatus = false;
	}

	if(newHTML.includes("Efeito 1 - Ligado") || newHTML.includes("Efeito 2 - Ligado") || newHTML.includes("Efeito 3 - Ligado")){
		animStatus = true;
	}else{
		animStatus = false;
	}

	if(newHTML.includes("Music - Ligado")){
		speakerStatus = true;
	}else{
		speakerStatus = false;
	}
	changeImg();

}

function executeCommand(comand){
	xmlhttp.open("GET", urlPrefix+comand, true);
    xmlhttp.send();
}

function imgPlus(){
	imgCounter = imgCounter + 1;
	if(imgCounter > 2){
		imgCounter = 2;
	}
	changeImg();
}

function imgMinus(){
	imgCounter = imgCounter - 1;
	if(imgCounter < 0){
		imgCounter = 0;
	}
	changeImg();
}

function changeImg(){
	switch(imgCounter){
		case 0:
			if(powerStatus == true){
				imgStatus.src = "resources/powerOn.png";
				imgStatus.style.filter = "invert(0)";
			}else{
				imgStatus.src = "resources/powerOff.png";
				imgStatus.style.filter = "invert(80%)";
			}
		break;
		case 1:
			if(animStatus == true){
				imgStatus.src = "resources/animation.png";
				imgStatus.style.filter = "invert(0)";
			}else{
				imgStatus.src = "resources/animation.png";
				imgStatus.style.filter = "invert(80%)";
			}
		break;
		case 2:
			if(speakerStatus == true){
				imgStatus.src = "resources/speakerOn.png";
				imgStatus.style.filter = "invert(0)";
			}else{
				imgStatus.src = "resources/speakerOff.png";
				imgStatus.style.filter = "invert(80%)";
			}
		break;
		default:
		break;
	}
}

function sendLedCommand(){
	switch(imgCounter){
		case 0:
			executeCommand("strip");
		break;
		case 1:
		break;
		case 2:
			executeCommand("music");
		break;
		default:
		break;
	}
}
