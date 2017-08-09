/**
 *    Copyright 2016-2017 the original author or authors.
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
package org.mybatis.dynamic.sql.select.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mybatis.dynamic.sql.AbstractSqlSupport;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingUtilities;

public class SelectSupport extends AbstractSqlSupport {
    
    private static final String DISTINCT_STRING = "distinct"; //$NON-NLS-1$

    private Optional<String> tableAlias;
    private String columnList;
    private Optional<String> whereClause;
    private Map<String, Object> parameters = new HashMap<>();
    private String distinct;
    private Optional<String> orderByClause;
    private Optional<String> joinClause;
    
    private SelectSupport(SqlTable table) {
        super(table);
    }
    
    public String getDistinct() {
        return distinct().orElse(EMPTY_STRING);
    }
    
    private Optional<String> distinct() {
        return Optional.ofNullable(distinct);
    }
    
    public String getWhereClause() {
        return whereClause.orElse(EMPTY_STRING);
    }

    public Optional<String> whereClause() {
        return whereClause;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public String getOrderByClause() {
        return orderByClause.orElse(EMPTY_STRING);
    }
    
    public Optional<String> orderByClause() {
        return orderByClause;
    }
    
    public Optional<String> joinClause() {
        return joinClause;
    }
    
    public String getColumnList() {
        return columnList;
    }
    
    public String getFullSelectStatement() {
        return "select " //$NON-NLS-1$
                + distinct().map(d -> d + ONE_SPACE).orElse(EMPTY_STRING)
                + getColumnList()
                + " from " //$NON-NLS-1$
                + tableNameIncludingAlias(tableAlias)
                + joinClause().map(w -> ONE_SPACE + w).orElse(EMPTY_STRING)
                + whereClause().map(w -> ONE_SPACE + w).orElse(EMPTY_STRING)
                + orderByClause().map(o -> ONE_SPACE + o).orElse(EMPTY_STRING);
    }
    
    private String tableNameIncludingAlias(Optional<String> tableAlias) {
        return RenderingUtilities.tableNameIncludingAlias(table(), tableAlias);
    }
    
    public static class Builder {
        private Optional<String> tableAlias = Optional.empty();
        private String distinct;
        private Optional<String> orderByClause = Optional.empty();
        private String whereClause;
        private Map<String, Object> parameters = new HashMap<>();
        private String columnList;
        private SqlTable table;
        private Optional<String> joinClause;
        
        public Builder(SqlTable table) {
            this.table = table;
        }
        
        public Builder isDistinct(boolean isDistinct) {
            if (isDistinct) {
                distinct = DISTINCT_STRING;
            }
            return this;
        }
        
        public Builder withOrderByClause(Optional<String> orderByClause) {
            this.orderByClause = orderByClause;
            return this;
        }
        
        public Builder withWhereClause(String whereClause) {
            this.whereClause = whereClause;
            return this;
        }
        
        public Builder withParameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }
        
        public Builder withColumnList(String columnList) {
            this.columnList = columnList;
            return this;
        }
        
        public Builder withTableAlias(Optional<String> tableAlias) {
            this.tableAlias = tableAlias;
            return this;
        }
        
        public Builder withJoinClause(Optional<String> joinClause) {
            this.joinClause = joinClause;
            return this;
        }
        
        public SelectSupport build() {
            SelectSupport selectSupport = new SelectSupport(table);
            selectSupport.tableAlias = tableAlias;
            selectSupport.distinct = distinct;
            selectSupport.orderByClause = orderByClause;
            selectSupport.whereClause = Optional.ofNullable(whereClause);
            selectSupport.joinClause = joinClause;
            selectSupport.parameters = parameters;
            selectSupport.columnList = columnList;
            return selectSupport;
        }
    }
}