package com.verba.language.emit.images.types.specialized;

import com.verba.language.emit.images.interfaces.AppendableObjectImage;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.types.basic.InMemoryObjectImage;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.graph.expressions.functions.FunctionGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * This facade makes it easier to write an object image.
 */
public class FunctionObjectImage implements ObjectImage {
  private final FunctionGraph functionGraph;
  private final AppendableObjectImage objectImage;
  private boolean isFrozen = false;

  public FunctionObjectImage(FunctionDeclarationExpression declaration,
                             StaticSpaceExpression staticSpace,
                             SymbolTable symbolTable) {

    this.functionGraph = new FunctionGraph(declaration, symbolTable, staticSpace);
    this.objectImage = new InMemoryObjectImage(declaration.name(), ImageType.FUNCTION);
  }

  private void generateOpCodeList() {
    objectImage.writeString("name", this.functionGraph.name());

    for (VerbatimOpCodeBase opCode : functionGraph.opcodes()) {
      objectImage.writeInt8(null, opCode.opcodeNumber());
      opCode.render(objectImage);
    }
  }

  public void displayCoreDump() {
    byte[] byteData = this.data();
    System.out.println(String.format("Image: %s (%s bytes)", objectImage.name(), objectImage.data().length));

    int count = 0;
    for (byte data : byteData) {
      if (count++ > 0 && count % 10 == 0) {
        System.out.println();
      }

      System.out.print(String.format("%02x ", data));
    }

    System.out.println();
  }

  @Override
  public String name() { return objectImage.name(); }

  @Override
  public ImageType imageType() { return objectImage.imageType(); }

  @Override
  public long size() { return objectImage.size(); }

  @Override
  public byte[] data() {
    if (!isFrozen) {
      this.generateOpCodeList();
      this.isFrozen = true;
    }

    return objectImage.data();
  }
}
