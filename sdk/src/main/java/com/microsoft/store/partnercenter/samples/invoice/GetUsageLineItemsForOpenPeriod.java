// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.invoice;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.invoices.BillingPeriod;
import com.microsoft.store.partnercenter.models.invoices.BillingProvider;
import com.microsoft.store.partnercenter.models.invoices.InvoiceLineItem;
import com.microsoft.store.partnercenter.models.invoices.InvoiceLineItemType;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * Get un-billed reconciliation invoice line items. 
 */
public class GetUsageLineItemsForOpenPeriod
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetUsageLineItemsForOpenPeriod} class.
     * 
     * @param context The scenario context.
     */
    public GetUsageLineItemsForOpenPeriod(IScenarioContext context)
    {
        super("Unbilled - Consumption - Reconciliation Line Items", context);
    }

    /**
     * Executes the defined scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String currencyCode = this.getContext().getConfiguration().getScenarioSettings().get("DefaultCurrencyCode");
        int pageNumber = 1;

        if (StringHelper.isNullOrWhiteSpace(currencyCode))
        {
            currencyCode = this.getContext().getConsoleHelper().readNonEmptyString(
                "Please enter three digit currency code to retrieve the unbilled consumption reconciliation line items", 
                "The currency code cannot be empty.");
        }
        else 
        {
            ConsoleHelper.getInstance().warning(MessageFormat.format("Found currency code: {0} in configuration.", currencyCode));
        }

        this.getContext().getConsoleHelper().startProgress("Getting unbilled consumption reconciliation line items");

        SeekBasedResourceCollection<InvoiceLineItem> lineItems = partnerOperations.getInvoices().byId("unbilled").by(BillingProvider.ONE_TIME, InvoiceLineItemType.USAGELINEITEMS, currencyCode, BillingPeriod.CURRENT).get();
        IResourceCollectionEnumerator<ResourceCollection<InvoiceLineItem>> invoiceLineItemEnumerator = partnerOperations.getEnumerators().getInvoiceLineItems().create(lineItems);

        this.getContext().getConsoleHelper().stopProgress();

        while(invoiceLineItemEnumerator.hasValue())
        {
            this.getContext().getConsoleHelper().writeObject(invoiceLineItemEnumerator.getCurrent(), MessageFormat.format("Invoice Line Item Page: {0}", pageNumber++));

            ConsoleHelper.getInstance().warning("\nPress Enter to get the next page of unbilled consumption reconciliation line items");
            ConsoleHelper.getInstance().getScanner().nextLine();

            this.getContext().getConsoleHelper().startProgress( "Getting next page of unbilled consumption reconciliation line items");

            invoiceLineItemEnumerator.next();
            this.getContext().getConsoleHelper().stopProgress();
        }
    }
}