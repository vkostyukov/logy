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

import java.util.*;

import logy.env.*;
import logy.logger.*;
import logy.parser.*;

public final class Logy {
	
	public static enum Level {
		NONE, DEBUG, ERROR, WARN, INFO, FINE, DEFAULT
	}

	private static class Intrusive {
		
		private Object obj;
		
		public Intrusive(Object obj) {
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
	
	private static String fetch(Object obj) {
		if (obj instanceof Intrusive[]) {
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
		Intrusive result[] = new Intrusive[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Intrusive(fetch(objs[i]).toUpperCase());
		}
		return result;
	}
	
	public static Object[] lower(Object ... objs) {
		Intrusive result[] = new Intrusive[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Intrusive(fetch(objs[i]).toLowerCase());
		}
		return result;
	}
	
	public static Object[] quote(Object ... objs) {
		Intrusive result[] = new Intrusive[objs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Intrusive("\"" + fetch(objs[i]) + "\"");
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
	
	private static String scope() {
//		Thread.currentThread().getS
		return "";
	}
	
	private static Map<String, String> context(String scope) {

		
		
		//Locale locale = Locale.forLanguageTag(System.getProperty("user.language"));
		//new Date

		return new HashMap<String, String>();
	}
}
