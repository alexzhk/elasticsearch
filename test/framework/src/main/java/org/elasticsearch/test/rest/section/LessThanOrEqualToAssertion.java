/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.test.rest.section;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a lte assert section:
 *
 *   - lte:     { fields._ttl: 0 }
 */
public class LessThanOrEqualToAssertion  extends Assertion {

    private static final ESLogger logger = Loggers.getLogger(LessThanOrEqualToAssertion.class);

    public LessThanOrEqualToAssertion(String field, Object expectedValue) {
        super(field, expectedValue);
    }

    @Override
    protected void doAssert(Object actualValue, Object expectedValue) {
        logger.trace("assert that [{}] is less than or equal to [{}] (field: [{}])", actualValue, expectedValue, getField());
        assertThat("value of [" + getField() + "] is not comparable (got [" + safeClass(actualValue) + "])", actualValue, instanceOf(Comparable.class));
        assertThat("expected value of [" + getField() + "] is not comparable (got [" + expectedValue.getClass() + "])", expectedValue, instanceOf(Comparable.class));
        try {
            assertThat(errorMessage(), (Comparable) actualValue, lessThanOrEqualTo((Comparable) expectedValue));
        } catch (ClassCastException e) {
            fail("cast error while checking (" + errorMessage() + "): " + e);
        }
    }

    private String errorMessage() {
        return "field [" + getField() + "] is not less than or equal to [" + getExpectedValue() + "]";
    }
}
