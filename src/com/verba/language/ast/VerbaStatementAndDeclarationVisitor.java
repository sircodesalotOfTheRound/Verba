package com.verba.language.ast;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.exceptions.CompilerException;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.expressions.containers.json.JsonExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.rvalue.simple.IdentifierExpression;
import com.verba.language.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.statements.declaration.MutaDeclarationStatement;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.expressions.tags.aspect.AspectTagExpression;
import com.verba.language.expressions.tags.hashtag.HashTagExpression;

/**
 * Visitor that descends an expression tree and runs a VerbaASTCallback for each node found.
 * Only follows Statements and Declarations (loosely "things that start a line") rather than
 * following every possibly subnode. This is primarily used to grab 'named things' for symbol tables
 * although things like if - statements and the like are visited as well
 * (since they might contain named expressions downstream).
 */
public class VerbaStatementAndDeclarationVisitor {
    private final VerbaExpression root;
    private VerbaASTNodeCallback callback;

    public VerbaStatementAndDeclarationVisitor(VerbaExpression root, VerbaASTNodeCallback callback) {
        this.root = root;
        this.callback = callback;

        this.visit(this.root);
    }

    private void visit(VerbaExpression node) {
        this.callback.callback(node);

        // Switch
        if (node instanceof BlockDeclarationExpression) this.visitBlock((BlockDeclarationExpression) node);
        else if (node instanceof VerbaCodePage) this.visitPage((VerbaCodePage) node);
        else if (node instanceof StaticSpaceExpression) this.visitStaticSpace((StaticSpaceExpression) node);

        else if (node instanceof TupleDeclarationExpression) this.visitTuple((TupleDeclarationExpression) node);

            // Declaration
        else if (node instanceof ClassDeclarationExpression) this.visitClass((ClassDeclarationExpression) node);
        else if (node instanceof TraitDeclarationExpression) this.visitTrait((TraitDeclarationExpression) node);
        else if (node instanceof FunctionDeclarationExpression)
            this.visitFunction((FunctionDeclarationExpression) node);

        else if (node instanceof VarNameDeclarationExpression) this.visitVarName((VarNameDeclarationExpression) node);

            // Containers
        else if (node instanceof JsonExpression) this.visitJson((JsonExpression) node);
        else if (node instanceof ArrayDeclarationExpression) this.visitArray((ArrayDeclarationExpression) node);

            // Meta Tags
        else if (node instanceof HashTagExpression) return;
        else if (node instanceof AspectTagExpression) return;

            // Statements
        else if (node instanceof ValDeclarationStatement) return;
        else if (node instanceof MutaDeclarationStatement) return;
        else if (node instanceof ReturnStatementExpression) return;

            // Terminal Expressions
        else if (node instanceof FullyQualifiedNameExpression) return;
        else if (node instanceof NumericExpression) return;
        else if (node instanceof MathOpExpression) return;
        else if (node instanceof IdentifierExpression) return;

        else throw new CompilerException("Invalid Node-type: %s", node.getClass());
    }

    private void visitTrait(TraitDeclarationExpression node) {
        this.visitAll(node.block());
    }

    private void visitStaticSpace(StaticSpaceExpression node) {
        this.visitAll(node.rootLevelExpressions());
    }

    public void visitVarName(VarNameDeclarationExpression var) {
        if (var.hasTypeConstraint()) this.visit((VerbaExpression) var.typeDeclaration());
    }

    public void visitClass(ClassDeclarationExpression classDeclaration) {
        this.visitAll(classDeclaration.block());
    }

    public void visitFunction(FunctionDeclarationExpression function) {
        this.visitAll(function.block());
    }

    private void visitArray(ArrayDeclarationExpression array) {
        this.visitAll(array.items());
    }

    private void visitJson(JsonExpression jsonExpression) {
        visitAll(jsonExpression.items());
    }

    private void visitTuple(TupleDeclarationExpression tuple) {
        this.visitAll(tuple.items());
    }

    private void visitBlock(BlockDeclarationExpression block) {
        this.visitAll(block);
    }

    private void visitPage(VerbaCodePage page) {
        this.visitAll(page.expressions());
    }

    private <T extends VerbaExpression> void visitAll(Iterable<T> expressions) {
        for (VerbaExpression expression : expressions) {
            this.visit(expression);
        }
    }
}
