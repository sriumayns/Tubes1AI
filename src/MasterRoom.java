import java.util.*;

public class MasterRoom{
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
