// -----------------------------------------------------------------------
// <copyright file="VerifyPartnerMpnId.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.indirectmodel;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.partners.MpnProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that verifies a partner MPN ID.
 */
public class VerifyPartnerMpnId
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #VerifyPartnerMpnId} class.
     * 
     * @param context The scenario context.
     */
    public VerifyPartnerMpnId( IScenarioContext context )
    {
        super( "Verify partner MPN ID", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Getting the logged in partner's profile" );
        MpnProfile currentPartnerProfile = partnerOperations.getProfiles().getMpnProfile().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( currentPartnerProfile, "Logged in partner profile" );
        String partnerMpnId = this.obtainMpnId( "Enter the MPN ID to verify" );
        this.getContext().getConsoleHelper().startProgress( MessageFormat.format( "Getting the partner profile for MPN ID: {0}",
                                                                                  partnerMpnId ) );
        MpnProfile partnerProfile =
            partnerOperations.getProfiles().getMpnProfile().get( partnerMpnId );
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( partnerProfile, "Partner profile" );
    }

}
