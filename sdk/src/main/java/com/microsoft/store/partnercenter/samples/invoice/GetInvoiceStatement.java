// -----------------------------------------------------------------------
// <copyright file="GetInvoiceStatement.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.invoice;

import java.io.IOException;
import java.io.InputStream;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the invoice statement for an invoice id.
 */
public class GetInvoiceStatement
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetInvoiceStatement} class.
     * 
     * @param context The scenario context.
     */
    public GetInvoiceStatement( IScenarioContext context )
    {
        super( "Get Invoice Statement by ID", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress("Getting Invoice Statement");

        String invoiceId = this.getContext().getConsoleHelper().readNonEmptyString(
            "Please enter the invoice ID to retrieve ",
             "The invoice ID can't be empty");

        // // Retrieving invoice statement for an invoice id
        InputStream invoiceStatement = partnerOperations.getInvoices().byId(invoiceId).getDocuments().getStatement().get();

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(invoiceId, "Invoice Id");

        try
        {
            this.getContext().getConsoleHelper().writeObject(invoiceStatement.available(), "Invoice Statement Size");
            
            invoiceStatement.close();
        } 
        catch (IOException ex)
        {
            this.getContext().getConsoleHelper().error(ex.getMessage());
        }
    }
}