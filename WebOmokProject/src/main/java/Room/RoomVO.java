package Room;

import java.sql.Date;
import lombok.Data;

@Data
public class RoomVO {
	
	private int room_id;
	private int owner_id;
	private int joined_no;
	private String room_nm;
	private Date created_date;
	private int option_val;
	private String room_pw;
	
}
 