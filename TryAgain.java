// Child thread throws interruptedexception -> main thread gets execution exception
// Child thread.isInterrupted -> if do nothing, nothing happens, if sleep, throws interruptedexception
// Parent thread got interrupted -> catch its own interruptionException




import java.util.concurrent.*;
import java.io.*;
import java.util.*;

public class TryAgain
{
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(9);
        Future<String> future = executor.submit(new SlowCallable());
        Future<String> future2 = executor.submit(new Interruptor(Thread.currentThread()));
        Future<String> future3 = executor.submit(new SlowCallable());
        Future<String> future4 = executor.submit(new ExecutionExceptionCallable());
        Future<String> future5 = executor.submit(new QuickCallable());
        Future<String> future6 = executor.submit(new QuickCallable());
        Future<String> future7 = executor.submit(new QuickCallable());
        Future<String> future8 = executor.submit(new QuickCallable());
        Future<String> future9 = executor.submit(new QuickCallable());
        List<Future<?>> futureList = new ArrayList<>();
        futureList.add(future);
        futureList.add(future2);
        futureList.add(future3);
        futureList.add(future4);
        futureList.add(future5);
        futureList.add(future6);
        futureList.add(future7);
        futureList.add(future8);
        futureList.add(future9);
        int i = 1;
        int goodcount = 0;
        int badcount = 0;
        for (Future<?> f : futureList) {
	        try {
	        	System.out.println("polling" + i);
	            System.out.println(f.get(5000, TimeUnit.MILLISECONDS));
	            goodcount++;
	            System.out.println("Good index:" + i);
	        } 
	        catch (TimeoutException e)
	        {
	        	//f.cancel(true);
	        	badcount++;
	        	System.out.println("Bad index:" + i);
	            System.out.println("TimeoutException!!!!!!");
	        }
	        catch (InterruptedException e)
	        {
	        	f.cancel(true);
	        	badcount++;
	        	System.out.println("Bad index:" + i);
	        	System.out.println("InterruptedException!!!!!!");
	            System.out.println("I was interrupted");
	            Thread.currentThread().interrupt();
	        }
	        catch (Exception e)
	        {
	        	//f.cancel(true);
	        	badcount++;
	        	System.out.println("Bad index:" + i);
	            System.out.println("OtherException!!!!!!" + e);
	        }
	        finally {
	        	System.out.println("GOOD: " + goodcount + "BAD: " + badcount);
	        	System.out.println("isInterrupted" + Thread.currentThread().isInterrupted());
	        	System.out.println("");
	        }
	        i++;
	    }

	    executor.shutdown();
    }

    private static class Interruptor implements Callable<String>
    {
        private final Thread threadToInterrupt;

        Interruptor(Thread threadToInterrupt)
        {
            this.threadToInterrupt = threadToInterrupt;
        }

        public String call() throws Exception
        {
        	try {
	            Thread.sleep(2000);
	            // int i = (1/0);
	            //throw new InterruptedException();
	            threadToInterrupt.interrupt();
	            Thread.currentThread().interrupt();
	            System.out.println("ShouldIBeCanceled222");
	            System.out.println("ShouldIBeCanceled222");
	            System.out.println("ShouldIBeCanceled222");
	            System.out.println("ShouldIBeCanceled222");
	            System.out.println("ShouldIBeCanceled222");
	            System.out.println("ShouldIBeCanceled222");
	            //Thread.sleep(1); // == trigger throw new InterruptedException();
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
	            System.out.println("ShouldIBeCanceled!");
        	} catch (InterruptedException e) {
        		System.out.println("Swallowed interruptionException");
        	}
        	return "interrupted other thread";
        }
    }

    private static class SlowCallable implements Callable<String>
    {
        public String call() throws Exception
        {
        	System.out.println("i sleep");
            Thread.sleep(10000);
            if (Thread.interrupted()) {
			    System.out.println("i die");
	        } else {
	        	System.out.println("here");
	        }
	        System.out.println("I finished");
            return "finished";
        }
    }

    private static class QuickCallable implements Callable<String>
    {
        public String call() throws Exception
        {
        	System.out.println("i sleep");
            if (Thread.interrupted()) {
			    System.out.println("i die");
	        } else {
	        	System.out.println("here");
	        }
	        System.out.println("Quick");
            return "quickly finished";
        }
    }

    private static class ExecutionExceptionCallable implements Callable<String>
    {
        public String call() throws Exception
        {
        	System.out.println("i sleep");
            Thread.sleep(10000);
            int i = 3/0;
	        System.out.println("I finished");
            return "finished";
        }
    }
}