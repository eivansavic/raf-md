package com.raf.md.rafmd

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import java.io.File

class CodeFileLineReference(
    element: PsiElement,
    range: TextRange,
    private val filePath: String,
    private val lineNumber: Int
) : PsiReferenceBase<PsiElement>(element, range) {

    override fun resolve(): PsiElement? {
        /*
            Find the VirtualFile by the path (relative to project, or absolute).
            For simplicity, let's assume it's relative to the project root:
         */
        val project = myElement.project
        val virtualFile = findFileInProject(project, filePath) ?: return null

        // Convert the VirtualFile to a PsiFile
        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return null

        /*
            Return a "fake" PsiElement that knows how to navigate to the right line.
            Alternatively, you could just return psiFile and rely on "Go To Declaration"
            going to the top, but let's show a custom approach that jumps to lineNumber.
         */
        return NavigationPsiFile(project, psiFile, lineNumber)
    }

    override fun getVariants(): Array<Any> = emptyArray() // no code-completion suggestions

    /**
     * Utility to find a file relative to the project's base path.
     */
    private fun findFileInProject(project: Project, relativePath: String) =
        project.baseDir?.findFileByRelativePath(relativePath)
            ?: run {
                // Optionally try to handle absolute paths or other logic
                val absolutePath = File(project.basePath, relativePath).absolutePath
                project.baseDir?.fileSystem?.findFileByPath(absolutePath)
            }
}