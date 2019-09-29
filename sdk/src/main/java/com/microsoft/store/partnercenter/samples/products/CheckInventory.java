// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.products.InventoryCheckRequest;
import com.microsoft.store.partnercenter.models.products.InventoryItem;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves inventory validation results for the provided country.
 */
public class CheckInventory
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #CheckInventory} class.
     * 
     * @param context The scenario context.
     */
    public CheckInventory( IScenarioContext context )
    {
        super( "Check Inventory", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String productId = this.obtainProductId("Enter the ID of the product to check inventory for");
        final String customerId = this.obtainCustomerId("Enter a customer ID");
        final String subscriptionId = this.obtainSubscriptionId(customerId, "Enter a subscription ID");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code", "The country code can't be empty");
        
        InventoryItem inventoryItem = new InventoryItem(); 
        inventoryItem.setProductId(productId);

        List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
        inventoryItems.add(inventoryItem);

        Map<String, String> inventoryContext = new HashMap<String, String>(); 
        inventoryContext.put("customerId", customerId); 
        inventoryContext.put("azureSubscriptionId", subscriptionId); 

        InventoryCheckRequest inventoryCheckRequest = new InventoryCheckRequest();
        inventoryCheckRequest.setInventoryContext(inventoryContext);
        inventoryCheckRequest.setTargetItems(inventoryItems);

        this.getContext().getConsoleHelper().startProgress(MessageFormat.format("Checking inventory for product {0} in country {1}", productId, countryCode));
       
        List<InventoryItem> inventoryResults = partnerOperations.getExtensions().getProduct().byCountry(countryCode).checkInventory(inventoryCheckRequest);
       
        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(inventoryResults, MessageFormat.format("Inventory check for product {0}", productId));
    }
}