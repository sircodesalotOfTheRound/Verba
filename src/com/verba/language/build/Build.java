package com.verba.language.build;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.build.resolution.SymbolResolver;
import com.verba.language.codegen.core.VlitFileGenerator;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;

/**
 * Represents a build from a set of CodePages or other source files.
 */
public class Build {
    private final QList<BuildItem> buildItems = new QList<>();

    public void addBuildItem(BuildItem item) {
        this.buildItems.add(item);
    }

    public void addBuildItemByPath(String path) {
        BuildItem item = new BuildItem(path);
        this.addBuildItem(item);
    }

    public void addBuildItemByClassPath(Class packageClass, String path) {
        BuildItem item = new BuildItem(packageClass, path);
        this.addBuildItem(item);
    }

    public VlitFileGenerator build() {
        QIterable<VerbaExpression> pages = generateCodePages();
        StaticSpaceExpression staticSpaceExpression = generateStaticSpaceExpression(pages);
        GlobalSymbolTable symbolTable = staticSpaceExpression.globalSymbolTable();
        SymbolResolver resolver = new SymbolResolver(symbolTable);

        return new VlitFileGenerator(staticSpaceExpression);
    }


    // (1) Generate code pages from raw text.
    private QIterable<VerbaExpression> generateCodePages() {
        return buildItems.map(item -> item.codePage());
    }

    // (2) Validate items
    public void validateItems(QIterable<VerbaCodePage> pages) {

    }

    // (3) Generate static space expression
    private StaticSpaceExpression generateStaticSpaceExpression(QIterable<VerbaExpression> pages) {
        return new StaticSpaceExpression(pages);
    }
}
