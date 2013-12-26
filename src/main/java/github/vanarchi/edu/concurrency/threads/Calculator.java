package github.vanarchi.edu.concurrency.threads;

public class Calculator implements Runnable {
	private int number;

	public Calculator(int number) {
		super();
		this.number = number;
	}

	public void run() {
		for (int i = 1; i <= 10; i++) {
			String calculationOutput = multiplyByFormatted(i);
			printResult(calculationOutput);
		}
	}

	private void printResult(String calculationOutput) {
		System.out.println(calculationOutput);
	}

	String multiplyByFormatted(int i) {
		return String.format("%s: %d * %d = %d", getThreadName(), number, i, i * number);
	}

	private String getThreadName() {
		return Thread.currentThread()
				.getName();
	}

}
