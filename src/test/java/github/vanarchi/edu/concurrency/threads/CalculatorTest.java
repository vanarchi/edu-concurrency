package github.vanarchi.edu.concurrency.threads;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	Calculator calc = new Calculator(5);
	private String threadNameBefore;
	
	@Before
	public void setUp() throws Exception {
		String oldThreadName = setThreadName("threadName");
		threadNameBefore = oldThreadName;
	}
	
	private String setThreadName(String threadName) {
		Thread currentThread = Thread.currentThread();
		String oldThreadName = currentThread.getName();
		currentThread.setName(threadName);
		return oldThreadName;
	}
	
	@After
	public void turnDown()
	{
		setThreadName(threadNameBefore);
	}
	
	@Test
		public void getMultiplyFormatted() throws Exception {
			assertEquals("threadName: 5 * 10 = 50", calc.getMultiplyFormatted(10));
		}
}
