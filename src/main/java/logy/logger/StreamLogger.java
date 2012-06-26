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

public class StreamLogger implements Logger {

	private PrintStream stream;

	public StreamLogger(PrintStream stream) {
		this.stream = stream;
	}

	@Override
	public void log(Object obj, String format, Map<String, String> context) {
		stream.print(obj.toString());
	}

	@Override
	public void newline() {
		stream.println();
	}
}
