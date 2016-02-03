/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.integtests.tooling.fixture

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.gradle.test.fixtures.file.TestFile
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.composite.GradleConnection

@ToolingApiVersion('>=2.12')
@TargetGradleVersion('>=2.12') // relax later
class CompositeToolingApiSpecification extends ToolingApiSpecification {

    GradleConnection createComposite(File... rootProjectDirectories) {
        createComposite(rootProjectDirectories as List<File>)
    }

    GradleConnection createComposite(List<File> rootProjectDirectories) {
        GradleConnection.Builder builder = GradleConnector.newGradleConnectionBuilder()

        rootProjectDirectories.each {
            // TODO: this isn't the right way to configure the gradle distribution
            builder.addBuild(it, dist.binDistribution.toURI())
        }

        builder.build()
    }

    void withCompositeConnection(File rootProjectDir, @ClosureParams(value = SimpleType, options = [ "org.gradle.tooling.composite.GradleConnection" ]) Closure c) {
        withCompositeConnection([rootProjectDir], c)
    }

    void withCompositeConnection(List<File> rootProjectDirectories, @ClosureParams(value = SimpleType, options = [ "org.gradle.tooling.composite.GradleConnection" ]) Closure c) {
        GradleConnection connection
        try {
            connection = createComposite(rootProjectDirectories)
            c(connection)
        } finally {
            connection?.close()
        }
    }

    TestFile getRootDir() {
        temporaryFolder.testDirectory
    }

    TestFile getProjectDir() {
        // TODO: Refactor ToolingApiSpecification
        throw new RuntimeException("need to specific participant")
    }

    TestFile getBuildFile() {
        // TODO: Refactor ToolingApiSpecification
        throw new RuntimeException("need to specific participant")
    }

    TestFile getSettingsFile() {
        // TODO: Refactor ToolingApiSpecification
        throw new RuntimeException("need to specific participant")
    }

    TestFile file(Object... path) {
        rootDir.file(path)
    }

    def populate(String project, @DelegatesTo(TestFile) Closure cl) {
        def projectDir = rootDir.file(project)
        projectDir.with(cl)
    }

    TestFile projectDir(String project) {
        file(project)
    }
}