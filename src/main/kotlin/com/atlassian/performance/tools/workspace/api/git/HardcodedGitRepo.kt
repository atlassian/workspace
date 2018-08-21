package com.atlassian.performance.tools.workspace.api.git

class HardcodedGitRepo(
    private val head: String
) : GitRepo {
    override fun getHead(): String = head
}
