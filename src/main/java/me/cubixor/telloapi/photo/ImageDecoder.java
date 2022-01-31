package me.cubixor.telloapi.photo;

public class ImageDecoder {

    public byte[] fileToByteArray(File file) {
        byte[] data = new byte[file.getFileSize()];

        int i = 0;
        for (FilePiece filePiece : file.getFilePieces().values()) {
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
}
