/**
 *    Copyright 2016-2020 the original author or authors.
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
package org.mybatis.dynamic.sql.util;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

class ColumnMappingVisitorTest {

    @Test
    void testThatGeneralInsertVisitorErrorsForColumnToColumnMapping() {
        TestTable table = new TestTable();
        GeneralInsertVisitor tv = new GeneralInsertVisitor();
        ColumnToColumnMapping mapping = ColumnToColumnMapping.of(table.id, table.description);

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }

    @Test
    void testThatGeneralInsertVisitorErrorsForSelectMapping() {
        TestTable table = new TestTable();
        GeneralInsertVisitor tv = new GeneralInsertVisitor();
        SelectMapping mapping = SelectMapping.of(table.id, SqlBuilder.select(table.id).from(table));

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }

    @Test
    void testThatGeneralInsertVisitorErrorsForPropertyMapping() {
        TestTable table = new TestTable();
        GeneralInsertVisitor tv = new GeneralInsertVisitor();
        PropertyMapping mapping = PropertyMapping.of(table.id, "id");

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }
    
    @Test
    void testThatInsertVisitorErrorsForColumnToColumnMapping() {
        TestTable table = new TestTable();
        InsertVisitor tv = new InsertVisitor();
        ColumnToColumnMapping mapping = ColumnToColumnMapping.of(table.id, table.description);

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }

    @Test
    void testThatInsertVisitorErrorsForSelectMapping() {
        TestTable table = new TestTable();
        InsertVisitor tv = new InsertVisitor();
        SelectMapping mapping = SelectMapping.of(table.id, SqlBuilder.select(table.id).from(table));

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }

    @Test
    void testThatInsertVisitorErrorsForValueMapping() {
        TestTable table = new TestTable();
        InsertVisitor tv = new InsertVisitor();
        ValueMapping<Integer> mapping = ValueMapping.of(table.id, () -> 3);

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }

    @Test
    void testThatUpdateVisitorErrorsForPropertyMapping() {
        TestTable table = new TestTable();
        UpdateVisitor tv = new UpdateVisitor();
        PropertyMapping mapping = PropertyMapping.of(table.id, "id");

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> tv.visit(mapping));
    }
    
    private static class TestTable extends SqlTable {
        public SqlColumn<Integer> id;
        public SqlColumn<String> description;

        public TestTable() {
            super("Test");

            id = column("id");
            description = column("description");
        }
    }

    private static class GeneralInsertVisitor implements GeneralInsertMappingVisitor<String> {
        @Override
        public String visit(NullMapping mapping) {
            return "Null Mapping";
        }

        @Override
        public String visit(ConstantMapping mapping) {
            return "Constant Mapping";
        }

        @Override
        public String visit(StringConstantMapping mapping) {
            return "String Constant Mapping";
        }

        @Override
        public <R> String visit(ValueMapping<R> mapping) {
            return "Value Mapping";
        }
    }

    private static class InsertVisitor implements InsertMappingVisitor<String> {
        @Override
        public String visit(NullMapping mapping) {
            return "Null Mapping";
        }

        @Override
        public String visit(ConstantMapping mapping) {
            return "Constant Mapping";
        }

        @Override
        public String visit(StringConstantMapping mapping) {
            return "String Constant Mapping";
        }

        @Override
        public String visit(PropertyMapping mapping) {
            return "Property Mapping";
        }
    }

    private static class UpdateVisitor implements UpdateMappingVisitor<String> {
        @Override
        public String visit(NullMapping mapping) {
            return "Null Mapping";
        }

        @Override
        public String visit(ConstantMapping mapping) {
            return "Constant Mapping";
        }

        @Override
        public String visit(StringConstantMapping mapping) {
            return "String Constant Mapping";
        }

        @Override
        public <R> String visit(ValueMapping<R> mapping) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String visit(SelectMapping mapping) {
            return "Select Mapping";
        }

        @Override
        public String visit(ColumnToColumnMapping columnMapping) {
            return "Column to Column Mapping";
        }
    }
}