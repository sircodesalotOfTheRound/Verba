package com.verba.language.emit.rendering.images;

import com.verba.language.emit.rendering.functions.MemoryStreamFunctionRenderer;

/**
 * Created by sircodesalot on 14/9/26.
 */
public interface ImageRenderer {
  void visit(MemoryStreamFunctionRenderer memoryStreamFunctionRenderer);
}
