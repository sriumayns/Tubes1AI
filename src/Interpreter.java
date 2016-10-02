import java.util.*;

public class Interpreter{
	public static class BeRoom{

		// Strict data untuk debugging
		private Boolean strict;

		// nama ruangan
		private String namaRuangan;

		// jam buka dan tutup kelas
		private int startHour, endHour;

		// array ketersedaan kelas pada hari senin sampai jumat
		private ArrayList<Boolean> availableDay = new ArrayList<Boolean>(5);

		// jadwal penggunaan ruangan berupa list dua dimensi [hari][jam]
		private ArrayList<ArrayList<String>> jadwal = new ArrayList<ArrayList<String>>(5);

		// Konstruktor
		// parameter awal "st" singkatan dari strict
		// jika false maka data yang salah akan tetap dimasukkan kedalam output
		// misalnya, jika available day adalah senin, rabu, kamis
		// ketika terdapat kelas pada hari jumat, kelas tersebut akan tercetak pada keluaran
		// sebaliknya jika true maka data tersebut akan diabaikan karena berada diluar
		// keadaan yang diinginkan
		public BeRoom(Boolean st){
			strict = st;
			for(int i = 0; i < 5; i++){
				jadwal.add(i, new ArrayList<String>());
				for(int j = 0; j <= 17; j++){
					jadwal.get(i).add("");
				}
			}
		}

		// Mengeset nama ruangan (kelas ini)
		public void setNamaRuangan(String str){
			namaRuangan = str;
		}

		// Jam tersedia kelas mulai buka hingga tutup
		// sementara jam buka kelas harus kontinu, 
		// tidak boleh terpisah seperti jam 07.00 - 11.00, 13.00 - 15.00
		public void setStartEndHour(int a, int b){
			startHour = a;
			endHour = b;
		}

		// Hari ketersediaan kelas dibuka
		// a: Senin, b: Selasa, dst sampai Jumat
		public void setAvailableDay(boolean a, boolean b, boolean c, boolean d, boolean e){
			availableDay.add(0, a);
			availableDay.add(1, b);
			availableDay.add(2, c);
			availableDay.add(3, d);
			availableDay.add(4, e);
		}

		// Menambah jadwal kelas pada hari ke-"index" dan jam ke-"jam" dengan
		// nilai "alVal"
		// Atribut strict (jika true) akan melakukan validasi penambahan data jika terdapat
		// ketidaksesuaian hari dan jam
		public void addJadwal(int index, int jam, String alVal){
			if((0 <= index && index <= 4) || !strict)
				if(availableDay.get(index) || !strict)
					if((7 <= jam  && jam <= 17) || !strict)
						if((getStartHour() <= jam && jam <= getEndHour()) || !strict){
							if(jadwal.get(index).get(jam) != "")
								jadwal.get(index).set(jam, jadwal.get(index).get(jam) + "<br/>" + alVal);
							else
								jadwal.get(index).set(jam, alVal);
						}
		}

		// Mengembalikan nama ruangan
		public String getNamaRuangan(){
			return namaRuangan;
		}

		// Mengembalikan jam kelas dibuka
		public int getStartHour(){
			return startHour;
		}

		// Mengembalikan jam kelas ditutup
		public int getEndHour(){
			return endHour;
		}

		// Mengebalikan array ketersediaan kelas dibuka
		// dalam array boolean (member = 5)
		public ArrayList<Boolean> getAvailableDay(){
			return availableDay;
		}

		// Mengembalikan list jadwal yang tersedia pada hari ke-i
		// berupa array list string
		public ArrayList<String> getJadwal(int i){
			return jadwal.get(i);
		}

		// Hampir sama dengan getJadwal(i), bedanya metode ini
		// mengembalikan string JSON array dari jadwal pada
		// hari ke-i mulai jam 7 hingga jam 17
		public String printJadwal(int k){
			String output = "";
			output += "[";
			for(int i = 0; i <= 17; i++)
				if(i != 17)
					output += "\"" + jadwal.get(k).get(i) + "\", ";
				else
					output += "\"" + jadwal.get(k).get(i) + "\"";

			output += "]";

			return output;
		}

		// Seluruh atribut kelas di cetak dalam bentuk JSON String
		// sesuai dengan kebutuhan pada interpreter PHP
		public String getOutput(){
			String output = "";
			output += "{";
			output += "\"nama_ruangan\": \"" + getNamaRuangan() + "\",";
			output += "\"hour\": {";
			output += "\"start\": \"" +  getStartHour() + "\",";
			output += "\"end\": \"" + getEndHour() + "\"";
			output += "},";
			output += "\"available\": " + getAvailableDay() + ",";
			output += "\"jadwal\": [";
			output += printJadwal(0);
			output += ", ";
			output += printJadwal(1);
			output += ", ";
			output += printJadwal(2);
			output += ", ";
			output += printJadwal(3);
			output += ", ";
			output += printJadwal(4);
			output += "]";
			output += "}";

			return output;
		}
	}

	public static class MasterRoom{
		// list room
		private ArrayList<BeRoom> arrRoom;
		private float akurasi;
		private int konflik;

		// Konstruktor
		// melakukan inisialisasi array list
		// jumlah ruangan yang digunakan bisa berapa saja
		public MasterRoom(){
			konflik = 0;
			akurasi = 0f;
			arrRoom = new ArrayList<BeRoom>();
		}

		// menambahkan ruangan baru
		public void addRoom(BeRoom br){
			arrRoom.add(br);
		}

		public void setKonflik(int k){
			konflik = k;
		}

		public void setAkurasi(float a){
			akurasi = a;
		}

		public int getKonflik(){
			return konflik;
		}

		public float getAkurasi(){
			return akurasi;
		}

		// mengembalikan ruangan pada indeks ke-"index"
		public BeRoom getRoom(int index){
			return arrRoom.get(index);
		}

		// mengembalikan string output seluruh ruangan dalam bentuk
		// JSON array sesuai dengan kebutuhan interpreter pada PHP
		public String getAllOutput(){
			String output = "{";
			output += "\"konflik\": " + getKonflik() + ", ";
			output += "\"akurasi\": " + getAkurasi() + ", ";
			output += "\"data_tabel\": [";
			for(int i = 0; i < arrRoom.size(); i++){
				output += getRoom(i).getOutput();
				if(i != (arrRoom.size() - 1))
					output += ", ";
			}
			output += "]";
			output += "}";

			return output;
		}
	}

	// public static void main(String[] args){
	// 	MasterRoom mr = new MasterRoom();
	// 	mr.setKonflik(5);
	// 	mr.setAkurasi(25.67f);

	// 	mr.addRoom(new BeRoom(true));
	// 	BeRoom mp;
	// 	mp = mr.getRoom(0);
	// 	mp.setNamaRuangan("Labdas II Informatika");
	// 	mp.setStartEndHour(7, 15);
	// 	mp.setAvailableDay(true, true, true ,false, false);
	// 	mp.addJadwal(0, 7, "IF3110");
	// 	mp.addJadwal(0, 8, "IF3110");
	// 	mp.addJadwal(0, 11, "IF4070");
	// 	mp.addJadwal(0, 12, "IF4070");

	// 	mr.addRoom(new BeRoom(true));
	// 	mp = mr.getRoom(1);
	// 	mp.setNamaRuangan("7602");
	// 	mp.setStartEndHour(9, 17);
	// 	mp.setAvailableDay(true, true, false, true, false);
	// 	mp.addJadwal(3, 9, "IF3110");
	// 	mp.addJadwal(3, 10, "IF3110");

	// 	// Contoh data konflik	
	// 	mp.addJadwal(1, 15, "IF4070");
	// 	mp.addJadwal(1, 16, "IF4070");
	// 	mp.addJadwal(1, 15, "IF1220");
	// 	mp.addJadwal(1, 16, "IF1220");

	// 	System.out.println(mr.getAllOutput());
	// }
}