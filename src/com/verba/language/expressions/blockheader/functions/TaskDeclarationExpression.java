package com.verba.language.expressions.blockheader.functions;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.NamedBlockExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.categories.GenericExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class TaskDeclarationExpression extends VerbaExpression
    implements NamedBlockExpression, GenericExpression {
    private final FullyQualifiedNameExpression identifier;
    private TypeDeclarationExpression returnType;
    private final TupleDeclarationExpression parameters;
    private final BlockDeclarationExpression block;
    private GenericTypeListExpression genericTypeList;

    public TaskDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "task");
        this.identifier = FullyQualifiedNameExpression.read(this, lexer);

        this.genericTypeList = GenericTypeListExpression.read(this, lexer);
        this.parameters = TupleDeclarationExpression.read(this, lexer);

        if (lexer.currentIs(OperatorToken.class, ":")) {
            lexer.readCurrentAndAdvance(OperatorToken.class, ":");
            this.returnType = TypeDeclarationExpression.read(this, lexer);
        }

        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    public static TaskDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new TaskDeclarationExpression(parent, lexer);
    }

    public FullyQualifiedNameExpression identifier() {
        return this.identifier;
    }

    public TypeDeclarationExpression returnType() {
        return this.returnType;
    }

    public TupleDeclarationExpression parameters() {
        return this.parameters;
    }

    public BlockDeclarationExpression block() {
        return this.block;
    }

    public GenericTypeListExpression genericParamters() {
        return this.genericTypeList;
    }


    @Override
    public String name() {
        return this.identifier.members().first().memberName();
    }

    @Override
    public boolean hasGenericParameters() {
        throw new NotImplementedException();
    }

    @Override
    public GenericTypeListExpression genericParameters() {
        return null;
    }
}
