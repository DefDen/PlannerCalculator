import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

public class ScheduleBuilder {
	private HashMap<String, StrictTask[]> strictSchedule;
	private HashMap<String, HashSet<StrictTask>> strictTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasks;
	private int wakeUp, sleep, earliness;
	
	public ScheduleBuilder() {
		this.strictTasks = new HashMap<String, HashSet<StrictTask>>();
		this.looseTasks = new HashMap<String, HashSet<LooseTask>>();
		this.strictSchedule = new HashMap<String, StrictTask[]>();
	}
	
	public HashMap<String, StrictTask[]> generate() {
		HashMap<String, StrictTask[]> finalSchedule = new HashMap<String, StrictTask[]>();
		for(String s : strictSchedule.keySet()) {
			finalSchedule.put(s, strictSchedule.get(s));
		}
		
		
		for(String s : looseTasks.keySet()) {
			for(LooseTask lt : looseTasks.get(s)) {
				// choose day
				Calendar day = addTaskToDay();
				while(day == null) {
					// choose new day
					// probably just increment or decrement day
				}
				addStrictTask(lt.toStrictTask(day));
			}
		}
	}
	
	private Calendar addTaskToDay(String day, LooseTask add) {
		int x = 0, increment = 0;
		switch(earliness) {
		case 0:
			x = wakeUp;
			increment = 5;
			break;
		case 1:
			x = sleep;
			if(sleep < wakeUp) {
				x = 23;
			}
			increment = -5;
			break;
		case 2:
			return addTaskMidDay(day, add);
		}
		do {
			
		} while(x != wakeUp && x != sleep);
	}
	
	private Calendar addTaskMidDay(String day, LooseTask add) {
		
	}
	
	/*public void addStrictTask(StrictTask task) {
		if (!strictTasks.containsKey(task.getName())) {
			strictTasks.put(task.getName(), new HashSet<StrictTask>());
		}
		strictTasks.get(task.getName()).add(task);
		String startKey = task.getStartKey();
		String endKey = task.getEndKey();
		int startDate = task.getStartTime().get(Calendar.DATE);
		int startMonth = task.getStartTime().get(Calendar.MONTH);
		int startYear = task.getStartTime().get(Calendar.YEAR);
		int startHour = task.getStartTime().get(Calendar.HOUR);
		int start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		int endYear = task.getEndTime().get(Calendar.YEAR);
		int endMonth = task.getEndTime().get(Calendar.MONTH);
		int endDate = task.getEndTime().get(Calendar.DATE);
		int endHour = task.getEndTime().get(Calendar.HOUR);
		int end5Minute = task.getEndTime().get(Calendar.MINUTE) / 5;
		while (startYear < endYear) {
			while (startMonth < 12) {
				int numberOfDaysInMonth = numberOfDaysInMonth(startMonth, startYear);
				while (startDate < numberOfDaysInMonth) {
					if (!strictSchedule.containsKey(startKey)) {
						strictSchedule.put(startKey, new StrictTask[288]);
					}
					while (startHour < 24) {
						while (start5Minute < 12) {
							strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
							start5Minute++;
						}
						start5Minute = 0;
						startHour++;
					}
					startHour = 0;
					startDate++;
				}
				startDate = 0;
				startMonth++;
			}
			startMonth = 0;
			startYear++;
		}
		startMonth = task.getStartTime().get(Calendar.MONTH);
		startDate = task.getStartTime().get(Calendar.DATE);
		startHour = task.getStartTime().get(Calendar.HOUR);
		start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		while (startMonth < endMonth) {
			while (startDate < numberOfDaysInMonth) {
				if (!strictSchedule.containsKey(startKey)) {
					strictSchedule.put(startKey, new StrictTask[288]);
				}
				while (startHour < 24) {
					while (start5Minute < 12) {
						strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
						start5Minute++;
					}
					start5Minute = 0;
					startHour++;
				}
				startHour = 0;
				startDate++;
			}
			startDate = 0;
			startMonth++;
		}
		startDate = task.getStartTime().get(Calendar.DATE);
		startHour = task.getStartTime().get(Calendar.HOUR);
		start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		while (startDate < endDate) {
			if (!strictSchedule.containsKey(startKey)) {
				strictSchedule.put(startKey, new StrictTask[288]);
			}
			while (startHour < 24) {
				while (start5Minute < 12) {
					strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
					start5Minute++;
				}
				start5Minute = 0;
				startHour++;
			}
			startHour = 0;
			startDate++;
		}
		startHour = task.getStartTime().get(Calendar.HOUR);
		start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		while (startHour < endHour) {
			while (start5Minute < 12) {
				strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
				start5Minute++;
			}
			start5Minute = 0;
			startHour++;
		}
		startHour = task.getStartTime().get(Calendar.HOUR);
		start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		while (start5Minute < end5Minute) {
			strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
			start5Minute++;
		}
	}*/
	
	public void addStrictTask(StrictTask task) {
		if (!strictTasks.containsKey(task.getName())) {
			strictTasks.put(task.getName(), new HashSet<StrictTask>());
		}
		strictTasks.get(task.getName()).add(task);
		String startKey = task.getStartKey();
		String endKey = task.getEndKey();
		int startHour = task.getStartTime().get(Calendar.HOUR);
		int start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
		while (!startKey.equals(endKey)) {
			if (!strictSchedule.containsKey(startKey)) {
				strictSchedule.put(startKey, new StrictTask[288]);
			}
			fill(start5Minute, 11, startHour, 23, task, startKey);
			String[] split = startKey.split(" ");
			int[] splitInts = new int[split.length];
			for (int i = 0; i < splitInts.length; i++) {
				splitInts[i] = Integer.parseInt(split[i]);
			}
			splitInts[1]++;
			int numDays = numberOfDaysInMonth(splitInts[0], splitInts[2], task.getStartTime()); 
			if (splitInts[1] > numDays) {
				splitInts[1] = 1;
				splitInts[0]++;
			}
			if (splitInts[0] >= 12) {
				splitInts[0] = 1;
				splitInts[2]++;
			}
			startKey = splitInts[0] + " " + splitInts[1] + " " + splitInts[2];
			start5Minute = 0;
			startHour = 0;
		}
		if (!strictSchedule.containsKey(startKey)) {
			strictSchedule.put(startKey, new StrictTask[288]);
		}
		int endHour = task.getEndTime().get(Calendar.HOUR);
		int end5Minute = task.getEndTime().get(Calendar.MINUTE) / 5;
		fill(start5Minute, end5Minute, startHour, endHour, task, startKey);
	}
	
	private void fill(int start5Minute, int end5Minute, int startHour, int endHour, StrictTask task, String startKey) {
		if (start5Minute >= 12) {
			start5Minute -= 12;
			startHour++;
		}
		if (startHour < endHour || startHour == endHour && start5Minute <= end5Minute) {
			if (strictSchedule.get(startKey)[startHour * 12 + start5Minute] != null) {
				throw new IllegalArgumentException();
			}
			strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
			fill(start5Minute + 1, end5Minute, startHour, endHour, task, startKey);
		}
	}
	
	/*private void fillEndOfDay(int minutesRemaining, int hoursRemaining, StrictTask task, String key) {
		if (minutesRemaining <= 0) {
			minutesRemaining += 12;
			hoursRemaining--;
		}
		if (hoursRemaining >= 0 ) {
			strictSchedule.get(key)[288 - (hoursRemaining * 12 + minutesRemaining)] = task;
			fillEndOfDay(minutesRemaining - 5, hoursRemaining, task, key);
		}
	}
	/*
	int startDate = task.getStartTime().get(Calendar.DATE);
	int startMonth = task.getStartTime().get(Calendar.MONTH);
	int startYear = task.getStartTime().get(Calendar.YEAR);
	int startHour = task.getStartTime().get(Calendar.HOUR);
	int start5Minute = task.getStartTime().get(Calendar.MINUTE) / 5;
	int endYear = task.getEndTime().get(Calendar.YEAR);
	int endMonth = task.getEndTime().get(Calendar.MONTH);
	int endDate = task.getEndTime().get(Calendar.DATE);
	int endHour = task.getEndTime().get(Calendar.HOUR);
	int end5Minute = task.getEndTime().get(Calendar.MINUTE) / 5;
	fillYears(startYear, endYear, startMonth, startDate, startHour, start5Minute, task, startKey);
	fillMonths(startMonth, endMonth, startYear, startDate, startHour, start5Minute, task, startKey);
	fillDates(startDate, endDate, startHour, start5Minute, task, startKey);
	fillHours(startHour, endHour, start5Minute, task, startKey);
	fill5Minutes(start5Minute, end5Minute, startHour, task, startKey);
}
	
	private void fill5Minutes(int start5Minute, int end5Minute, int startHour, StrictTask task, String startKey) {
		while (start5Minute < end5Minute) {
			strictSchedule.get(startKey)[startHour * 12 + start5Minute] = task;
			start5Minute++;
		}
	}
	
	
	private void fillHours(int startHour, int endHour, int start5Minute, StrictTask task, String startKey) {
		while (startHour < endHour) {
			fill5Minutes(start5Minute, 12, startHour, task, startKey);
			start5Minute = 0;
			startHour++;
		}
	}
	
	private void fillDates(int startDate, int endDate, int startHour, int start5Minute, StrictTask task, String startKey) {
		while (startDate < endDate) {
			if (!strictSchedule.containsKey(startKey)) {
				System.out.println(startKey);
				strictSchedule.put(startKey, new StrictTask[288]);
			}
			fillHours(startHour, 24, start5Minute, task, startKey);
			startHour = 0;
			startDate++;
			startKey = startKey.split(" ")[0] + " " + startDate + " " + startKey.split(" ")[2];
		}
		if (!strictSchedule.containsKey(startKey)) {
			System.out.println(startKey);
			strictSchedule.put(startKey, new StrictTask[288]);
		}
	}
	
	private void fillMonths(int startMonth, int endMonth, int startYear, int startDate, int startHour, int start5Minute, StrictTask task, String startKey) {
		while (startMonth < endMonth) {
			int numberOfDaysInMonth = numberOfDaysInMonth(startMonth, startYear, task.getStartTime());
			fillDates(startDate, numberOfDaysInMonth, startHour, start5Minute, task, startKey);
			startDate = 0;
			startMonth++;
			startKey = startMonth + " " + startKey.split(" ")[1] + " " + startKey.split(" ")[2];
		}
	}
	
	private void fillYears(int startYear, int endYear, int startMonth, int startDate, int startHour, int start5Minute, StrictTask task, String startKey) {
		while (startYear < endYear) {
			fillMonths(startMonth, 13, startYear, startDate, startHour, start5Minute, task, startKey);
			startMonth = 0;
			startYear++;
			startKey = startKey.split(" ")[0] + " " + startKey.split(" ")[1] + " " + startYear;
		}
	}*/
	
	private int numberOfDaysInMonth(int startMonth, int startYear, Calendar cal) {
		if (startMonth == Calendar.FEBRUARY) {
			GregorianCalendar greg = (GregorianCalendar) cal;
			if (greg.isLeapYear(startYear)) {
				return 29;
			}
			return 28;
		}
		return (startMonth + 1 - startMonth / 7) % 2 + 30;
	}
	
	public void addLooseTask(LooseTask task) {
		if (!looseTasks.containsKey(task.getName())) {
			looseTasks.put(task.getName(), new HashSet<LooseTask>());
		}
		looseTasks.get(task.getName()).add(task);
	}
	
	public void removeStrictTask(StrictTask task) {
		strictTasks.get(task.getName()).remove(task);
	}
	
	public void removeLooseTask(LooseTask task) {
		looseTasks.get(task.getName()).remove(task);
	}
	
	public HashMap<String, HashSet<StrictTask>> getStrictTasks() {
		return strictTasks;
	}
	
	public HashMap<String, HashSet<LooseTask>> getLooseTasks() {
		return looseTasks;
	}
	
	public HashMap<String, StrictTask[]> getStrictSchedule() {
		return strictSchedule;
	}
	
	
}
