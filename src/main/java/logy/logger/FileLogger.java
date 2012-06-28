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

public class FileLogger extends Logger {

	public static final String DEFAULT_FILENAME = "logy.log";

	private File file;
	
	public FileLogger(File file) {
		this.file = file;
	}

	@Override
	public void log(Object obj) {
		try {
			BufferedWriter writter = new BufferedWriter(
				new FileWriter(file, true));
			writter.write(obj.toString());
			writter.flush();
			writter.close();
		} catch (IOException ignored) {
			System.err.println(ignored);
		}
	}

	@Override
	public void newline() {
		try {
			BufferedWriter writter = new BufferedWriter(
				new FileWriter(file, true));
			writter.newLine();
			writter.close();
		} catch (IOException ignored) {
			System.err.println(ignored);
		}
	}

	@Override
	public String toString() {
		return "file:" + file.getName();
	}
}
