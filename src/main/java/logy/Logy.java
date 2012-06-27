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

import java.text.*;
import java.util.*;

import logy.env.*;
import logy.logger.*;
import logy.parser.*;

public final class Logy {
	
	public static enum Level {
		NONE, DEBUG, ERROR, WARN, INFO, FINE, DEFAULT
	}

	private static class IntrusiveObject {
		
		private Object obj;
		
		public IntrusiveObject(Object obj) {
			this.obj = obj;
		}

		@Override
		public String toString() {
			return obj.toString();
		}
	}

	private static Environment env = new LogyParser().parse();

	public static Object join(Object ... objs) {
		StringBuilder joiner = new StringBuilder();
		for (Object obj: objs) {
			joiner.append(fetch(obj));
			joiner.append(" ");
		}
		joiner.delete(joiner.length() - 1, joiner.length());
		return joiner.toString();
	}

	public static Object group(Object ... objs) {
		if (objs instanceof IntrusiveObject[]) {
			Object result[] = new Object[objs.length];
			for (int i = 0; i < objs.length; i++) {
				result[i] = objs[i].toString();
			}
			return result;
		} else {
			return objs;
		}
	}

	private static String fetch(Object obj) {
		if (obj == null) {
			return "null";
		} else if (obj instanceof IntrusiveObject[]) {
			return join(scalar(obj)).toString();
		} else if (obj instanceof int[]) {
			return Arrays.toString((int[]) obj);
		} else if (obj instanceof double[]) {
			return Arrays.toString((double[]) obj);
		} else if (obj instanceof boolean[]) {
			return Arrays.toString((boolean[]) obj);
		} else if (obj instanceof char[]) {
			return Arrays.toString((char[]) obj);
		} else if (obj instanceof byte[]) {
			return Arrays.toString((byte[]) obj);
		} else if (obj instanceof long[]) {
			return Arrays.toString((long[]) obj);
		} else if (obj instanceof short[]) {
			return Arrays.toString((short[]) obj);
		} else if (obj instanceof Object[]) {
			return Arrays.deepToString((Object[]) obj);
		} else {
			return obj.toString();
		}
	}

	public static Object[] upper(Object ... objs) {
		IntrusiveObject result[] = new IntrusiveObject[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new IntrusiveObject(fetch(objs[i]).toUpperCase());
		}
		return result;
	}

	public static Object[] lower(Object ... objs) {
		IntrusiveObject result[] = new IntrusiveObject[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new IntrusiveObject(fetch(objs[i]).toLowerCase());
		}
		return result;
	}

	public static Object[] quote(Object ... objs) {
		IntrusiveObject result[] = new IntrusiveObject[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new IntrusiveObject("\"" + fetch(objs[i]) + "\"");
		}
		return result;
	}

	public static Object[] scalar(Object obj) {
		return (Object[]) obj;
	}

	public static Object array(Object obj[]) {
		return (Object) obj;
	}

	public static void debug(Object ... objs) {
		log(Level.DEBUG, join(objs).toString());
	}

	public static void error(Object ... objs) {
		log(Level.ERROR, join(objs).toString());
	}

	public static void warn(Object ... objs) {
		log(Level.WARN, join(objs).toString());
	}

	public static void info(Object ... objs) {
		log(Level.INFO, join(objs).toString());
	}

	public static void fine(Object ... objs) {
		log(Level.FINE, join(objs).toString());
	}

	public static void log(Object ... objs) {
		log(Level.DEFAULT, join(objs).toString());
	}

	private static void log(Level level, String obj) {

		String scope = scope();

		if (level == Level.DEFAULT) {
			level = env.level(scope);
		}

		if (level.ordinal() < env.level(scope).ordinal()) {
			return;
		}

		Map<String, String> context = context(scope);
		context.put("level", level.toString());
		
		String format = env.format(scope);
		
		Logger logger = env.logger(scope);
		logger.log(obj, format, context);
		logger.newline();
	}
	
	static String scope() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return stack[3].getClassName() + "." + stack[3].getMethodName();
	}
	
	static Map<String, String> context(String scope) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("scope", scope);

		// TODO: I have to use regular expressions here!
		String parts[] = scope.split("\\.");
		result.put("method", parts[parts.length - 1]);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length - 1; i++) {
			sb.append(parts[i]);
			if (i != parts.length - 2) {
				sb.append(".");
			}
		}
		result.put("class", sb.toString());

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
		result.put("date", df.format(now));

		DateFormat tf = DateFormat.getTimeInstance(DateFormat.DEFAULT);
		result.put("time", tf.format(now));

		return result;
	}
}
