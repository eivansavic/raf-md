package com.raf.md.rafmd.editor

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

class CodeLinePsiReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        // Check for format [Some Link](Some.java#42)
        val rawText = element.text ?: return PsiReference.EMPTY_ARRAY

        // Parse (Some.java#42)
        val text = rawText.removeSurrounding("(", ")")

        // Pattern: {{filepath}}#{{lineNumber}}
        val pattern = Regex("""^([\w/\\.\-]+)\#(\d+)$""")

        val match = pattern.find(text) ?: return PsiReference.EMPTY_ARRAY
        val filePath = match.groupValues[1]  // e.g. "src/main/java/Some.java"
        val lineNumber = match.groupValues[2].toInt() // e.g. "42"

        // Link text
        val range = TextRange(0, rawText.length)

        // Return a reference
        return arrayOf(CodeFileLineReference(element, range, filePath, lineNumber))
    }
}