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

import logy.context.*;
import logy.parser.*;
import logy.logger.*;

public final class Logy {
	
	public static enum Level {
		NONE, DEBUG, ERROR, WARN, INFO, FINE, DEFAULT
	}
	
	private static Context context = new LogyParser().parse();

	public static void dump(Object ... objs) {
		log(Level.DEBUG, export(objs));
	}
	
	public static void debug(Object ... objs) {
		log(Level.DEBUG, objs);
	}
	
	public static void error(Object ... objs) {
		log(Level.ERROR, objs);
	}
	
	public static void warn(Object ... objs) {
		log(Level.WARN, objs);
	}
	
	public static void info(Object ... objs) {
		log(Level.INFO, objs);
	}
	
	public static void fine(Object ... objs) {
		log(Level.FINE, objs);
	}
	
	public static void log(Object ... objs) {
		log(Level.DEFAULT, objs);
	}
	
	private static void log(Level level, Object ... objs) {

		Logger logger = context.logger(scope());
		for (Object obj: objs) {
			logger.log(obj);
		}
		logger.newline();
		
		//Level level = context.level(scope());
	}
	
	public static String export(Object ... objs) {
		return "";
	}
	
	private static String scope() {
		return "";
	}
}
