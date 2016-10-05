public class GeneticAlgorithmString{
	private String[] schBoardStr;
	private float percentFitness;
	private float randomValue;

	public GeneticAlgorithmString(){
		ScheduleBoard board = new ScheduleBoard();
		schBoardStr = GeneticAlgorithm.getScheduleBoardToStr(board);

		//Fitness Function
		percentFitness = (float)board.getNTrueCredit()/(float)FileReaderMachine.getNCredit();
	}

	public void setSchBoardStr(String[] inputSchBoardStr){
		schBoardStr = inputSchBoardStr;
		ScheduleBoard board = new ScheduleBoard("empty");
		board = GeneticAlgorithm.getStrToScheduleBoard(schBoardStr);

		//Fitness Function
		percentFitness = (float)board.getNTrueCredit()/(float)FileReaderMachine.getNCredit();
	}

	/*
		Merubah random value
	*/
	public void setRandomValue(){
		randomValue = percentFitness * FileReaderMachine.randInt(1,25);		
	}

	/*
		Get String schedule board
	*/
	public String[] getSchBoardStr(){
		return schBoardStr;
	}

	/*
		Get percent fitness function
	*/
	public float getFitness(){
		return percentFitness;
	}

	/*
		Mendapatkan nilai random value
	*/
	public float getRandomValue(){
		return randomValue;
	}

	/*
		Menghitung conflict yang ada dalam schedule board 
	*/
	public int getConflictCredit(){
		ScheduleBoard board = new ScheduleBoard("empty");
		board = GeneticAlgorithm.getStrToScheduleBoard(schBoardStr);

		int conflictCredit = FileReaderMachine.getNCredit() - board.getNTrueCredit();

		return conflictCredit;
	}

	/*
	
	*/
	public boolean isValid(){
		ScheduleBoard board = GeneticAlgorithm.getStrToScheduleBoard(schBoardStr);
		Schedule[] schedules = board.getAllSchedule();

		boolean valid = true;

		int nCredit = 0;
		for(int i = 0;i < schedules.length;i++){
			nCredit = schedules[i].getNCredit();
		}

		if(nCredit == FileReaderMachine.getNCredit()){
			valid = true;
		}else{
			valid = false;
		}


		return valid;
	}

}