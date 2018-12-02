// -----------------------------------------------------------------------
// <copyright file="GetAgreementDetails.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.agreements;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.agreements.AgreementMetaData;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * Showcases getting the list of agreement details.
 */
public class GetAgreementDetails   
    extends BasePartnerScenario 
{
    public GetAgreementDetails (IScenarioContext context) 
    {
		super("Get agreement details", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
    protected void runScenario() 
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();

        this.getContext().getConsoleHelper().startProgress( "Retrieving agreement details" );
        
        ResourceCollection<AgreementMetaData> agreementDetails = partnerOperations.getAgreementDetails().get();

        this.getContext().getConsoleHelper().stopProgress();
        
        this.getContext().getConsoleHelper().writeObject( agreementDetails, "Agreement details:" );
	}
}