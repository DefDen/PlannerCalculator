import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("new run");
		ScheduleBuilder sb = new ScheduleBuilder();
		GregorianCalendar psychEndTime = new GregorianCalendar();
		psychEndTime.set(2020, 11, 14, 6, 30, 0);
		GregorianCalendar actual = new GregorianCalendar();
		actual.set(2020, 11, 11, 6, 30, 0);
		sb.setActualDate(actual);
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
		sb2.setActualDate(actual);
		sb2.addStrictTask(st[0]);
		sb2.addStrictTask(st[1]);
		sb2.addStrictTask(st[2]); 
		sb2.printStrictSchedule();
	}
		
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
}
		
//		for (String day : sb.getStrictSchedule().keySet()) {
//			for (int i = 0; i < sb.getStrictSchedule().get(day).length; i++) {
//				if (sb.getStrictSchedule().get(day)[i] == null) {
//					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i]);
//				} else {
//					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i].getName() + " " + i);
//				}
//			}
//		}
	

	
