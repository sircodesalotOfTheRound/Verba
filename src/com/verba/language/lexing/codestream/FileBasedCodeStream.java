package com.verba.language.lexing.codestream;

import com.verba.language.exceptions.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class FileBasedCodeStream implements CodeStream {
    private CodeStream codeStream;

    public FileBasedCodeStream(String filename, InputStream stream) {
        this.codeStream = new StringBasedCodeStream(filename, readToEnd(stream));
    }

    public FileBasedCodeStream(String path) {
        this.codeStream = new StringBasedCodeStream(path, readToEnd(path));
    }

    private String readToEnd(InputStream stream) {
        StringBuilder contents = new StringBuilder();

        try {
            int next;
            while ((next = stream.read()) != -1)
                contents.append((char) next);
        } catch (IOException ex) {
        }

        return contents.toString();
    }

    private String readToEnd(String path) {
        try {
            StringBuilder contents = new StringBuilder();
            FileInputStream file = new FileInputStream(path);
            int next = -1;
            while ((next = file.read()) != -1) contents.append((char) next);

            file.close();
            return contents.toString();
        } catch (IOException ex) {
        }

        throw new ParseException("File not found");
    }

    @Override
    public int peekLineForNextChar() {
        return codeStream.peekLineForNextChar();
    }

    @Override
    public int absolutePosition() {
        return codeStream.absolutePosition();
    }

    @Override
    public Character peek() {
        return codeStream.peek();
    }

    @Override
    public Character read() {
        return codeStream.read();
    }

    @Override
    public boolean hasNext() {
        return codeStream.hasNext();
    }

    @Override
    public int column() {
        return codeStream.column();
    }

    @Override
    public int line() {
        return codeStream.line();
    }

    @Override
    public int getFileLength() {
        return codeStream.getFileLength();
    }

    @Override
    public String filename() {
        return this.codeStream.filename();
    }

    @Override
    public void setUndoPosition() {
        this.codeStream.setUndoPosition();
    }

    @Override
    public void rollbackToUndoPosition() {
        this.codeStream.rollbackToUndoPosition();
    }
}
