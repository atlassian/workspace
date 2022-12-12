package com.atlassian.performance.tools.report

import com.atlassian.performance.tools.workspace.api.RootWorkspace
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.util.Files
import org.junit.Test

class RootWorkspaceTest {

    @Test
    fun shouldCreatingNewInstanceHaveNoSideEffect() {
        // given
        val parent = Files.newTemporaryFolder()
        // when
        RootWorkspace(parent = parent.toPath())
        // then
        assertThat(parent).isEmptyDirectory()
    }

    @Test
    fun shouldCreateDirectoryOnRead() {
        // given
        val parent = Files.newTemporaryFolder()
        // when
        RootWorkspace(parent = parent.toPath()).directory
        // then
        assertThat(parent).isNotEmptyDirectory()
    }

}
