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

import com.abb.evci.payment.PaymentEventListener;
import com.abb.evci.payment.TransactionInfo;

public class PaymentEventImpl implements PaymentEventListener{

	@Override
	public void authorizationResult(int paymentSessionId, PaymentAuthorizationResult authorizationResult, String code,
			PaymentFailureReason reason, TransactionInfo info, String detail) {	 
		System.out.println("-------------------Authorization Result------------------------");
		System.out.println("Payment Session Id ::::: "+paymentSessionId);
		System.out.println("Authorization Result ::::: "+authorizationResult);
		System.out.println("Code :::: "+code);
		System.out.println("Payment Failure Reason :::: "+reason);
		if(info!=null) {
			System.out.println("----------\nTransactionInfo:::: \n");
			System.out.println("TransactionId: "+info.getTransactionId()+"\n\nReceiptText: \n\n"+info.getReceiptText()+"\n\nResultText: "+info.getResultText()+"\n");
		}		
		System.out.println("Detail:::: \n"+detail);		
	}

	@Override
	public void paymentConfirmation(int paymentSessionId, ConfirmationResult result, TransactionInfo info,
			String detail) {
		System.out.println("-------------------Payment Result------------------------");
		System.out.println("Payment Session Id ::::: "+paymentSessionId);
		System.out.println("Confirmation Result ::::: "+result);
		if (info != null) {
			System.out.println("TransactionInfo:::: \n" + "\nTransactionId: "+info.getTransactionId()+ "\nReceiptText" + info.getReceiptText()+ "\n");
		}
		System.out.println("Detail :::: "+detail);						
	}

	

	@Override
	public void refundConfirmation(int arg0, ConfirmationResult arg1, TransactionInfo arg2, String arg3) {
		// TODO Auto-generated method stub
		
	}
		

}
