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

package org.gradle.tooling.internal.protocol;

import org.gradle.tooling.internal.protocol.exceptions.InternalUnsupportedBuildArgumentException;
import org.gradle.tooling.internal.protocol.test.InternalTestExecutionConnection;

public interface InternalCompositeAwareConnection extends InternalTestExecutionConnection, InternalCancellableConnection {
    /**
     * Performs some action against a composite and returns the requested model.
     *
     * <p>Consumer compatibility: This method is used by all consumer versions from 2.13-rc-1.</p>
     * <p>Provider compatibility: This method is implemented by all provider versions from 2.13-rc-1.</p>
     *
     * @param modelIdentifier The identifier of the model to build.
     * @param cancellationToken The token to propagate cancellation.
     * @throws BuildExceptionVersion1 On build failure.
     * @throws InternalUnsupportedModelException When the requested model is not supported.
     * @throws InternalUnsupportedBuildArgumentException When the specified command-line options are not supported.
     * @throws InternalBuildCancelledException When the operation was cancelled before it could complete.
     * @throws IllegalStateException When this connection has been stopped.
     * @since 2.13-rc-1
     */
    BuildResult<?> getModels(ModelIdentifier modelIdentifier, InternalCancellationToken cancellationToken,
                            BuildParameters operationParameters) throws
        BuildExceptionVersion1,
        InternalUnsupportedModelException,
        InternalUnsupportedBuildArgumentException,
        InternalBuildCancelledException,
        IllegalStateException;
}
