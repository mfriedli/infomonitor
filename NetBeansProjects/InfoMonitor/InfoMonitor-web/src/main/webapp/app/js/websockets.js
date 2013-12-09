var websocket = null;

function getRootUri() {
    return "ws://" + (document.location.hostname === "" ? "localhost" : document.location.hostname) + ":" +
            (document.location.port === "" ? "8080" : document.location.port);
}

function connect() {
    var wsUri = getRootUri() + "/InfoMonitor-web/infoendpoint";
    websocket = new WebSocket(wsUri);

    websocket.onmessage = function(evt) {
        onMessage(evt);
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };
}

function disconnect () {
    websocket.close();
}
function onMessage(evt) {
    writeToScreen(evt.data);
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend(message) {
    writeToScreen("SENT: " + message);
    websocket.send(message);
}

function writeToScreen(message) {
   var lockerrooms = JSON.parse(message); 
  
   var createdHtmlTable = ConvertJsonToTable(lockerrooms, 'lockerroomtable', 'lockerroomtableclass', null);
   var lockerroomsDiv = document.getElementById("lockerrooms");
   lockerroomsDiv.innerHTML = createdHtmlTable;
 
}






