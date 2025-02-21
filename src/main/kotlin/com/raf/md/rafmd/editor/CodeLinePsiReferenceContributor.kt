package com.raf.md.rafmd.editor

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkDestination

class CodeLinePsiReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns
                .psiElement(MarkdownLinkDestination::class.java),
            CodeLinePsiReferenceProvider()
        )
    }
}