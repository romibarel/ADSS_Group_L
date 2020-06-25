package DeliveryAndWorkers.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestsMainDeliveries
{
	public static void testFunc()
	{
		JUnitCore jUnitCore = new JUnitCore();
		Result result;
		result = jUnitCore.run(DeliveryTest.class);
		for (Failure failure:result.getFailures())
			System.out.println(failure.toString());
		System.out.println("Delivery Test: Successful: " + result.wasSuccessful() + " run of " + result.getRunCount() + " tests");
	}

}
