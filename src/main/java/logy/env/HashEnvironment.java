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

import logy.logger.*;

public class HashEnvironment extends Environment {

	public static final class HashedScope {
		private final String scope;

		public HashedScope(String scope) {
			this.scope = scope;
		}
	}

	private static final HashEnvironment.HashedScope GLOBAL_SCOPE = new HashedScope("*");

	private Map<HashEnvironment.HashedScope, Logger> loggers;
	private Map<HashEnvironment.HashedScope, String> formats;
	private Map<HashEnvironment.HashedScope, Logger.Level> levels;

	public HashEnvironment(Collection<Environment.Tripple> tripples) {
		super(tripples);

		this.loggers = new HashMap<HashEnvironment.HashedScope, Logger>();
	}		

	@Override
	public String format(String scope) {
		if (formats.containsKey(new HashedScope(scope))) {
			return formats.get(scope);
		} else if (formats.containsKey(GLOBAL_SCOPE)) {
			return formats.get(GLOBAL_SCOPE);
		} else {
			return super.format(scope);
		}
	}
}
