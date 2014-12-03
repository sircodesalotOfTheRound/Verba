package com.verba.scratchpad;

import com.verba.language.build.Build;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "fn main() { val string = \"Something is amiss\" }");
  }
}
