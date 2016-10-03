

public class HillClimbing {

	public static ScheduleBoard runHillClimbing(){
		ScheduleBoard scheduleBoard = new ScheduleBoard();
		hillClimbing(scheduleBoard);
		return scheduleBoard;
	}
	
	private static void hillClimbing(ScheduleBoard scheduleBoard) {
		int step = 0;
		int maxStep = 5;
		int startStep = 0;
		int currentConflict = scheduleBoard.countConflict();

		int scheduleIdx;
		int day;
		int hour;
		int selectedDay;
		int selectedHour;
		int selectedScheduleIdx;
		Course course;
		int courseId;
		int[] temp_result = new int[3];
		String roomName;


		while ((currentConflict > 0)&&(startStep < maxStep)) {
			step++;
			temp_result = scheduleBoard.getMaxConflictLocation();
			day = temp_result[0];
			hour = temp_result[1];
			scheduleIdx = temp_result[2];
			courseId = scheduleBoard.getLastInsertedCourseId(scheduleIdx,day,hour);
			course = scheduleBoard.getAndDeleteCourseById(courseId,scheduleIdx,day,hour);
			if (!course.getRoomConstraint().equals("-")) {
				roomName = course.getRoomConstraint();
				selectedScheduleIdx = scheduleBoard.getScheduleIdx(roomName);
				temp_result = scheduleBoard.searchBestLocationOnSchedule(selectedScheduleIdx,course);
				selectedDay = temp_result[0];
				selectedHour = temp_result[1];
			}
			else {
				temp_result = scheduleBoard.searchBestLocation(course);
				selectedDay = temp_result[0];
				selectedHour = temp_result[1];
				selectedScheduleIdx = temp_result[2];
			}

			scheduleBoard.insertCourse(course,selectedScheduleIdx,selectedDay,selectedHour);
			

			if (scheduleBoard.countConflict() >= currentConflict) {
				startStep++;
			}
			else {
				startStep = 0;
				currentConflict = scheduleBoard.countConflict();
			}


		}
		//System.out.println("totalStep : "+step);
	} 
}
