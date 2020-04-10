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
package com.gridscape.nayax.impl;

import com.abb.evci.payment.PaymentCommandResultListener;
import com.abb.evci.payment.PaymentEventListener.ConfirmationResult;

public class PaymentCommandResultListenerImpl implements PaymentCommandResultListener {

	@Override
	public void commandFinished(String command, ConfirmationResult result, String info) {	
		System.out.println("--------------------------------Command Finished--------------------------------");
		System.out.println("Command:: "+command);
		System.out.println("ConfirmationResult:: "+result);
		System.out.println("Info:: "+info);		
		System.out.println("--------------------------------Command Finished--------------------------------");
	}

}
