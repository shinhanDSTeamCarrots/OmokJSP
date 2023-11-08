<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    var chatTextDiv;
    window.onload = function(){
        let param = {};
        let test = document.getElementById("roomid");
        param.roomId = test.value.trim();
        param.myId = document.getElementById("myid").value.trim();
        //오목 콜백 함수. 오목 착수한 위치를 보낸다
        param.omokcallback = function(stone, row, col){
            console.log("오목 콜백 함수");
            console.log("{상대이름} "+"가 "+row+","+col+"에 착수하였습니다.");



            
    	    let dom = document.getElementById("omok_"+row+"_"+col);
            dom.className="omokbox blackplace";
            dom.omokval = "2";
            isWait = false;
        };
        //채팅 콜백 함수. 채팅 내용 보냄
        param.chatCallback = function(content){
            console.log("채팅 콜백 함수");
            console.log("상대: "+content);
            //$('#chatText').append("<p class='otherChat'>"+content+"</p>");
            chatTextDiv.innerHTML += "<p class='otherChat'>"+content+"</p>";
        };
        //상대가 이미지 보내고 콜백. 이미지 src 를 보낸다
        param.imageCallback = function(imgurl){
            console.log("이미지 콜백 함수");
            chatTextDiv.innerHTML += "<img class='otherChatimg' src='"+content+"<'/>";
        };
        //시스템 콜백 함수
        //이거 분리할 예정
        param.systemCallback = function(content){
            console.log("시스템 콜백 함수");
        };
        
        RoomStart(param);
        chatTextDiv = document.getElementById("chatText");
    }


    //연습용 함수들 ㅋㅋ
    function Chacksu(row,col){
        if(isWait == true){
            alert("상대가 착수할 때 까지 기다려 주십시오");
            return;
        }
    	let dom = document.getElementById("omok_"+row+"_"+col);
        dom.className="omokbox whiteplace";
        dom.omokval = "1";
        console.log("당신은 "+row+","+col+"에 착수하셨습니다.");
        omokPlay("B",row,col);
        isWait = true;
        
    }
    function ImageSend(number){
        sendImage(number);
    }
    function Chat(string){
        $('#chatText').append("<p class='myChat'>"+string+"</p>");
        sendChat(string);
    }
    function onChatbtnclicked(){
        let txt = $('#chatInput').val().trim();
        if(txt == ""){
            return;
        }
        Chat(txt);
        $('#chatInput').val('');
        return;
    }
    function onKeyup(){
        if (window.event.keyCode == 13) { // 13 = Enter 키의 코드값
			onChatbtnclicked();
		}
    }
    function onPressBtn(i,j){
    	//i,j에 클릭함
    	//ajax로 자바에 전송 후 무결 결론 뜨면
    	//css 바꿈
    	Chacksu(i,j);
    }
    function onEmojiClick(){
        //이모지 팝업 실행
        //아무거나 다른 곳 누르면 팝업 해제
        let target;
    }
</script>
<style>
    content{
        width: 70%;
        height: 100%;
        
    }
    aside {
        width: 30%;
        height: 100%;
    }

    #chatText{
        width: 100%;
        height: 80%;
    }
    #underChat{
        width: 100%;
        height: 20%;
    }
    .myChat{
        text-align: right;
        background-color: #248bf5;
        color:#fff;
        width: fit-content;
        align-self: flex-end;
    }
    .myChat::after{
        background-color: #fff;
        border-bottom-left-radius: 0.5rem;
        right: -40px;
        transform: translate(-30px, -2px);
        width: 10px;
    }
    .myChat::before{
        border-bottom-left-radius: 0.8rem 0.7rem;
        border-right: 1rem solid #248bf5;
        right: -0.35rem;
        transform: translate(0, -0.1rem);
    }
    .otherChat{
        align-items: flex-start;
        text-align: left;
        color:black;
        background-color: #e5e5ea;
        width: fit-content;
    }
    .otherChat::after{
        background-color: #fff;
        border-bottom-right-radius: 0.5rem;
        left: 20px;
        transform: translate(-30px, -2px);
        width: 10px;
    }
    .otherChat::before{
        border-bottom-right-radius: 0.8rem 0.7rem;
        border-left: 1rem solid #e5e5ea;
        left: -0.35rem;
        transform: translate(0, -0.1rem);
    }
    .systemChat{
        text-align: center;
        background-color:midnightblue;
        color:#e5e5ea;
        width: fit-content;
    }
    .omokbox{
        width: 40px;
        height: 40px;
        box-sizing: border-box;
        background-color: orange;
    }
    .blackplace{
        background: radial-gradient(closest-corner at 30% 25%, rgb(119, 119, 119), rgb(34, 34, 34));
        border-radius: 50%;
        transform: scale(0.9);
    }
    .whiteplace{
        background: radial-gradient(closest-corner at 30% 25%, rgb(238, 238, 238), rgb(187, 187, 187));
        border-radius: 50%;
        transform: scale(0.9);
    }
    .noplace:hover {
    	/*내거 띄우기*/
    	background: radial-gradient(closest-corner at 30% 25%, rgb(238, 238, 238), rgb(187, 187, 187));
        border-radius: 50%;
        transform: scale(0.9);
    }
</style>
</head>
<body>
    <input type="hidden" id="myid" value="${ param.playerId }"/>
    <input type="hidden" id="roomid" value="${ param.roomId }"/>
    <input type="hidden" value="opponent" id="opponentid"/>
    <article id="content">
        <!--오목판-->
        <table>
            <c:forEach var="i" begin="1" end="19">
            <tr>
            	<c:forEach var="j" begin="1" end="19">
            	<td>
            		<div data-omokval = "0" onclick="onPressBtn(${i},${j})" id="omok_${i}_${j}" class="omokbox noplace">
                    </div>
            	</td>
            	</c:forEach>
            </tr>
            </c:forEach>
        </table>
    </article>
    <asdie id="sidebar">
        <div id="chatWindow">
            <div id="chatText">

            </div>
            <div id="emojiChatBox" class="emojiChatBox"></div>
            <div id="underChat">
                <input type="text" placeholder="채팅을 입력하세용" id="chatInput" class="chatInput" width="75%" onkeyup="onKeyup()"/>
                <input type="button" src="/img/testImage/emoji.png" width="5%" onclick="onEmojiClick()"/>
                <input type="button" value="전송" id="ChatBtn" class="chatBtn" width="20%" onclick="onChatbtnclicked()"/>
            </div>
        </div>
    </asdie>
    
</body>
</html>