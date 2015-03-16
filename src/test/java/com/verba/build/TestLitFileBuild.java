package com.verba.build;

import com.javalinq.implementations.QSet;
import com.verba.language.build.artifacts.SourcesBuildArtifact;
import com.verba.language.build.artifacts.StringTableBuildArtifact;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import org.junit.Test;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/10.
 */
public class TestLitFileBuild {
  private final LitFileBuildManager build = new BuildSpecification()
    .addSourceFolder("verba_sources/glob_test")
    .createLitFileBuild();

  @Test
  public void testGlobbing() {
    assert(build.containsArtifactOfType(SourcesBuildArtifact.class));

    QSet<String> filesAsSet = build
      .getArtifactOfType(SourcesBuildArtifact.class)
      .files()
      .map(File::toString)
      .toSet();

    assert(filesAsSet.count() == 5);
    assert(filesAsSet.contains("verba_sources/glob_test/File1.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/File2.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/File3.v"));

    assert(filesAsSet.contains("verba_sources/glob_test/subfolder/File4.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/subfolder/File5.v"));
  }

  @Test
  public void testContainsStringTable() {
    assert(build.containsArtifactOfType(StringTableBuildArtifact.class));
  }

  @Test
  public void testCompilation() {

  }
}
