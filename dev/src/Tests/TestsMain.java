package Tests;

import Tests.ConstraintTest;
import Tests.WorkersTest;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import Tests.ShiftsTest;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestsMain
{
	public static void main(String[] args)
	{
		JUnitCore jUnitCore = new JUnitCore();

		Result result = jUnitCore.run(ShiftsTest.class);
		for (Failure failure:result.getFailures())
			System.out.println(failure.toString());
		System.out.println("ShiftsTest: Successful: " + result.wasSuccessful() + " run of " + result.getRunCount() + " tests");

		result = jUnitCore.run(WorkersTest.class);
		for (Failure failure:result.getFailures())
			System.out.println(failure.toString());
		System.out.println("WorkersTest: Successful: " + result.wasSuccessful() + " run of " + result.getRunCount() + " tests");

		result = jUnitCore.run(ConstraintTest.class);
		for (Failure failure:result.getFailures())
			System.out.println(failure.toString());
		System.out.println("ConstraintTest: Successful: " + result.wasSuccessful() + " run of " + result.getRunCount() + " tests");

	}

}
