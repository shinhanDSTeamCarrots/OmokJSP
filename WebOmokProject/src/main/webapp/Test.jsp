<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="SocketControll.js"></script>
<script>
    var isWait = false;
    class Param{
        roomId;
        myId;
        omokcallback;
        chatCallback;
        imageCallback;
        systemCallback;
        joinCallback;
    }
    window.onload = function(){
        var param = new Param();
        let test = document.getElementById("roomid");
        param.roomId = test.value.trim();
        param.myId = document.getElementById("myid").value.trim();
        //오목 콜백 함수. 오목 착수한 위치를 보낸다
        param.omokcallback = function(stone, row, col){
            console.log("오목 콜백 함수");
            console.log("{상대이름} "+"가 "+row+","+col+"에 착수하였습니다.");
            isWait = false;
        };
        //채팅 콜백 함수. 채팅 내용 보냄
        param.chatCallback = function(content){
            console.log("채팅 콜백 함수");
            console.log("상대: "+content);
        };
        //상대가 이미지 보내고 콜백. 이미지 src 를 보낸다
        param.imageCallback = function(imgurl){
            console.log("이미지 콜백 함수");
        };
        //시스템 콜백 함수
        //이거 분리할 예정
        param.systemCallback = function(content){
            console.log("시스템 콜백 함수");
        };
        RoomStart(param);
    }


    //연습용 함수들 ㅋㅋ
    function Chacksu(row,col,callBack){
        if(isWait == true){
            alert("상대가 착수할 때 까지 기다려 주십시오");
            return;
        }
        console.log("당신은 "+row+","+col+"에 착수하셨습니다.");
        omokPlay("B",row,col);
        isWait = true;
        
    }
    function ImageSend(number){
        sendImage(number);
    }
    function Chat(string){
        sendChat(string);
    }
</script>

<style>
    .chatWindow{
        /*display: none;
        left: 50%;
        top:50%;
        width: 20%;
        margin-left: -10%;
        height: 300px;
        margin-top: -200px;
        z-index: 1000;
        box-shadow: 0 0 6px 1px rgb(0 0 0 / 30%);*/
    }
</style>
</head>
<body>
    테스트용임
    <input type="hidden" id="myid" value="${ param.playerId }"/>
    <input type="hidden" id="roomid" value="${ param.roomId }"/>
    <input type="hidden" value="opponent" id="opponentid"/>
    <div class = "chatWindow" id="chatWindow" >
    </div>
    
</body>
</html>