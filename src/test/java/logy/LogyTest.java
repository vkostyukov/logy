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

package logy;

import static logy.Logy.*;

import java.util.*;

import junit.framework.*;

public class LogyTest extends TestCase {

	public static Test suite() {
		return new TestSuite(LogyTest.class);
	}

	public void testContextAndScope() {
		Map<String, String> context = context(scope());
		assertEquals(context.get("scope"), 
			"sun.reflect.NativeMethodAccessorImpl.invoke");
		assertEquals(context.get("class"), 
			"sun.reflect.NativeMethodAccessorImpl");
		assertEquals(context.get("method"), "invoke");
	}

	public void testQuote() {
		int arr[] = {1, 2, 3, 4};
		info("There is no", quote(5), "in array", quote(arr));
		
		assertEquals("\"[1, 2, 3, 4]\"", export(quote(arr)));
		assertEquals("\"1\" \"2\" \"3\" \"4\"", export(quote(scalar(arr))));
	}
}
