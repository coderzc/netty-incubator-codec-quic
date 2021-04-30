/*
 * Copyright 2020 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.incubator.codec.quic;

import io.netty.channel.ChannelOption;
import io.netty.util.AttributeKey;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuicTest extends AbstractQuicTest {

    @Test
    public void test() {
        Quic.ensureAvailability();
        assertNotNull(Quiche.quiche_version());
    }

    @Test
    public void testVersionSupported() {
        // draft-27, draft-28, draft-29 and 1 should be supported.
        assertTrue(Quic.isVersionSupported(0xff00_001b));
        assertTrue(Quic.isVersionSupported(0xff00_001c));
        assertTrue(Quic.isVersionSupported(0xff00_001d));
        assertTrue(Quic.isVersionSupported(0x0000_0001));

        assertFalse(Quic.isVersionSupported(0xff00_001a));
    }

    @Test
    public void testToAttributesArrayDoesCopy() {
        AttributeKey<String> key = AttributeKey.valueOf(UUID.randomUUID().toString());
        String value = "testValue";
        Map<AttributeKey<?>, Object> attributes = new HashMap<>();
        attributes.put(key, value);
        Map.Entry<AttributeKey<?>, Object>[] array = Quic.toAttributesArray(attributes);
        assertEquals(1, array.length);
        attributes.put(key, "newTestValue");
        Map.Entry<AttributeKey<?>, Object> entry = array[0];
        assertEquals(key, entry.getKey());
        assertEquals(value, entry.getValue());
    }

    @Test
    public void testToOptionsArrayDoesCopy() {
        Map<ChannelOption<?>, Object> options = new HashMap<>();
        options.put(ChannelOption.AUTO_READ, true);
        Map.Entry<ChannelOption<?>, Object>[] array = Quic.toOptionsArray(options);
        assertEquals(1, array.length);
        options.put(ChannelOption.AUTO_READ, false);
        Map.Entry<ChannelOption<?>, Object> entry = array[0];
        assertEquals(ChannelOption.AUTO_READ, entry.getKey());
        assertEquals(true, entry.getValue());
    }
}
