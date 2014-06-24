package scratchpad;

import com.verba.language.build.Build;
import com.verba.language.codegen.core.VlitFileGenerator;
import com.verba.language.codegen.functions.VlitFunctionGenerator;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Program {
    public static void main(String[] args) {
        Build build = new Build();
        build.addBuildItemByClassPath(Program.class, "src/SimpleSource.v");

        VlitFileGenerator vlitFile = build.build();

        DataSegment segment = new DataSegment();
        for (VlitFunctionGenerator function : vlitFile.functions()) {
            function.emit(segment);
        }

        segment.debugPrint();
        segment.save("tester.vlit");
    }
}
