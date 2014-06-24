package com.verba.language.symbols.table.tables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.blockheader.namespaces.NamespaceDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.categories.NamedExpression;
import com.verba.language.expressions.categories.SymbolTableExpression;
import com.verba.language.expressions.statements.declaration.MutaDeclarationStatement;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.symbols.meta.GenericParameterSymbolTableItem;
import com.verba.language.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.symbols.meta.ParameterSymbolTableItem;
import com.verba.language.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.entries.SymbolTableEntrySet;
import com.verba.language.validation.violations.ValidationViolation;


/**
 * Created by sircodesalot on 14-3-9.
 */
public class ScopedSymbolTable {
    private final String fqn;
    private final String name;
    private final ScopedSymbolTable parent;
    private final SymbolTableExpression headerExpression; // The block item (function, class, anonymous-block, ...)
    private final SymbolTableEntrySet entrySet;
    private final QList<ValidationViolation> violations = new QList<>();
    private final QList<ScopedSymbolTable> nestedTables = new QList<>();

    // Construction
    // Anonymous block
    public ScopedSymbolTable(SymbolTableExpression block) {
        this(null, block);
    }

    public ScopedSymbolTable(ScopedSymbolTable parent, SymbolTableExpression block) {
        this.entrySet = new SymbolTableEntrySet(this);
        this.name = resolveName(block);
        this.parent = parent;
        this.headerExpression = block;
        this.fqn = resolveFqn(name);

        this.scan(block);
    }

    private String resolveName(SymbolTableExpression block) {
        if (block instanceof NamedExpression) {
            return ((NamedExpression) block).name();
        }

        return null;
    }

    private String resolveFqn(String name) {
        if (this.hasParentTable() && (this.parent.fqn != null) && (name != null)) {
            return String.format("%s.%s", this.parent.fqn(), name);
        }

        return name;
    }

    // Scanning (walk the item, but don't add it).
    private void scan(SymbolTableExpression block) {
        if (block instanceof BlockDeclarationExpression)
            this.scanBlock((BlockDeclarationExpression) block);

        else if (block instanceof FunctionDeclarationExpression)
            this.scanFunction((FunctionDeclarationExpression) block);

        else if (block instanceof NamespaceDeclarationExpression)
            this.scanNamespace((NamespaceDeclarationExpression) block);

        else if (block instanceof ClassDeclarationExpression)
            this.scanClass((ClassDeclarationExpression) block);

        else if (block instanceof TraitDeclarationExpression)
            this.scanTrait((TraitDeclarationExpression) block);

        else if (block instanceof StaticSpaceExpression)
            this.scanStaticSpace((StaticSpaceExpression) block);

        else if (block instanceof VerbaCodePage)
            this.scanCodePage((VerbaCodePage) block);
    }


    private void scanStaticSpace(StaticSpaceExpression block) {
        for (VerbaExpression expression : block.rootLevelExpressions()) {
            if (expression instanceof VerbaCodePage)
                scanCodePage((VerbaCodePage) expression);

            else if (expression instanceof ValDeclarationStatement)
                addValDeclarationStatement((ValDeclarationStatement) expression);

            else if (expression instanceof MutaDeclarationStatement)
                addMutaDeclarationStatement((MutaDeclarationStatement) expression);

            else if (expression instanceof FunctionDeclarationExpression)
                addFunctionDeclarationStatement((FunctionDeclarationExpression) expression);

            else if (expression instanceof ClassDeclarationExpression)
                addClassDeclarationStatement((ClassDeclarationExpression) expression);

            else if (expression instanceof TraitDeclarationExpression)
                addTraitDeclarationStatement((TraitDeclarationExpression) expression);

            else if (expression instanceof TaskDeclarationExpression)
                addTaskDeclarationStatement((TaskDeclarationExpression) expression);
        }
    }

    private void scanCodePage(VerbaCodePage block) {
        for (NamedExpression expression : block.expressions().ofType(NamedExpression.class)) {
            this.addNested(expression);
        }
    }

    private void scanClass(ClassDeclarationExpression classDeclaration) {
        this.scanGenericParameters(classDeclaration.genericParameters());
        this.scanBlock(classDeclaration.block());
    }

    private void scanTrait(TraitDeclarationExpression trait) {
        this.scanGenericParameters(trait.genericParameters());
        this.scanBlock(trait.block());
    }

    private void scanNamespace(NamespaceDeclarationExpression namespace) {
        this.scanBlock(namespace.block());
    }

    private void scanFunction(FunctionDeclarationExpression function) {
        // First add the parameters
        QIterable<VarNameDeclarationExpression> parameters = function.parameters()
            .flatten(tuple -> tuple.items())
            .cast(VarNameDeclarationExpression.class);

        this.scanGenericParameters(function.genericParameters());

        // Then add regular parameters
        for (VarNameDeclarationExpression parameter : parameters) {
            this.add(parameter.identifier().representation(), parameter, new ParameterSymbolTableItem());
        }

        // Then add the body
        this.scanBlock(function.block());
    }

    private void scanGenericParameters(GenericTypeListExpression genericParameters) {
        // Add generic parameters
        for (VarNameDeclarationExpression genericParam : genericParameters) {
            this.add(genericParam.identifier().representation(), genericParam, new GenericParameterSymbolTableItem());
        }
    }

    private void scanBlock(BlockDeclarationExpression block) {
        for (VerbaExpression expression : block) {
            if (expression instanceof ValDeclarationStatement)
                addValDeclarationStatement((ValDeclarationStatement) expression);

            else if (expression instanceof MutaDeclarationStatement)
                addMutaDeclarationStatement((MutaDeclarationStatement) expression);

            else if (expression instanceof FunctionDeclarationExpression)
                addFunctionDeclarationStatement((FunctionDeclarationExpression) expression);

            else if (expression instanceof ClassDeclarationExpression)
                addClassDeclarationStatement((ClassDeclarationExpression) expression);

            else if (expression instanceof TraitDeclarationExpression)
                addTraitDeclarationStatement((TraitDeclarationExpression) expression);

            else if (expression instanceof TaskDeclarationExpression)
                addTaskDeclarationStatement((TaskDeclarationExpression) expression);
        }
    }


    // Additions
    public void add(SymbolTableEntry entry) {
        this.entrySet.add(entry);
    }

    public void add(String name, VerbaExpression object, SymbolTableMetadata... metadata) {
        // Add a new symbol table entry, mark it as a nested symbol table item.
        SymbolTableEntry entry = new SymbolTableEntry(name, this, object);

        // Add Metadata
        for (SymbolTableMetadata metadataItem : metadata) {
            entry.addMetadata(metadataItem);
        }

        // Add the entry to the symbol table
        this.add(entry);
    }

    private void addNested(NamedExpression expression) {
        this.addNested(expression.name(), (SymbolTableExpression) expression);
    }

    private void addNested(String name, SymbolTableExpression block) {
        ScopedSymbolTable nestedTable = new ScopedSymbolTable(this, block);

        this.add(name, (VerbaExpression) block, new NestedSymbolTableMetadata(nestedTable));
        this.nestedTables.add(nestedTable);
    }

    private void addTaskDeclarationStatement(TaskDeclarationExpression task) {
        this.addNested(task);
    }

    private void addClassDeclarationStatement(ClassDeclarationExpression classDeclaration) {
        this.addNested(classDeclaration);
    }

    private void addTraitDeclarationStatement(TraitDeclarationExpression trait) {
        this.addNested(trait);
    }

    private void addFunctionDeclarationStatement(FunctionDeclarationExpression function) {
        this.addNested(function);
    }

    private void addNamespaceDeclaration(NamespaceDeclarationExpression namespace) {
        this.addNested(namespace);
    }

    private void addValDeclarationStatement(ValDeclarationStatement statement) {
        this.add(statement.name(), statement);
    }

    private void addMutaDeclarationStatement(MutaDeclarationStatement statement) {
        this.add(statement.name(), statement);
    }

    // Accessing Items
    public String name() {
        return this.name;
    }

    public boolean hasName() {
        return this.name != null;
    }

    public boolean hasParentTable() {
        return this.parent != null;
    }

    public ScopedSymbolTable parent() {
        return this.parent;
    }

    public boolean hasItems() {
        return this.entrySet.count() > 0;
    }

    public void clear() {
        this.entrySet.clear();
    }

    public void addViolation(ValidationViolation violation) {
        this.violations.add(violation);
    }

    public QIterable<SymbolTableEntry> get(String key) {
        return this.entrySet.get(key);
    }

    public SymbolTableEntry get(int index) {
        return this.entrySet.get(index);
    }

    public QIterable<String> keys() {
        return new QList<>(this.entrySet.keySet());
    }

    public boolean containsKey(String key) {
        return this.entrySet.containsKey(key);
    }

    public QIterable<SymbolTableEntry> entries() {
        return this.entrySet.entries();
    }

    public int getIndex(SymbolTableEntry entry) {
        return this.entrySet.getIndex(entry);
    }

    public QIterable<ScopedSymbolTable> nestedTables() {
        return this.nestedTables;
    }

    public String fqn() {
        return this.fqn;
    }

    public SymbolTableExpression header() {
        return this.headerExpression;
    }

    public QIterable<ValidationViolation> violations() {
        return collectViolations(new QList<>(), this);
    }

    private QList<ValidationViolation> collectViolations(QList<ValidationViolation> violations,
                                                         ScopedSymbolTable onTable) {
        // Tree descent
        for (ScopedSymbolTable table : onTable.nestedTables) {
            collectViolations(violations, table);
        }

        // Violation capturing
        for (ValidationViolation violation : onTable.violations) {
            violations.add(violation);
        }

        return violations;
    }

    public QIterable<SymbolTableEntry> resolveName(String name) {
        QList<SymbolTableEntry> resolvedNames = new QList<>();
        ScopedSymbolTable searchTable = this;

        // Search upward through all parent scopes to find items
        // that match the name.
        do {
            if (searchTable.containsKey(name)) {
                resolvedNames.add(searchTable.get(name));
            }

            searchTable = searchTable.parent();
        } while (searchTable != null);

        return resolvedNames;
    }
}
