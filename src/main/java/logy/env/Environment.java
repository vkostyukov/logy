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

import logy.Logy.Level;
import logy.logger.*;

public abstract class Environment {

	public static final String DEFAULT_FORMAT = 
		"%date% %time% %scope% [%level%] :: %%%";
	public static final Level DEFAULT_LEVEL = Level.INFO;
	public static final Logger DEFAULT_LOGGER = new StreamLogger(System.out);

	public static Environment create(Map<String, String> content) {
		return new HashEnvironment(content);
	}

	public abstract String format(String scope);
	public abstract Level level(String scope);
	public abstract Logger logger(String scope);

}
