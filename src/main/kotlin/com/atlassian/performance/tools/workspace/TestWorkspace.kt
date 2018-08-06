package com.atlassian.performance.tools.workspace

import java.nio.file.Path

/**
 * Contains files necessary to make the test work.
 */
data class TestWorkspace(
    val directory: Path
)