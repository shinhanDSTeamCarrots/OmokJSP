/**
 * 
 */
 
//room_id, member_no 불러오기
//member_no > request로 받기


var stonInfoCallBack = function( row, col){
	let x = row;
	let y = col;
	
	console.log("착수 콜백 함수");
	sendYouInfo(x,y);
    isPlay = true
	stone(x, y, oppstone);
};
var sysCallback = function(type,content){
    console.log("시스템 콜백 함수");
    
    if(type == "TIME"){
    	confirm("상대 접속이 끊겼습니다.");
    	sendMemStatus(type);
    	location.href="/WebOmokProject/room/listRoom.jsp"; //대기실로 이동
    }else if(type == "SURRENDER"){
    	confirm("상대가 항복하였습니다.");
    	sendMemStatus(type);
    	location.href="/WebOmokProject/room/listRoom.jsp";
    }else if(type == "VICTORY"){
    	confirm("상대가 승리하였습니다.");
    	location.href="/WebOmokProject/room/listRoom.jsp";
    }else if(type == "JOIN"){
    	alert("상대가 참가하였습니다.");
        //내 돌은 black 상대 돌은 white 나는 플레이 가능
        mystone = 1;
        oppstone = 2;
        isPlay = true;
        opponentId = content;
        welcomeMessage();
    	requUserInfo(content);
    }
    else if(type == "WELCOME"){
    	alert("맞은편의 상대가 인사했습니다!");
        //내 돌은 black 상대 돌은 white 나는 플레이 가능
        mystone = 2;
        oppstone = 1;
        isPlay = false;
        opponentId = content;
    	requUserInfo(content);
    }
};
