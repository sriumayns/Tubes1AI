import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class FileReaderMachine{
	private static Room[] rooms = new Room[100];
	private static Course[] courses = new Course[100];
	private static int roomSize = 0;
	private static int courseSize = 0;

	public static void readTestCase() throws IOException {
		//READ FROM TXT
		int roomIdx = 0;
		int courseIdx = 0;
		
		boolean readRoomActive = false;
		boolean readCourseActive = false;
		
		BufferedReader in = new BufferedReader(new FileReader("Testcase.txt"));
		String line;
		while((line = in.readLine()) != null){
			if (line.equals("")) {

			}else if (line.equals("Ruangan")) {
				readRoomActive = true;
				readCourseActive = false;
			}else if (line.equals("Jadwal")) {
				readRoomActive = false;
				readCourseActive = true;
			}else {
				String[] lineParsed = line.split(";");
				if ((readRoomActive) && (lineParsed[1] != null)) {
					Room readRoom = new Room();
					readRoom.setRoomName(lineParsed[0]);
					readRoom.setStartHour(lineParsed[1]);
					readRoom.setEndHour(lineParsed[2]);
					readRoom.setAvailableDay(lineParsed[3]);
					rooms[roomSize] = readRoom;
					roomSize++;
				}
				if (readCourseActive && (lineParsed[1] != null)) {
					Course readCourse = new Course();
					readCourse.setCourseName(lineParsed[0]);
					readCourse.setRoomConstraint(lineParsed[1]);
					readCourse.setStartHourConstraint(lineParsed[2]);
					readCourse.setEndHourConstraint(lineParsed[3]);
					readCourse.setTotalCredit(lineParsed[4]);
					readCourse.setDayConstraint(lineParsed[5]);
					courses[courseSize] = readCourse;
					courseSize++;
				}
			}
		}
		in.close();
	}
		
	


	public static int getRoomSize(){
		return roomSize;
	}

	public static int getCourseSize(){
		return courseSize;
	}

	public static Room getRoomAtIdx(int idx){
		return rooms[idx];
	}

	public static Course getCourseAtIdx(int idx){
		return courses[idx];
	}	

}