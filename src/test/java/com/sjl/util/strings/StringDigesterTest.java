package com.sjl.util.strings;

import static junit.framework.Assert.*;

import java.util.*;

import org.junit.*;

import com.sjl.util.strings.parse.*;

public class StringDigesterTest {

	@Test
	public void testSimpleOneLevelParsing() {
		MyStuff _ms = new MyStuffParser().digest("steve 123 456");

		assertEquals("steve", _ms.getMyString());
		assertEquals(new Integer(123), _ms.getMyInt());
		assertEquals(new Long(456), _ms.getMyLong());
	}

	@Test
	public void testMultiLevelParsing() {
		MyComplexStuff _mcs = new MyComplexStuffParser().digest("hello, steve 123 456");
		
		assertEquals("hello", _mcs.getGreeting());
		
		MyStuff _ms = _mcs.getMyStuff();
		assertNotNull(_ms);
		assertEquals("steve", _ms.getMyString());
		assertEquals(new Integer(123), _ms.getMyInt());
		assertEquals(new Long(456), _ms.getMyLong());
	}
	
	static class MyStuff {
		private String myString;
		private Integer myInt;
		private Long myLong;

		public MyStuff(String aMyString, Integer aMyInt, Long aMyLong) {
			myString = aMyString;
			myInt = aMyInt;
			myLong = aMyLong;
		}

		public String getMyString() {
			return myString;
		}

		public Integer getMyInt() {
			return myInt;
		}

		public Long getMyLong() {
			return myLong;
		}
	}
	
	static class MyComplexStuff {
		private String greeting;
		private MyStuff myStuff;
		
		public MyComplexStuff(String aGreeting, MyStuff aMyStuff) {
			greeting = aGreeting;
			myStuff = aMyStuff;
		}
		
		public String getGreeting() {
			return greeting;
		}
		
		public MyStuff getMyStuff() {
			return myStuff;
		}
	}

	static class MyStuffParser extends StringDigester<MyStuff> {
		private StringKey stringKey;
		private IntKey intKey;
		private LongKey longKey;

		public MyStuffParser() {
			super("(\\S*) ([0-9]*) ([0-9]*)");
			add(stringKey = new StringKey("string"));
			add(intKey = new IntKey("int"));
			add(longKey = new LongKey("long"));
		}

		@Override
		protected MyStuff digest(Map<Key<?>, String> aMappedValues) {
			return new MyStuff(
				stringKey.get(aMappedValues), 
				intKey.get(aMappedValues), 
				longKey.get(aMappedValues)
			);
		}
	}
	
	static class MyStuffKey extends Key<MyStuff> {
		private MyStuffParser parser = new MyStuffParser();
		
		public MyStuffKey(String aName) {
			super(aName);
		}
		
		@Override
		protected MyStuff convert(String aString) {
			return parser.digest(aString);
		}
	}
	
	static class MyComplexStuffParser extends StringDigester<MyComplexStuff> {
		private StringKey greetingKey;
		private MyStuffKey myStuffKey;
		
		public MyComplexStuffParser() {
			super("(\\S*), (.*)");
			add(greetingKey = new StringKey("greeting"));
			add(myStuffKey = new MyStuffKey("myStuff"));
		}
		
		@Override
		protected MyComplexStuff digest(Map<Key<?>, String> aMappedValues) {
			return new MyComplexStuff(
				greetingKey.get(aMappedValues),
				myStuffKey.get(aMappedValues)
			);
		}
	}

}
