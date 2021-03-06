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

package org.gradle.internal.logging.events;

import org.gradle.api.Nullable;
import org.gradle.api.logging.LogLevel;
import org.gradle.internal.logging.text.StyledTextOutput;

public class LogEvent extends RenderableOutputEvent {
    private final String message;
    private final Throwable throwable;

    public LogEvent(long timestamp, String category, LogLevel logLevel, String message, @Nullable Throwable throwable) {
        super(timestamp, category, logLevel);
        this.message = message;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    public void render(StyledTextOutput output) {
        output.text(message);
        output.println();
        if (throwable != null) {
            output.exception(throwable);
        }
    }

    @Override
    public String toString() {
        return "[" + getLogLevel() + "] [" + getCategory() + "] " + message;
    }
}
