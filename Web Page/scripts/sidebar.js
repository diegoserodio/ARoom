var counter = document.getElementById('counter').getContext('2d');
	var no = 0; // Starting Point
    var percentage = 100;
    var pointToFill = 4.72;  //Point from where you want to fill the circle
    var cw = counter.canvas.width;  //Return canvas width
    var ch = counter.canvas.height; //Return canvas height
    var diff;   // find the different between current value (no) and trageted value (100)
    var progressBarValue = document.getElementById('progressbarvalue');
    var coolerStatus = document.getElementById('coolerstatus');
    var ledColorDisplay = document.getElementById('ledcolordisplay');
    var circleColor = '#fff';

	var coolerStatusValue = "...";
	var fill;

	function updateStatusSideBar(){
		var percentageValue = newHTML.substring(newHTML.indexOf("tempValue")+44, newHTML.indexOf("tempValue")+47);
		percentage = mapValue(parseInt(percentageValue));

		var redValue = newHTML.substring(newHTML.indexOf("rgbValue")+29, newHTML.indexOf("rgbValue")+32);
		var greenValue = newHTML.substring(newHTML.indexOf("rgbValue")+33, newHTML.indexOf("rgbValue")+36);
		var blueValue = newHTML.substring(newHTML.indexOf("rgbValue")+37, newHTML.indexOf("rgbValue")+40);
        redValue = ((redValue-100)*2);
        greenValue = ((greenValue-100)*2);
        blueValue = ((blueValue-100)*2);

		if(newHTML.includes("Cooler: 1")){
			coolerStatusValue = "Ligado";
		}
		else if(newHTML.includes("Cooler: 0")){
			coolerStatusValue = "Desligado";
		}
		if(percentage >= 50 && percentage < 80){
        	circleColor = '#FFBF00';
        }
        else if(percentage >= 80){
        	circleColor = '#DF3A01';
        }else{
        	circleColor = '#fff';
        }

        if(newHTML.includes("Fita - Desligado")){
            ledColorDisplay.style.backgroundColor = 'rgb('+64+','+64+','+64+')';
        }
        else if(newHTML.includes("Fita - Ligado")){
            ledColorDisplay.style.backgroundColor = 'rgb('+redValue+','+greenValue+','+blueValue+')';
        }
        coolerStatus.innerHTML = "<h4>"+coolerStatusValue+"</h4>";
        if(!document.hidden){
            no = 0;
            fill = setInterval(fillCounter,1);
        }
	}

    function fillCounter(){
        diff = ((no/100) * Math.PI*2*10);
        counter.clearRect(0,0,cw,ch);   // Clear canvas every time when function is call
        counter.lineWidth = 5;     // size of stroke
        counter.fillStyle = circleColor;     // color that you want to fill in counter/circle
        counter.strokeStyle = circleColor;    // Stroke Color
        counter.textAlign = 'center';
        counter.font = "35px monospace";    //set font size and face
        counter.fillText(no+'%',100,110);       //fillText(text,x,y);
        counter.beginPath();
        counter.arc(100,100,90,pointToFill,diff/10+pointToFill);    //arc(x,y,radius,start,stop)
        counter.stroke();   // to fill stroke
        progressBarValue.innerHTML = "<h4>"+percentage.toString()+"%"+"</h4>";
        // now add condition
        if(no > 100){
            window.location.reload(false);
        }
        else if(no >= percentage){
            clearTimeout(fill);     //fill is a variable that call the function fillcounter()
        }
        no++;
    }

    function mapValue(value){
        var mappedValue = ((value-100)*100)/500;
        if(mappedValue > 100){
            mappedValue = 100;
        }
    	return mappedValue;
    }
