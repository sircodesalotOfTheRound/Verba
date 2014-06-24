package com.verba.language.symbols.resolution.metatags;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.expressions.categories.MetaTagExpression;
import com.verba.language.expressions.tags.aspect.AspectTagExpression;
import com.verba.language.symbols.meta.MetatagSymbolTableMetadata;
import com.verba.language.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-5-13.
 */
public class AspectTagResolver implements SymbolResolver<AspectTagExpression, MetaTagResolutionInfo> {
    public final GlobalSymbolTable table;

    public AspectTagResolver(GlobalSymbolTable table) {
        this.table = table;
    }

    @Override
    public MetaTagResolutionInfo resolve(AspectTagExpression expression) {
        SymbolTableEntry entry = this.table.getByInstance(expression);
        SymbolTableEntry bindingTarget = findBindingTarget(entry.table(), entry);

        return new MetaTagResolutionInfo(bindingTarget);
    }

    private SymbolTableEntry findBindingTarget(ScopedSymbolTable table, SymbolTableEntry entry) {
        int index = table.getIndex(entry);

        // Bind the hashtag to the next function
        while (index++ < table.entries().count()) {
            SymbolTableEntry possiblyBindableEntry = table.get(index);
            VerbaExpression expression = possiblyBindableEntry.instance();

            // The next expression must be another MetaTag
            // or the binding target (Function, Class, Task).
            // Otherwise the formation isn't legal.
            if (expression instanceof MetaTagExpression)
                continue;

            else if (expression instanceof FunctionDeclarationExpression
                || expression instanceof TaskDeclarationExpression) {

                return possiblyBindableEntry;

            } else break;
        }

        return null;
    }

    private void bindTagToEntry(SymbolTableEntry toEntry, AspectTagExpression aspectTag) {
        MetatagSymbolTableMetadata metatagSymbolTableMetadata
            = new MetatagSymbolTableMetadata(aspectTag);

        toEntry.addMetadata(metatagSymbolTableMetadata);
    }
}
