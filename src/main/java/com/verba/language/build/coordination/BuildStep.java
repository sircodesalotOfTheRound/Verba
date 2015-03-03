package com.verba.language.build.coordination;

/**
 * Created by sircodesalot on 15/3/3.
 */
public interface BuildStep {
  void accept(BuildProcess process);
}
