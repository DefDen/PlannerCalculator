import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		ScheduleBuilder sb = new ScheduleBuilder();
		Scanner sc = new Scanner(System.in);
		String input = "", generation = "";
		System.out.println("Would you consider yourself a morning person? (Y/N)");
		input = sc.nextLine().trim();
		if(input.equalsIgnoreCase("y")) {
			sb.setIsEarly(true);
		}
		boolean running = true, alreadyGenerated = false;
		while (running) {
			System.out.println("Enter 's' to add a strict task, 'l' to enter a loose task, 'g' to generate your schedule or anything else to quit");
			input = sc.nextLine().trim();
			if (input.equals("s")) {
				System.out.println("Enter the name of your strict task");
				String name = sc.nextLine().trim();
				System.out.println("Enter the starting time of your strict task in the following format (note: military time): 7:30 PM March 6 2020 >> 3 6 2020 19 30");
				String[] start = sc.nextLine().trim().split(" ");
				System.out.println("Enter the ending time of your strict task in the following format (note: military time): 8:30 PM March 6 2020 >> 3 6 2020 20 30");
				String[] end = sc.nextLine().trim().split(" ");
				sb.addStrictTaskConsole(name, Integer.parseInt(start[2]), Integer.parseInt(start[0]), Integer.parseInt(start[1]),
				Integer.parseInt(start[3]), Integer.parseInt(start[4]), Integer.parseInt(end[2]), Integer.parseInt(end[0]), 
				Integer.parseInt(end[1]), Integer.parseInt(end[3]), Integer.parseInt(end[4]));
				alreadyGenerated = false;
			} else if (input.equals("l")) {
				System.out.println("Enter the name of your loose task");
				String name = sc.nextLine().trim();
				System.out.println("Enter the deadline in the following format: 7:30 PM March 6 2020 >> 3 6 2020 7 30");
				String[] start = sc.nextLine().trim().split(" ");
				System.out.println("Enter the estimated number of minutes this task will take");
				int end = Integer.parseInt(sc.nextLine().trim());
				sb.addLooseTaskConsole(name, end, Integer.parseInt(start[2]), Integer.parseInt(start[0]), Integer.parseInt(start[1]),
				Integer.parseInt(start[3]), Integer.parseInt(start[4]));
				alreadyGenerated = false;
			} else if (input.equals("g")) {
				if(!alreadyGenerated) {
					sb.generate();
					generation = sb.toString();
				}
				System.out.println(generation);
				alreadyGenerated = true;
			} else {
				running = false;
			}
		}

	}
}
		


		/*System.out.println("\n\n\n");
		
		ScheduleBuilder sb2 = new ScheduleBuilder();
		GregorianCalendar[] t = new GregorianCalendar[6];
		t[0] = new GregorianCalendar();
		t[0].set(2020, 11, 11, 6, 30, 0);
		t[1] = new GregorianCalendar();
		t[1].set(2020, 11, 11, 6, 35, 0);
		t[2] = new GregorianCalendar();
		t[2].set(2020, 11, 11, 7, 30, 0);
		t[3] = new GregorianCalendar();
		t[3].set(2020, 11, 11, 8, 0, 0);
		t[4] = new GregorianCalendar();
		t[4].set(2020, 11, 11, 12, 30, 0);
		t[5] = new GregorianCalendar();
		t[5].set(2020, 11, 11, 15, 30, 0);
		LooseTask lt = new LooseTask("hi", new GregorianCalendar(), 60);
		StrictTask[] st = new StrictTask[3];
		st[0] = new StrictTask("aaaa", t[0], t[1]);
		st[1] = new StrictTask("bbbb", t[2], t[3]);
		st[2] = new StrictTask("cccc", t[4], t[5]);
		sb2.addStrictTask(st[0]);
		sb2.addStrictTask(st[1]);
		sb2.addStrictTask(st[2]); 
		sb2.printStrictSchedule();*/
	

/*System.out.println("new run");
		ScheduleBuilder sb = new ScheduleBuilder();
		GregorianCalendar psychEndTime = new GregorianCalendar();
		psychEndTime.set(2020, 11, 14, 6, 30, 0);
		sb.addLooseTask(new LooseTask("Study for Psych", psychEndTime, 721));
		System.out.println(sb.daysBetween(actual, psychEndTime));
		HashMap<String, HashMap<LooseTask, Integer>> map = sb.chooseDays();
		for (String key : map.keySet()) {
			HashMap<LooseTask, Integer> innerMap = map.get(key);
			for (LooseTask task : innerMap.keySet()) {
				System.out.println(key + " " + task.getName() + " " + innerMap.get(task));
			}

		}
		
		System.out.println("\n\n\n");
		
		ScheduleBuilder sb2 = new ScheduleBuilder();
		GregorianCalendar[] t = new GregorianCalendar[6];
		t[0] = new GregorianCalendar();
		t[0].set(2020, 11, 14, 6, 30, 0);
		t[1] = new GregorianCalendar();
		t[1].set(2020, 11, 14, 6, 35, 0);
		t[2] = new GregorianCalendar();
		t[2].set(2020, 11, 14, 7, 30, 0);
		t[3] = new GregorianCalendar();
		t[3].set(2020, 11, 14, 8, 0, 0);
		t[4] = new GregorianCalendar();
		t[4].set(2020, 11, 14, 12, 30, 0);
		t[5] = new GregorianCalendar();
		t[5].set(2020, 11, 14, 15, 30, 0);
		LooseTask lt = new LooseTask("hi", new GregorianCalendar(), 60);
		StrictTask[] st = new StrictTask[3];
		st[0] = new StrictTask("aaaa", t[0], t[1]);
		st[1] = new StrictTask("bbbb", t[2], t[3]);
		st[2] = new StrictTask("cccc", t[4], t[5]);
		sb2.setActualDate(actual);
		sb2.addStrictTask(st[0]);
		sb2.addStrictTask(st[1]);
		sb2.addStrictTask(st[2]); 
		sb2.addTaskToDay(map);
		System.out.println("\n\n\n");
		sb2.printStrictSchedule();
=======
		}/*
/*System.out.println(":)");
ScheduleBuilder sb = new ScheduleBuilder();
GregorianCalendar psychEndTime = new GregorianCalendar();
psychEndTime.set(2020, 11, 31, 6, 30, 0);
GregorianCalendar actual = new GregorianCalendar();
actual.set(2020, 11, 11, 6, 30, 0);
sb.addLooseTask(new LooseTask("Study for Psych", psychEndTime, 721));
GregorianCalendar csEndTime = new GregorianCalendar();
sb.addLooseTask(new LooseTask("Study for CS", csEndTime, 721));
csEndTime.set(2020, 11, 31, 6, 30, 0);
System.out.println(sb.daysBetween(actual, csEndTime));
HashMap<String, HashMap<LooseTask, Integer>> map = sb.chooseDays();
for (String key : map.keySet()) {
	HashMap<LooseTask, Integer> innerMap = map.get(key);
	for (LooseTask task : innerMap.keySet()) {
		System.out.println(key + " " + task.getName() + " " + innerMap.get(task));
	}
}*/
		
	/*ScheduleBuilder sb = new ScheduleBuilder();
		GregorianCalendar csStartTime = new GregorianCalendar();
		csStartTime.set(2020, 11, 9, 8, 30, 0);
		GregorianCalendar csEndTime = new GregorianCalendar();
		csEndTime.set(2020, 11, 9, 9, 30, 0);
		GregorianCalendar mathStartTime = new GregorianCalendar();
		mathStartTime.set(2020, 11, 9, 21, 30, 0);
		GregorianCalendar mathEndTime = new GregorianCalendar();
		mathEndTime.set(2020, 11, 9, 22, 30, 0);
		sb.addStrictTask(new StrictTask("CS", csStartTime, csEndTime));
		sb.addStrictTask(new StrictTask("Math", mathStartTime, mathEndTime));
		sb.start();
		LooseTask lt = new LooseTask("Nap Time", new GregorianCalendar(), 60);
		sb.addLooseTask(lt);
		sb.addTaskToDay("11 9 2020", lt, 60);
		sb.start();
		sb.printFinal();*/

	
		
//		for (String day : sb.getStrictSchedule().keySet()) {
//			for (int i = 0; i < sb.getStrictSchedule().get(day).length; i++) {
//				if (sb.getStrictSchedule().get(day)[i] == null) {
//					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i]);
//				} else {
//					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i].getName() + " " + i);
//				}
//			}
//		}
	

	
