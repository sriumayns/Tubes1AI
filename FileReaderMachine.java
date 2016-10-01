import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class FileReaderMachine{
	private static Room[] rooms = new Room[100];
	private static Course[] courses = new Course[100];
	private static Schedule[] schedules = new Schedule[10];
	private static int roomSize = 0;
	private static int courseSize = 0;

	/*
		Memindahkan isi testCase ke dalam array
	*/
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
					schedules[roomSize] = new Schedule(readRoom);
					rooms[roomSize] = readRoom;
					roomSize++;
				}
				if (readCourseActive && (lineParsed[1] != null)) {
					Course readCourse = new Course();
					readCourse.setCourseName(lineParsed[0]);
					readCourse.setStartHourConstraint(lineParsed[2]);
					readCourse.setEndHourConstraint(lineParsed[3]);
					readCourse.setTotalCredit(lineParsed[4]);
					readCourse.setDayConstraint(lineParsed[5]);
					readCourse.setRoomConstraint(lineParsed[1]);
					readCourse.setId(courseSize+1);
					courses[courseSize] = readCourse;
					courseSize++;
				}
			}
		}
		in.close();
		
		sortingCourse();
	}
		
	

	/*
		Mengmbalikan jumlah room
	*/
	public static int getRoomSize(){
		return roomSize;
	}

	/*
		Mengembalikan jumlah course
	*/
	public static int getCourseSize(){
		return courseSize;
	}

	/*
		Mengembalikan Room berdasarkan index
	*/
	public static Room getRoomAtIdx(int idx){
		return rooms[idx];
	}

	/*
		Mengembalikan Schedule berdasarkan index
	*/
	public static Schedule getScheduleAtIdx(int idx){
		return schedules[idx];
	}

	/*
		Mengembalikan Schedule berdasarkan nama room
	*/
	public static Schedule getScheduleByRoomName(String roomName){
		boolean found = false;
		Schedule schedule = null;
		int i = 0;
		while((!found) && (i < roomSize)){
			if(schedules[i].getRoom().getRoomName().equals(roomName)){
				schedule = schedules[i];
				found = true;
			}
			i++;
		}

		return schedule;
	}


	/*
		Mengembalikan course berdasrkan index
	*/	
	public static Course getCourseAtIdx(int idx){
		return courses[idx];
	}

	/*
		Mengembalikan room yang berdasarkan nama.
		Prekondisi: nama Room pasti ada
	*/
	public static Room getRoomByName(String roomName){
		boolean found = false;
		Room room = null;
		int i = 0;
		while((!found) && (i < roomSize)){
			if(rooms[i].getRoomName().equals(roomName)){
				room = rooms[i];
				found = true;
			}
			i++;
		}

		return room;
	}

	/*
		Sorting course berdasarkan dari yang paling terconstraint
	*/
	private static void sortingCourse(){
		boolean sorted = false;

		while(!sorted){
			sorted = true;
			for (int i = 0;i < courseSize - 1;i++ ) {
				if(courses[i].getNSlotAvaliable() > courses[i+1].getNSlotAvaliable()){
					Course temp = courses[i];
					courses[i] = courses[i+1];
					courses[i+1] = temp;
					sorted = false;
				}

			}
		}
	}

}