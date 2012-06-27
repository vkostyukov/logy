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

import logy.Logy.*;
import logy.logger.*;

public class HashEnvironment extends Environment {

	private Map<String, String> content;
	
	public HashEnvironment(Map<String, String> content) {
		this.content = content;
	}

	@Override
	public String format(String scope) {
		return DEFAULT_FORMAT;
	}

	@Override
	public Level level(String scope) {
		return DEFAULT_LEVEL;
	}

	@Override
	public Logger logger(String scope) {
		return DEFAULT_LOGGER;
	}
}
