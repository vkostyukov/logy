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

package logy.logger;

import java.util.*;

import junit.framework.*;

public class LoggerTest extends TestCase {

	public static class IntrusiveLogger extends Logger {
		private Object obj;

		@Override
		public void log(Object obj) {
			this.obj = obj;
		}

		@Override
		public void newline() {
			// do nothing
		}

		public Object obj() {
			return obj;
		}
	}

	public static Test suite() {
		return new TestSuite(LoggerTest.class);
	}

	public void testFromString() {
		assertEquals("stream:out", Logger.fromString("stream:").toString());
		assertEquals("stream:out", Logger.fromString("stream").toString());
		assertEquals("stream:out", Logger.fromString("stream:out").toString());
		assertEquals("stream:err", Logger.fromString("stream:err").toString());

		assertEquals("file:test.log", 
			Logger.fromString("file:test.log").toString());
		assertEquals("file:" + FileLogger.DEFAULT_FILENAME, 
			Logger.fromString("file").toString());
		assertEquals("file:" + FileLogger.DEFAULT_FILENAME, 
				Logger.fromString("file:").toString());
	}

	public void testLog() {
		IntrusiveLogger logger = new IntrusiveLogger();
		Map<String, String> context = new HashMap<String, String>();
		context.put("a", "A");
		context.put("b", "B");
		context.put("c", "C");
		context.put("aaa", "A");
		context.put("bbb", "B");
		context.put("ccc", "C");

		logger.log("", "%a%%b%%c%", context);
		assertEquals("ABC", logger.obj());

		logger.log("", "%aaa%%a%A", context);
		assertEquals("AAA", logger.obj());

		logger.log("message", "%aaa%b%c%bb%b% %%%", context);
		assertEquals("AbCbbB message", logger.obj());
	}
}
