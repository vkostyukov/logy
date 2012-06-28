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
import java.util.regex.*;

import logy.logger.*;
import logy.logger.Logger.Level;

public class RegexEnvironment extends Environment {

	private Map<String, Logger> loggers;
	private Map<String, String> formats;
	private Map<String, Logger.Level> levels;

	private List<Pattern> patterns;

	public RegexEnvironment(Collection<Environment.Tripple> tripples) {
		super(tripples);

		this.loggers = new HashMap<String, Logger>();
		this.formats = new HashMap<String, String>();
		this.levels = new HashMap<String, Logger.Level>();

		Set<String> regexes = new HashSet<String>();
		for(Environment.Tripple tripple: tripples) {
			String regex = tripple.scope;

			if (regex.isEmpty()) {
				regex = "^.*$";
			} else {
				regex = "^" + tripple.scope.replaceAll("\\.", "\\\\.")
					.replaceAll("\\*", ".*") + "$";
			}
			regexes.add(regex);

			if (tripple.key.equals("logger")) {
				loggers.put(regex, Logger.fromString(tripple.value));
			} else if (tripple.key.equals("format")) {
				formats.put(regex, tripple.value);
			} else if (tripple.key.equals("level")) {
				levels.put(regex, Logger.Level.valueOf(
					tripple.value.toUpperCase()));
			}
		}

		List<String> sorted = new ArrayList<String>(regexes);
		Collections.sort(sorted, new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				int one = arg0.split("\\\\.").length;
				int two = arg1.split("\\\\.").length;
				if (one == two) {
					one = arg1.split("\\*").length;
					two = arg0.split("\\*").length;
				}
				return two - one;
			}
		});

		this.patterns = new ArrayList<Pattern>();
		for (String regex: sorted) {
			patterns.add(Pattern.compile(regex));
		}
	}

	@Override
	public String format(String scope) {
		for (Pattern pattern: patterns) {
			if (pattern.matcher(scope).matches()) {
				return formats.get(pattern.pattern());
			}
		}
		return super.format(scope);
	}

	@Override
	public Level level(String scope) {
		for (Pattern pattern: patterns) {
			if (pattern.matcher(scope).matches()) {
				return levels.get(pattern.pattern());
			}
		}
		return super.level(scope);
	}

	@Override
	public Logger logger(String scope) {
		for (Pattern pattern: patterns) {
			if (pattern.matcher(scope).matches()) {
				return loggers.get(pattern.pattern());
			}
		}
		return super.logger(scope);
	}
}
