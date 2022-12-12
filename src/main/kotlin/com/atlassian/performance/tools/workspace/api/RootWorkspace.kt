package com.atlassian.performance.tools.workspace.api

import com.atlassian.performance.tools.io.api.directories
import com.atlassian.performance.tools.io.api.ensureDirectory
import com.atlassian.performance.tools.io.api.resolveSafely
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
    val directory: Path by lazy {
        parent
            .resolve("jpt-workspace")
            .toAbsolutePath()
            .normalize()
            .ensureDirectory()
    }

    /**
     * The current task workspace unique to this root workspace instance.
     */
    val currentTask: TaskWorkspace by lazy {
        isolateTask(
            label = System.getenv("bamboo_buildResultKey")
                ?: LocalDateTime.now()
                    .format(ISO_LOCAL_DATE_TIME)
        )
    }

    /**
     * Finds a new workspaces for a task.
     */
    fun isolateTask(label: String): TaskWorkspace = TaskWorkspace(directory.resolveSafely(label))

    /**
     * Lists the workspaces of all tasks.
     */
    fun listTasks(): List<TaskWorkspace> = directory
        .toFile()
        .directories()
        .map { TaskWorkspace(it.toPath()) }

    /**
     * Lists the workspaces of all previous tasks.
     */
    @Deprecated(
        message = "You probably don't need to filter out the [currentTask], and if you do, you can do it manually",
        replaceWith = ReplaceWith("listTasks()")
    )
    fun listPreviousTasks(): List<TaskWorkspace> = listTasks().filter { it != currentTask }
}
