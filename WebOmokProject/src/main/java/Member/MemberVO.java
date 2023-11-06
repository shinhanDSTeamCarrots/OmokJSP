package member;

import java.sql.Date;
import lombok.Data;

@Data
public class MemberVO {
	private int member_no;
	private String member_id;
	private String member_pw;
	private String member_nm;
	private Date join_date;
	private String member_nicknm;
	private String email;
}
