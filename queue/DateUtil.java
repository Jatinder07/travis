package travis.queue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date parseMessageDate(String msgDate) {
		Date date = null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy­MM­dd'T'HH:mm:ss.SSSZ");
			date = (Date)formatter.parseObject(msgDate);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return date;
	}
}
