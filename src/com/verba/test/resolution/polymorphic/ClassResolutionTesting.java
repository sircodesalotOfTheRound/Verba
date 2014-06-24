package com.verba.test.resolution.polymorphic;

import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolutionMetadata;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolver;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-19.
 */
public class ClassResolutionTesting {

    @Test
    public void resolveVisibleClassMembers() throws Exception {
        Lexer lexer = GeneralLexing.generateFromFileInThisPackage("ClassResolutionTests.v");

        TraitDeclarationExpression firstTrait = (TraitDeclarationExpression) VerbaExpression.read(null, lexer);
        TraitDeclarationExpression secondTrait = (TraitDeclarationExpression) VerbaExpression.read(null, lexer);
        TraitDeclarationExpression thirdTrait = (TraitDeclarationExpression) VerbaExpression.read(null, lexer);
        ClassDeclarationExpression subClass = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);
        ClassDeclarationExpression derivedClass = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        StaticSpaceExpression staticSpaceExpression
            = new StaticSpaceExpression(firstTrait, secondTrait, thirdTrait, subClass, derivedClass);

        // We'll resolve the third trait so that we can test caching for 'ThirdTrait' and 'FirstTrait' (it's base).
        // See PolymorphicResolver/PolymorphiocResolutionMetadata for more information about subclass caching.
        new PolymorphicResolver(staticSpaceExpression.globalSymbolTable()).resolve(thirdTrait);

        PolymorphicResolver resolver = new PolymorphicResolver(staticSpaceExpression.globalSymbolTable());
        PolymorphicResolutionMetadata classMemberResolutionMetadata = resolver.resolve(derivedClass);

        // Doesn't contain an inheritance loop
        assert (!classMemberResolutionMetadata.containsInheritanceLoop());

        // Items pulled in from class
        assert (classMemberResolutionMetadata.containsKey("first"));
        assert (classMemberResolutionMetadata.containsKey("second"));
        assert (classMemberResolutionMetadata.containsKey("third"));

        // Items pulled in from FirstTrait
        assert (classMemberResolutionMetadata.containsKey("myFunction"));
        assert (classMemberResolutionMetadata.containsKey("firstTraitEntry"));

        // Items pulled in from SecondTrait
        assert (classMemberResolutionMetadata.containsKey("anotherFunction"));
        assert (classMemberResolutionMetadata.containsKey("secondTraitEntry"));

        // Items pulled in from subClass
        assert (classMemberResolutionMetadata.containsKey("functionInAClass"));
    }

}
