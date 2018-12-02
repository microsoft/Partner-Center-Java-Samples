// -----------------------------------------------------------------------
// <copyright file="GetSkuDownloadOptions.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.products;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.products.SkuDownloadOptions;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that retrieves the download options of a product's SKU.
 */
public class GetSkuDownloadOptions
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #GetSkuDownloadOptions} class.
     * 
     * @param context The scenario context.
     */
    public GetSkuDownloadOptions( IScenarioContext context )
    {
        super( "Get SKU download options", context );
    }

    /**
     * Executes the scenario.
     */
    @Override
    protected void runScenario()
    {
        IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String productId = this.obtainProductId("Enter the ID of the corresponding product");
        String skuId = this.obtainSkuId("Enter the ID of the corresponding sku");
        String countryCode = this.getContext().getConsoleHelper().readNonEmptyString("Enter the 2 digit country code of the sku", "The country code can't be empty");

        this.getContext().getConsoleHelper().startProgress(
            MessageFormat.format("Getting sku download options for product {0} and sku {1} in country {2}", productId, skuId, countryCode));
       
        ResourceCollection<SkuDownloadOptions> sku = partnerOperations.getProducts().byCountry(countryCode).byId(productId).getSkus().byId(skuId).getDownloadOptions().get();

        this.getContext().getConsoleHelper().stopProgress();

        this.getContext().getConsoleHelper().writeObject(
            sku, 
            MessageFormat.format("Download options for sku {0}", skuId));
    }
}