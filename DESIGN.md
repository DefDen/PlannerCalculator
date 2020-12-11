# PlannerCalculator
Our project is a schedule builder which adapts to the needs of the user. We take in tasks which the user must complete as a series of starting and ending times, which are placed onto the schedule as strict, immovable blocks. These are defined in a HashMap where days of the year point to their respective tasks in order to save space on days where no tasks are occurring.

After strict tasks are defined, loose tasks can be added. These type of tasks represent ones which need to be done by a certain day, like homework, but don't necesarily need to be done at any given time like going to a class. Loose tasks are placed by a recursive algorithm around strict tasks making sure to spread out  enough as to not overwhelm users.

To implement the tasks and calender functionality we used the java.util.calender library and the java.util.gregoriancalendar library. Each task uses a calender instance for us to better determine if there were conflicts between tasks, wheather one came before another, and where to best place loose tasks.
