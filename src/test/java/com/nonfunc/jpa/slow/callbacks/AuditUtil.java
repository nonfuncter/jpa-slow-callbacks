package com.nonfunc.jpa.slow.callbacks;

/*
 * #%L
 * em
 * %%
 * Copyright (C) 2016 nonfunc.com
 * %%
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
 * #L%
 */

import java.util.Random;
import java.util.UUID;

public class AuditUtil {
	
	public static boolean delay = true;
	
	public static String determineAuditValue() {
		if (delay) {
			try {
				Thread.sleep(randomNumberInRange(100, 200));
			} catch (InterruptedException e) {
				// Do nothing
			}
		}
		return UUID.randomUUID().toString();
	}
	
	private static int randomNumberInRange(int min, int max) {
		Random r = new Random();
		return (r.nextInt(max) + min);
	}
}