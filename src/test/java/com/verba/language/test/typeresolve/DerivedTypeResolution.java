package com.verba.language.test.typeresolve;

import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.graph.symbols.resolution.fields.VariableTypeResolutionMetadata;
import com.verba.language.graph.symbols.resolution.function.FunctionReturnTypeResolutionMetadata;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/12.
 */
public class DerivedTypeResolution {

  @Test
  public void testTypesDerivedFromParameter() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;

    GlobalSymbolTable symbolTable = codeFile.globalSymbolTable();
    symbolTable.resolveSymbolNames();

    SymbolTableEntry first = symbolTable.getByFqn("typesDerivedFromParameter.derivedFirst").single();
    SymbolTableEntry second = symbolTable.getByFqn("typesDerivedFromParameter.derivedSecond").single();

    VariableTypeResolutionMetadata firstMeta = first.metadata().ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata secondMeta = second.metadata().ofType(VariableTypeResolutionMetadata.class).single();

    assert(firstMeta.symbolType().representation().equals("string"));
    assert(secondMeta.symbolType().representation().equals("uint32"));
  }

  @Test
  public void testChainDerived() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;
    GlobalSymbolTable symbolTable = codeFile.globalSymbolTable();
    symbolTable.resolveSymbolNames();

    SymbolTableEntry first = symbolTable.getByFqn("chainDerived.firstChain").single();
    SymbolTableEntry second = symbolTable.getByFqn("chainDerived.secondChain").single();
    SymbolTableEntry third = symbolTable.getByFqn("chainDerived.thirdChain").single();
    SymbolTableEntry fourth = symbolTable.getByFqn("chainDerived.fourthChain").single();

    VariableTypeResolutionMetadata firstMeta = first.metadata().ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata secondMeta = second.metadata().ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata thirdMeta = third.metadata().ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata fourthMeta = fourth.metadata().ofType(VariableTypeResolutionMetadata.class).single();

    assert(firstMeta.symbolType().representation().equals("object"));
    assert(secondMeta.symbolType().representation().equals("object"));

    assert(thirdMeta.symbolType().representation().equals("uint64"));
    assert(fourthMeta.symbolType().representation().equals("uint64"));
  }

  @Test
  public void testContainsClosure() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;
    GlobalSymbolTable symbolTable = codeFile.globalSymbolTable();
    symbolTable.resolveSymbolNames();

    SymbolTableEntry parameterChained = symbolTable.getByFqn("containsClosure.parameterChained").single();
    SymbolTableEntry parameter = symbolTable.getByFqn("containsClosure.closureFunction.closedOverParameter").single();
    SymbolTableEntry chainValue = symbolTable.getByFqn("containsClosure.closureFunction.closedOverChainValue").single();
    SymbolTableEntry number = symbolTable.getByFqn("containsClosure.closureFunction.closedOverNumber").single();

    VariableTypeResolutionMetadata parameterChainedMeta = parameterChained.metadata()
      .ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata parameterMeta = parameter.metadata()
      .ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata chainValueMeta = chainValue.metadata()
      .ofType(VariableTypeResolutionMetadata.class).single();
    VariableTypeResolutionMetadata numberMeta = number.metadata()
      .ofType(VariableTypeResolutionMetadata.class).single();

    assert(parameterMeta.symbolType().representation().equals("string"));
    assert(chainValueMeta.symbolType().representation().equals("string"));
    assert(numberMeta.symbolType().representation().equals("uint32"));

    assert(!parameterChainedMeta.isClosedOver());
    assert(parameterMeta.isClosedOver());
    assert(chainValueMeta.isClosedOver());
    assert(numberMeta.isClosedOver());
  }

  @Test
  public void testDerivedTypeFromLiteral() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;
    GlobalSymbolTable symbolTable = codeFile.globalSymbolTable();
    symbolTable.resolveSymbolNames();

    SymbolTableEntry implicitStringReturnType = symbolTable.getByFqn("implicitStringReturnType").single();
    SymbolTableEntry implicitIntReturnType = symbolTable.getByFqn("implicitIntReturnType").single();
    SymbolTableEntry implicitVariableReturnType = symbolTable.getByFqn("implicitVariableReturnType").single();

    FunctionReturnTypeResolutionMetadata implicitStringMeta
      = implicitStringReturnType.metadata().ofType(FunctionReturnTypeResolutionMetadata.class).single();
    FunctionReturnTypeResolutionMetadata implicitIntMeta
      = implicitIntReturnType.metadata().ofType(FunctionReturnTypeResolutionMetadata.class).single();
    FunctionReturnTypeResolutionMetadata implicitVariableMeta
      = implicitVariableReturnType.metadata().ofType(FunctionReturnTypeResolutionMetadata.class).single();

    assert (implicitStringMeta.symbolType().representation().equals("utf8"));
    assert (implicitIntMeta.symbolType().representation().equals("uint32"));
    assert (implicitVariableMeta.symbolType().representation().equals("utf8"));
  }

}
