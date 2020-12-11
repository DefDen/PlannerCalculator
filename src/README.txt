This project creates a schedule for you. It takes as inputs two types of tasks:
loose tasks and strict tasks. Strict tasks are the most intuitive. They have a 
name, a start time, and an end time. They get put into the schedule at the proper 
time. Loose tasks are less intuitive. Loose tasks have a deadline and a number 
of minutes you estimate it will take to complete them. The project distributes 
those minutes from now until the deadline in a reasonable way. 

To use, in the main method, construct a ScheduleBuilder with a default constructor.
Before adding a strict task, you need to create two GregorianCalendar objects to be 
the task’s start time and end time. Before adding a loose task, you need to create
one GregorianCalendar object to be the task’s deadline. Construct a GregorianCalendar
with a default constructor, then use the set method with 6 parameters. The method is
set(year, month, day, hour, minute, second). A few notes. One, month is indexed at 0.
0 means January and 11 means December. Two, second should always be 0 since the 
project uses 5 minute intervals, making seconds meaningless. Three, hours are in
military time and indexed at 0. The hour starting at midnight is zero and the hour 
starting at noon is 12. However, dates are indexed at 1. After constructing
GregorianCalendars, add tasks with addLooseTask(new LooseTask(String name,
GregorianCalendar deadline, int minutes)) or addStrictTask(new StrictTask(String name,
GregorianCalendar startTime, GregorianCalendar endTime)). You must also call
setActualDate(GregorianCalendar actual) with a GregorianCalendar representing the
current actual real life time.