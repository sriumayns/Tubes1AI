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
		
		
		if(args[2].equals("console")){
			board.printScheduleBoard();
			System.out.println("======================================================================================");		
			System.out.println("Conflict: "+board.countConflict());
			System.out.println("Accuracy: "+board.getAccuracy());
			return;
		}

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

		MasterRoom mr = new MasterRoom();
		mr.setKonflik(board.countConflict());
		mr.setAkurasi(25.67f);


		for (int idx=0; idx < FileReaderMachine.getRoomSize(); idx++)
			mr.addRoom(new BeRoom(false));

		for (int idx=0; idx < FileReaderMachine.getRoomSize(); idx++){
			Schedule sch = board.getScheduleWithIndex(idx);

			BeRoom mp;
			mp = mr.getRoom(idx);
			mp.setNamaRuangan(sch.getRoom().getRoomName());
			mp.setStartEndHour(sch.getRoom().getStartHour(), sch.getRoom().getEndHour());
			int dayAvailable[] = sch.getRoom().getAvailableDay();

			Boolean avail[] = new Boolean[6];
			for(int k = 0; k < 6; k++)
				avail[k] = false;

			for(int k = 0; k < dayAvailable.length; k++)
				avail[dayAvailable[k]] = true;

			mp.setAvailableDay(avail[1], avail[2], avail[3], avail[4], avail[5]);


			for (int hour = 7; hour<18; hour++)
				for (int day = 1; day <6;day++)
					if (sch.getSlot(day, hour).getNumberOfCourse() > 0)
						for (int i=0; i< sch.getSlot(day, hour).getNumberOfCourse(); i++)
							mp.addJadwal(day, hour, sch.getSlot(day, hour).getCourseWithIndex(i).getCourseName());
							// System.out.print(sch.getRoom().getSlot(day, hour).getCourseWithIndex(i).getCourseName());
		}

		System.out.println(mr.getAllOutput());
		
	}
}
