# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## API
The API consists of all public Java types from `com.atlassian.performance.tools.workspace.api` and its subpackages:

  * [source compatibility]
  * [binary compatibility]
  * [behavioral compatibility] with behavioral contracts expressed via Javadoc

[source compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#source_compatibility
[binary compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#binary_compatibility
[behavioral compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#behavioral_compatibility

## [Unreleased]
[Unreleased]: https://bitbucket.org/atlassian/workspace/branches/compare/master%0Drelease-1.0.0

## [1.1.0] - 2018-08-21
[1.1.0]: https://bitbucket.org/atlassian/workspace/branches/compare/release-1.1.0%0Drelease-1.0.0

### Added
- Add `RootWorkspace.listTasks`.

### Deprecated
- Deprecate `RootWorkspace.listPreviousTasks`.

## [1.0.0] - 2018-08-21
[1.0.0]: https://bitbucket.org/atlassian/workspace/branches/compare/release-1.0.0%0Drelease-0.0.1

### Changed
- Define the public API.

### Added
- Add missing [CHANGELOG.md](CHANGELOG.md).

## [0.0.1] - 2018-08-06
[0.0.1]: https://bitbucket.org/atlassian/workspace/branches/compare/release-0.0.1%0Dinitial-commit

### Added
- Migrate multi-task workspace management from [JPT submodule].
- Add [README.md](README.md).
- Configure Bitbucket Pipelines.

[JPT submodule]: https://stash.atlassian.com/projects/JIRASERVER/repos/jira-performance-tests/browse/workspace?at=30c7fb028b4254f7f510e58526f0f85253bf994f
