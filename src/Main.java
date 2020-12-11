import java.util.GregorianCalendar;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScheduleBuilder sb = new ScheduleBuilder();
		GregorianCalendar csStartTime = new GregorianCalendar();
		csStartTime.set(2020, 11, 9, 3, 30, 0);
		GregorianCalendar csEndTime = new GregorianCalendar();
		csEndTime.set(2020, 11, 9, 4, 30, 0);
		GregorianCalendar mathStartTime = new GregorianCalendar();
		mathStartTime.set(2020, 11, 9, 1, 30, 0);
		GregorianCalendar mathEndTime = new GregorianCalendar();
		mathEndTime.set(2020, 11, 9, 2, 30, 0);
		sb.addStrictTask(new StrictTask("CS", csStartTime, csEndTime));
		sb.addStrictTask(new StrictTask("Math", mathStartTime, mathEndTime));
		for (String day : sb.getStrictSchedule().keySet()) {
			for (int i = 0; i < sb.getStrictSchedule().get(day).length; i++) {
				if (sb.getStrictSchedule().get(day)[i] == null) {
					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i]);
				} else {
					System.out.println(day + " " + sb.getStrictSchedule().get(day)[i].getName() + " " + i);
				}
			}
		}
	}
}
