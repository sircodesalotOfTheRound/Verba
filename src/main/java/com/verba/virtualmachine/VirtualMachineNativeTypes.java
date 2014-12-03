package com.verba.virtualmachine;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.NativeTypeExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-5-28.
 */
public final class VirtualMachineNativeTypes {
  // TODO: These items should be declared on the symbol table.
  // String Literal type.
  public static final TypeDeclarationExpression UTF8 = new TypeDeclarationExpression() {
    @Override
    public String representation() {
      return "utf8";
    }
  };

  public static final TypeDeclarationExpression INT32_LITERAL = new TypeDeclarationExpression() {
    @Override
    public String representation() {
      return "uint32";
    }
  };

  public static final TypeDeclarationExpression UNIT_EXPRESSION = new TypeDeclarationExpression() {
    @Override
    public String representation() {
      return "unit";
    }
  };

  public static final TypeDeclarationExpression BOX_UINT64 = new TypeDeclarationExpression() {
    @Override
    public String representation() {
      return "box<uint64>";
    }
  };

  public static boolean isVirtualMachineType(VerbaExpression expression) {
    if (expression instanceof LiteralExpression) return true;

    return false;
  }

  public static TypeDeclarationExpression getTypeFromInstance(VerbaExpression expression) {
    if (!(expression instanceof NativeTypeExpression)) {
      throw new NotImplementedException();
    }

    return ((NativeTypeExpression) expression).nativeTypeDeclaration();
  }
}
