import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class Main{
	public static void main(String [] args){
		/*
			args[0] = pilihan, 	jika 1 = algoritma yang dijalankan hillclimbing
								jika 2 = algoritma yang dijalankan Simulated Anealing
								jika 3 = algoritma yang dijalankan genetic algoritma

			args[1] nama file

		*/
		String namaFile = args[1];

		try{
			FileReaderMachine.readTestCase(namaFile);	
		}catch(IOException e){

		}

		ScheduleBoard board ;

		if(args[0].equals("1")){
			board = HillClimbing.runHillClimbing();
		}else if(args[0].equals("2")){
			board = SimulatedAnnealing.runSimulatedAnnealing();
		}else{
			board = GeneticAlgorithm.runGeneticAlgorithm();
		}
		
		
		
		board.printScheduleBoard();
		System.out.println("======================================================================================");		
		System.out.println("Conflict: "+board.countConflict());

		/*
			Setiap ScheduleBoard memiliki beberapa Schedule, 
			cara mengambil semua schedule scheduleBoard.getAllSchedule() 
			Schedule merepresentasikan jadwal tiap ruangan,

			Setiap Schedule memiliki beberapa slot,
			slot dapat diisi beberapa matakuliah,
			cara mengambil slot schedule.getSlot(day,hour)
			day nilainya 1-5, dan hour nilainya 7-17

			setiap slot dapat diisi oleh beberapa matakuliah
			caramengambil banyaknya matakuliah yang ada dalam slot 
			slot.getNumberOfCourse(), dan cara mengambil course dalam slot
			slot.getCourseWithIndex(index)

			cara menampilkan nama mata kuliah course.getCourseName() 
		*/

		
		
	}
}
