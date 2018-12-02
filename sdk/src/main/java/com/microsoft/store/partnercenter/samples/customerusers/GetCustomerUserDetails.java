// -----------------------------------------------------------------------
// <copyright file="GetCustomerUserDetails.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.customerusers;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class GetCustomerUserDetails extends BasePartnerScenario 
{
    public GetCustomerUserDetails(IScenarioContext context) 
    {
        super("Get information of a Customer User", context);
    }

    @Override
    protected void runScenario() 
    {
        String customerId = this.obtainCustomerId();
        String customerUserId = this.obtainCustomerUserId();
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        // Get user details.
        this.getContext().getConsoleHelper().startProgress(
                        "Retrieving user with id: " + customerUserId + "for customer: " + customerId);
        CustomerUser customerUserInfo = partnerOperations.getCustomers().byId(customerId).getUsers()
                        .byId(customerUserId).get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().success("Success!");
        this.getContext().getConsoleHelper().writeObject(customerUserInfo,
                        "Retrieved Customer user Information");
    }
}