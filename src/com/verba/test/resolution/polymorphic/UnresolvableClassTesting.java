package com.verba.test.resolution.polymorphic;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolutionMetadata;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class UnresolvableClassTesting {
    @Test
    public void testUnresolvableClass() {
        VerbaCodePage codePage = VerbaCodePage.fromPackageItem(null, this.getClass(), "UnresolvableClassTesting.v");
        StaticSpaceExpression staticSpaceExpression = new StaticSpaceExpression(codePage);

        SymbolTableEntry derived = staticSpaceExpression.globalSymbolTable().getByFriendlyName("Derived").single();
        PolymorphicResolver resolver = new PolymorphicResolver(staticSpaceExpression.globalSymbolTable());
        PolymorphicResolutionMetadata classMemberResolutionMetadata
            = resolver.resolve((ClassDeclarationExpression) derived.instance());

        assert (classMemberResolutionMetadata.hasUnresolveableTraits());
        assert (classMemberResolutionMetadata.unresolvedTraits().size() == 1);
    }
}
