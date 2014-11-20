package com.verba.language.build.rendering.images;

import com.verba.language.build.rendering.functions.MemoryStreamFunctionRenderer;

/**
 * Created by sircodesalot on 14/9/26.
 */
public interface ImageRenderer {
  void visit(MemoryStreamFunctionRenderer memoryStreamFunctionRenderer);
}
