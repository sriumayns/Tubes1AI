import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class ScheduleBoard{
	private Course emptyCourse = new Course();
	private Slot emptySlot = new Slot();
	private Schedule[] scheduleBoard;
	
	/*
		Constructor
	*/
	public ScheduleBoard(){
		scheduleBoard = new Schedule[FileReaderMachine.getRoomSize()];	
		for (int i=0; i < FileReaderMachine.getRoomSize(); i++) {
			scheduleBoard[i] = new Schedule(FileReaderMachine.getRoomAtIdx(i));
		}
		initializeSolutionRandomly();
	}

	public ScheduleBoard(String status){
		if(status.equals("empty")){
			scheduleBoard = new Schedule[FileReaderMachine.getRoomSize()];	
			for (int i=0; i < FileReaderMachine.getRoomSize(); i++) {
				scheduleBoard[i] = new Schedule(FileReaderMachine.getRoomAtIdx(i));
			}
		}
	}
	
	/*
		Mengembalikan banyaknya Schedule dalam Schedule board
	*/
	public int getScheduleBoardLength() {
		return scheduleBoard.length;
	}
	
	/*
		Mendapatkan semua schedule dalam schedule board
	*/
	public Schedule[] getAllSchedule() {
		return scheduleBoard;
	}
	
	/*
		Merubah seluruh schedule board
	*/
	public void setAllSchedule(Schedule[] schedule) {
		scheduleBoard = schedule;
	}
	
	/*
		Mengembalikan Schedule dengan index tertentu
	*/
	public Schedule getScheduleWithIndex(int idx) {
		return scheduleBoard[idx];
	}
	
	/*
		Merubah schedule dengan index tertentu
	*/
	public void setScheduleWithIndex(int idx, Schedule schedule) {
		scheduleBoard[idx] = schedule;
	}

	public Course getEmptyCourse() {
		return emptyCourse;
	}
	
	/*
		Print Schedule Board
	*/
	public void printScheduleBoard() {
		for (int i=0; i < FileReaderMachine.getRoomSize(); i++) {
			scheduleBoard[i].printSchedule();
		}
	}

	/*
		Print Room
	*/
	public void printRooms(Room[] rooms) {
		System.out.println("Ruangan");
		for (int i=0; i<FileReaderMachine.getRoomSize(); i++) {
			System.out.print(FileReaderMachine.getRoomAtIdx(i).getRoomName() + ";" + FileReaderMachine.getRoomAtIdx(i).getStartHour() +";"+ FileReaderMachine.getRoomAtIdx(i).getEndHour()
				+";");
			for(int j=0; j<FileReaderMachine.getRoomAtIdx(i).getAvailableDay().length; j++) {
				System.out.print(FileReaderMachine.getRoomAtIdx(i).getAvailableDay()[j]+",");
			}
			System.out.println();
		}
	}
	
	/*
		Print Course
	*/
	public void printCourses(Course[] courses) {
		System.out.println("Jadwal");
		for (int i=0; i<FileReaderMachine.getCourseSize(); i++) {
			System.out.print(FileReaderMachine.getCourseAtIdx(i).getCourseName() +";" + FileReaderMachine.getCourseAtIdx(i).getRoomConstraint() + ";" + FileReaderMachine.getCourseAtIdx(i).getStartHourConstraint() +";"+ FileReaderMachine.getCourseAtIdx(i).getEndHourConstraint()
				+";"+FileReaderMachine.getCourseAtIdx(i).getTotalCredit()  + ";");
			for(int j=0; j<FileReaderMachine.getCourseAtIdx(i).getDayConstraint().length; j++) {
				System.out.print(FileReaderMachine.getCourseAtIdx(i).getDayConstraint()[j]+",");
			}
			System.out.println();
		}
	}
	
	/*
		Menginisialisasi dengan random variable
    */
    public void initializeSolutionRandomly() {
		for (int i =0; i < FileReaderMachine.getCourseSize(); i++) {
			int randomDay=0;
			int randomHour=0;
			int randomRoomIndex;
			boolean slotLock = true;
			String choosenRoomName;
			int randomDayIdx;
			int[] availDay;
			int j=0;
			int totalCredit;
			// Meramdom Course sampai course mendapatkan ruangan yang tidak terconstraint
			while (slotLock) {
				slotLock = false;
				if (FileReaderMachine.getCourseAtIdx(i).getRoomConstraint().equals("-")) {
					randomRoomIndex = randInt(0,FileReaderMachine.getRoomSize()-1);
					choosenRoomName = FileReaderMachine.getRoomAtIdx(randomRoomIndex).getRoomName();
				}
				else {
					choosenRoomName = FileReaderMachine.getCourseAtIdx(i).getRoomConstraint();
				}
				randomDayIdx = randInt(0,FileReaderMachine.getCourseAtIdx(i).getDayConstraint().length-1);
				availDay = FileReaderMachine.getCourseAtIdx(i).getDayConstraint();
				randomDay = availDay[randomDayIdx];
				randomHour = randInt(FileReaderMachine.getCourseAtIdx(i).getStartHourConstraint(),FileReaderMachine.getCourseAtIdx(i).getEndHourConstraint()-FileReaderMachine.getCourseAtIdx(i).getTotalCredit());
				j =0;
				while (!scheduleBoard[j].getRoom().getRoomName().equals(choosenRoomName)) {
					j++;
				}

				totalCredit =  FileReaderMachine.getCourseAtIdx(i).getTotalCredit();
				for (int k = 0; k < totalCredit; k++) {
					if (!scheduleBoard[j].isScheduleOpen(randomDay,randomHour+k)) {
						slotLock = true;
					}

				}
				/*if (scheduleBoard[j].isScheduleOpen(randomDay,randomHour+FileReaderMachine.getCourseAtIdx(i).getTotalCredit()-1)) {
					slotLock = false;
				}*/
			}
			scheduleBoard[j].insertCourseToSchedule(randomDay,randomHour,FileReaderMachine.getCourseAtIdx(i));
		}
			
	}
    
    /*
		Menghitung jumlah confict
    */
    public int countConflict() {
		int totalConflict = 0;
		for (int i=0; i< scheduleBoard.length; i++) {
			for (int day = 1; day < 6; day++) {
				for (int hour = 7; hour < 18; hour++) {
					totalConflict += scheduleBoard[i].getConflict(day,hour);
				}
			}
		}
		return totalConflict;
	}

	/*
		Mengembalikan bilangan random dari min sampai max
	*/
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		
		return randomNum;
	}

	/*
		Untuk mencari lokasi konflik maksimal pada seluruh board. 
		Mengembalikan indeks schedule dan koordinat slot yang memiliki konflik terbanyak dan tidak terkunci. 
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17)
		int [2] = schedule(1-n)

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
		int[2] = 0;
	*/

	public int[] getMaxConflictLocation() {
		int currentDay = 0;
		int currentHour = 0;
		int currentSchedule = 0;
		int currentConflict = 0;
		int[] result_temp = new int[2];
		int[] result = new int[3];
		for(int i=0; i < scheduleBoard.length; i++) {
			result_temp = scheduleBoard[i].getMaxConflictSlot();
			if((result_temp[0]!=0)&&(result_temp[1]!=0)) {
				if(currentConflict < scheduleBoard[i].getConflict(result_temp[0],result_temp[1])) {
					currentConflict = scheduleBoard[i].getConflict(result_temp[0],result_temp[1]);
					currentDay = result_temp[0];
					currentHour = result_temp[1];
					currentSchedule = i;
				}
			}
		}
		if (currentConflict == 0) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentHour;
			result[2] = currentSchedule;
		}
		return result;
	}
	/*
		Untuk mencara runtutan slot sebanyak total kredit yang memiliki jumlah konflik yang paling sedikit.
		Serta koordinat slot tidak terkunci. 
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17), merupakan jam terbaik untuk memulai course
		int [2] = jumlah konflik

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
		int[2] = 0;
	*/
	public int[] searchBestLocation(Course course) {
		int totalCredit = course.getTotalCredit();
		int currentConflict = 10000;
		int currentDay = 0;
		int currentStartHour = 0;
		int currentSchedule = 0;
		int[] result_temp = new int[3];
		int[] result = new int[3];
		for (int i =0; i < scheduleBoard.length; i++) {
			result_temp =  scheduleBoard[i].searchBestSlot(course);
			if ((result_temp[0]!=0)&&(result_temp[1]!=0)) {
				if(result_temp[2] < currentConflict) {
					currentConflict = result_temp[2];
					currentDay =  result_temp[0];
					currentStartHour = result_temp[1];
					currentSchedule = i;
				}
			}
		}
		if (currentConflict == 10000) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentStartHour;
			result[2] = currentSchedule;
		}
		return result;
	}
	/*
		Seperti seatchBestLocation tetapi hanya melakukan pencarian di sebuah schedule berdasarkan indeks.
	*/
	public int[] searchBestLocationOnSchedule(int scheduleIdx, Course course) {
		int totalCredit =  course.getTotalCredit();
		int[] result_temp = new int[3];
		int[] result = new int[3];
		result_temp = scheduleBoard[scheduleIdx].searchBestSlot(course);
		if ((result_temp[0]==0)&&(result_temp[1]==0)) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = result_temp[0];
			result[1] = result_temp[1];
			result[2] = scheduleIdx;
		}
		return result;
	}
	/*
		Menambahkan course ke schedule berdasarkan indeks schedule, hari dan jam mulai.
	*/
	public void insertCourse(Course course, int scheduleIdx, int day, int startHour) {
		scheduleBoard[scheduleIdx].insertCourseToSchedule(day,startHour,course);
	}
	/*
		Mengembalikan id dari course
	*/
	public int getLastInsertedCourseId(int scheduleIdx, int day, int hour) {
		return scheduleBoard[scheduleIdx].getLastInsertedCourseId(day,hour);
	}
	/*
		Mengambil dan mengapus course berdasarkan courseId pada day dan hour yang diberikan, kemudian membersihkan course
		serupa (memiliki id yang sama) di sekitar course tersebut.
	*/
	public Course getAndDeleteCourseById(int courseId, int scheduleIdx, int day, int hour) {
		return scheduleBoard[scheduleIdx].getAndDeleteCourseById(courseId,day,hour);
	}

	/*
		Mengembalikan credit yang tidak bentrok dalam satu schedule board
	*/
	public int getNTrueCredit(){
		int nCredit = 0;
		for(int i = 0;i < scheduleBoard.length;i++){
			nCredit = nCredit + scheduleBoard[i].getNTrueCreditSchedule();
		}

		return nCredit;
	}

	/*
		Mengembalikan akurasi
	*/
	public int getAccuracy(){
		if(FileReaderMachine.getNCredit() == 0)
			return 0;

		return (100 * getNTrueCredit()) / FileReaderMachine.getNCredit();
	}

	/*
		Mengembalikan indeks schedule yang memiliki nama ruangan = roomName
	*/
	public int getScheduleIdx(String roomName) {
		int i =0;
		boolean found = false;
		while ((!found)&&(i < scheduleBoard.length)) {
			if(roomName.equals(scheduleBoard[i].getRoom().getRoomName())) {
				found = true;
			}
			else {
				i++;
			}
		}
		return i;
	}
}
