// -----------------------------------------------------------------------
// <copyright file="CreateAzureReservationOrder.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.orders.Order;
import com.microsoft.store.partnercenter.models.orders.OrderLineItem;
import com.microsoft.store.partnercenter.models.offers.BillingCycleType;
import com.microsoft.store.partnercenter.models.products.Availability;
import com.microsoft.store.partnercenter.models.products.Sku;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * A scenario that creates a new Azure RI order for a customer.
 */
public class CreateAzureReservationOrder
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #CreateAzureReservationOrder} class.
     * 
     * @param context The scenario context.
     */
    public CreateAzureReservationOrder( IScenarioContext context )
    {
        super( "Create an Azure Reservation order", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String customerId = this.obtainCustomerId("Enter the ID of the customer making the purchase");
        String productId = this.obtainProductId();
        String skuId = this.obtainSkuId();
        String subscriptionId = this.obtainAzureSubscriptionId();
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString(
            "Enter the 2 digit country code of the sku",
            "The country code can't be empty");

        Sku sku = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).get();
        ResourceCollection<Availability> availabilities = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).getAvailabilities().get();

        if ((sku.getDynamicAttributes() == null) || StringHelper.isNullOrEmpty(sku.getDynamicAttributes().get("duration")))
        {
            this.getContext().getConsoleHelper().warning("Invalid Azure catalog item ID.");
        }
        else
        {
            if (availabilities.getTotalCount() == 0)
            {
                this.getContext().getConsoleHelper().warning("No availabilities found.");
            }
            else
            {
                Map<String, String> provisioningContext = new HashMap<String,String>();

                provisioningContext.put("duration", sku.getDynamicAttributes().get("duration"));
                provisioningContext.put("scope", "shared"); 
                provisioningContext.put("subscriptionId", subscriptionId);

                OrderLineItem lineItem =  new OrderLineItem();

                lineItem.setFriendlyName("ASampleRI");
                lineItem.setLineItemNumber(0);
                lineItem.setOfferId(availabilities.getItems().iterator().next().getCatalogItemId());
                lineItem.setProvisioningContext(provisioningContext);
                lineItem.setQuantity(1);

                List<OrderLineItem> lineItems = new ArrayList<OrderLineItem>();
                lineItems.add(lineItem);

                Order order = new Order(); 

                order.setBillingCycle(BillingCycleType.ONE_TIME);
                order.setLineItems(lineItems);
                order.setReferenceCustomerId(customerId);

                this.getContext().getConsoleHelper().writeObject(order, "Azure Reservation order to be placed");
                this.getContext().getConsoleHelper().startProgress("Placing order");

                Order createdOrder = partnerOperations.getCustomers().byId(customerId).getOrders().create(order);

                this.getContext().getConsoleHelper().stopProgress();
                this.getContext().getConsoleHelper().writeObject(createdOrder, "Created Azure Reservation order");
            }
        }
    }
}