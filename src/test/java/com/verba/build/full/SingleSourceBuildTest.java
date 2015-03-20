package com.verba.build.full;

import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class SingleSourceBuildTest {
  public static LitFileBuildManager build = new BuildSpecification()
    .addSourceFolder("verba_sources/single_source_build_test")
    .isDebugBuild(false)
    .createLitFileBuild();

  @Test
  public void testSourceLoading() {

  }

}
