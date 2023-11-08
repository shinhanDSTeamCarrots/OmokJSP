/*보드 클릭 시 콘솔 로그에 좌표값 출력*/
function stoneInfo(e){
	//좌표값 보정
	let x = Math.round(e.offsetX/28);
	let y = Math.round(e.offsetY/28);
	//좌표값 콘솔 출력
	console.log("X: "+x+", "+"Y: "+y);
}

/*나가기 버튼 클릭 시, 대기방으로 이동*/
 var is;
 function exit(){
	is = confirm("정말 그만두시겠습니까?");
	
	if(is){
		location.href="/WebOmokProject/room/listRoom.jsp";
	}
}