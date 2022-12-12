package com.atlassian.performance.tools.workspace.api

import com.atlassian.performance.tools.io.api.ensureDirectory
import java.nio.file.Path

/**
 * Contains files necessary to perform a JPT task.
 */
data class TaskWorkspace(
    val directory: Path
) {
    fun isolateTest(
        label: String
    ): TestWorkspace = TestWorkspace(
        directory.resolve(label).ensureDirectory()
    )

    fun isolateReport(
        reportFile: String
    ): Path = directory.resolve(reportFile)

}
