/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr.analytics.value;

import java.util.Arrays;
import java.util.Iterator;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.analytics.value.FillableTestValue.TestBooleanValueStream;
import org.junit.Test;

public class CastingBooleanValueStreamTest extends SolrTestCaseJ4 {

  @Test
  public void stringStreamCastingTest() {
    TestBooleanValueStream val = new TestBooleanValueStream();

    assertTrue(val instanceof StringValueStream);
    StringValueStream casted = (StringValueStream) val;

    // No values
    val.setValues();
    casted.streamStrings(
        value -> {
          fail("There should be no values to stream");
        });

    // Multiple Values
    val.setValues(false, true, false);
    Iterator<String> values = Arrays.asList("false", "true", "false").iterator();
    casted.streamStrings(
        value -> {
          assertTrue(values.hasNext());
          assertEquals(values.next(), value);
        });
    assertFalse(values.hasNext());
  }

  @Test
  public void objectStreamCastingTest() {
    TestBooleanValueStream val = new TestBooleanValueStream();

    assertTrue(val instanceof AnalyticsValueStream);
    AnalyticsValueStream casted = (AnalyticsValueStream) val;

    // No values
    val.setValues();
    casted.streamObjects(
        value -> {
          fail("There should be no values to stream");
        });

    // Multiple Values
    val.setValues(false, true, false);
    Iterator<Object> values =
        Arrays.<Object>asList(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE).iterator();
    casted.streamObjects(
        value -> {
          assertTrue(values.hasNext());
          assertEquals(values.next(), value);
        });
    assertFalse(values.hasNext());
  }

  @Test
  public void constantConversionTest() {
    AnalyticsValueStream val = new TestBooleanValueStream();
    assertSame(val, val.convertToConstant());
  }
}
