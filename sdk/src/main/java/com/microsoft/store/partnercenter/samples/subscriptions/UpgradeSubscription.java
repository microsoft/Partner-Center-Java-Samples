// -----------------------------------------------------------------------
// <copyright file="UpgradeSubscription.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.subscriptions.Upgrade;
import com.microsoft.store.partnercenter.models.subscriptions.UpgradeResult;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.subscriptions.ISubscription;

/**
 * A scenario that upgrades a customer subscription to a higher skew.
 */
public class UpgradeSubscription
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #UpgradeSubscription} class.
     * 
     * @param context The scenario context.
     */
    public UpgradeSubscription( IScenarioContext context )
    {
        super( "Upgrade customer subscription", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId();
        String subscriptionId =
            this.obtainSubscriptionId( customerId, "Enter the ID of the subscription to find upgrades for" );
        ISubscription subscriptionOperations =
            partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId );
        this.getContext().getConsoleHelper().startProgress( "Retrieving subscription upgrades" );
        ResourceCollection<Upgrade> upgrades = subscriptionOperations.getUpgrades().get();
        this.getContext().getConsoleHelper().stopProgress();
        if ( upgrades.getTotalCount() <= 0 )
        {
            this.getContext().getConsoleHelper().error( "This subscription has no upgrades" );
        }
        else
        {
            this.getContext().getConsoleHelper().writeObject( upgrades, "Available upgrades" );
            // prompt the user to enter the offer ID of the upgrade he wishes to get
            String upgradeOfferId =
                this.getContext().getConsoleHelper().readNonEmptyString( "Enter the upgrade offer ID",
                                                                         "Upgrade offer ID can't be empty" );

            Upgrade selectedUpgrade = null;
            for ( Upgrade upgrade : upgrades.getItems() )
            {
                if ( upgrade.getTargetOffer().getId() == upgradeOfferId )
                {
                    selectedUpgrade = upgrade;
                    break;
                }
            }

            if ( selectedUpgrade == null )
            {
                this.getContext().getConsoleHelper().error( "The entered upgrade offer ID was not found in the list of upgrades" );
            }
            else if ( !selectedUpgrade.isEligible() )
            {
                this.getContext().getConsoleHelper().error( "The entered upgrade is not eligible for the following reasons:" );
                this.getContext().getConsoleHelper().writeObject( selectedUpgrade.getUpgradeErrors(),
                                                                  "Upgrade errors list" );
            }
            else
            {
                // the selected upgrade is eligible, go ahead and perform the upgrade
                this.getContext().getConsoleHelper().startProgress( "Upgrading subscription" );
                UpgradeResult updgradeResult = subscriptionOperations.getUpgrades().create( selectedUpgrade );
                this.getContext().getConsoleHelper().stopProgress();
                this.getContext().getConsoleHelper().writeObject( updgradeResult, "Upgrade details" );
            }
        }
    }
}