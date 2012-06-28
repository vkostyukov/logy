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

import java.io.*;
import java.util.*;
import java.util.regex.*;

public abstract class Logger {

	public static enum Level {
		NONE, DEBUG, ERROR, WARN, INFO, FINE, DEFAULT
	}

	public static final Logger DEFAULT_LOGGER = new StreamLogger(System.out);

	public static final String DEFAULT_FORMAT = 
		"%date% %time% %scope% [%level%] :: %%%";

	public static final Level DEFAULT_LEVEL = Level.INFO;

	private static final Pattern PATTERN = Pattern.compile("(\\w+):.*");
	
	public static Logger fromString(String string) {
		// TODO: I can write it better!
		Matcher matcher = PATTERN.matcher(string);
		if (matcher.matches()) {
			if (matcher.group(1).equals("stream")) {
				if (matcher.groupCount() > 2) {
					if (matcher.group(2).equals("err")) {
						return new StreamLogger(System.err);
					} else if (matcher.group(2).equals("out")) {
						return new StreamLogger(System.out);
					} else {
						return DEFAULT_LOGGER;
					}
				} else {
					return DEFAULT_LOGGER;
				}
			} else if (matcher.group(1).equals("file")) {
				if (matcher.groupCount() > 2) {
					return new FileLogger(new File(matcher.group(2)));
				} else {
					return new FileLogger(
						new File(FileLogger.DEFAULT_FILENAME));
				}
			} else {
				return DEFAULT_LOGGER;
			}
		} else {
			return DEFAULT_LOGGER;
		}
	}

	public void log(Object obj, String format, Map<String, String> context) {

		String message = format;

		for (String pattern: context.keySet()) {
			message = message.replaceAll("%" + pattern + "%", 
				context.get(pattern));
		}

		log(message.replaceAll("%%%", obj.toString()));
	}

	public abstract void log(Object obj);

	public abstract void newline();
}
