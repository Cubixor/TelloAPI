package me.cubixor.telloapi.photo;

public class FileChunk {

    private final int fileChunkID;
    private final int length;
    private final byte[] data;

    public FileChunk(int fileChunkID, int length, byte[] data) {
        this.fileChunkID = fileChunkID;
        this.length = length;
        this.data = data;
    }

    public int getFileChunkID() {
        return fileChunkID;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "FileChunk{" +
                "fileChunkID=" + fileChunkID +
                ", length=" + length +
                ", data=" + data.length +
                '}';
    }
}
