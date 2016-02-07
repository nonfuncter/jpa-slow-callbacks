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

import static com.airhacks.rulz.em.EntityManagerProvider.persistenceUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import com.airhacks.rulz.em.EntityManagerProvider;

public class CallbackTest {

	private static Logger logger = Logger.getLogger(CallbackTest.class.getName());
	private static EntityManager entityManager;
	private static EntityTransaction transaction;

	@ClassRule
	public static EntityManagerProvider provider = persistenceUnit("slow-callback");

	@Rule
	public Stopwatch stopwatch = new Stopwatch() {
		@Override
		protected void finished(long nanos, Description description) {
			log(description.getMethodName(), nanos);
		}
	};

	@BeforeClass
	public static void setUp() throws Exception {
		entityManager = provider.em();
		transaction = provider.tx();
		transaction.begin();

		entityManager.flush();
		entityManager.clear();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		transaction.rollback();
	}

	@Before
	public void before() {
		entityManager.clear();
	}

	@Test
	public void testCallbacks() {		
		createAndUpdateFoos();	
	}
	
	@Test
	public void testNoCallbacks() {		
		AuditUtil.delay = false;
		createAndUpdateFoos();	
	}
	
	private void createAndUpdateFoos() {
		final int items = 5;
		List<Foo> foos = new ArrayList<>();
		
		for (int i = 0; i < items; i++) {
			Foo foo = new Foo();
			foo.setCode(i);
			foo.setDescription("description=["+i+"]");
			
			entityManager.persist(foo);
			
			foos.add(foo);
		}
		
		entityManager.flush();
		
		for (Foo foo : foos) {
			foo.setCode(foo.getCode() + 1);
			entityManager.merge(foo);
		}
		
		entityManager.flush();
	}

	private static void log(String test, long nanos) {
		logger.info(String.format("Test %s took %d milliseconds.", test, TimeUnit.NANOSECONDS.toMillis(nanos)));
	}
}
