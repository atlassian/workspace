package com.atlassian.performance.tools.workspace

import com.atlassian.performance.tools.io.ensureDirectory
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