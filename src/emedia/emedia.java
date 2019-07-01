package emedia;
import java.io.*;



public class emedia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WaveData waveData;
		String url = "duck.wav";
		try {
			WaveReader waveHeader = new WaveReader(url);
            waveData= waveHeader.read();
            System.out.println(waveData.toString());
            waveHeader.readData();
            System.out.println("**********************");
            
            waveHeader.spectrum();
            System.out.println("start encoding");
            waveHeader.encrypting();
            System.out.println("Succesfully exit");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + url + " not found!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
	}
}
