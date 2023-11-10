/*돌 배치*/
var isPlay =false;
var mystone;
var oppstone;
function stone(x, y, color){
	let stoneDiv = document.createElement("div");
	stoneDiv.className = "stone";
	stoneDiv.style.top = y*28 + "px"; // Y 좌표에 따라 위치 설정
	stoneDiv.style.left = x*28 + "px"; // X 좌표에 따라 위치 설정
	
	//돌 색상 설정
	if(color === 1){ //data타입까지 비교를 위해 ===사용
		stoneDiv.style.background = `radial-gradient(circle at center,  #4e4e4e 0%, #000000 100%)`;	
	}else if(color === 2){
		stoneDiv.style.background = `radial-gradient(at 30% 20%,  #ffffff 0%, #cccccc 50%, #999999 100%)`;
	}
	
	// <div id="board"> 요소 내에 돌을 추가
    let boardDiv = document.getElementById("board");
    if (boardDiv) {
        boardDiv.appendChild(stoneDiv);
    }
}

/*보드 클릭 시 콘솔 로그에 좌표값 출력*/
function stoneInfo(e){
	if(isPlay){
		//좌표값 보정
		let x = Math.round(e.offsetX/28);
		let y = Math.round(e.offsetY/28);
		//좌표값 콘솔 출력
		console.log("X: "+x+", "+"Y: "+y);	
		sendMyInfo(x,y);
	}else{
		alert("상대의 턴 입니다 대기해 주세요!");
	}
	/*-----------돌 배치 테스트----------*/
	//stone(x, y, "white");
	
}

/*나가기 버튼 클릭 시, 대기방으로 이동*/
 var is;
 function exit(){
	is = confirm("정말 그만두시겠습니까?");
	
	if(is){
		location.href="/WebOmokProject/room/listRoom.jsp";
	}
}
