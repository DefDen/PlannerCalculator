import java.util.GregorianCalendar;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		System.out.println("new run");
		ScheduleBuilder sb = new ScheduleBuilder();
		GregorianCalendar psychEndTime = new GregorianCalendar();
		psychEndTime.set(2020, 11, 14, 6, 30, 0);
		GregorianCalendar actual = new GregorianCalendar();
		actual.set(2020, 11, 11, 6, 30, 0);
		sb.setActualDate(actual);
		sb.addLooseTask(new LooseTask("Study for Psych", psychEndTime, 721));
		System.out.println(sb.daysBetween(actual, psychEndTime));
		HashMap<String, HashMap<LooseTask, Integer>> map = sb.easyChooseDays();

			for (HashMap<LooseTask, Integer> innerMap : map.values()) {
				for (LooseTask task : innerMap.keySet()) {
					System.out.println(task.getDeadlineKey() + " " + task.getName() + " " + innerMap.get(task));
				}
			}
	
	}
}
