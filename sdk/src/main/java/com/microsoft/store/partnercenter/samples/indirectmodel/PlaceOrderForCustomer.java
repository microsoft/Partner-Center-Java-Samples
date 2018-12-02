// -----------------------------------------------------------------------
// <copyright file="PlaceOrderForCustomer.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.indirectmodel;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.orders.Order;
import com.microsoft.store.partnercenter.models.orders.OrderLineItem;
import com.microsoft.store.partnercenter.models.relationships.PartnerRelationship;
import com.microsoft.store.partnercenter.models.relationships.PartnerRelationshipType;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that creates a new order for a customer with an indirect reseller.
 */
public class PlaceOrderForCustomer
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #PlaceOrderForCustomer} class.
     * 
     * @param context The scenario context.
     */
    public PlaceOrderForCustomer( IScenarioContext context )
    {
        super( "Create an order for a customer of an indirect reseller", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer making the purchase");
        String offerId = this.obtainOfferId("Enter the ID of the offer to purchase");
        String indirectResellerId = this.obtainIndirectResellerId("Enter the ID of the indirect reseller: ");

        this.getContext().getConsoleHelper().startProgress( "Getting list of indirect resellers" );

        ResourceCollection<PartnerRelationship> indirectResellers = partnerOperations.getRelationships().get(
            PartnerRelationshipType.IS_INDIRECT_CLOUD_SOLUTION_PROVIDER_OF);
        
        this.getContext().getConsoleHelper().stopProgress();

        PartnerRelationship partnerRelationship = null; 

        for (PartnerRelationship reseller : indirectResellers.getItems()) 
        {
            if(reseller.getId().equals(indirectResellerId))
            {
                partnerRelationship = reseller;
                break;
            }            
        }

        OrderLineItem newOrderLineItem = new OrderLineItem();

        newOrderLineItem.setFriendlyName("new offer purchase");
        newOrderLineItem.setOfferId(offerId);
        newOrderLineItem.setQuantity(5);

        if(partnerRelationship != null) 
        {
            newOrderLineItem.setPartnerIdOnRecord(partnerRelationship.getMpnId());
        }

        List<OrderLineItem> lineItems = new ArrayList<OrderLineItem>();

        lineItems.add(newOrderLineItem);

        Order order = new Order();

        order.setLineItems(lineItems);

        this.getContext().getConsoleHelper().writeObject(order, "Order to be placed");
        this.getContext().getConsoleHelper().startProgress("Placing order");

        Order createdOrder = partnerOperations.getCustomers().byId(customerId).getOrders().create(order);

        this.getContext().getConsoleHelper().stopProgress();
        this.getContext().getConsoleHelper().writeObject(createdOrder, "Created order");
    }
}