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
import java.util.*;
import java.util.regex.*;

import logy.env.*;

public class LogyParser implements Parser {

	public static final Pattern TRIPPLE = Pattern.compile("(.+)@(.*)=(.+)");

	@Override
	public Collection<Environment.Tripple> parse(File file) {

		List<Environment.Tripple> tripples = 
			new ArrayList<Environment.Tripple>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.ready()) {
				String line = reader.readLine();

				if (line.matches("^#.*")) {
					continue;
				}

				Matcher matcher = TRIPPLE.matcher(line);
				if (matcher.matches()) {
					String scope = matcher.group(2).isEmpty() ? "*" :
						matcher.group(2);
					tripples.add(new Environment.Tripple(
						scope, matcher.group(1), matcher.group(3)));
				} else {
					throw new IOException("Bad config file format!");
				}
			}
		} catch (IOException ignored) {
			// using defaults
			// return Environment.DEFAULT_TRIPPLES;
		}

		return tripples;
	}
}
