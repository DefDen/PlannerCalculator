import java.util.*;

public class StrictTask {
	private String name;
	private Calendar startTime;
	private Calendar endTime;

	public StrictTask (String name, Calendar startTime, Calendar endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setStartDate(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public String getStartKey() {
		return startTime.get(Calendar.MONTH) + " " + startTime.get(Calendar.DATE) + " " + startTime.get(Calendar.YEAR); 
	}

	public String getEndKey() {
		return endTime.get(Calendar.MONTH) + " " + endTime.get(Calendar.DATE) + " " + endTime.get(Calendar.YEAR);
	}
}