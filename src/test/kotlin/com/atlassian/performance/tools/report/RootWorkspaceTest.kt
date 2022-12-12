package com.atlassian.performance.tools.report

import com.atlassian.performance.tools.workspace.api.RootWorkspace
import org.assertj.core.util.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RootWorkspaceTest {

    @Test
    fun shouldCreatingNewInstanceHaveNoSideEffect() {
        // given
        val tempDir = Files.newTemporaryFolder()
        // when
        RootWorkspace(parent = tempDir.toPath())
        // then
        assertThat(tempDir).isEmptyDirectory()
    }

    @Test
    fun shouldCreateDirWhenGetCurrentTask() {
        // given
        val tempDir = Files.newTemporaryFolder()
        // when
        RootWorkspace(parent = tempDir.toPath()).currentTask
        // then
        assertThat(tempDir).isNotEmptyDirectory()
    }

}
