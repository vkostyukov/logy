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

public abstract class Environment {

	public static final class Tripple {
		public final String scope;
		public final String key;
		public final String value;

		public Tripple(String scope, String key, String value) {
			this.scope = scope;
			this.key = key;
			this.value = value;
		}
	}

	public static Environment fromTripples(
		Collection<Environment.Tripple> tripples) {
		return new HashEnvironment(tripples);
	}

	protected Collection<Environment.Tripple> tripples;

	public Environment(Collection<Tripple> tripples) {
		this.tripples = tripples;
	}

	public String format(String scope) {
		return Logger.DEFAULT_FORMAT;
	}

	public Logger.Level level(String scope) {
		return Logger.DEFAULT_LEVEL;
	}

	public Logger logger(String scope) {
		return Logger.DEFAULT_LOGGER; 
	}
}
