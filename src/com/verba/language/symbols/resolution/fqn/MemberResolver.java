package com.verba.language.symbols.resolution.fqn;

import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.symbols.resolution.interfaces.SymbolResolver;

/**
 * Created by sircodesalot on 14-5-16.
 */
public class MemberResolver implements SymbolResolver<MemberExpression, MemberResolutionInfo> {
    @Override
    public MemberResolutionInfo resolve(MemberExpression expression) {
        return null;
    }
}
