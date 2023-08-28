/*
 *    Copyright 2016-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.render;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderingContext {

    private final AtomicInteger sequence;
    private final RenderingStrategy renderingStrategy;
    private final TableAliasCalculator tableAliasCalculator;
    private final String parameterName;

    private RenderingContext(Builder builder) {
        sequence = Objects.requireNonNull(builder.sequence);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
        tableAliasCalculator = Objects.requireNonNull(builder.tableAliasCalculator);
        if (builder.parameterName == null) {
            parameterName = RenderingStrategy.DEFAULT_PARAMETER_PREFIX;
        } else {
            parameterName = builder.parameterName + "." + RenderingStrategy.DEFAULT_PARAMETER_PREFIX; //$NON-NLS-1$
        }
    }

    public AtomicInteger sequence() {
        return sequence;
    }

    public RenderingStrategy renderingStrategy() {
        return renderingStrategy;
    }

    public TableAliasCalculator tableAliasCalculator() {
        return tableAliasCalculator;
    }

    public String parameterName() {
        return parameterName;
    }

    public String nextMapKey() {
        return renderingStrategy.formatParameterMapKey(sequence);
    }

    public static Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
        return new Builder().withRenderingStrategy(renderingStrategy);
    }

    public static class Builder {
        private AtomicInteger sequence = new AtomicInteger(1);
        private RenderingStrategy renderingStrategy;
        private TableAliasCalculator tableAliasCalculator = TableAliasCalculator.empty();
        private String parameterName;

        public Builder withSequence(AtomicInteger sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public Builder withTableAliasCalculator(TableAliasCalculator tableAliasCalculator) {
            this.tableAliasCalculator = tableAliasCalculator;
            return this;
        }

        public Builder withParameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public RenderingContext build() {
            return new RenderingContext(this);
        }
    }
}
