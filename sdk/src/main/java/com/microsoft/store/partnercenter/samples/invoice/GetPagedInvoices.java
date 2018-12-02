// -----------------------------------------------------------------------
// <copyright file="GetPagedInvoices.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.invoice;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.invoices.Invoice;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * Gets paged invoices for partners.
 */
public class GetPagedInvoices
    extends BasePartnerScenario
{
    /**
     * The invoice page size.
     */
    private final int invoicePageSize;

    /**
     * Initializes a new instance of the {@link #GetPagedInvoices} class.
     * 
     * @param context The scenario context.
     * @param invoicePageSize The number of invoices to return per page.
     */
    public GetPagedInvoices( IScenarioContext context, int invoicePageSize )
    {
        super( "Get paged invoices", context );
        this.invoicePageSize = invoicePageSize;
    }

    /**
     * executes the get paged invoices scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Querying invoices" );
        int pageNumber = 1;
        
        // Query the invoices, get the first page if a page size was set, otherwise get all invoices
        ResourceCollection<Invoice> invoicesPage =
            ( this.invoicePageSize <= 0 ) ? partnerOperations.getInvoices().get()
                            : partnerOperations.getInvoices().get( 0, this.invoicePageSize );

        IResourceCollectionEnumerator<ResourceCollection<Invoice>> invoiceEnumerator =
            partnerOperations.getEnumerators().getInvoices().create( invoicesPage );

        this.getContext().getConsoleHelper().stopProgress();

        while ( invoiceEnumerator.hasValue() )
        {
            this.getContext().getConsoleHelper().writeObject( 
                invoiceEnumerator.getCurrent(),
                MessageFormat.format( 
                    "Invoices Page: {0}",
                    pageNumber++ ) );
            
            ConsoleHelper.getInstance().warning( "\nPress Enter to retrieve the next invoice page" );
            ConsoleHelper.getInstance().getScanner().nextLine();
            
            this.getContext().getConsoleHelper().startProgress( "Getting next invoice page" );
            
            // Get the next page of invoices
            invoiceEnumerator.next();
            
            this.getContext().getConsoleHelper().stopProgress();
        }
    }

}
