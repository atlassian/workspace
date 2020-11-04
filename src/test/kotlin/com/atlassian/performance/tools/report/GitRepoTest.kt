package com.atlassian.performance.tools.report

import com.atlassian.performance.tools.workspace.api.git.GitRepo
import com.atlassian.performance.tools.workspace.git.LocalGitRepo
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.File
import java.nio.file.Path

class GitRepoTest {

    @Test
    fun shouldFindRepo() {
        val repo = GitRepo.findFromCurrentDirectory()

        assertThat(repo, notNullValue())
    }

    @Test
    fun shouldPrintBranchHead() {
        val testResource = "on-branch.git"
        val repo = getTestRepo(testResource)

        val head = repo.getHead()

        assertThat(head, equalTo("8b1171cbf2f51c76e4da0dd9ff563761b1f191ab"))
        println("Got Git branch HEAD: $head")
    }

    @Test
    fun shouldPrintBranchHeadForPackedRef() {
        val testResource = "on-branch-packed-refs.git"
        val repo = getTestRepo(testResource)

        val head = repo.getHead()

        assertThat(head, equalTo("14f2d8ec89d8dd885f7b561dc6ec823e402d551e"))
        println("Got Git branch HEAD: $head")
    }

    @Test
    fun shouldPrintCommitHead() {
        val repo = getTestRepo("on-commit.git")

        val head = repo.getHead()

        assertThat(head, equalTo("bc8578c511a558dbf542adc23fa1a33b24695c1f"))
        println("Got Git commit HEAD: $head")
    }

    @Test
    fun shouldFindWorktreeRepo() {
        configureMain()
        val worktreeFolder = configureWorktree()

        val repo = GitRepo.findFromDirectory(worktreeFolder.toString())

        val head = repo.getHead()

        assertThat(head, equalTo("14f2d8ec89d8dd885f7b561dc6ec823e402d551e"))
        println("Got Git commit HEAD: $head")
    }

    /**
     * Dot files/folders, e.g. .git, don't seem to be copied as resources.
     * Therefore in order to create a viable 'worktree' .git it is necessary to use a 'dotgit' file as a template,
     * copy it and then populate it with a path that is valid at runtime.
     *
     * NB. relative file paths are valid for git in .git files, but they don't work with LocalGitRepo which needs a
     * full path.
     */
    private fun configureWorktree(): Path {
        val worktreeTestDotGit = File(
                javaClass.getResource("worktree/dotgit").toURI()
        )

        val worktreeFolder = worktreeTestDotGit.parentFile.toPath()
        val worktreeDotGit = worktreeFolder.resolve(".git")
        worktreeDotGit.toFile().writeText("gitdir: "
            + worktreeFolder.parent.toAbsolutePath()
            .resolve("main")
            .resolve(".git")
            .resolve("worktrees")
            .resolve("worktree")
            .toString())
        return worktreeFolder
    }

    /**
     * Dot files/folders, e.g. .git, don't seem to be copied as resources.
     * Therefore in order to create a viable 'main' .git it is necessary to use a 'dotgit' folder as a name,
     * copy it and rename it at runtime.
     */
    private fun configureMain(): Path {
        val mainTestDotGit = File(
                javaClass.getResource("main/dotgit").toURI()
        )
        val mainFolder = mainTestDotGit.parentFile.toPath()
        val mainDotGit = mainFolder.resolve(".git")
        mainTestDotGit.copyRecursively(mainDotGit.toFile(), true)
        return mainFolder
    }

    private fun getTestRepo(
        testResource: String
    ): GitRepo = LocalGitRepo(
        FileRepository(
            File(
                javaClass.getResource(testResource).toURI()
            )
        )
    )
}