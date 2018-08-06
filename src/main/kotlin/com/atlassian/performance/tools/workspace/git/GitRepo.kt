package com.atlassian.performance.tools.workspace.git

import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Repository
import java.io.File

interface GitRepo {

    fun getHead(): String

    companion object Locator {
        fun findFromCurrentDirectory(): GitRepo = findInAncestors(File(".").absoluteFile)
            ?.let { LocalGitRepo(it) }
            ?: HardcodedGitRepo(head = "not found")

        /**
         * Theoretically can get stuck in infinite loop if you have created a symlink cycle.
         */
        private fun findInAncestors(
            descendant: File
        ): Repository? {
            if (descendant.isDirectory) {
                descendant
                    .listFiles()
                    .singleOrNull { it.name == ".git" }
                    ?.let { return FileRepository(it) }
            }
            val parent = descendant.parentFile
            return when (parent) {
                null -> null
                else -> findInAncestors(parent)
            }
        }
    }
}