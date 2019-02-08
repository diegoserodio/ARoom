function updateStatusMainPage(){
	var statusLamp = document.getElementById('lamp');
	var statusVent = document.getElementById('fan');
	var statusLights = document.getElementById('lights');
	var statusScreen = document.getElementById('screen');
	var imageLamp = document.getElementById('lightIcon');
	var imageVent = document.getElementById('fanIcon');
	var imageLights = document.getElementById('lightsIcon');
	var imageScreen = document.getElementById('screenIcon');

	if(newHTML.includes("Lampada - Ligado")){
		statusLamp.innerHTML = "<h5>Ligada</h5>";
		imageLamp.src = "resources/bulbOn.png";
		imageLamp.style.filter = "invert(0%)";
	}
	else if(newHTML.includes("Lampada - Desligado")){
		statusLamp.innerHTML = "<h5>Desligada</h5>";
		imageLamp.src = "resources/bulbOff.png";
		imageLamp.style.filter = "invert(70%)";
	}

	if(newHTML.includes("Ventilador - Ligado")){
		statusVent.innerHTML = "<h5>Ligado</h5>";
		imageVent.src = "resources/fanOn.png";
		imageVent.style.filter = "invert(0%)";
	}
	else if(newHTML.includes("Ventilador - Desligado")){
		statusVent.innerHTML = "<h5>Desligado</h5>";
		imageVent.src = "resources/fanOff.png";
		imageVent.style.filter = "invert(70%)";
	}

	if(newHTML.includes("Pisca - Ligado")){
		statusLights.innerHTML = "<h5>Ligado</h5>";
		imageLights.src = "resources/lightsOn.png";
		imageLights.style.filter = "invert(0%)";
	}
	else if(newHTML.includes("Pisca - Desligado")){
		statusLights.innerHTML = "<h5>Desligado</h5>";
		imageLights.src = "resources/lightsOff.png";
		imageLights.style.filter = "invert(70%)";
	}

	if(newHTML.includes("Tela - Ligado")){
		statusScreen.innerHTML = "<h5>Ligado</h5>";
		imageScreen.src = "resources/screenOn.png";
		imageScreen.style.filter = "invert(0%)";
	}
	else if(newHTML.includes("Tela - Desligado")){
		statusScreen.innerHTML = "<h5>Desligado</h5>";
		imageScreen.src = "resources/screenOff.png";
		imageScreen.style.filter = "invert(70%)";
	}
}

var btnLightOn = document.getElementById('btnLightOn');
var btnLIghtOff = document.getElementById('btnLightOff');
var btnFanOn = document.getElementById('btnFanOn');
var btnFanOff = document.getElementById('btnFanOff');
var btnLightsOn = document.getElementById('btnLightsOn');
var btnLightsOff = document.getElementById('btnLightsOff');
var btnScreenOn = document.getElementById('btnScreenOn');
var btnScreenOff = document.getElementById('btnScreenOff');

btnLightOn.addEventListener("click", executeCommand("luzOn"));
btnLightOff.addEventListener("click", executeCommand("luzOff"));
btnFanOn.addEventListener("click", executeCommand("ventOn"));
btnFanOff.addEventListener("click", executeCommand("ventOff"));
btnLightsOn.addEventListener("click", executeCommand("natalOn"));
btnLightsOff.addEventListener("click", executeCommand("natalOff"));
btnScreenOn.addEventListener("click", executeCommand("telaOn"));
btnScreenOff.addEventListener("click", executeCommand("telaOff"));

function executeCommand(comand){
    return function(){
    	xmlhttp.open("GET", urlPrefix+comand, true);
        xmlhttp.send();
    }
}