/*************************************************************************
 * 
 * Gridscape Solutions, Inc. - CONFIDENTIAL & PROPRIETARY
 * __________________
 * 
 *  Copyright @ 2016-2021 Gridscape Solutions, Inc.
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Gridscape Solutions, Inc.
 * The intellectual and technical concepts contained
 * herein are proprietary to Gridscape Solutions, Inc.
 * and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Gridscape Solutions.
 * 
 * Author: Jayesh, Rahul.
 */
package com.gridscape.nayax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.abb.evci.payment.PaymentCommandResultListener;
import com.abb.evci.payment.PaymentEventListener;
import com.abb.evci.payment.PaymentModuleInterface;
import com.abb.evci.payment.PaymentModuleStatusListener;
import com.abb.evci.payment.PaymentModuleStatusListener.PaymentModuleStatus;
import com.gridscape.nayax.impl.PaymentCommandResultListenerImpl;
import com.gridscape.nayax.impl.PaymentEventImpl;
import com.gridscape.nayax.impl.PaymentModuleStatusListenerImpl;
import com.gridscape.nayax.log.LogUtility;

public class PosTerminal {
	
	static int option = 0 ;
	static int paymentSessionId = 0;
	static boolean sysInitialized = false;
	static BigDecimal amountinput = null;
	
	static PaymentModuleInterface paymentModule;
	
	public static void main(String[] args) {		
		try {
		paymentModule = new PaymentModuleImpl();
		PaymentModuleStatusListener paymentModuleStatusListener;
		showOptions();
		do {			
			while(selectAppropriateOption("Select Appropriate Option") == -1){}									
			switch (option) {
			case 1:
				initialize(args);
				break;
			case 2:
				int id = startPayment();
				System.out.println("Payment Session Id "+id);				
				break;
			case 3:
				confirmPayment();				
				break;
			case 4:
				abortPayment();
				break;
			case 5:
				List<Integer> sessionList = paymentModule.getOpenPaymentSessions();
				System.out.println("\n@SESSIONLIST@\n");
				if (sessionList.size() > 0) {
					int c = 1;
					for (Integer session : sessionList) {
						System.out.println(c + " : " + session);
						c++;
					}
				} else {
					System.out.println("No Open Payment Sessions");
				}
				System.out.println("\n@SESSIONLIST@\n");
				break;
			case 6:
				System.out.println("\n@REGISTERSTATUSLISTENER@\n");
				paymentModuleStatusListener = new PaymentModuleStatusListenerImpl();
				paymentModule.registerStatusListener(paymentModuleStatusListener);
				break;
			case 7:
				System.out.println("\n@SHUTDOWN@\n");
				if(!paymentModule.shutdown()) { 
					System.out.println("System has not been initialized yet or"
							+ "\n Some error has come");
				}
				System.out.println("\n@SHUTDOWN_COMPLETED@\n");
				break;
			case 8:
				System.out.println("\n@GETPAYMENTMODULESTATUS@\n");
				PaymentModuleStatus paymentModuleStatus1 = paymentModule.getPaymentModuleStatus();
				System.out.println("Payment Module Status:: " + paymentModuleStatus1);
				System.out.println("\n@GETPAYMENTMODULESTATUS@\n");
				break;
			case 9:
				System.out.println("\n@REPORTDIAGNOSIS@\n");
				PaymentCommandResultListener result = new PaymentCommandResultListenerImpl();
				if(!paymentModule.reportDiagnose(result)) {
					System.out.println("System has not been initialized yet or"
							+ "\n Some error has come");
				}
				System.out.println("\n@REPORTDIAGNOSIS@\n");
				break;
			case 10:
				executeCommand();
				break;
			case 11:
				System.out.println("\n@Reset@\n");
				if(!paymentModule.reset()) {
					System.out.println("System has not been initialized yet or"
							+ "\n Some error has come");
				}
				System.out.println("\n@Reset COMPLETED@\n");
				break;
			case 12:
				System.out.println("\n@Perform Self test@\n");
				PaymentCommandResultListener performTestCommandListener = new PaymentCommandResultListenerImpl();
				if(!paymentModule.performSelfTest(performTestCommandListener)) {
					System.out.println("System has not been initialized yet or"
							+ "\n Some error has come");
				}
				else {
					System.out.println("\n@Perform Self test@RESULT: boolean true\n");
				}
				
				break;
			case 13:
				System.out.println("\n@Enable/Disable Logs@\n");
				enableDisableLog();
				break;
			case 14:
				System.out.println("\n@Get Supported Command@\n");
				getSupportedCommands();
				break;
			case 15:
				System.out.println("\n@Get Supported Parameters@\n");
				getSupportedParameters();
				break;
			case 16:
				System.out.println("\n@Get value of parameters@\n");
				getConfiguration();
				break;
			case 17:
				System.out.println("\n@SetConfiguration@\n");
				System.out.println("Result: "+setConfiguration());
				break;
			default:
				System.out.println("No Match");
				break;
			}
		} while (true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static int selectAppropriateOption(String message) {
		int selection;
		System.out.println("\n"+message+"\n");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String choice = scan.nextLine();
		try {
		selection = (int)Integer.parseInt(choice);
		option = selection;
		} catch(NumberFormatException nfe) {
			System.out.println("Invalid input. Please select integer value to proceed");
			return -1;
		}
		return selection;
	}
	
	public static BigDecimal selectAppropriateAmount(String message) {		
		BigDecimal selection;
		System.out.println("\n" + message + "\n");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		try {
			BigDecimal choice = scan.nextBigDecimal();
			if (choice != null) {
				selection = choice;
				amountinput = selection;
			} else {
				System.out.println("Invalid input. Please enter valid amount to proceed");
				return null;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please enter valid amount to proceed");
			return null;
		}
		return selection;
	}
	
	public static void initialize(String args[]) {
		System.out.println("\n@INITIATE@\n");
		Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		try {
			map.put("serialPortId", args[0]);
		}catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please provide valid serial port id");
			return;
		}
		map.put("Currency", "USD");
		map.put("debug", "true");
		map.put("cardSwipeTimeOut", "60");
		map.put("maxSession", "10");
		map.put("clearSessionsOnStartup", "true");
		//map.put("dummy", "dummy");
		//map.put("dateFormat", "MMMM");
		
		
		sysInitialized = paymentModule.initiate(map);
		System.out.println("Result: "+sysInitialized);
	}
	
	public static int startPayment() {
		System.out.println("\n@STARTPAYMENT(5)@\n");
		String currency = "USD";
		while (selectAppropriateAmount("Enter PRE-AUTH amount") == null) {
		}
		BigDecimal amount = amountinput;
		PaymentEventListener listener = new PaymentEventImpl();
		int result = paymentModule.startPayment(currency, amount, listener);
		return paymentModule.startPayment(currency, amount, listener);
	}

	public static void confirmPayment() {
		System.out.println("\n@CONFIRMPAYMENT@\n");
		settlePayment(true, false);
	}

	public static void abortPayment() {
		System.out.println("\n@ABORTPAYMENT@\n");
		settlePayment(false, true);
	}
	
	public static void settlePayment(boolean confirmPayment,
			boolean abortPayment) {
			while(selectAppropriateOption("Enter valid payment session id") == -1) {
				
			}
			boolean settlementResult = false;
			if (confirmPayment) {
				while (selectAppropriateAmount("Enter final amount to confirm") == null) {
				}
				BigDecimal finalAmount = amountinput;
				settlementResult = paymentModule.confirmPayment(option, finalAmount);
			}
			if (abortPayment) {
				settlementResult = paymentModule.abortPayment(option);
			}
			System.out.println("Execution Result: "+settlementResult);
		
	}
	
	public static void getPaymentSessionIdFromList(List<Integer> list) {
		Iterator<Integer> iterator = list.iterator();
		int counter = 1;
		while (iterator.hasNext()) {
			System.out.println(counter + ": " + iterator.next());
			counter++;
		}
		while(selectAppropriateOption("Select index from the given list of open payment sessions") == -1){}																	
		while(checkId(option,list) == -1){}
	}
	
	public static int checkId(int index, List<Integer> list) {
		try{
			paymentSessionId = list.get(index-1);
			return paymentSessionId;
		} catch (IndexOutOfBoundsException indexBoundException) {
			while(selectAppropriateOption("Select valid index") == -1){}
			return -1;
		}	
	}
	
	public static void showOptions() {
		System.out.println("Choose an option:" + "\n1)Initialization." + "\n2)StartPayment with 10"
				+ "\n3)ConfirmPayment." 
				+ "\n4)Abort payment." 
				+ "\n5)Show List of Open Payment Session"
				+ "\n6)Register Status Listener" 
				+ "\n7)Shutdown" 
				+ "\n8)Payment Status Listener"
				+ "\n9)Report Diagnosis" 
				+ "\n10)Execute Command" 
				+ "\n11)Reset"
				+ "\n12)Perform self test"
				+ "\n13)Enable/Disable Logs"
				+ "\n14)Get supported command"
				+ "\n15)Get supported parameters"
				+ "\n16)Get configuration"
				+ "\n17)Set configuration"
				+ "\n-------------------------------------------");
	}
	
	public static void executeCommand() {
		System.out.println("\n@EXECUTECOMMAND@\n");
		while (selectAppropriateOption(
				"Select which command you want to execute\nSelect below options \n1 for confirmPayment and \n2 for abortPayment") == -1) {
		}
		while(option > paymentModule.getSupportedCommands().size() || option == 0) {
			while(selectAppropriateOption("Select Valid execute command option") == -1) {}
		}
		switch (option) {
		case 1:
			executeSettleCommand(true, false);
			break;
		case 2:
			executeSettleCommand(false, true);
			break;
		}
	}
	
	public static void executeSettleCommand(boolean confirmPayment, boolean abortPayment) {
		PaymentCommandResultListener executeCommandListener = new PaymentCommandResultListenerImpl();
		ArrayList<String> argList = new ArrayList<String>();
		boolean executeCommandResult = false;
		List<Integer> openSessionList = paymentModule.getOpenPaymentSessions();
		if (openSessionList.size() > 0) {
			while(selectAppropriateOption("Enter valid payment session id") == -1) {}
			if (confirmPayment) {
				while (selectAppropriateAmount("Enter final amount to confirm") == null) {
				}				
				BigDecimal finalAmount = amountinput;
				argList.add(String.valueOf(option));
				argList.add(String.valueOf(finalAmount.floatValue()));
				executeCommandResult = paymentModule.executeCommand("confirmPayment", argList, executeCommandListener);
			}
			if (abortPayment) {
				argList.add(String.valueOf(option));
				executeCommandResult = paymentModule.executeCommand("abortPayment", argList, executeCommandListener);
			}
			if (!executeCommandResult) {
				String msg = "Can't Proceed Further." + "\nMay be your requested session id is not valid";
				if (confirmPayment)
					msg += "or\nRequested amount is greater than pre-auth amount";
				System.out.println(msg);
			}
		} else {
			System.out.println("No Open Payment Sessions Available for Settle");
		}
	}	
	
	public static void enableDisableLog() {
		if(LogUtility.isLogUtility()) {
			LogUtility.setLogUtility(false);
			System.out.println("Log has been disabled");
		} else {
			LogUtility.setLogUtility(true);
			System.out.println("Log has been enabled");
		}
	}
	
	public static void showTransactionInConfirmRequest() {
		
	}
	
	public static void getSupportedParameters(){
		List<String> list = paymentModule.getSupportedParameters();
		for(String param: list) {
			System.out.println(param);
		}
		System.out.println("");
	}
	
	public static void getSupportedCommands(){
		List<String> list = paymentModule.getSupportedCommands();
		for(String param: list) {
			System.out.println(param);
		}
		System.out.println("");
	}
	
	public static void getConfiguration() {
		
		List<String> list = paymentModule.getSupportedParameters();
		for(String param : list) {
			System.out.println(paymentModule.getConfiguration(param));
		}		
	}
	
	public static boolean setConfiguration() {
		//return paymentModule.setConfiguration("cardSwipeTimeOut", "20");
		//return paymentModule.setConfiguration("dateFormat", "MMM dd, yyyy hh:mm:ss");
		return paymentModule.setConfiguration("clearSessionsOnStartup", "false");
	}
	
	
}
