package com.atlassian.performance.tools.workspace.api

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
    private val parent: Path = Paths.get(System.getProperty("jpt.workspace.parent") ?: ".")
) {

    /**
     * The directory containing the root workspace.
     * Embedded within [parent].
     */
    val directory: Path = parent
        .resolve("jpt-workspace")
        .toAbsolutePath()
        .normalize()
        .ensureDirectory()

    /**
     * The current task workspace unique to this root workspace instance.
     */
    val currentTask: TaskWorkspace = isolateTask(
        label = System.getenv("bamboo_buildResultKey")
            ?: LocalDateTime.now()
                .format(ISO_LOCAL_DATE_TIME)
    )

    /**
     * Finds a new workspaces for a task.
     */
    fun isolateTask(
        label: String
    ): TaskWorkspace = TaskWorkspace(
        directory.resolveSafely(label).ensureDirectory()
    )

    /**
     * Lists the workspaces of all previous tasks.
     */
    fun listPreviousTasks(): List<TaskWorkspace> = directory
        .toFile()
        .directories()
        .map { TaskWorkspace(it.toPath()) }
        .filter { it != currentTask }
}