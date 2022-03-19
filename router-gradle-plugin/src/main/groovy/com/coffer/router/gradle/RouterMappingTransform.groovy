package com.coffer.router.gradle

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform

class RouterMappingTransform extends Transform{

    @Override
    String getName() {
        return null
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return null
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return null
    }

    @Override
    boolean isIncremental() {
        return false
    }
}