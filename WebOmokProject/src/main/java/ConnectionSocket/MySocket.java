package ConnectionSocket;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;

@ServerEndpoint(value="/OmokGameSocket/{roomId}/{playerId}")
public class MySocket {
	private static Set<RSession> clients = Collections.synchronizedSet(new HashSet<RSession>());
	// 순서를 유지하지 않고 저장하며, 중복 저장이 불가능하다.

	@OnOpen // 클라이언트 접속 시 실행
	public void onOpen(Session session, @PathParam("roomId") String roomid, @PathParam("playerId") String playerid) {
		RSession rsession = new RSession(Integer.parseInt(roomid),Integer.parseInt(playerid), session);
		clients.add(rsession); // 세션 추가
		System.out.println("웹소켓 연결: " + rsession.getId()+"룸 아이디: "+roomid +"플레이어 아이디: "+playerid);
	}

	@OnMessage // 메시지를 받으면 실행
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("메시지 수신 " + session.getId() + ": " + message);
		int roomId = -1;
		try {
			JSONObject jsonObject = new JSONObject(message);
			roomId = jsonObject.getInt("roomId");
		}
		catch(Exception e){
			System.out.println("메시지 json 오류 ");
		}
		synchronized (clients) {
			//서버단에서 클라이언트 중에서 당사자한테만 전달
			for (RSession client : clients) {
				//메시지에서 받은 client
				if(roomId == client.getRoomId() && !client.getMsession().equals(session)) {
					client.getMsession().getBasicRemote().sendText(message);
				}
			}
		}
	}

	@OnClose // 클라이언트와의 연결이 끊기면 실행
	public void onClose(Session session) {
		//clients.remove(session);
		clients.removeIf(t -> t.getMsession().equals(session));
		System.out.println("웹소켓 종료: " + session.getId());
	}

	@OnError
	public void onError(Throwable e) {
		System.out.println("에러 발생");
		e.printStackTrace();
	}
}

class RSession{
	public RSession(int roomid,int playerid, Session s) {
		this.roomId = roomid;
		this.playerId = playerid;
		this.msession = s;
	}
	private int roomId;
	private Session msession;
	private int playerId;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomid) {
		this.roomId = roomid;
	}
	public Session getMsession() {
		return msession;
	}
	public void setMsession(Session msession) {
		this.msession = msession;
	}
	public String getId() {
		return msession.getId();
	}
	public void setSession(Session ss) {
		this.msession = ss;
	}
}
