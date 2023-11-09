package Room;

import java.sql.Date;
import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class RoomVO {
	
	private int room_id=-1;
	private int owner_id=-1;
	private int joined_no=-1;
	private String room_nm;
	private Date created_date;
	private int option_val;
	private String room_pw;
	private String owner_nm;
	private String joined_nm;
	public String getDateString() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM-dd hh:mm:ss");
		String sDate = fmt.format(created_date);
		return sDate;
	}
}
 