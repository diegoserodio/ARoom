var tempPercentage = document.getElementById('tempValue');
	musicPercentage = document.getElementById('musicValue');

function updateStatusMainPage(){
	var tempValue = newHTML.substring(newHTML.indexOf("tempValue")+44, newHTML.indexOf("tempValue")+47);
	tempValue = getPercentage(parseInt(tempValue), "temp");

	var musicValue = newHTML.substring(newHTML.indexOf("musicValue")+33, newHTML.indexOf("musicValue")+36);
	musicValue = getPercentage(parseInt(musicValue), "music");
	musicValue = Math.round(musicValue);

	tempPercentage.style.width = tempValue+"%";
	tempPercentage.style.backgroundColor = getColor(tempValue);
	tempPercentage.innerHTML = "<h5>"+tempValue+"%"+"</h5>";

	musicPercentage.style.width = musicValue+"%";
	musicPercentage.style.backgroundColor = getColor(musicValue);
	musicPercentage.innerHTML = "<h5>"+musicValue+"%"+"</h5>";

}

function getPercentage(data, sensor){
	var mappedValue;
	if(sensor == "temp"){
		mappedValue = ((data-100)*100)/500;
	}
	else if(sensor == "music"){
		mappedValue = ((data-100)*100)/60;
	}
	if(mappedValue > 100){
	        mappedValue = 100;
	}
    return mappedValue;
}

function getColor(percentage){
	var hexColor;
	if(percentage >= 0 && percentage < 50){
		hexColor = "rgb("+46+","+254+","+46+")";
	}
	else if(percentage >= 50 && percentage < 80){
		hexColor = "rgb("+255+","+191+","+0+")";
	}
	else if(percentage >= 80){
		hexColor = "rgb("+180+","+4+","+4+")";
	}
	return hexColor;
}

