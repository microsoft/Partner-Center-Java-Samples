// -----------------------------------------------------------------------
// <copyright file="AddSubscriptionAddOn.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.subscriptions;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.orders.Order;
import com.microsoft.store.partnercenter.models.orders.OrderLineItem;
import com.microsoft.store.partnercenter.models.subscriptions.Subscription;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.subscriptions.ISubscription;

/**
 * A scenario that adds a new add on to an existing subscription.
 */
public class AddSubscriptionAddOn
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #AddSubscriptionAddOn} class.
     * 
     * @param context The scenario context.
     */
    public AddSubscriptionAddOn( IScenarioContext context )
    {
        super( "Add subscription add on", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        // obtain the customer ID, the ID of the subscription to amend with the add on offer and the add on offer ID
        String customerId = this.obtainCustomerId();
        String subscriptionId =
            this.obtainSubscriptionId( customerId, "Enter the subscription ID for which to purchase an add on" );
        String addOnOfferId =
            this.getContext().getConsoleHelper().readNonEmptyString( "Enter the ID of the add on offer to purchase",
                                                                     "Offer ID can't be empty" );
        ISubscription subscriptionOperations =
            partnerOperations.getCustomers().byId( customerId ).getSubscriptions().byId( subscriptionId );
        // get the parent subscription details
        this.getContext().getConsoleHelper().startProgress( "Retrieving order information for existing subscription" );
        Subscription parentSubscription = subscriptionOperations.get();
        this.getContext().getConsoleHelper().stopProgress();
        // in order to buy an add on subscription for this offer, we need to patch/update the order through which the
        // base offer was purchased
        // by creating an order object with a single line item which represents the add-on offer purchase.
        OrderLineItem lineItem = new OrderLineItem();
        lineItem.setLineItemNumber( 0 );
        lineItem.setOfferId( addOnOfferId );
        lineItem.setFriendlyName( "Some friendly name" );
        lineItem.setQuantity( 2 );
        lineItem.setParentSubscriptionId( subscriptionId );

        List<OrderLineItem> lineItemList = new ArrayList<OrderLineItem>();
        lineItemList.add( lineItem );

        Order orderToUpdate = new Order();
        orderToUpdate.setLineItems( lineItemList );
        orderToUpdate.setReferenceCustomerId( customerId );

        // update the order to apply the add on purchase
        this.getContext().getConsoleHelper().startProgress( "Updating parent subscription order" );
        Order updatedOrder =
            partnerOperations.getCustomers().byId( customerId ).getOrders().byId( parentSubscription.getOrderId() ).patch( orderToUpdate );
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( updatedOrder, "Updated order" );
        // fetch the subscription add ons and display these
        this.getContext().getConsoleHelper().startProgress( "Retrieving subscription supported add ons" );
        ResourceCollection<Subscription> subscriptionAddOns = subscriptionOperations.getAddOns().get();
        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject( subscriptionAddOns, "Subscription add ons" );
    }
}