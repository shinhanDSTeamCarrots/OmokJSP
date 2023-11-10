
var webSocket;
//var chatWindow, chatMessage, chatId;
// 채팅창이 열리면 대화창, 메시지 입력창, 아이디 표시란으로 사용할 DOM 객체 저장
// 윈도우가 로드되면 실행할 익명 함수
var roomId, opponentId, myId;
//방번호, 상대 번호, 내 번호

var callbackFunc = {};

function sendMessage(type, content) {
	let msgJson = new Object();
	console.log("roomid:"+roomId);
	msgJson.roomId = roomId;
	msgJson.senderId = myId;
	msgJson.opponent = opponentId;
	msgJson.type = type;
	console.log(JSON.stringify(msgJson));
	msgJson.content = content;
	console.log("메시지 송신");
	console.log(JSON.stringify(msgJson));
	webSocket.send(JSON.stringify(msgJson));
}


function RoomStart(param) {
	console.log("init Start");
	roomId = param.roomId;
	myId = param.myId;
	opponentId = param.opponentId;
	callbackFunc.omok = param.omokCallback;
	callbackFunc.chat = param.chatCallback;
	callbackFunc.image = param.imageCallback;
	callbackFunc.system = param.systemCallback;
	addr = param.addr;
	console.log("addr: " + addr);
	webSocket = new WebSocket(addr);
	webSocketMethodSet();
}

function omokPlay(rowpos, colpos) {
	let msg = new Object();
	msg.row = rowpos;
	msg.col = colpos;
	console.log("(오목 보내는 함수) x,y "+rowpos+","+colpos);
	sendMessage("O", msg);
	console.log("오목 전송: " + msg);
}
function sendChat(str) {
	sendMessage("C", str);
}

function sendImage(imgnum) {
	sendMessage("I", imgnum);
}
function sendSystem(system, content) {
	console.log("시스템 메시지 송신");
	if (!content) {
		console.log("시스템 content is no");
		sendMessage("S", system);
	} else {
		console.log("시스템 content is yes");
		let temp = new Object();
		temp.type = system;
		temp.content = content;
		sendMessage("S", temp);
	}
}

function sendJoinMessage() {
	console.log("그리팅!");
	sendSystem("JOIN", myId);
}
function welcomeMessage(){
	console.log("웰컴!");
	sendSystem("WELCOME",myId);
}
function sendSurrenderMessage() {
	sendSystem("SURRENDER");
}
function sendVictoryMessage() {
	sendSystem("VICTORY");
}
function sendTimeOutMessage() {
	sendSystem("TIME");
}


function disconnect() { // 함수명 수정
	webSocket.close();
}

function webSocketMethodSet() {
	// 웹소켓 서버에 연결되었을 때 실행
	console.log("웹소켓 접속 시작 \n 함수 세팅중");
	webSocket.onopen = function(event) {
		//접속 메시지 전송
		console.log("웹소켓 접속 성공. 그리팅 메시지 전송");
		sendJoinMessage();
	};

	// 웹소켓이 닫혔을 때 실행
	webSocket.onclose = function(event) {
	}

	webSocket.onerror = function(event) {
		alert(event.data);
	}

	// 메시지를 받았을 때 실행
	webSocket.onmessage = function(event) {
		console.log("메시지 수신");
		let jsontext = JSON.parse(event.data);
		console.log(jsontext);
		if (jsontext.roomId != roomId)
			return;

		if (jsontext.type == "O") {
			//오목데이터
			//상대가 착수한 오목임
			//해당 값을 받을 사람에게 보냄
			if (callbackFunc.omok)
				callbackFunc.omok( jsontext.content.row, jsontext.content.col);
		}
		else if (jsontext.type == "S") {
			//시스템데이터
			//상대의 서렌, 소켓 타임아웃, 탈주, 승리 데이터, 접속 등...
			//상대의 서렌: 
			if (!callbackFunc.system)
			return;
			if(!jsontext.content.content)
				callbackFunc.system(jsontext.content);
			else
				callbackFunc.system(jsontext.content.type, jsontext.content.content);

		}
		else if (jsontext.type == "C") {
			//확장형 채팅 데이터
			if (callbackFunc.chat) {
				callbackFunc.chat(jsontext.content);
			}
			console.log(jsontext.content);
		}
		else if (jsontext.type == "I") {
			//확장 데이터 2
			if (callbackFunc.image)
				callbackFunc.image("img/testImage/" + jsontext.content + ".png");
		}

	}
}

