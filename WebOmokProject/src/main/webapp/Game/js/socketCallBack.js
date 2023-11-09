/**
 * 
 */
 
 //room_id, member_no 불러오기
//member_no > request로 받기

var stonInfoCallBack = function(stone, row, col){
	var x = row;
	var y = col;
	
	console.log("착수 콜백 함수");
	sendYouInfo();
};
var sysCallback = function(type,content){
    console.log("시스템 콜백 함수");
    
    if(type == "TIME"){
    	alert("상대 접속이 끊겼습니다.");
    	sendMemStatus(type);
    	location.href="/WebOmokProject/room/listRoom.jsp"; //대기실로 이동
    }else if(type == "SURRENDER"){
    	alert("상대가 항복하였습니다.");
    	sendMemStatus(type);
    	location.href="/WebOmokProject/room/listRoom.jsp";
    }else if(type == "VICTORY"){
    	alert("상대가 승리하였습니다.");
    	location.href="/WebOmokProject/room/listRoom.jsp";
    }else if(type == "JOIN"){
    	alert("상대가 참가하였습니다.");
    	requUserInfo(content);
    }
};

//통신 콜백 함수
function callBack(){
   //제공할 매개변수들 값
   let param = {};
   
   //현재 방 NO
   param.roomNo = ${roomNo}
   //자기 NO
   param.myNo = ${myNo}
   
   /* 
    Def: 오목 콜백 함수. 오목 착수한 위치를 받는다
    Param:   
    	Stone: 상대가 착수한 돌. "B", "W" 로 전달
    	row: 상대가 착수한 돌의 행 위치
    	col: 상대가 착수한 돌 열 위치
   */
   param.omokCallback = stonInfoCallBack;
   
   /* 
	Def: 시스템 콜백 함수
    Param:   
    	  type: 상대가 보낸 시스템 메시지 타입
   */
   param.systemCallback = sysCallback;
   
   //함수 실행
   RoomStart(param);
}

//콜백 함수 호출!
callBack();
