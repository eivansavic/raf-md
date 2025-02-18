package com.raf.md.rafmd

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

class NavigationPsiFile(
    private val project: Project,
    private val file: PsiFile,
    private val lineNumber: Int
) : NavigatablePsiElement, PsiElement by file {

    override fun navigate(requestFocus: Boolean) {
        // logicalLine indexing starts from 0, but lineNumber starts from 1
        val descriptor = OpenFileDescriptor(project, file.virtualFile, lineNumber - 1, 0)
        descriptor.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = true
    override fun canNavigateToSource(): Boolean = true

    override fun getName(): String {
        return "Navigation File"
    }

    override fun getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String {
                return name
            }

            override fun getIcon(unused: Boolean): Icon? {
                return null
            }
        }
    }
}