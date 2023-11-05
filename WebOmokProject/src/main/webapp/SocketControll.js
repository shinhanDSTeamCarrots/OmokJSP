var ip = "${pageContext.request.serverName}";
var port = "${pageContext.request.serverPort}";
var path = "${pageContext.request.contextPath}";
var addr = "ws://"+ip+":"+port+"/"+path+"/"+"ChatingServer";
addr = "ws://localhost:8901/WebOmokProject/OmokGameSocket";
//var webSocket = new WebSocket(addr);
var webSocket;
//var chatWindow, chatMessage, chatId;
// 채팅창이 열리면 대화창, 메시지 입력창, 아이디 표시란으로 사용할 DOM 객체 저장
// 윈도우가 로드되면 실행할 익명 함수
var roomId, opponentId, myId;
//방번호, 상대 번호, 내 번호

class CallBackFunc{
    omok;
    chat;
    image;
    system;
    join;
}
var callbackFunc = new CallBackFunc();

function sendMessage(type, content) {
    let msgJson = new Object();
    msgJson.roomId = roomId;
    msgJson.senderId = myId;
    msgJson.opponent = opponentId;
    msgJson.type = type;
    msgJson.content = content;
    webSocket.send(JSON.stringify(msgJson));
}


function RoomStart(param){
    console.log("init Start");
    roomId = param.roomId;
    myId = param.myId;
    opponentId = param.opponentId;
    console.log(typeof param.chatCallback);
    callbackFunc.omok = param.omokcallback;
    callbackFunc.chat = param.chatCallback;
    callbackFunc.image = param.imageCallback;
    callbackFunc.system = param.systemCallback;
    console.log(typeof callbackFunc.chat);
    addr = addr + "/"+roomId+"/"+myId;
    console.log("addr: "+addr);
    webSocket = new WebSocket(addr);
    webSocketMethodSet();
}

function omokPlay(stone, rowpos, colpos){
    let msg  = new Object();
    msg.stone = stone;
    msg.row = rowpos;
    msg.col = colpos;
    sendMessage("O",msg);
    console.log("오목 전송: "+msgJson);
}
function sendChat(str){
    sendMessage("C",str);
    //chatWindow.innerHTML += "<div class='myMsg'>" +"나: "+ str + "</div>";
}

function sendImage(imgnum){
    sendMessage("I",imgnum);
    //chatWindow.innerHTML += "<img src='"+"img/testImage/"+msgJson.content+".png'/>";
}
function sendSystem(system){
    sendMessage("S",system);
    //chatWindow.innerHTML += "<img src='"+"img/testImage/"+msgJson.content+".png'/>";
}

function disconnect() { // 함수명 수정
    webSocket.close();
}

function webSocketMethodSet(){
    // 웹소켓 서버에 연결되었을 때 실행
    webSocket.onopen = function(event) {
        //chatWindow.innerHTML += "웹소켓 서버에 연결되었습니다.<br>";
    };

    // 웹소켓이 닫혔을 때 실행
    webSocket.onclose = function(event) {
        //chatWindow.innerHTML += "웹소켓 서버가 종료되었습니다.<br>";
    }

    webSocket.onerror = function(event) {
        alert(event.data);
        //chatWindow.innerHTML += "채팅 중 에러가 발생하였습니다.<br>";
    }

    // 메시지를 받았을 때 실행
    webSocket.onmessage = function(event) {
        let jsontext = JSON.parse(event.data);
        console.log(jsontext);
        if(jsontext.roomId != roomId)
            return;
        
        if(jsontext.type == "O"){
            //오목데이터
            //상대가 착수한 오목임
            //해당 값을 받을 사람에게 보냄
            if(!callbackFunc.omok)
                callbackFunc.omok(jsontext.content.stone,jsontext.content.row,jsontext.content.col);
        }
        else if (jsontext.type == "S"){
            //시스템데이터
            //상대의 서렌, 소켓 타임아웃, 탈주, 승리 데이터, 접속 등...
            if(!callbackFunc.system)
                callbackFunc.system(opponentId,jsontext.content);
        }
        else if (jsontext.type == "C"){
            //확장형 채팅 데이터
            if(!callbackFunc.chat)
                callbackFunc.chat(jsontext.content);
            console.log(jsontext.content);
        }
        else if (jsontext.type == "I"){
            //확장 데이터 2
            if(!callbackFunc.image)
                callbackFunc.image("img/testImage/"+jsontext.content+".png");
        }
    
    }
}

