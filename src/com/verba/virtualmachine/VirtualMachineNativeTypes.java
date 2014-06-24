package com.verba.virtualmachine;

import com.verba.language.exceptions.CompilerException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.LiteralExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;

/**
 * Created by sircodesalot on 14-5-28.
 */
public final class VirtualMachineNativeTypes {
    // TODO: These items should be declared on the symbol table.
    // String Literal type.
    public static final TypeDeclarationExpression STRING_LITERAL = new TypeDeclarationExpression() {
        @Override
        public String representation() {
            return "VM.String";
        }
    };

    public static final TypeDeclarationExpression INT32_LITERAL = new TypeDeclarationExpression() {
        @Override
        public String representation() {
            return "VM.Int32";
        }
    };

    public static final TypeDeclarationExpression UNIT_EXPRESSION = new TypeDeclarationExpression() {
        @Override
        public String representation() {
            return "VM.Unit";
        }
    };

    public static boolean isVirtualMachineType(VerbaExpression expression) {
        if (expression instanceof LiteralExpression) return true;

        return false;
    }

    public static TypeDeclarationExpression getTypeFromInstance(VerbaExpression expression) {
        if (expression instanceof QuoteExpression) return VirtualMachineNativeTypes.STRING_LITERAL;
        else if (expression instanceof NumericExpression) return VirtualMachineNativeTypes.INT32_LITERAL;

        throw new CompilerException("%s is not a virtual machine native type", expression);
    }
}
