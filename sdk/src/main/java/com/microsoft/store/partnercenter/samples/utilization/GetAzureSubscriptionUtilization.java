// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.utilization;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.utilizations.AzureUtilizationGranularity;
import com.microsoft.store.partnercenter.models.utilizations.AzureUtilizationRecord;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

import org.joda.time.DateTime;

/**
 * A scenario that shows retrieving utilization records for an Azure subscription.
 */
public class GetAzureSubscriptionUtilization
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAzureSubscriptionUtilization} class.
     * 
     * @param context The scenario context.
     */
    public GetAzureSubscriptionUtilization( IScenarioContext context )
    {
        super( "Retrieve Azure Subscription Utilization Records", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId();
        String subscriptionId = this.obtainSubscriptionId(customerId, "");

        this.getContext().getConsoleHelper().startProgress("Retrieving Azure subscription utilization records");

        ResourceCollection<AzureUtilizationRecord> utilizationRecords = partnerOperations.getCustomers()
            .byId(customerId).getSubscriptions().byId(subscriptionId)
            .getUtilization().getAzure().query(
                new DateTime().minusYears(1), 
                new DateTime(), 
                AzureUtilizationGranularity.DAILY, 
                true, 
                100);

        // Create an Azure utilization enumerator which will aid us in traversing the utilization pages
        IResourceCollectionEnumerator<ResourceCollection<AzureUtilizationRecord>> utilizationRecordEnumerator = 
            partnerOperations.getEnumerators().getUtilization().getAzure().create(utilizationRecords);

        int pageNumber = 1;

        while (utilizationRecordEnumerator.hasValue())
        {
            // print the current utilization results page
            this.getContext().getConsoleHelper().writeObject(
                utilizationRecordEnumerator.getCurrent(),
                MessageFormat.format("Azure Utilization Records Page: {0}", pageNumber++));

            System.out.println();
            System.out.println("Press any key to retrieve the next Azure utilization records page");
            this.getContext().getConsoleHelper().getScanner().nextLine();

            this.getContext().getConsoleHelper().startProgress("Getting next Azure utilization records page");

            // get the next page
            utilizationRecordEnumerator.next();

            this.getContext().getConsoleHelper().stopProgress();
        }
    }
}