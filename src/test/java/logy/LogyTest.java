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

	public void testExport() {
		String strArr[] = {"a", "b", "c", "d"};
		int intArr[] = {1, 2, 3, 4};

		assertEquals(
			"[\"A\", \"B\", \"C\", \"D\", \"1\", \"2\", \"3\", \"4\"]",
			export(group(upper(quote(scalar(strArr))), quote(scalar(intArr)))));
	}

	public void testLower() {
		String arr[] = {"A", "B", "C"};

		assertEquals("[a, b, c]", export(lower(array(arr))));
		assertEquals("a b c", export(lower(scalar(arr))));
		assertEquals("[a, b, c] d e", export(lower(arr, "D", "E")));
	}

	public void testUpper() {
		String arr[] = {"a", "b", "c"};

		assertEquals("[A, B, C]", export(upper(array(arr))));
		assertEquals("A B C", export(upper(scalar(arr))));
		assertEquals("[A, B, C] D E", export(upper(arr, "D", "E")));
	}

	public void testQuote() {
		int arr[] = {1, 2, 3, 4};

		assertEquals("\"[1, 2, 3, 4]\"", export(quote(arr)));
		assertEquals("\"1\" \"2\" \"3\" \"4\"", export(quote(scalar(arr))));
		assertEquals("\"\"A\"\" \"\"B\"\" \"\"C\"\" \"\"[1, 2, 3, 4]\"\"", 
			export(quote(quote("A", "B", "C", arr))));
	}

	public void testJoin() {
		String arr[] = {"a", "b", "c", "d"};

		assertEquals("1 2 3 a b c", export(join(1, 2, 3, "a", "b", "c")));
		assertEquals("a b c d", export(join(scalar(arr))));
	}

	public void testGroup() {
		String arr[] = {"a", "b", "c", "d"};

		assertEquals("[1, 2, a, b]", export(group(1, 2, "a", "b")));
		assertEquals("[a, b, c, d]", export(group(scalar(arr))));
		assertEquals("[a, b, c, d, a, 1]", export(group(scalar(arr), "a", 1)));
	}

	public void testScalar() {
		String arr[] = {"a", "b", "c", "d"};

		assertEquals("a b c d", export(scalar(arr)));
		assertEquals("1 2 3", export(scalar(new int[] {1, 2, 3})));
	}

	public void testArray() {
		String strArr[] = {"a", "b", "c", "d"};
		int intArr[] = {1, 2, 3, 4};

		assertEquals("[a, b, c, d]", export(array(strArr)));
		assertEquals("[1, 2, 3, 4]", export(intArr));
	}
}
