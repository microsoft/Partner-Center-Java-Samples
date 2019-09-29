// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.ratecards;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ratecards.AzureRateCard;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Gets the Azure shared services rate card.
 */
public class GetAzureSharedRateCard
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetAzureSharedRateCard} class.
     * 
     * @param context The scenario context.
     */
    public GetAzureSharedRateCard( IScenarioContext context )
    {
        super( "Get Azure share service rate card", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress("Retrieving Azure shared rate card");

        AzureRateCard azureSharedRateCard = partnerOperations.getRateCards().getAzure().getShared("", "");

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(azureSharedRateCard, "Azure Shared Services Rate Card");
    }
}