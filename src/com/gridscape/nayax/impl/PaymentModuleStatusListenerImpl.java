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

import com.abb.evci.payment.PaymentModuleStatusListener;

public class PaymentModuleStatusListenerImpl implements PaymentModuleStatusListener {

	@Override
	public void statusChanged(PaymentModuleStatus status, String detail) {
		System.out.println("-------------------Status Changed------------------------");		
		System.out.println("Payment Module Status Changed to ::::: "+status);
		System.out.println("Detail :::: "+detail);
		System.out.println("---------------------------------------------------------");
	}
}
