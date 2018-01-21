/**
 *    Copyright 2016-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.select.function;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class Constant<T> implements BasicColumn {

    private String alias;
    private T value;
    
    private Constant(T value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return value.toString();
    }

    @Override
    public Constant<T> as(String alias) {
        Constant<T> copy = new Constant<>(value);
        copy.alias = alias;
        return copy;
    }
    
    public static <T> Constant<T> of(T value) {
        return new Constant<>(value);
    }
}