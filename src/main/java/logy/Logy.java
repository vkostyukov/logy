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

public class Logy {

	private static final int LEVEL_DEBUG = 0;
	private static final int LEVEL_ERROR = 1;
	private static final int LEVEL_WARN = 2;
	private static final int LEVEL_INFO = 3;
	private static final int LEVEL_FINE = 4;
	
	public static void dump(Object ... objs) {
		log(LEVEL_DEBUG, export(objs));
	}
	
	public static void debug(Object ... objs) {
		log(LEVEL_DEBUG, objs);
	}
	
	public static void error(Object ... objs) {
		log(LEVEL_ERROR, objs);
	}
	
	public static void warn(Object ... objs) {
		log(LEVEL_WARN, objs);
	}
	
	public static void info(Object ... objs) {
		log(LEVEL_INFO, objs);
	}
	
	public static void fine(Object ... objs) {
		log(LEVEL_FINE, objs);
	}
	
	public static void log(Object ... objs) {
		
	}
	
	private static void log(int level, Object ... objs) {
		
	}
	
	public static String export(Object ... objs) {
		return "";
	}
}
