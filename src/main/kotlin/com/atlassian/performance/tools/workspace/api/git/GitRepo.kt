package com.atlassian.performance.tools.workspace.api.git

import com.atlassian.performance.tools.workspace.git.LocalGitRepo
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Repository
import java.io.File

/**
 * Provides versioning context.
 */
interface GitRepo {

    /**
     * @return Git HEAD description
     */
    fun getHead(): String

    companion object Locator {
        fun findFromCurrentDirectory(): GitRepo = findFromDirectory(".")

        fun findFromDirectory(pathName: String): GitRepo = findInAncestors(File(pathName).absoluteFile)
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
                    ?.let { return FileRepository(toGitFolder(it)) }
            }
            val parent = descendant.parentFile
            return when (parent) {
                null -> null
                else -> findInAncestors(parent)
            }
        }

        private fun toGitFolder(file: File): File? {

            if (file.isDirectory) {
                return file
            }

            val line = file.bufferedReader().use { it.readLine() }
            val pattern = "gitdir: (.*)".toRegex()
            val match = pattern.find(line);
            val path = match!!.groupValues[1]
            val gitFolderPath = path.substring(0, path.indexOf(".git") + 4)

            return File(gitFolderPath)
        }
    }
}
