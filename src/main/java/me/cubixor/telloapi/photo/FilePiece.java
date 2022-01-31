package me.cubixor.telloapi.photo;

import java.util.Arrays;

public class FilePiece {

    private final int filePieceID;
    private final FileChunk[] fileChunks = new FileChunk[8];

    public FilePiece(int filePieceID) {
        this.filePieceID = filePieceID;
    }

    public int getFilePieceID() {
        return filePieceID;
    }

    public FileChunk[] getFileChunks() {
        return fileChunks;
    }

    @Override
    public String toString() {
        return "FilePiece{" +
                "filePieceID=" + filePieceID +
                ", fileChunks=" + Arrays.toString(fileChunks) +
                '}';
    }
}
