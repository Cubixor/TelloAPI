package me.cubixor.telloapi.photo;

import java.util.HashMap;

public class File {

    private final int fileID;
    private final int fileSize;
    private final HashMap<Integer, FilePiece> filePieces = new HashMap<>();
    private int currentSize;
    private boolean received;

    public File(int fileID, int fileSize) {
        this.fileID = fileID;
        this.fileSize = fileSize;
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
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
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
