/*유저 정보 요청 및 좌표 전달 */

//유저 정보 요청
function requUserInfo(){
	$.ajax({
		type: "post",
		async: "false", //동기 방식으로 동작
		url:"/WebOmokProject/omok/requUserInfo.do", //전달할 주소
		dataType:"json", //전달할 데이터 타입
		data:{ //전달할 데이터
			my_id : myId,
			opp_id: opponentId
		},
		success: function(msg){ //유저id, 승리, 패배, 승률
			//유저 id 값 받아서 리턴
		},
		error:function(){
			alert("유저 정보 요청 실패");
		}
	})
}

//내가 둔 돌 좌표 전달
function sendMyInfo(x, y){
	$.ajax({
		type: "post",
		async: "false", 
		url:"/WebOmokProject/omok/sendMyInfo.do",
		dataType:"",
		data:{
			x_row: x,
			y_col: y,
			stone: mystone
		},
		success: function (success){
			
			var jsonInfo = JSON.parse(success);
			console.log(success);
			
			if(success){
				if(message == "T"){
					stone(x, y, mystone);
				}
				if(message == "W"){
					stone(x, y, mystone);
					alert("승리");
				}		
			} else if(success == "false"){
				if(message == "6"){
					alert("6목 이상입니다.");
				}
				if(message == "3"){
					alert("쌍삼입니다.");
				}
				if(message == "F"){
					alert("놓을 수 없는 자리 입니다");
				}
			}
		},
		error:function(){
			alert("Myinfo좌표 전달 실패");
		}
	})
}

//상대 둔 돌 좌표 전달 >>>서버 통신 받으면 바로 이미지 출력
function sendYouInfo(x,y){
	$.ajax({
		type: "post",
		async: "true", 
		url:"/WebOmokProject/omok/sendYouInfo.do",
		dataType:"stone", 
		data:{
			x_row: x,
			y_col: y,
			stone: oppstone
		},
		success: function(){
			isPlay = true;
		}
	})
}

//유저 상태 전달 > 패배, 상대 항복
function sendMemStatus(type){
	$.ajax({
		type: "post",
		async: "true",
		url:"/WebOmokProject/omok/sendMemStatus.do",
		dataType:"text",
		data:{
			type: type
		}
	})
}