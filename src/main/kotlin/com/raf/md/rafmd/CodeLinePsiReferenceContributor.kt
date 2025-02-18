package com.raf.md.rafmd

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownFile
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkLabel

class CodeLinePsiReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val linkDestinationPattern = PlatformPatterns
            .psiElement(MarkdownLinkLabel::class.java)
            .inFile(PlatformPatterns.psiFile(MarkdownFile::class.java))

        registrar.registerReferenceProvider(
            linkDestinationPattern,
            CodeLinePsiReferenceProvider()
        )
    }
}