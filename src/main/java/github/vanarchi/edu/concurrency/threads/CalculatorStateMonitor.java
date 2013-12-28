package github.vanarchi.edu.concurrency.threads;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class CalculatorStateMonitor {
	public static void main(String[] args) throws Exception {
		Thread threads[] = new Thread[10];

		Runtime.getRuntime().addShutdownHook(new Thread(){@Override
			public void run() {
				System.out.println("hooked");
			}});
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Calculator(i));
			threads[i] = thread;
			if ((i % 2) == 0) {
				threads[i].setPriority(Thread.MAX_PRIORITY);
			} else {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			threads[i].setName("Thread " + i);
		}
		try (FileWriter file = new FileWriter(".\\log.txt");
				PrintWriter pw = new PrintWriter(file);) {
			State status[] = new Thread.State[threads.length];
			saveInitialThreadStatus(threads, status, pw);
			startThreads(threads);
			monitoringStatus(threads, status, pw);
		}
	}

	private static void monitoringStatus(Thread[] threads,
			Thread.State[] savedStatuses, PrintWriter pw) {

		boolean finish = false;
		while (!finish) {
			for (int i = 0; i < threads.length; i++) {
				State newState = threads[i].getState();
				if (newState != savedStatuses[i]) {
					writeThreadInfo(pw, threads[i], savedStatuses[i], newState);
					savedStatuses[i] = newState;
				}
			}
			finish = true;
			for (int i = 0; i < threads.length; i++) {
				finish = finish
						&& (threads[i].getState() == State.TERMINATED);
			}
		}
	}

	private static void saveInitialThreadStatus(Thread[] threads,
			Thread.State[] status, PrintWriter pw) {
		for (int i = 0; i < threads.length; i++) {
			pw.println("Main : Status of Thread " + i + " : "
					+ threads[i].getState());
			status[i] = threads[i].getState();
		}
	}

	private static void startThreads(Thread[] threads) {
		for (Thread thread: threads) {
			thread.start();
		}
	}

	private static void writeThreadInfo(PrintWriter pw, Thread thread,
			State state, State newState) {
		pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
		pw.printf("Main : Priority: %d\n", thread.getPriority());
		pw.printf("Main : Old State: %s\n", state);
		pw.printf("Main : New State: %s\n", newState);
		pw.printf("Main : ************************************\n");
	}
}
