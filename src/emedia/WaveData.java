package emedia;

public class WaveData {
	byte[] chunkID = new byte[4];
    int chunkSize;
    byte[] format = new byte[4];
    byte[] subChunk1ID = new byte[4];
    int subChunk1Size;
    short audioFormat;
    short numChannels;
    int sampleRate;
    int byteRate;
    short blockAlign;
    short bitsPerSample;
    byte[] subChunk2ID = new byte[4];
    int subChunk2Size;


    public byte[] getChunkID() {
        return chunkID;
    }

    public void setChunkID(byte[] chunkID) {
        this.chunkID = chunkID;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public byte[] getFormat() {
        return format;
    }

    public void setFormat(byte[] format) {
        this.format = format;
    }

    public byte[] getSubChunk1ID() {
        return subChunk1ID;
    }

    public void setSubChunk1ID(byte[] subChunk1ID) {
        this.subChunk1ID = subChunk1ID;
    }

    public int getSubChunk1Size() {
        return subChunk1Size;
    }

    public void setSubChunk1Size(int subChunk1Size) {
        this.subChunk1Size = subChunk1Size;
    }

    public short getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(short audioFormat) {
        this.audioFormat = audioFormat;
    }

    public short getNumChannels() {
        return numChannels;
    }

    public void setNumChannels(short numChannels) {
        this.numChannels = numChannels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getByteRate() {
        return byteRate;
    }

    public void setByteRate(int byteRate) {
        this.byteRate = byteRate;
    }

    public short getBlockAlign() {
        return blockAlign;
    }

    public void setBlockAlign(short blockAlign) {
        this.blockAlign = blockAlign;
    }

    public short getBitsPerSample() {
        return bitsPerSample;
    }

    public void setBitsPerSample(short bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public byte[] getSubChunk2ID() {
        return subChunk2ID;
    }

    public void setSubChunk2ID(byte[] subChunk2ID) {
        this.subChunk2ID = subChunk2ID;
    }

    public int getSubChunk2Size() {
        return subChunk2Size;
    }

    public void setSubChunk2Size(int subChunk2Size) {
        this.subChunk2Size = subChunk2Size;
    }

    @Override
    public String toString() {
        return "The RIFF chunk desriptor: " + new String(this.getChunkID())+
                "\nSize of this chunk: " + this.getChunkSize() +
                "\nFormat: " + new String(this.getFormat()) +
                "\nfmt subchunk: " + new String(this.getSubChunk1ID()) +
                "\nSize of this chunk: " + this.getSubChunk1Size() +
                "\nAudio format: " + this.getAudioFormat() +
                "\nNumber of channels: " + this.getNumChannels() +
                "\nSample rate: " + this.getSampleRate() +
                "\nByte rate: " + this.getByteRate() +
                "\nBlock align: " + this.getBlockAlign() +
                "\nBits per sample: " + this.getBitsPerSample() +
                "\ndata subchunk: " + new String(this.getSubChunk2ID()) + 
                "\nSize of this chunk: " + this.getSubChunk2Size();
    }
}
