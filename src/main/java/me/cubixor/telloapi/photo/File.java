package me.cubixor.telloapi.photo;

import java.util.HashMap;

public class File {

    private final int fileID;
    private final int fileSize;
    private final HashMap<Integer, FilePiece> filePieces = new HashMap<>();
    private int currentSize;
    private boolean received;
    private float percentageDone;

    public File(int fileID, int fileSize) {
        this.fileID = fileID;
        this.fileSize = fileSize;
        percentageDone = 0;
    }

    public byte[] toByteArray() {
        byte[] data = new byte[this.getFileSize()];

        int i = 0;
        for (FilePiece filePiece : this.getFilePieces().values()) {
            for (FileChunk fileChunk : filePiece.getFileChunks()) {
                if (fileChunk != null) {
                    for (byte b : fileChunk.getData()) {
                        data[i] = b;
                        i++;
                    }
                }
            }
        }

        return data;
    }

    public int getFileID() {
        return fileID;
    }

    public int getFileSize() {
        return fileSize;
    }

    public HashMap<Integer, FilePiece> getFilePieces() {
        return filePieces;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void addCurrentSize(int currentSize) {
        this.currentSize += currentSize;
        this.percentageDone = ((float) this.currentSize) / ((float) fileSize);
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public float getPercentageDone() {
        return percentageDone;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileID=" + fileID +
                ", fileSize=" + fileSize +
                ", currentSize=" + currentSize +
                ", filePieces=" + filePieces +
                '}';
    }
}
