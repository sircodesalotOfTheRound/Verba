package com.verba.language.build;

import com.verba.language.build.codepage.VerbaCodePage;

import java.io.InputStream;

/**
 * An item that is part of a build. Namely a CodePage or a lit-file.
 */
public class BuildItem {
    private final Class packageClass;
    private final String path;

    public BuildItem(Class packageClass, String path) {
        this.packageClass = packageClass;
        this.path = path;
    }

    public BuildItem(String path) {
        this(null, path);
    }

    public Class packageClass() {
        return this.packageClass;
    }

    public String path() {
        return this.path;
    }

    // Return the raw file contents.
    public InputStream fileStream() {
        return this.packageClass.getResourceAsStream(this.path);
    }

    // Return a code page from the specified file.
    public VerbaCodePage codePage() {
        if (this.packageClass != null) {
            return VerbaCodePage.fromPackageItem(null, this.packageClass, this.path);
        }

        return VerbaCodePage.fromFile(null, this.path);
    }
}
