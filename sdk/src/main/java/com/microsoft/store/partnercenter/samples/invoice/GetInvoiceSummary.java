// -----------------------------------------------------------------------
// <copyright file="GetInvoiceSummary.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.invoice;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.invoices.InvoiceSummary;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the account balance for a partner.
 */
public class GetInvoiceSummary
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetInvoiceSummary} class.
     * 
     * @param context The scenario context.
     */
    public GetInvoiceSummary( IScenarioContext context )
    {
        super( "Get invoice summary", context );
    }

    /**
     * executes the get invoice summary scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting Invoice Summary" );
        // Getting the account balance
        InvoiceSummary invoiceSummary = partnerOperations.getInvoices().getSummary().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( invoiceSummary, "Invoice Summary" );
    }

}
