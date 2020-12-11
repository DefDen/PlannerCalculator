import java.util.*;


public class ScheduleBuilder {
	private HashMap<String, StrictTask[]> strictSchedule, finalSchedule;
	private HashMap<String, HashSet<StrictTask>> strictTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasks;
	private HashMap<String, HashSet<LooseTask>> looseTasksByName;
	private Calendar actualDate;
	private int wakeUpMinutes = 480, sleepMinutes = 1320;
	private boolean isEarly = false;

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
		if(duration % 5 != 0) {
			duration = duration - duration % 5 + 5;
		}
		String[] dayArgs = day.split(" ");
		int[] dayInts = new int[dayArgs.length];
		for(int x = 0; x < dayArgs.length; x++) {
			dayInts[x] = Integer.parseInt(dayArgs[x]);
		}
		GregorianCalendar time = new GregorianCalendar();
		if(strictSchedule.get(day) == null) {
			if(isEarly) {
				time.set(dayInts[2], dayInts[0], dayInts[1], 8, 0);
			}
			time.set(dayInts[2], dayInts[0], dayInts[1], 20, 0);
			addStrictTask(add.toStrictTask(time, duration));
			return;
		}
		int maxSpaceAM = 0, maxSpacePM = 0, indexAM = -1, indexPM = -1, current = 0, newLeader = 0;
		boolean isNewChain = true;
		for(int x = 144; x < 288; x++) {
			if(strictSchedule.get(day)[x] == null) {
				if(isNewChain) {
					current = 1;
					newLeader = x;
					isNewChain = false;
				} else {
					current++;
				}
				if(current > maxSpaceAM) {
					indexAM = newLeader;
					maxSpaceAM = current;
				}
			} else {
				isNewChain = true;
			}
		}
		for(int x = 144; x > 0; x--) {
			if(strictSchedule.get(day)[x] == null) {
				if(isNewChain) {
					current = 1;
					newLeader = x;
					isNewChain = false;
				} else {
					current++;
				}
				if(current > maxSpaceAM) {
					indexPM = newLeader;
					maxSpaceAM = current;
				}
			} else {
				isNewChain = true;
			}
		}
		if(maxSpaceAM >= duration / 5 && maxSpacePM >= duration) {
			if(isEarly) {
				//put am
				time.set(dayInts[2], dayInts[0], dayInts[1], 0, maxSpaceAM * 5 + 720);
				System.out.print(add.getName());
				addStrictTask(add.toStrictTask(time, duration));
				return;
			} else {
				//put pm
				time.set(dayInts[2], dayInts[0], dayInts[1], 0, maxSpacePM * 5 - duration - 720);
				System.out.print(add.getName());
				addStrictTask(add.toStrictTask(time, duration));
				return;
			}
		} else if(maxSpaceAM >= duration) {
			//put am
			time.set(dayInts[2], dayInts[0], dayInts[1], 0, maxSpaceAM * 5 + 720);
			System.out.print(add.getName());
			addStrictTask(add.toStrictTask(time, duration));
			return;
		} else {
			// put pm
			time.set(dayInts[2], dayInts[0], dayInts[1], 0, maxSpacePM * 5 - duration - 720);
			System.out.print(add.getName());
			addStrictTask(add.toStrictTask(time, duration));
			return;
		}
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

	public void printStrictSchedule() {
		for (String day : strictSchedule.keySet()) {
			for (int i = 0; i < strictSchedule.get(day).length; i++) {
				GregorianCalendar g = new GregorianCalendar(0, 0, 0, 0, i * 5, 0);
				if (strictSchedule.get(day)[i] == null) {
					System.out.println(day + " " + strictSchedule.get(day)[i] + " " + g.get(Calendar.HOUR_OF_DAY) + ":" + g.get(Calendar.MINUTE));
				} else {
					System.out.println(day + " " + strictSchedule.get(day)[i].getName() + " " + g.get(Calendar.HOUR_OF_DAY) + ":" + g.get(Calendar.MINUTE));
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
				GregorianCalendar actual = new GregorianCalendar();
				int daysBetween = daysBetween(actual, task.getDeadline());
				if (minutesRemaining / 60 < daysBetween) {
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
			if (r.get(key) == null) {
				HashMap<LooseTask, Integer> mini = new HashMap<LooseTask, Integer>();
				r.put(key, mini);
			}
			if (minutesRemaining < 5 + duration) {
				duration += 5;
			}
			r.get(key).put(task, Integer.min(duration / 5 * 5, minutesRemaining));
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
			if (splitInts[1] <= 0) {
				splitInts[0]--;
				if (splitInts[0] < 0) {
					splitInts[0] = 11;
					splitInts[2]--;
				}
				splitInts[1] = numberOfDaysInMonth(splitInts[0], splitInts[2], new GregorianCalendar());
			}
			splitInts[1] = numberOfDaysInMonth(splitInts[0], splitInts[2], new GregorianCalendar());
		}
		currDate = splitInts[0] + " " + splitInts[1] + " " + splitInts[2];
		return currDate;
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
		if (startHour < endHour || startHour == endHour && start5Minute < end5Minute) {
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

	public void addLooseTaskConsole(String name, int durationMinutes, int deadlineYear, int deadlineMonth, int deadlineDate, int deadlineHour, int deadlineMinute) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(deadlineYear, deadlineMonth, deadlineDate, deadlineHour, deadlineMinute, 0);
		addLooseTask(new LooseTask(name, cal, durationMinutes));	
	}

	public void addStrictTaskConsole(String name, int startYear, int startMonth, int startDate, int startHour, int startMinute, int endYear, int endMonth, int endDate, int endHour, int endMinute) {
		GregorianCalendar startCal = new GregorianCalendar();
		startCal.set(startYear, startMonth, startDate, startHour, startMinute, 0);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.set(endYear, endMonth, endDate, endHour, endMinute, 0);
		addStrictTask(new StrictTask(name, startCal, endCal));
	}
}
