import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;


public class ScheduleBuilder {
	private HashMap<String, StrictTask[]> strictSchedule, finalSchedule;
	private HashMap<String, HashSet<StrictTask>> strictTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasksByName;
	private Calendar actualDate;
	private int wakeUpSeconds = 480, sleepSeconds = 1320;
	private boolean isEarly = true;

	public ScheduleBuilder() {
		this.strictTasks = new HashMap<String, HashSet<StrictTask>>();
		this.looseTasks = new HashMap<String, HashSet<LooseTask>>();
		this.looseTasksByName = new HashMap<String, HashSet<LooseTask>>();
		this.strictSchedule = new HashMap<String, StrictTask[]>();
		this.finalSchedule = new HashMap<String, StrictTask[]>();
	}
	
	public void start() {
		for(String s : strictSchedule.keySet()) {
			finalSchedule.put(s, strictSchedule.get(s));
		}
	}

	public void addTaskToDay(HashMap<String, HashMap<LooseTask, Integer>> tasks) {
		for(String s : tasks.keySet()) {
			for(LooseTask lt : tasks.get(s).keySet()) {
				addTaskToDay(s, lt, tasks.get(s).get(lt));
			}
		}
	}
	
	public void addTaskToDay(String day, LooseTask add, int duration) {
		int x = 0, increment = 0;
		int[] date = new int[3];
		String[] dayArray = day.split(" ");
		for (int y = 0; y < date.length; y++) {
			date[y] = Integer.parseInt(dayArray[y]);
		}
		if (isEarly) {
			x = wakeUpSeconds;
			increment = 5;
		} else {
			x = sleepSeconds;
			increment = -5;
		}
		System.out.println(x);
		GregorianCalendar calendarDate = new GregorianCalendar(date[2], date[0], date[1], x / 60, x % 60);
		System.out.println(calendarDate.get(Calendar.HOUR_OF_DAY) + " " + calendarDate.get(Calendar.MINUTE));
		if (finalSchedule.get(day) == null) {
			addStrictTask(add.toStrictTask(calendarDate, duration));
		}
		do {
			if (finalSchedule.get(day)[x / 5] == null) {
				int length = 0;
				while (Math.abs(length) <= duration && finalSchedule.get(day)[(x + length) / 5] == null) {
					System.out.println("1");
					length += increment;
				}
				System.out.println("2 " + length + " " + duration);
				if(Math.abs(length) >= duration) {
					System.out.println("3");
					if(!isEarly) {
						x -= 2 * length;
						calendarDate = new GregorianCalendar(date[2], date[0], date[1], x / 60, x % 60);
					}
					System.out.println(calendarDate.get(Calendar.HOUR_OF_DAY) + " " + calendarDate.get(Calendar.MINUTE));
					addStrictTask(add.toStrictTask(calendarDate, duration));
					return;
				}
				x += length;
				calendarDate = new GregorianCalendar(date[2], date[0], date[1], x / 60, x % 60);
			}
			x += increment;
		} while (x >= wakeUpSeconds && x <= sleepSeconds);
	}

	public void printFinal() {
		for (String day : finalSchedule.keySet()) {
			for (int i = 0; i < finalSchedule.get(day).length; i++) {
				if (finalSchedule.get(day)[i] == null) {
					System.out.println(day + " " + finalSchedule.get(day)[i]);
				} else {
					System.out.println(day + " " + finalSchedule.get(day)[i].getName() + " " + finalSchedule.get(day)[i].getStartTime().get(Calendar.HOUR_OF_DAY) + ":" + finalSchedule.get(day)[i].getStartTime().get(Calendar.MINUTE));
				}
			}
		}
	}

	public HashMap<String, HashMap<LooseTask, Integer>> chooseDays() {
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
			if (minutesRemaining < 5 + duration) {
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

	public void setActualDate(Calendar actualDate) {
		this.actualDate = actualDate;
	}

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
