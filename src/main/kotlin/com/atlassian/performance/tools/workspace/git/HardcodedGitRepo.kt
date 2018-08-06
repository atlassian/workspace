package com.atlassian.performance.tools.workspace.git

class HardcodedGitRepo(
    private val head: String
) : GitRepo {
    override fun getHead(): String = head
}
