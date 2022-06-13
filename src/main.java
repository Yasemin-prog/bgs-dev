import java.io.BufferedReader; //karakterlerin dosyalara yaz�lmas�n� sa�layan k�t�phane
import java.io.File;
import java.io.FileInputStream; // dosya okumak i�in gerekli olan k�t�phane
import java.io.InputStreamReader; //Karakter tabanl� dosyalardaki karakterlerin okunmas�n� sa�lar
import java.util.ArrayList;
import java.util.HashMap;

public class main {

	// t�rk�e kelimeleri tuttacag�m�z listemiz
	static ArrayList<String> trCharcer = new ArrayList<String>();
	// sezar �ifre k�rma methotomuz i�in es ge�ilecek karakterler
	static String esGec = "������������.,'��!? ";

	static HashMap<Integer, Character> alfabe = new HashMap<Integer, Character>();
	static HashMap<Character, Integer> asciAlfabe = new HashMap<Character, Integer>();
	// harfleri string olarak tan�ml�yoruz text inizde bulunacak olan karakterleri alta ekleyebilirsiniz
	static String Harf = "abc�defg�h�ijklmno�prs�tu�vyzABC�DEFG�HI�JKLMNO�PRS�TU�VYZ0123456789., /*-+'";

	public static void main(String[] args) {
		// txt belgelerindeki t�rk�e kelimeleri liste haline getirip trCharcer listemize
		// at�yoruz. tek bir text i�inde bulamad�m t�m kelimeleri �zg�n�m :(
		getString("Birle�tirilmi�_S�zl�k_Kelime_Listesi");
		getString("ortak_kelimeler");
		getString("tdk_fark_zemberek");
		getString("TDK_S�zl�k_Kelime_Listesi");
		getString("zemberek_fark_tdk");
		// �zel �ifrelememiz i�in gereken alfabemizi olu�turan methot
		alfabeEkle();

		// �ssteki i�lemler kurulum i�indir ---

		// metnimizi offset vererek �ifreliyoruz
		String sezarSifreli = sezarSifrele("Yasemin", 7);
		System.out.println("sezarSifreli text :" + sezarSifreli);

		// sezarSifreKir methotumuza sadece �ifrelenmi� mesaj�m�z� veriyoruz ve sonucu
		// merakla bekliyoruz :)
		String sezarKir = sezarSifreKir(sezarSifreli);
		System.out.println("D�nen cevap " + sezarKir);

		// sezar metodu ile �ifreledigimiz text i dogruluyoruz
		String sezarCozulmus = sezarSifreCoz(sezarSifreli, 3);
		System.out.println("sezarSifreli text :" + sezarCozulmus);

		// bu k�s�mda yasemin text ini bili�im key i ile �ifreliyoruz
		String s = keyliSifrele("Tahmin Ediyorum Bu �ifreleme, '/*-+' bu �zel karakterler ile bile �al���r.", "Bili�im");
		// ��kan �ifrelenmi� metin
		System.out.println("�zel key ile �ifrelenmni� text :" + s);
		// bu k�s�mda da �ifreli text i bili�im keyi ni kullanarak ��z�yoruz
		System.out.println(keyliSifreCoz(s, "Bili�im"));
	}

	// �stte belirttigimiz harf stringindeki degerleri daha kolay i�leyebilmek i�in
	// hash map a at�yoruz
	public static void alfabeEkle() {
		for (int i = 0; i < Harf.length(); i++) {
			// index den harf �ag�rmak i�in alfabe map ine
			alfabe.put(i, Harf.charAt(i));
			asciAlfabe.put(Harf.charAt(i), i);
		}
		System.out.println(alfabe);
	}

	// text dosyadaki kelimeleri �eken methotumuz
	public static void getString(String fileName) {
		// .txt dosyalar�n bulundugu yol
		File file = new File(
				"C:\\\\Users\\\\Yaasemin\\\\Desktop\\\\eclipse\\\\YaseminAtes\\\\src\\\\kelimeler\\\\" + fileName + ".txt");

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			String str;

			while ((str = in.readLine()) != null) {
				// dosya imindeki her bir sat�rda bulunan String i listemize ekliyoruz
				trCharcer.add(str);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// bu methot 2. k�s�m i�indir
	private static String keyliSifrele(String text, String key) {
		String sifreli = "";
		int offset = 0;
		// gelen key in her bir karakterini asci kodunu listeye at�yoruz
		char[] keyToCharList = key.toCharArray();
		// gelen text in her bir karakterini asci kodunu listeye at�yoruz
		char[] textToCharList = text.toCharArray();

		// text in her bir karakterini �ifrelemek i�in for loop a sokuyoruz
		for (int i = 0; i < textToCharList.length; i++) {
			// key ile text in o indisindeki karakterlerin asciAlfabe map indeki kar��l�k
			// gelen rakamlar� birbirinden ��kart�yoruz
			offset = asciAlfabe.get(textToCharList[i]) + asciAlfabe.get(keyToCharList[i % keyToCharList.length]);

			if (offset != 0)
				sifreli += alfabe.get(offset % alfabe.size());
			else
				sifreli += alfabe.get(offset);
		}
		// �ifreledigimiz metini geri veriyoruz
		return sifreli;

	}

	// bu methot 2. k�s�m i�indir
	private static String keyliSifreCoz(String text, String key) {
		String sifreli = "";
		int offset = 0;
		// gelen key in her bir karakterini asci kodunu listeye at�yoruz
		char[] keyToCharList = key.toCharArray();
		// gelen text in her bir karakterini asci kodunu listeye at�yoruz
		char[] textToCharList = text.toCharArray();

		// text in her bir karakterini �ifrelemek i�in for loop a sokuyoruz
		for (int i = 0; i < textToCharList.length; i++) {
			// key ile text in o indisindeki karakterlerin asciAlfabe map indeki kar��l�k
			// gelen rakamlar� birbirinden ��kart�yoruz
			offset = asciAlfabe.get(textToCharList[i]) - asciAlfabe.get(keyToCharList[i % keyToCharList.length]);

			if (offset != 0)
				if (offset > 0)// offset + degerdeyse direk ��kart�yoruz
					sifreli += alfabe.get(offset % alfabe.size());
				else// bu kisim �ifre ��zerken eger ��kart�lmas� gereken offset - degere d��er ise sondan geriye dogru gidiyoruz
					sifreli += alfabe.get(alfabe.size()+(offset % alfabe.size()));
			else// gelen offset s�f�r ise b�lme i�lemi yapt�rm�yoruz mat hatas� al�r�z 
				sifreli += alfabe.get(offset);
		}
		// �ifreledigimiz metini geri veriyoruz
		return sifreli;

	}

	public static String sezarSifreKir(String sifreli) {
		String kirilanSifre = "";
		String[] sifreliList = sifreli.split(" ");
		int offset = 0;
		boolean kirildi = false;
		// ofset max alfabe kadar ya da kat� olucag�ndan 26 kere d�necek d�ng�ye
		// sokuyoruz
		while (offset < 27) {
			// c�mleninm ilk kelimesini k�rmay� deniyoruz sonu� al�rsak �ifre ��z�lm��
			// oluyor
			String s1 = sezarSifreCoz(sifreliList[0], offset);
			if (trCharcer.contains(s1.toLowerCase())) {
				kirilanSifre += s1 + " ";

			} else {
				kirilanSifre = "";
				offset += 1;

			}

			// eger k�r�lan �ifre degi�keni bo� degilse bir kelime bulunmu� demektir bu
			// a�amada tahmini ofsetimiz ��km�� oluyor
			if (kirilanSifre != "") {
				// buldugumuz ofset ile t�m metni k�r�yoruz
				kirilanSifre = sezarSifreCoz(sifreli, offset);
				// art�k d�dg�yle i�imiz kalmad�g� i�in k�r�yoruz d�ng�m�z�
				break;
			}
		}

		return "Tahmini k�r�lan �ifre : " + kirilanSifre + " tahmin ettigimiz offset :" + offset;
	}

	// assci tablosuna g�re sezar �ifreleme methotumuz

	public static String sezarSifrele(String metin, int oteleMod) {
		try

		{
			// �telenecek offsetin alfabeyi ta�mamas� i�in modunu al�yoruz
			int otele = oteleMod % 26;
			char[] harfler = metin.toCharArray();

			String sifreli = "";

			System.out.println(harfler);
			for (int i = 0; i < harfler.length; i++) {
				if (esGec.contains(Character.toString(harfler[i]))) {
					sifreli += Character.toString(harfler[i]);
				} else {
					int ote = (harfler[i] + otele);
					if (96 < ote) {
						if (ote > 122) {
							int tote = ote - 122;
							sifreli += Character.toString((char) (96 + tote));
						} else {
							sifreli += Character.toString((char) (ote));
						}
					} else {
						if (ote > 90) {
							int tote = ote - 90;
							sifreli += Character.toString((char) (64 + tote));
						} else {
							sifreli += Character.toString((char) (ote));
						}
					}
				}

			}

			return sifreli;

		}

		catch (Exception ex)

		{
			System.out.println(ex.getMessage());

			return null;

		}

	}

	// assci tablosuna g�re �ifrelenmi� sezar �ifresi k�rma methotumuz
	// �ifreleme methotundan tek fark� - ��kartma i�lemi olan k�s�mlard�r
	public static String sezarSifreCoz(String metin, int oteleMod) {
		try

		{
			int otele = oteleMod % 26;

			char[] harfler = metin.toCharArray();

			String sifreli = "";

			for (int i = 0; i < harfler.length; i++) {
				if (esGec.contains(Character.toString(harfler[i]))) {
					sifreli += Character.toString(harfler[i]);
				} else {
					int ote = (harfler[i] - otele);
					if (64 < harfler[i] && 91 > harfler[i]) {
						if (ote < 65) {
							int tote = 65 - ote;

							sifreli += Character.toString((char) (91 - tote));
						} else {
							sifreli += Character.toString((char) (ote));
						}
					} else {
						if (ote < 97) {
							int tote = 97 - ote;
							sifreli += Character.toString((char) (123 - tote));
						} else {
							sifreli += Character.toString((char) (ote));
						}
					}
				}

			}

			return sifreli;

		}

		catch (Exception ex)

		{
			System.out.println(ex.getMessage());

			return null;

		}

	}

}
