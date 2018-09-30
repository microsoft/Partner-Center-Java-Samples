//-----------------------------------------------------------------------
//<copyright file="GetAzureRateCard.java" company="Microsoft">
// Copyright (c) Microsoft Corporation. All rights reserved.
//</copyright>
//-----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.ratecards;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ratecards.AzureRateCard;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
* A scenario that Showcases getting Azure rate card.
*/
public class GetAzureRateCard
	extends BasePartnerScenario
{
/**
* Initializes a new instance of the {@link #GetAzureRateCard} class.
* 
* @param context The scenario context.
*/
public GetAzureRateCard( IScenarioContext context )
{
   super( "Get Azure rate card", context );
}

/**
* Executes the scenario.
*/
@Override
protected void runScenario()
{
   IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
   this.getContext().getConsoleHelper().startProgress( "Retrieving rate cards" );
   AzureRateCard azureRateCard = partnerOperations.getRateCards().getAzure().get( "" , "" );
   this.getContext().getConsoleHelper().stopProgress();
   this.getContext().getConsoleHelper().writeObject( azureRateCard, "Azure Rate Card" );        
}

}
