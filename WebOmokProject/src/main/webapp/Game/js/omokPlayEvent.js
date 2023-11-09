/*보드 클릭 시 콘솔 로그에 좌표값 출력*/
function stoneInfo(e){
	//좌표값 보정
	let x = Math.round(e.offsetX/28);
	let y = Math.round(e.offsetY/28);
	//좌표값 콘솔 출력
	console.log("X: "+x+", "+"Y: "+y);
	console.log("X: "+e.offsetX+", "+"Y: "+e.offsetY);	
	
	
	//-----------------돌 배치 테스트-------------------
	//돌 표기
	let stoneDiv = document.createElement("div");
	stoneDiv.className = "stone";
	stoneDiv.style.top = y*28 + "px"; // Y 좌표에 따라 위치 설정
	stoneDiv.style.left = x*28 + "px"; // X 좌표에 따라 위치 설정
	
	// <div id="board"> 요소 내에 돌을 추가
    let boardDiv = document.getElementById("board");
    if (boardDiv) {
        boardDiv.appendChild(stoneDiv);
    }
}

/*나가기 버튼 클릭 시, 대기방으로 이동*/
 var is;
 function exit(){
	is = confirm("정말 그만두시겠습니까?");
	
	if(is){
		location.href="/WebOmokProject/room/listRoom.jsp";
	}
}