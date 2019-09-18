// return and throw same severity
// do normal things in try until hit exception, then do normal things in catch, until hit throw/return, do things in finally, return
//or throw, then back to catch's return / throw

import java.io.*;
import java.util.*;

public class Try {
	private static final int three = 3;
	public static void main(String[] args) {
	Thread loop = new Thread(new Runnable() {
	  	@Override
	    public void run() {
		    try{
		      while (true) {
		      	try {
			        if (Thread.interrupted()) {
			        	System.out.println("i die");
			          //throw new InterruptedException();
			        }
		    	} finally {
		    		System.out.println("do nothing");
		    	}
		        System.out.println("but I don't wanna die");
		        // Continue to do nothing
		      }
		    
			} finally {
				System.out.println("do something");
			}
			// System.out.println("do something later");

    	}});
	loop.start();
	loop.interrupt();
	}

	static String letstry(int i) {
		try {
			int j = (1 / 0);
		} catch (Exception e){
			System.out.println("lolll1");
			throw new NullPointerException("csadkjbcas");
		} finally {
			System.out.println("lolll");
		}
		return "lollllllll";
	}

}

