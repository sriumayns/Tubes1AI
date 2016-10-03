public class GeneticAlgorithm{
	private static int nIndividu = 0;
	private static GeneticAlgorithmString[] individu = new GeneticAlgorithmString[70];
	
	/*
		Fungsi untuk menjalankan Genetic Algorithm
	*/
	public static ScheduleBoard runGeneticAlgorithm(){
		nIndividu = 0;
		for (int i = 0;i < 4;i++) {
			individu[nIndividu] = new GeneticAlgorithmString();
			/*System.out.println(individu[i].getFitness());
			System.out.println(individu[i].getConflictCredit());
			ScheduleBoard board = new ScheduleBoard("empty");
			board = getStrToScheduleBoard(individu[i].getSchBoardStr());
			board.printScheduleBoard();
			System.out.println("================================================================");
			System.out.println("================================================================");
			System.out.println("================================================================");
			System.out.println("================================================================");
			System.out.println("================================================================");*/
			nIndividu++;
		}

		int idx = -1;
		int i = 0;
		while((idx == -1) && (i < 30)){

			for(int j = 0;j < nIndividu;j++){
				if(individu[j].getConflictCredit() == 0){
					idx = j;
				}
				individu[j].setRandomValue();
			}

			if(idx == -1){
				sortIndividuByRandom();
				individu[nIndividu] = new GeneticAlgorithmString();
				nIndividu++;

				String[] string1 = individu[0].getSchBoardStr();
				String[] string2 = individu[1].getSchBoardStr();
				String[] scheduleBoardStr = new String[5];
				int randomIndexs = FileReaderMachine.randInt(1,FileReaderMachine.getCourseSize()-1);
				//System.out.println("Ini random" + randomIndexs);
 				scheduleBoardStr[0] = string1[0].substring(0,randomIndexs) + string2[0].substring(randomIndexs,FileReaderMachine.getCourseSize()-1);
 				scheduleBoardStr[1] = string1[1].substring(0,randomIndexs) + string2[1].substring(randomIndexs,FileReaderMachine.getCourseSize()-1);
 				scheduleBoardStr[2] = string1[2].substring(0,randomIndexs) + string2[2].substring(randomIndexs,FileReaderMachine.getCourseSize()-1);
 				scheduleBoardStr[3] = string1[3].substring(0,randomIndexs) + string2[3].substring(randomIndexs,FileReaderMachine.getCourseSize()-1); 
 				scheduleBoardStr[4] = string1[4];

 				individu[nIndividu-1].setSchBoardStr(scheduleBoardStr);
			}

			i++;
		}

		if(idx != -1){
			return getStrToScheduleBoard(individu[idx].getSchBoardStr());	
		}else{
			sortIndividuByConflict();
			return getStrToScheduleBoard(individu[0].getSchBoardStr());	
		}
		
	} 


	/*
		Fungsi mengembalikan scheduleBoard dalam bentuk array of string
	*/
	public static String[] getScheduleBoardToStr(ScheduleBoard initialScheduleBoard){
		int scheduleLength = initialScheduleBoard.getScheduleBoardLength();
		Schedule[] tabSchedule = initialScheduleBoard.getAllSchedule();
		String[] scheduleBoardStr = new String[5];
		for(int i=0; i<5; i++){
			scheduleBoardStr[i] = "";
		}
		char iRoom = 'A';
		for(int i=0; i<scheduleLength; i++){ 
			for(int j=1; j<6; j++){
				char iHour = 'A';
				for(int k=7; k<18; k++){
					for(int l=0; l<tabSchedule[i].getSlot(j,k).getNumberOfCourse(); l++){
						String courseSchedule = iRoom + "";
						courseSchedule = courseSchedule + j;
						courseSchedule = courseSchedule + tabSchedule[i].getSlot(j,k).getCourse(l).getTotalCredit();
						courseSchedule = courseSchedule + getCourseCode(tabSchedule[i].getSlot(j,k).getCourse(l).getId());
						if (!isCourseWritten(scheduleBoardStr,courseSchedule)){
							scheduleBoardStr[0] = scheduleBoardStr[0] + iRoom;
							scheduleBoardStr[1] = scheduleBoardStr[1] + j;
							scheduleBoardStr[2] = scheduleBoardStr[2] + iHour;
							scheduleBoardStr[3] = scheduleBoardStr[3] + tabSchedule[i].getSlot(j,k).getCourse(l).getTotalCredit();
							scheduleBoardStr[4] = scheduleBoardStr[4] + getCourseCode(tabSchedule[i].getSlot(j,k).getCourse(l).getId());
						}
					}
					iHour++;
				}
			}
			iRoom++;
		}
		scheduleBoardStr = sortStrByCourse(scheduleBoardStr);
		/*for(int i=0; i<5; i++){
			System.out.println(scheduleBoardStr[i]);
		}*/
		return scheduleBoardStr;
	}

	/*
		Masukan array of string
		Fungsi mengembalikan schedule board
	*/
	public static ScheduleBoard getStrToScheduleBoard(String[] scheduleBoardStr){
		ScheduleBoard board = new ScheduleBoard("empty");
		Schedule[] scheduleBoard = board.getAllSchedule();
		int strLength = scheduleBoardStr[0].length();
		String roomStr = scheduleBoardStr[0];
		String dayStr = scheduleBoardStr[1];
		String hourStr = scheduleBoardStr[2];
		String creditStr = scheduleBoardStr[3];
		String courseStr = scheduleBoardStr[4];

		for(int i=0; i<strLength; i++){
			scheduleBoard[numberOfRoomCode(roomStr.charAt(i))].insertCourseToSchedule(Integer.parseInt(dayStr.charAt(i)+""), hourOfCode(hourStr.charAt(i)), getCourseFromCode(courseStr.charAt(i)));
		}
		return board;
	}

	/*
	*/
	public static char getCourseCode(int courseId){
		char code = 'A';
		int i = 0;
		boolean found = false;
		while (i<FileReaderMachine.getCourseSize() && !found){
			if (FileReaderMachine.getCourseAtIdx(i).getId()==courseId){
				found = true;
			} else {
				code++;
				i++;	
			}
			
		}
		return code;
	}
	public static Course getCourseFromCode(char courseCode){
		char code = 'A';
		int i = 0;
		boolean found = false;
		while (i<FileReaderMachine.getCourseSize() && !found){
			if (courseCode == code){
				found = true;
			} else {
				code++;
				i++;	
			}
			
		}
		return FileReaderMachine.getCourseAtIdx(i);	
	}

	public static int numberOfRoomCode(char roomCode){
		char code = 'A';
		int i = 0;
		boolean found = false;
		while (i<FileReaderMachine.getRoomSize() && !found){
			if (roomCode==code){
				found = true;
			} else {
				code++;
				i++;	
			}
			
		}
		return i;
	}

	public static int hourOfCode(char hourCode){
		char code = 'A';
		int i = 7;
		boolean found = false;
		while (i<18 && !found){
			if (hourCode==code){
				found = true;
			} else {
				code++;
				i++;	
			}
			
		}
		return i;	
	}

	public static boolean isCourseWritten(String[] scheduleStr, String courseSchedule){
		int length = scheduleStr[0].length();
		boolean found = false;
		int i = 0;

		while(i<length && !found){
			String course = "";
			for(int j=0; j<5; j++){
				if (j!=2){
					course = course + scheduleStr[j].charAt(i);
				}
			}
			
			if(course.equals(courseSchedule)){
				found = true;
			} else {
				i++;
			}

		}
		return found;
	}

	/*
		Mengurutkan individu bersarkan nilai random
	*/
	private static void sortIndividuByRandom(){
		boolean sorted = false;

		while(!sorted){
			sorted = true;
			for (int i = 0;i < nIndividu - 1;i++ ) {
				if(individu[i].getRandomValue() < individu[i+1].getRandomValue()){
					GeneticAlgorithmString temp = individu[i];
					individu[i] = individu[i+1];
					individu[i+1] = temp;
					sorted = false;
				}

			}
		}
	}

	/*

	*/

	private static void sortIndividuByConflict(){
		boolean sorted = false;

		while(!sorted){
			sorted = true;
			for (int i = 0;i < nIndividu - 1;i++ ) {
				if(individu[i].getConflictCredit() > individu[i+1].getConflictCredit()){
					GeneticAlgorithmString temp = individu[i];
					individu[i] = individu[i+1];
					individu[i+1] = temp;
					sorted = false;
				}

			}
		}
	}
	private static String[] sortStrByCourse(String[] scheduleBoardStr){
		StringBuilder roomStr = new StringBuilder(scheduleBoardStr[0]);
		StringBuilder dayStr = new StringBuilder(scheduleBoardStr[1]);
		StringBuilder hourStr = new StringBuilder(scheduleBoardStr[2]);
		StringBuilder creditStr = new StringBuilder(scheduleBoardStr[3]);
		StringBuilder courseStr = new StringBuilder(scheduleBoardStr[4]);
		
		char temp = ' ';
		for(int i=1; i<roomStr.length(); i++){
			for (int j=0; j<i; j++) {
				if(courseStr.charAt(j)>courseStr.charAt(i)){
					temp = roomStr.charAt(j);
					roomStr.setCharAt(j,roomStr.charAt(i));
					roomStr.setCharAt(i,temp);

					temp = dayStr.charAt(j);
					dayStr.setCharAt(j,dayStr.charAt(i));
					dayStr.setCharAt(i,temp);

					temp = hourStr.charAt(j);
					hourStr.setCharAt(j,hourStr.charAt(i));
					hourStr.setCharAt(i,temp);

					temp = creditStr.charAt(j);
					creditStr.setCharAt(j,creditStr.charAt(i));
					creditStr.setCharAt(i,temp);

					temp = courseStr.charAt(j);
					courseStr.setCharAt(j,courseStr.charAt(i));
					courseStr.setCharAt(i,temp);
				}
			}
		}
		String[] result = new String[5];
		result[0] = roomStr.toString();
		result[1] = dayStr.toString();
		result[2] = hourStr.toString();
		result[3] = creditStr.toString();
		result[4] = courseStr.toString();
		return result;

	}
}