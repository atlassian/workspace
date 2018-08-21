package com.atlassian.performance.tools.workspace.git

import com.atlassian.performance.tools.workspace.api.git.GitRepo
import org.eclipse.jgit.lib.Repository

/**
 * @param repo the `.git` directory
 */
class LocalGitRepo(
    private val repo: Repository
) : GitRepo {
    override fun getHead(): String {
        return repo.exactRef("HEAD").objectId.name
    }
}