/*
 * Logy Project: https://github.com/vkostyukov/logy
 * 
 * Copyright 2012 Vladimir Kostyukov (http://vkostyukov.ru)
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

package logy.env;

import java.util.*;

import junit.framework.*;
import logy.logger.*;

public abstract class EnvironmentTest extends TestCase {

	public abstract Environment environment(
		Collection<Environment.Tripple> tripples);

	public void testDefaults() {
		List<Environment.Tripple> tripples = Arrays.asList();
		Environment env = environment(tripples);

		assertEquals(Logger.DEFAULT_LEVEL, env.level("any.package"));
		assertEquals(Logger.DEFAULT_LOGGER, env.logger("any.package"));
		assertEquals(Logger.DEFAULT_FORMAT, env.format("any.package"));
	}

	public void testFormat() {
		List<Environment.Tripple> tripples = 
			Arrays.asList(
				new Environment.Tripple("*", "format", "global"),
				new Environment.Tripple("a.*", "format", "a"),
				new Environment.Tripple("a.b.*", "format", "ab"),
				new Environment.Tripple("a.*.c.*", "format", "ac"),
				new Environment.Tripple("a.b.c.*", "format", "abc"),
				new Environment.Tripple("a.b.c.d", "format", "abcd")
			);

		Environment env = environment(tripples);

		assertEquals("global", env.format("any.package"));
		assertEquals("a", env.format("a.package"));
		assertEquals("ab", env.format("a.b.package"));
		assertEquals("ac", env.format("a.d.c.package"));
		assertEquals("abc", env.format("a.b.c.package"));
		assertEquals("abcd", env.format("a.b.c.d"));
	}

	public void testLevel() {
		List<Environment.Tripple> tripples = 
			Arrays.asList(
				new Environment.Tripple("*", "level", "info"),
				new Environment.Tripple("a.*", "level", "FINE"),
				new Environment.Tripple("*.b.*", "level", "error"),
				new Environment.Tripple("*.c", "level", "debug"),
				new Environment.Tripple("*.c.*", "level", "warn"),
				new Environment.Tripple("*.b.*.c", "level", "NONE")
			);

		Environment env = environment(tripples);

		assertEquals(Logger.Level.INFO, env.level("any.package"));
		assertEquals(Logger.Level.FINE, env.level("a.package"));
		assertEquals(Logger.Level.ERROR, env.level("a.b.package"));
		assertEquals(Logger.Level.DEBUG, env.level("package.c"));
		assertEquals(Logger.Level.WARN, env.level("package.c.package"));
		assertEquals(Logger.Level.NONE, env.level("a.b.package.c"));
	}

	public void testLogger() {
		List<Environment.Tripple> tripples = 
			Arrays.asList(
				new Environment.Tripple("*", "logger", "stream"),
				new Environment.Tripple("a.*", "logger", "file")
			);

		Environment env = environment(tripples);

		assertEquals(StreamLogger.class, env.logger("any.package").getClass());
		assertEquals(FileLogger.class, env.logger("a.package").getClass());
	}
}
