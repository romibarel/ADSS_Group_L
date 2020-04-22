package Tests;

import Tests.Tests;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestsMain
{
	public static void main(String[] args)
	{
		JUnitCore jUnitCore = new JUnitCore();

		Result result = jUnitCore.run(Tests.class);
		for (Failure failure:result.getFailures())
			System.out.println(failure.toString());
		System.out.println("Tests: Successful: " + result.wasSuccessful() + " run of " + result.getRunCount() + " tests");
	}

}
