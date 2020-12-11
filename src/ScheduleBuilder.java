import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

public class ScheduleBuilder {
	private HashMap<String, StrictTask[]> strictSchedule;
	private HashMap<String, HashSet<StrictTask>> strictTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasksByName;
	private Calendar actualDate;

	public ScheduleBuilder() {
		this.strictTasks = new HashMap<String, HashSet<StrictTask>>();
		this.looseTasks = new HashMap<String, HashSet<LooseTask>>();
		this.looseTasksByName = new HashMap<String, HashSet<LooseTask>>();
		this.strictSchedule = new HashMap<String, StrictTask[]>();
	}
	
	public HashMap<String, HashMap<LooseTask, Integer>> easyChooseDays() {
		HashMap<String, HashMap<LooseTask, Integer>> r = new HashMap<String, HashMap<LooseTask, Integer>>();
		for (String name : looseTasksByName.keySet()) {
			for (LooseTask task : looseTasksByName.get(name)) {
				String key = task.getDeadlineKey();
				int minutesRemaining = task.getDurationMinutes();
				int daysBetween = daysBetween(actualDate, task.getDeadline());
				if (minutesRemaining / 60 < daysBetween(actualDate, task.getDeadline())) {
					fillDays(key, minutesRemaining, 60, task, r);
				} else {
					fillDays(key, minutesRemaining, minutesRemaining / daysBetween, task, r);
				}
			}
		}
		return r;
	}

	private void fillDays(String key, int minutesRemaining, int duration, LooseTask task, HashMap<String, HashMap<LooseTask, Integer>> r) {
		if (minutesRemaining > 0) {
			HashMap<LooseTask, Integer> mini = new HashMap<LooseTask, Integer>();
			if (minutesRemaining < 5) {
				duration += 5;
			}
			mini.put(task, Integer.min(duration / 5 * 5, minutesRemaining));
			r.put(key, mini);
			fillDays(increment(key), minutesRemaining - duration, duration, task, r); 
		}
	}

	private String increment(String currDate) {
		String[] split = currDate.split(" ");
			int[] splitInts = new int[split.length];
			for (int i = 0; i < splitInts.length; i++) {
				splitInts[i] = Integer.parseInt(split[i]);
			}
			splitInts[1]--;
			if (splitInts[1] < 0) {
				splitInts[0]--;
				if (splitInts[0] < 0) {
					splitInts[0] = 11;
					splitInts[2]--;
				}
				splitInts[1] = numberOfDaysInMonth(splitInts[0], splitInts[2], new GregorianCalendar());
			}
			currDate = splitInts[0] + " " + splitInts[1] + " " + splitInts[2];
			return currDate;
		}

	/*private HashMap<String, HashMap<LooseTask, Integer>> chooseDays() {
		HashMap<String, HashMap<LooseTask, Integer>> r = new HashMap<String, HashMap<LooseTask, Integer>>();
		LooseTask currTask = lastLooseTask;
		String currDate = lastLooseTask.getDeadlineKey();
		int bigRemainingMinutes = totalMinutes;
≈		while (bigRemainingMinutes >= 0) {
			addExactMeans();
			compare last item to second to last item
	}


	private ArrayList<Integer> addExactMeans( {
		int smallRemainingMinutes
		if (looseTasks.containsKey(currDate)) {
			return smallRemainingMinutes;
		} else {
			HashMap<LooseTask, Integer> miniMap = new HashMap<LooseTask, Integer>();
			int idealMinutesToPut = mean - strictHours.get(currDate) / 5 * 5 + 5;
			int actualMinutesToPut = Integer.min(idealMinutesToPut, smallRemainingMinutes);
			if (actualMinutesToPut > 0 && smallRemainingMinutes > 0) {
				miniMap.put(currTask, actualMinutesToPut);
				r.put(currDate, miniMap);
				bigRemainingMinutes -= actualMinutesToPut;
				smallRemainingMinutes -= actualMinutesToPut;
			}
			String[] split = currDate.split(" ");
			int[] splitInts = new int[split.length];
			for (int i = 0; i < splitInts.length; i++) {
				splitInts[i] = Integer.parseInt(split[i]);
			}
			splitInts[1]--;
			if (splitInts[1] < 0) {
				splitInts[0]--;
				if (splitInts[0] < 0) {
					splitInts[0] = 11;
					splitInts[2]--;
				}
				splitInts[1] = numberOfDaysInMonth(splitInts[0], splitInts[2], new GregorianCalendar());
			}
			currDate = splitInts[0] + " " + splitInts[1] + " " + splitInts[2];
			}
		}
	}
	}*/

	public void setActualDate(Calendar actualDate) {
		this.actualDate = actualDate;
	}

	/*public HashMap<String, StrictTask[]> generate() {
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
	
	/*private int leftSkew() {
		int r = 0;
		for (String day : strictSchedule.keySet()) {
			int dayMinutes;
			for (StrictTask i : strictSchedule.get(day)) {
				if (i != null) {
					dayMinutes += 5;
				}
			}
			String[] split = 
			r += daysBetween(currentDate, new Calendar(day));
		}
	}*/

	/*private int screwedUpAverageDeviation() {
		int daysBetween = daysBetween(currentDate, lastEndTime)
		double mean = totalMinutes / daysBetween; 
		int totalVariation = 0;
		for (StrictTask[] day : strictSchedule.values()) {
			int dayMinutes;
			for (StrictTask i : day) {
				if (i != null) {
					dayMinutes += 5;
				}
			}
			totalVariation += Math.sqrt(Math.abs(dayMinutes - mean));
		}
		return totalVariation / daysBetween;
	}*/

	public int daysBetween(Calendar first, Calendar second) {
		int r = 1;
		for (int i = first.get(Calendar.YEAR); i < second.get(Calendar.YEAR); i++) {
			r += 365;
			GregorianCalendar greg = (GregorianCalendar)(first);
			if ((greg).isLeapYear(i)) {
				r += 1;
			}
		}
		for (int i = first.get(Calendar.MONTH); i < second.get(Calendar.MONTH); i++) {
			r += numberOfDaysInMonth(i, second.get(Calendar.YEAR), second);
		}
		r += second.get(Calendar.DATE) - first.get(Calendar.DATE);
		return r;
	}
	
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
			looseTasksByName.put(task.getName(), new HashSet<LooseTask>());
			looseTasks.put(task.getDeadlineKey(), new HashSet<LooseTask>());
		}
		looseTasks.get(task.getDeadlineKey()).add(task);
		looseTasksByName.get(task.getName()).add(task);

	}
	
	public void removeStrictTask(StrictTask task) {
		strictTasks.get(task.getName()).remove(task);
	}
	
	public void removeLooseTask(LooseTask task) {
		looseTasks.get(task.getDeadlineKey()).remove(task);
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
