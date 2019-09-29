// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.invoice;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the account balance for a partner.
 */
public class GetAccountBalance
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAccountBalance} class.
     * 
     * @param context The scenario context.
     */
    public GetAccountBalance( IScenarioContext context )
    {
        super( "Get account balance", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress("Getting account balance");

        // Getting the account balance
        double accountBalance = partnerOperations.getInvoices().getSummary().get().getBalanceAmount();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(accountBalance, "Account Balance");
    }
}