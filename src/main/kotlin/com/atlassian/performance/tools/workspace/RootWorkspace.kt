package com.atlassian.performance.tools.workspace

import com.atlassian.performance.tools.io.directories
import com.atlassian.performance.tools.io.ensureDirectory
import com.atlassian.performance.tools.io.resolveSafely
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/**
 * Contains JPT task workspaces.
 */
class RootWorkspace(
    parent: Path = Paths.get(System.getProperty("jpt.workspace.parent") ?: ".")
) {

    val directory: Path = parent
        .resolve("jpt-workspace")
        .toAbsolutePath()
        .normalize()
        .ensureDirectory()

    val currentTask: TaskWorkspace = isolateTask(
        label = System.getenv("bamboo_buildResultKey")
            ?: LocalDateTime.now()
                .format(ISO_LOCAL_DATE_TIME)
    )

    fun isolateTask(
        label: String
    ): TaskWorkspace = TaskWorkspace(
        directory.resolveSafely(label).ensureDirectory()
    )

    fun listPreviousTasks(): List<TaskWorkspace> = directory
        .toFile()
        .directories()
        .map { TaskWorkspace(it.toPath()) }
        .filter { it != currentTask }
}