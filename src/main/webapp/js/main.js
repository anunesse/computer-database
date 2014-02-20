var ROSIP = "108";

$( "#action-container" ).hide();

var startRoomName = "";
var endRoomName = "";

// Connecting to ROS
// --------------------------

	var ros = new ROSLIB.Ros();

	// If there is an error on the backend, an 'error' emit will be emitted.
	ros.on('error', function(error) {
		divLog(error);
		alert("Error");
	});

	// Find out exactly when we made a connection.
	ros.on('connection', function() {
		divLog('Connection made!');

		$( "#action-container" ).show();
		$( "#connect" ).hide();
		$( "#intro" ).text("Connected ! You can now choose the route you want to give to the turtlebot.");
		getStatus();
	});



// Topics
// ---------------------------

	// Topic listener
		var listenerTopic = new ROSLIB.Topic({
			ros : ros,
			name : '/listener',
			messageType : 'std_msgs/String'
		});

		listenerTopic.subscribe(function(message) {
			divLog("[Topic " + listenerTopic.name + "] : " + message.data);
		});

	// Topic status
		var statusTopic = new ROSLIB.Topic({
			ros : ros,
			name : '/status',
			messageType : 'std_msgs/String'
		});

		statusTopic.subscribe(function(message) {
			divLog("[Topic " + statusTopic.name + "] : " + message.data);

			handleStatus(message.data);

		});

		function getStatus() {
			callStatusService();
		}

		function handleStatus(status) {

			divLog("Handling status : " + status);

			if (status == 6 || status == 7) {

				// Status sent by user
				// -> do nothing

			} else {

				state = (Number(status) / 5) * 100;
				state += "%";
				$('.progress-bar').animate({ width: state}, 1000);
				var txt = Number(status) + 1;
				document.getElementById("delivery-step").innerHTML = txt;

				statusDivId = "status" + status;
				document.getElementById("placeholder").innerHTML = document.getElementById(statusDivId).innerHTML;
			}

		}

	// Topic emergency
		var emergencyTopic = new ROSLIB.Topic({
			ros : ros,
			name : '/stop',
			messageType : 'std_msgs/String'
		});

		emergencyTopic.subscribe(function(message) {
			divLog("[Topic " + emergencyTopic.name + "]");
		});

		function emergency(){
			emergencyTopic.publish(new ROSLIB.Message({data : "Emergency stop from client !"}));
			divLog("Emergency stop sent.");
		}

	// Topic move
		var moveTopic = new ROSLIB.Topic({
			ros : ros,
			name : '/mobile_base/commands/velocity',
			messageType : 'geometry_msgs/Twist'
		});

		moveTopic.subscribe(function(message) {
		});

		function move(x,y) {
			var robotFwd = y;
			var robotTurn = x;
			var mess = new ROSLIB.Message({
					linear : {
						x : robotFwd,
						y : 0,
						z : 0
					},
					angular : {
						x : 0,
						y : 0,
						z : robotTurn
					}
				});
			moveTopic.publish(mess);
		}

	// Topic postions
		var positionsTopic = new ROSLIB.Topic({
			ros : ros,
			name : '/positions',
			messageType : 'turtle_delivery/position'
		});

		positionsTopic.subscribe(function(message) {
			//divLog("[Topic " + positionsTopic.name + "]");
			processMapInfo(message.tx, message.ty, message.ox, message.oy, message.cx, message.cy);
		});



// Services / ServiceRequests
// ---------------------------

	var commandSrvClient = new ROSLIB.Service({
		ros : ros,
		name : '/command',
		serviceType : 'turtle_delivery/command_srv'
	});

	var statusSrvClient = new ROSLIB.Service({
		ros : ros,
		name : '/status',
		serviceType : 'turtle_delivery/status_srv'
	});



// Calling a service
// --------------------------

	// Calling test service
	function callCommandService(rooms){
		divLog("Calling service " + commandSrvClient.name + " with rooms : " + rooms.start + " - " + rooms.end);
		// Call service
		commandSrvClient.callService(rooms, function(result) {
			divLog("[Answer from service " + commandSrvClient.name + "] : " + result.response);
			if (result.response == "OK") {
				
				startRoomName = "";
				endRoomName = "";

				divLog("Clear. Ready for another round.");
			} else {
				
				startRoomName = "";
				endRoomName = "";

				divLog("Something bad happened... Don't know what, but it looks bad...");
			}
		});	
	}

	// Calling status service
	function callStatusService(){
		divLog("Calling service " + statusSrvClient.name + " for robot status...");
		statusRequest = new ROSLIB.ServiceRequest({ request : "Status asked by client" });
		// Call service
		statusSrvClient.callService(statusRequest, function(result) {
			divLog("[Answer from service " + commandSrvClient.name + "] : " + result.response);
			handleStatus(result.response);
		});	
	}



// Document Ready
// ---------------------------

	$( document ).ready(function() {

		$( "#connect" ).click(function( event ) {

			address = 'ws://192.168.1.' + ROSIP + ':9090';
			divLog("Connecting to ROS at : " + address);

			// Create a connection to the rosbridge WebSocket server.
			ros.connect(address);
		 
		});
	 
	});



// Logging
// --------------------------

	function divLog(aString){
		document.getElementById('output').innerHTML += aString + "\n";
		scrollToBottom();
	}

	function divLog2(aString){
		document.getElementById('output').innerHTML = aString + "\n";
		scrollToBottom();
	}

	function scrollToBottom(){
		var output = $('#output');
	    output.scrollTop( output[0].scrollHeight - output.height() );
	}



// Route
// --------------------------

	function buttonSelected(clicked_id) {

		divLog("Start room : " + startRoomName + " | End room : " + endRoomName);

		if (startRoomName == "") {

			// Highlight start button
			$("#" + clicked_id).toggleClass("btn-primary");
			$("#" + clicked_id).toggleClass("btn-info");

			// Store start
			startRoomName = clicked_id.split("_").pop();
			divLog("Start room : " + startRoomName);

			// Disable start button
			$("#" + clicked_id).addClass("disabled");
		}

		else {

			// Highlight end button
			$("#" + clicked_id).toggleClass("btn-primary");
			$("#" + clicked_id).toggleClass("btn-success");

			// Disable all buttons
			var rooms = document.getElementById('rooms');
			var buttons = rooms.getElementsByTagName('a');
			for (var i = 0; i < buttons.length; i += 1) {
				$("#" + buttons[i].id).addClass("disabled");
			}

			// Store end
			endRoomName = clicked_id.split("_").pop();
			divLog("End room : " + endRoomName);

			// Request
			requestRoom = new ROSLIB.ServiceRequest({
				start : startRoomName,
				end : endRoomName
			});

			callCommandService(requestRoom);
		}

	}

	function objectPlaced(){
		statusTopic.publish(new ROSLIB.Message({data : "6"}));
		divLog("Sent on topic : object placed");
	}

	function objectPickedUp(){
		statusTopic.publish(new ROSLIB.Message({data : "7"}));
		divLog("Sent on topic : object picked up");
	}



// Map
// --------------------------

var mapCanvas,
mapCanvasContext,
mapContainer;

var mapImg = new Image();
mapImg.src = "map.png";

var turtleImg = new Image();
turtleImg.src = "turtle_blue_inverted_mini.png";

var objectImg = new Image();
objectImg.src = "object.png";

var targetImg = new Image();
targetImg.src = "target.png";

var AImg = new Image();
AImg.src = "A.png";

var BImg = new Image();
BImg.src = "B.png";

var CImg = new Image();
CImg.src = "C.png";

var HomeImg = new Image();
HomeImg.src = "home.png";

var AX, AY, BX, BY, CX, CY, HomeX, HomeY;

document.addEventListener("DOMContentLoaded", initMap);

window.onorientationchange = resetMapCanvas;
window.onresize = resetMapCanvas;

function initMap() {
    setupMapCanvas();

    AX = processX(2);
	AY = processY(1);

	BX = processX(4);
	BY = processY(2);

	CX = processX(3);
	CY = processY(0);

	HomeX = processX(0);
	HomeY = processY(1);
}

function setupMapCanvas() {
    mapCanvas = document.getElementById('canvasSurfaceMap');
    mapCanvasContext = mapCanvas.getContext('2d');
    resetMapCanvas();
    mapCanvasContext.strokeStyle = "#ffffff";
    mapCanvasContext.lineWidth = 2;
}

function resetMapCanvas() {
    // resize the canvas - but remember - this clears the canvas too.
	mapCanvas.width = 238;
    mapCanvas.height = 126;
}

var turtleMapWidth = 11;
var turtleMapHeight = 5.5;
var turtleMapXOffset = 1;
var turtleMapYOffset = 3.5;

var turtleX = 0;
var turtleY = 0;

var objectX = 0;
var objectY = 0;

var targetX = 0;
var targetY = 0;

function drawTurtle() {

	mapCanvasContext.clearRect(0,0,mapCanvasContext.canvas.width,mapCanvasContext.canvas.height);
	mapCanvasContext.drawImage(mapImg, 0, 0);
	mapCanvasContext.drawImage(AImg, AX, AY);
	mapCanvasContext.drawImage(BImg, BX, BY);
	mapCanvasContext.drawImage(CImg, CX, CY);
	mapCanvasContext.drawImage(HomeImg, HomeX, HomeY);
	mapCanvasContext.drawImage(objectImg, objectX, objectY);
	mapCanvasContext.drawImage(targetImg, targetX, targetY);
	mapCanvasContext.drawImage(turtleImg, turtleX, turtleY);
}

function processMapInfo(tx, ty, ox, oy, cx, cy) {

	turtleX = processX(tx);
	turtleY = processY(ty);

	objectX = processX(ox);
	objectY = processY(oy);

	targetX = processX(cx);
	targetY = processY(cy);

	drawTurtle();
}

function processX (x) {

	return toInt((x + turtleMapXOffset) * (mapCanvas.width / turtleMapWidth));
}

function processY (y) {

	return toInt(-(y - turtleMapYOffset) * (mapCanvas.height / turtleMapHeight));
}

function toInt (value) {
	return ~~value;
}


// Joystick
// --------------------------

// shim layer with setTimeout fallback
window.requestAnimFrame = (function () {
    return window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.oRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    function (callback) {
        window.setTimeout(callback, 1000 / 60);
    };
})();

var canvas,
c, // c is the canvas' context 2D
container,
halfWidth,
halfHeight,
leftPointerID = -1,
leftPointerPos = new Vector2(0, 0),
leftPointerStartPos = new Vector2(0, 0),
leftVector = new Vector2(0, 0);

var touches; // collections of pointers

document.addEventListener("DOMContentLoaded", init);

window.onorientationchange = resetCanvas;
window.onresize = resetCanvas;

function init() {
    setupCanvas();
    touches = new Collection();
    canvas.addEventListener('pointerdown', onPointerDown, false);
    canvas.addEventListener('pointermove', onPointerMove, false);
    canvas.addEventListener('pointerup', onPointerUp, false);
    canvas.addEventListener('pointerout', onPointerUp, false);
    requestAnimFrame(draw);
    //canvas.style.backgroundImage = 'url(http://192.168.1.102:8080/stream?topic=/camera/rgb/image_raw)';
}

function setupCanvas() {
    canvas = document.getElementById('canvasSurfaceGame');
    c = canvas.getContext('2d');
    resetCanvas();
    c.strokeStyle = "#ffffff";
    c.lineWidth = 2;
}

function resetCanvas(e) {
    // resize the canvas - but remember - this clears the canvas too.
	canvas.width = 250;
    canvas.height = 200;

    halfWidth = canvas.width / 2;
    halfHeight = canvas.height / 2;
}

function draw() {
    c.clearRect(0, 0, canvas.width, canvas.height);

    touches.forEach(function (touch) {
        if (touch.identifier == leftPointerID) {
        	var rect = canvas.getBoundingClientRect();
            c.beginPath();
            c.strokeStyle = "#29B18A";
            c.lineWidth = 6;
            c.arc(leftPointerStartPos.x - rect.left, leftPointerStartPos.y - rect.top, 40, 0, Math.PI * 2, true);
            c.stroke();
            c.beginPath();
            c.strokeStyle = "#29B18A";
            c.lineWidth = 2;
            c.arc(leftPointerStartPos.x - rect.left, leftPointerStartPos.y - rect.top, 60, 0, Math.PI * 2, true);
            c.stroke();
            c.beginPath();
            c.strokeStyle = "#29B18A";
            c.arc(leftPointerPos.x - rect.left, leftPointerPos.y - rect.top, 40, 0, Math.PI * 2, true);
            c.stroke();

        }
    });

    requestAnimFrame(draw);
}

function givePointerType(event) {
    switch (event.pointerType) {
        case event.POINTER_TYPE_MOUSE:
            return "MOUSE";
            break;
        case event.POINTER_TYPE_PEN:
            return "PEN";
            break;
        case event.POINTER_TYPE_TOUCH:
            return "TOUCH";
            break;
    }
}

function onPointerDown(e) {
    var newPointer = { identifier: e.pointerId, x: e.clientX, y: e.clientY, type: givePointerType(e) };
    if ((leftPointerID < 0)) {
        leftPointerID = e.pointerId;
        leftPointerStartPos.reset(e.clientX, e.clientY);
        leftPointerPos.copyFrom(leftPointerStartPos);
        leftVector.reset(0, 0);
    }
    touches.add(e.pointerId, newPointer);
}

function onPointerMove(e) {
    if (leftPointerID == e.pointerId) {
        leftPointerPos.reset(e.clientX, e.clientY);
        leftVector.copyFrom(leftPointerPos);
        leftVector.minusEq(leftPointerStartPos);

        tempY = -leftVector.y / canvas.height;
        tempX = - (leftVector.x / canvas.width) * 4;
        move(tempX,tempY);
        //divLog2(tempX + " | " + tempY);
    }
    else {
        if (touches.item(e.pointerId)) {
            touches.item(e.pointerId).x = e.clientX;
            touches.item(e.pointerId).y = e.clientY;
        }
    }
}

function onPointerUp(e) {
    if (leftPointerID == e.pointerId) {
        leftPointerID = -1;
        leftVector.reset(0, 0);

    }
    leftVector.reset(0, 0);

    move(0,0);

    touches.remove(e.pointerId);
}
