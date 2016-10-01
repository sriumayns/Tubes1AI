

public class HillClimbing {
	
	public static void hillClimbing(ScheduleBoard scheduleBoard) {
		int maxStep = 10;
		int startStep = 0;
		int currentConflict = scheduleBoard.currentConflict();

		int scheduleIdx;
		int day;
		int hour;
		int selectedDay;
		int selectedHour;
		int selectedScheduleIdx;
		Course course;
		int courseId;
		int[] temp_result;

		while ((currentConflict > 0)&&(startStep < maxStep)) {
			temp_result = scheduleBoard.getMaxConflictLocation();
			day = temp_result[0];
			hour = temp_result[1];
			scheduleIdx = temp_result[2];

			courseId = scheduleBoard.getLastInsertedCourseId(scheduleIdx,day.hour);
			course = scheduleBoard.getAndDeleteCourseById(courseId,scheduleIdx,day,hour);
			temp_result = scheduleBoard.searchBestLocation(course.getTotalCredit());

			selectedDay = temp_result[0];
			selectedHour = temp_result[1];
			selectedScheduleIdx = temp_result[2];

			scheduleBoard.insertCourse(course,selectedScheduleIdx,selectedDay,selectedHour);

			if (scheduleBoard.countConflict() >= currentConflict) {
				startStep++;
			}
			else {
				startStep = 0;
			}

		}
	} 
}
