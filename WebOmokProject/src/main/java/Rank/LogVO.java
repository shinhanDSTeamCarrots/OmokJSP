package Rank;

import java.sql.Date;
import lombok.Data;

@Data
public class LogVO {
	
	private int log_id;
	private int winner_no;
	private int loser_no;
	private Date log_date;
	
}
