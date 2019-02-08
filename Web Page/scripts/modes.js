function updateStatusMainPage(){
}

var btnAllOn = document.getElementById('btnAllOn');
var btnAllOff = document.getElementById('btnAllOff');
var btnNetflixOn = document.getElementById('btnNetflixOn');
var btnNetflixOff = document.getElementById('btnNetflixOff');
var btnPartyOn = document.getElementById('btnPartyOn');
var btnPartyOff = document.getElementById('btnPartyOff');
var btnSleepOn = document.getElementById('btnSleepOn');
var btnSleepOff = document.getElementById('btnSleepOff');

btnAllOn.addEventListener("click", executeCommand("tudoOn"));
btnAllOff.addEventListener("click", executeCommand("tudoOff"));
btnNetflixOn.addEventListener("click", executeCommand("netflixOn"));
btnNetflixOff.addEventListener("click", executeCommand("netflixOff"));
btnPartyOn.addEventListener("click", executeCommand("festaOn"));
btnPartyOff.addEventListener("click", executeCommand("festaOff"));
btnSleepOn.addEventListener("click", executeCommand("comaOn"));
btnSleepOff.addEventListener("click", executeCommand("comaOff"));

function executeCommand(comand){
    return function(){
    	xmlhttp.open("GET", urlPrefix+comand, true);
        xmlhttp.send();
    }
}