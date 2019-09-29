// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.models.users.UserState;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Restore a deleted customer user.
 */
public class CustomerUserRestore extends BasePartnerScenario 
{
    public CustomerUserRestore (IScenarioContext context) 
    {
		super("Restore a deleted customer user", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the corresponding customer to restore customer user details");
        String userId = this.obtainCustomerUserId("Enter the ID of the customer user to restore");

        CustomerUser updatedCustomerUser = new CustomerUser();
        updatedCustomerUser.setState(UserState.ACTIVE);

        this.getContext().getConsoleHelper().startProgress("Restoring the customer user");
        
        // restore customer user information using older upn.
        CustomerUser restoredCustomerUserInfo = partnerOperations.getCustomers().byId(customerId).getUsers().byId(userId).patch(updatedCustomerUser);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(restoredCustomerUserInfo, "Restored customer user.");
	}
}