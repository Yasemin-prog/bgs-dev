import java.io.BufferedReader; //karakterlerin dosyalara yazýlmasýný saðlayan kütüphane
import java.io.File;
import java.io.FileInputStream; // dosya okumak için gerekli olan kütüphane
import java.io.InputStreamReader; //Karakter tabanlý dosyalardaki karakterlerin okunmasýný saðlar
import java.util.ArrayList;
import java.util.HashMap;

public class main {

	// türkçe kelimeleri tuttacagýmýz listemiz
	static ArrayList<String> trCharcer = new ArrayList<String>();
	// sezar þifre kýrma methotomuz için es geçilecek karakterler
	static String esGec = "ðÐçÇþÞüÜöÖýÝ.,'“”!? ";

	static HashMap<Integer, Character> alfabe = new HashMap<Integer, Character>();
	static HashMap<Character, Integer> asciAlfabe = new HashMap<Character, Integer>();
	// harfleri string olarak tanýmlýyoruz text inizde bulunacak olan karakterleri alta ekleyebilirsiniz
	static String Harf = "abcçdefgðhýijklmnoöprsþtuüvyzABCÇDEFGÐHIÝJKLMNOÖPRSÞTUÜVYZ0123456789., /*-+'";

	public static void main(String[] args) {
		// txt belgelerindeki türkçe kelimeleri liste haline getirip trCharcer listemize
		// atýyoruz. tek bir text içinde bulamadým tüm kelimeleri üzgünüm :(
		getString("Birleþtirilmiþ_Sözlük_Kelime_Listesi");
		getString("ortak_kelimeler");
		getString("tdk_fark_zemberek");
		getString("TDK_Sözlük_Kelime_Listesi");
		getString("zemberek_fark_tdk");
		// özel þifrelememiz için gereken alfabemizi oluþturan methot
		alfabeEkle();

		// üssteki iþlemler kurulum içindir ---

		// metnimizi offset vererek þifreliyoruz
		String sezarSifreli = sezarSifrele("Yasemin", 7);
		System.out.println("sezarSifreli text :" + sezarSifreli);

		// sezarSifreKir methotumuza sadece þifrelenmiþ mesajýmýzý veriyoruz ve sonucu
		// merakla bekliyoruz :)
		String sezarKir = sezarSifreKir(sezarSifreli);
		System.out.println("Dönen cevap " + sezarKir);

		// sezar metodu ile þifreledigimiz text i dogruluyoruz
		String sezarCozulmus = sezarSifreCoz(sezarSifreli, 3);
		System.out.println("sezarSifreli text :" + sezarCozulmus);

		// bu kýsýmda yasemin text ini biliþim key i ile þifreliyoruz
		String s = keyliSifrele("Tahmin Ediyorum Bu þifreleme, '/*-+' bu özel karakterler ile bile çalýþýr.", "Biliþim");
		// çýkan þifrelenmiþ metin
		System.out.println("özel key ile þifrelenmniþ text :" + s);
		// bu kýsýmda da þifreli text i biliþim keyi ni kullanarak çözüyoruz
		System.out.println(keyliSifreCoz(s, "Biliþim"));
	}

	// üstte belirttigimiz harf stringindeki degerleri daha kolay iþleyebilmek için
	// hash map a atýyoruz
	public static void alfabeEkle() {
		for (int i = 0; i < Harf.length(); i++) {
			// index den harf çagýrmak için alfabe map ine
			alfabe.put(i, Harf.charAt(i));
			asciAlfabe.put(Harf.charAt(i), i);
		}
		System.out.println(alfabe);
	}

	// text dosyadaki kelimeleri çeken methotumuz
	public static void getString(String fileName) {
		// .txt dosyalarýn bulundugu yol
		File file = new File(
				"C:\\\\Users\\\\Yaasemin\\\\Desktop\\\\eclipse\\\\YaseminAtes\\\\src\\\\kelimeler\\\\" + fileName + ".txt");

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			String str;

			while ((str = in.readLine()) != null) {
				// dosya imindeki her bir satýrda bulunan String i listemize ekliyoruz
				trCharcer.add(str);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// bu methot 2. kýsým içindir
	private static String keyliSifrele(String text, String key) {
		String sifreli = "";
		int offset = 0;
		// gelen key in her bir karakterini asci kodunu listeye atýyoruz
		char[] keyToCharList = key.toCharArray();
		// gelen text in her bir karakterini asci kodunu listeye atýyoruz
		char[] textToCharList = text.toCharArray();

		// text in her bir karakterini þifrelemek için for loop a sokuyoruz
		for (int i = 0; i < textToCharList.length; i++) {
			// key ile text in o indisindeki karakterlerin asciAlfabe map indeki karþýlýk
			// gelen rakamlarý birbirinden çýkartýyoruz
			offset = asciAlfabe.get(textToCharList[i]) + asciAlfabe.get(keyToCharList[i % keyToCharList.length]);

			if (offset != 0)
				sifreli += alfabe.get(offset % alfabe.size());
			else
				sifreli += alfabe.get(offset);
		}
		// þifreledigimiz metini geri veriyoruz
		return sifreli;

	}

	// bu methot 2. kýsým içindir
	private static String keyliSifreCoz(String text, String key) {
		String sifreli = "";
		int offset = 0;
		// gelen key in her bir karakterini asci kodunu listeye atýyoruz
		char[] keyToCharList = key.toCharArray();
		// gelen text in her bir karakterini asci kodunu listeye atýyoruz
		char[] textToCharList = text.toCharArray();

		// text in her bir karakterini þifrelemek için for loop a sokuyoruz
		for (int i = 0; i < textToCharList.length; i++) {
			// key ile text in o indisindeki karakterlerin asciAlfabe map indeki karþýlýk
			// gelen rakamlarý birbirinden çýkartýyoruz
			offset = asciAlfabe.get(textToCharList[i]) - asciAlfabe.get(keyToCharList[i % keyToCharList.length]);

			if (offset != 0)
				if (offset > 0)// offset + degerdeyse direk çýkartýyoruz
					sifreli += alfabe.get(offset % alfabe.size());
				else// bu kisim þifre çözerken eger çýkartýlmasý gereken offset - degere düþer ise sondan geriye dogru gidiyoruz
					sifreli += alfabe.get(alfabe.size()+(offset % alfabe.size()));
			else// gelen offset sýfýr ise bölme iþlemi yaptýrmýyoruz mat hatasý alýrýz 
				sifreli += alfabe.get(offset);
		}
		// þifreledigimiz metini geri veriyoruz
		return sifreli;

	}

	public static String sezarSifreKir(String sifreli) {
		String kirilanSifre = "";
		String[] sifreliList = sifreli.split(" ");
		int offset = 0;
		boolean kirildi = false;
		// ofset max alfabe kadar ya da katý olucagýndan 26 kere dönecek döngüye
		// sokuyoruz
		while (offset < 27) {
			// cümleninm ilk kelimesini kýrmayý deniyoruz sonuç alýrsak þifre çözülmüþ
			// oluyor
			String s1 = sezarSifreCoz(sifreliList[0], offset);
			if (trCharcer.contains(s1.toLowerCase())) {
				kirilanSifre += s1 + " ";

			} else {
				kirilanSifre = "";
				offset += 1;

			}

			// eger kýrýlan þifre degiþkeni boþ degilse bir kelime bulunmuþ demektir bu
			// aþamada tahmini ofsetimiz çýkmýþ oluyor
			if (kirilanSifre != "") {
				// buldugumuz ofset ile tüm metni kýrýyoruz
				kirilanSifre = sezarSifreCoz(sifreli, offset);
				// artýk dödgüyle iþimiz kalmadýgý için kýrýyoruz döngümüzü
				break;
			}
		}

		return "Tahmini kýrýlan þifre : " + kirilanSifre + " tahmin ettigimiz offset :" + offset;
	}

	// assci tablosuna göre sezar þifreleme methotumuz

	public static String sezarSifrele(String metin, int oteleMod) {
		try

		{
			// ötelenecek offsetin alfabeyi taþmamasý için modunu alýyoruz
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

	// assci tablosuna göre þifrelenmiþ sezar þifresi kýrma methotumuz
	// þifreleme methotundan tek farký - çýkartma iþlemi olan kýsýmlardýr
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
