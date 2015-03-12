package com.verba.language.build.steps;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.coordination.BuildStep;
import com.verba.language.build.info.BuildInfoItem;
import com.verba.language.build.processes.BuildAssemblyProcess;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class ParseSyntaxTreeBuildStep extends BuildStep {
  public ParseSyntaxTreeBuildStep(BuildAssemblyProcess process) {
    
  }

  @Override
  public QIterable<BuildInfoItem> buildItems() {
    return null;
  }
}
