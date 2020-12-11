import java.util.*;

public class LooseTask {
	private String name;
	private Calendar deadline;
	private int durationMinutes;
	
	public LooseTask(String name, Calendar deadline, int durationMinutes) {
		this.name = name;
		this.deadline = deadline;
		this.durationMinutes = durationMinutes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}
	
	public Calendar getDeadline() {
		return deadline;
	}
	
	public void setDuration(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	
	public int getDurationMinutes() {
		return durationMinutes;
	}

	public String getDeadlineKey() {
		return deadline.get(Calendar.MONTH) + " " + deadline.get(Calendar.DATE) + " " + deadline.get(Calendar.YEAR);
	}

	public StrictTask toStrictTask(Calendar startTime, int duration) {
		GregorianCalendar endTime = (GregorianCalendar) startTime.clone();
		endTime.add(Calendar.MINUTE, duration);
		return new StrictTask(this.name, startTime, endTime);
	}
}
