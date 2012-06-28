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

package logy.parser;

import java.io.*;
import java.net.*;
import java.util.*;

import junit.framework.*;
import logy.env.*;

public abstract class ParserTest extends TestCase {

	public abstract Parser parser();

	public void testEmpty() {
		URL url = getClass().getClassLoader().getResource("empty.logy");
		Parser parser = parser();

		Collection<Environment.Tripple> tripples = parser.parse(
			new File(url.getPath()));
		assertEquals(0, tripples.size());
	}

	public void testLost() {
		Parser parser = parser();

		Collection<Environment.Tripple> tripples = parser.parse(
			new File("lost.logy"));
		assertEquals(0, tripples.size());
	}
	

	public void testBroken() {
		
	}

	public void testSimple() {
		URL url = getClass().getClassLoader().getResource("simple.logy");
		Parser parser = parser();

		Collection<Environment.Tripple> tripples = parser.parse(
			new File(url.getPath())
		);
		assertEquals(3, tripples.size());

		Set<String> dump = dump(tripples);
		assertTrue(dump.contains("level@*=debug"));
		assertTrue(dump.contains("format@*=%date% %%%"));
		assertTrue(dump.contains("logger@*=stream:err"));
	}
	
	public void testMedium() {
		URL url = getClass().getClassLoader().getResource("medium.logy");
		Parser parser = parser();

		Collection<Environment.Tripple> tripples = parser.parse(
			new File(url.getPath())
		);
		assertEquals(5, tripples.size());

		Set<String> dump = dump(tripples);
		assertTrue(dump.contains("format@*=%class% %%%"));
		assertTrue(dump.contains("logger@*=stream:out"));
		assertTrue(dump.contains("logger@a.*=stream:err"));
		assertTrue(dump.contains("level@a.b.*=info"));
		assertTrue(dump.contains("level@*=debug"));
	}
	
	public void testHard() {
		URL url = getClass().getClassLoader().getResource("hard.logy");
		Parser parser = parser();

		Collection<Environment.Tripple> tripples = parser.parse(
			new File(url.getPath())
		);
		assertEquals(7, tripples.size());

		Set<String> dump = dump(tripples);
		assertTrue(dump.contains("format@*=%date%%%scope% %%%"));
		assertTrue(dump.contains("format@a.*= %date% % %%%"));
		assertTrue(dump.contains("logger@*=stream:err"));
		assertTrue(dump.contains("logger@a.*.c=stream"));
		assertTrue(dump.contains("logger@a.b.*=file:test.log"));
		assertTrue(dump.contains("level@a.b.*.c.*=NONE"));
		assertTrue(dump.contains("level@*=WARN"));
	}
	
	private Set<String> dump(Collection<Environment.Tripple> tripples) {
		Set<String> result = new HashSet<String>();
		for (Environment.Tripple tripple: tripples) {
			result.add(tripple.toString());
		}
		return result;
	}
}
