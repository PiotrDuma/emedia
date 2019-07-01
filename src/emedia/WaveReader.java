package emedia;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;

public class WaveReader {

	private byte[] dataFile; // whole wav file
	private byte[] dataSample; // sample wav
	private int[] dataSampleInt; // sample wav

	private InputStream inputStream; // file
	private static int headerSize = 44; // 44bytes header length
	private byte[] buf = new byte[headerSize];
	private WaveData header = new WaveData(); // header

	public WaveReader() {
	} // empty costructor

	public WaveReader(String url) throws IOException { // new constructor
		inputStream = new FileInputStream(url);
	}

	public WaveReader(InputStream inputStream) { // existing source constructor
		this.inputStream = inputStream;
	}

	public WaveData read() throws IOException { // reading header to WaveData object
		int res = inputStream.read(buf);
		if (res != headerSize) {
			throw new IOException("Header reading failed");
		}
		header.setChunkID(Arrays.copyOfRange(buf, 0, 4));
		if (new String(header.getChunkID()).compareTo("RIFF") != 0) {
			throw new IOException("Format failed");
		}

		header.setChunkSize(toInt(4, false));
		header.setFormat(Arrays.copyOfRange(buf, 8, 12));
		header.setSubChunk1ID(Arrays.copyOfRange(buf, 12, 16));
		header.setSubChunk1Size(toInt(16, false));
		header.setAudioFormat(toShort(20, false));
		header.setNumChannels(toShort(22, false));
		header.setSampleRate(toInt(24, false));
		header.setByteRate(toInt(28, false));
		header.setBlockAlign(toShort(32, false));
		header.setBitsPerSample(toShort(34, false));
		header.setSubChunk2ID(Arrays.copyOfRange(buf, 36, 40));
		header.setSubChunk2Size(toInt(40, false));
		return header;
	}

	public byte[] concat(byte[] a, byte[] b) {
		int aLen = a.length;
		int bLen = b.length;
		byte[] c = new byte[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	public void readData() throws IOException { // reading header to WaveData object
		dataFile = new byte[inputStream.available()];

		int res = inputStream.read(dataFile);
		if (res != dataFile.length)
			throw new IOException("Wave data reading failed");

		dataSample = new byte[dataFile.length - headerSize];// copy data array to decode
		for (int i = 0; i < dataSample.length; i++) {
			dataSample[i] = dataFile[i + headerSize];
		}

		dataSampleInt = new int[dataSample.length / header.getBlockAlign()];
		int index = 0;
		for (int i = 0; i < header.getSubChunk2Size() - headerSize; i += header.getBlockAlign()) { // write sample data
																									// to int array
			int buf = ((dataSample[i] & 0xFF) << 24) + ((dataSample[i + 1] & 0xFF) << 16)
					+ ((dataSample[i + 2] & 0xFF) << 8) + (dataSample[i + 3] & 0xFF);
			dataSampleInt[index] = buf;
			index++;
		}
	}

	public double[] transform(double[] x) {
		double[] temp = new double[x.length / 2];
		int index = 0;
		for (int i = 0; i < x.length; i += 2) {
			temp[index] = Math.sqrt((x[i]) * (x[i]) + (x[i + 1]) * (x[i + 1]));
			index++;
		}
		return temp;
	}

	public void spectrum() {
		int size = 4096;// largestPowerOf2(dataSampleInt.length); //get first 4096 samples.
		int[] newArray = Arrays.copyOfRange(dataSampleInt, 0, size);
		double[] real = copyFromIntArray(newArray);
		double[] imag = new double[real.length];
		double[] transformed;
		transformed = FFT.fft(real, imag, true);
		double[] transformed1 = transform(transformed);

		GraphPanel graphpanel = new GraphPanel(transformed1);
		graphpanel.run();
	}

	public void encrypting() {
		RSA rsa = new RSA();
//		rsa.test();
		rsa.run(dataSampleInt);

		int[] encrypted = rsa.getEncrypted();

		System.out.println("Saving encrypted");
		byte[] local = integerArrayToByteArray(encrypted);
		byte[] temp = concat(buf, local);
		saveFile(temp, "encrypted.wav");
		System.out.println("Saving Decrypted");
		int[] decrypted = rsa.getDecrypted();
		byte[] local1 = integerArrayToByteArray(decrypted);
		byte[] temp1 = concat(buf, local1);
		saveFile(temp1, "decrypted.wav");
	}

	/*****************************************************/
	private int toInt(int start, boolean endian) {
		int k = (endian) ? 1 : -1;
		if (!endian) {
			start += 3;
		}
		return ((buf[start] & 0xFF) << 24) + ((buf[start + k * 1] & 0xFF) << 16) + ((buf[start + k * 2] & 0xFF) << 8)
				+ (buf[start + k * 3] & 0xFF);
	}

	private short toShort(int start, boolean endian) {
		short k = (endian) ? (short) 1 : -1;
		if (!endian) {
			start++;
		}
		return (short) ((buf[start] << 8) + (buf[start + k * 1]));
	}

	private static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	private static double[] toDoubleArray(byte[] byteArray) {
		int times = Double.SIZE / Byte.SIZE;
		double[] doubles = new double[byteArray.length / times];
		for (int i = 0; i < doubles.length; i++) {
			doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
		}
		return doubles;
	}

	private byte[] integerArrayToByteArray(int[] values) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(values.length * 4);
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(values);

		byte[] array = byteBuffer.array();
		return array;
	}
	
	//might be used for plot whole samples
	private int largestPowerOf2(int n) { 
		int res = 2;
		while (res < n) {
			int rem = res;
			res = (int) Math.pow(res, 2);
			if (res > n)
				return rem;
		}
		return res;
	}

	private static double[] copyFromIntArray(int[] source) {
		double[] dest = new double[source.length];
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
		return dest;
	}

	public byte[] getBuf() {
		return buf;
	}

	public WaveData getHeader() {
		return header;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getDataFile() {
		return dataFile;
	}

	public void saveFile(byte[] data, String name) {
		try {
			FileOutputStream os = new FileOutputStream(name);
			os.write(data);
			os.close();
		} catch (IOException e) {
			System.out.println("Cant save file " + name);
		}

	}
}
