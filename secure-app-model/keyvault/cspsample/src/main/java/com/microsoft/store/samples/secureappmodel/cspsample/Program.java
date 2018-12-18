// -----------------------------------------------------------------------
// <copyright file="Program.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cspsample;

import java.io.IOException;
import java.util.Properties;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.graph.models.extensions.Domain;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDomainCollectionPage;
import com.microsoft.store.partnercenter.IAadLoginHandler;
import com.microsoft.store.partnercenter.IPartner;
import com.microsoft.store.partnercenter.IPartnerCredentials;
import com.microsoft.store.partnercenter.PartnerService;
import com.microsoft.store.partnercenter.extensions.PartnerCredentials;
import com.microsoft.store.partnercenter.models.partners.OrganizationProfile;
import com.microsoft.store.samples.secureappmodel.cspsample.security.AccessTokenProvider;
import com.microsoft.store.samples.secureappmodel.cspsample.security.AzureTokenProvider;
import com.microsoft.store.samples.secureappmodel.cspsample.security.GraphAuthenticationProvider;
import com.microsoft.store.samples.secureappmodel.cspsample.security.IAccessTokenProvider;
import com.microsoft.store.samples.secureappmodel.cspsample.security.SecureLoginHandler;

import org.apache.commons.lang3.StringUtils;

/** 
 * Sample application that demonstrates how a Cloud Solution Provider 
 * partner should utilize the secure application model to interact with the 
 * Partner Center API, Microsoft Azure Resource Manager, and Microsoft Graph.
 */
public class Program 
{
    /**
     * Entry point for the console application.
     * 
     * @param args Arguments passed from the command line.
     */
    public static void main(String args[])
    {
        IAadLoginHandler loginHandler;
        IPartner partnerOperations;  
        IPartnerCredentials credentials;
        IAccessTokenProvider tokenProvider; 
        OrganizationProfile profile;
        Properties properties; 

        try
        {
            properties = new Properties(); 
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PropertyName.APP_PROPERTIES));

            tokenProvider = new AccessTokenProvider(properties);
            loginHandler = new SecureLoginHandler(properties, tokenProvider);

            credentials = PartnerCredentials.getInstance().generateByUserCredentials(
                properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_ID), 
                loginHandler.authenticate(), 
                loginHandler);

            partnerOperations = PartnerService.getInstance().createPartnerOperations(credentials);
            profile = partnerOperations.getProfiles().getOrganizationProfile().get();

            System.out.println(profile.getCompanyName());

            /**
             * Uncomment the following if you want to run the Azure task sample. Please note that this requires 
             * the Azure AD application to have the Windows Azure Service Management API configured as one of 
             * the required permissions. 
             */
            
             // RunAzureTask(properties, tokenProvider, "SPECIFY-THE-IDENTIFIER-OF-CUSTOMER");

            /**
             * Uncomment the following if you want to run the Microsoft Graph task sample. Please note that this
             * requires the Azure AD application to have Microsoft Graph configured as one of the required permissions.
             * If you are receive an error stating the identity of the calling application could not be established, 
             * ensure the Azure AD application is configured to be multi-tenanted and has been configured for pre-consent. 
             */

            // RunGraphTask(properties, tokenProvider, "SPECIFY-THE-IDENTIFIER-OF-CUSTOMER");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Demonstrates how a partner can interact with Microsoft Azure Resource Manager in the context of the customer.
     *  
     * @param properties The configuration information stored in the application.properties file.
     * @param tokenProvider Provides the ability to request access tokens.
     * @param customerId Identifier of the customer.
     */
    private static void RunAzureTask(Properties properties, IAccessTokenProvider tokenProvider, String customerId)
    {
        Azure.Authenticated azureAuth;
        Azure azure;
        PagedList<ResourceGroup> resourceGroups;

        if(properties == null)
        {
            throw new IllegalArgumentException("properties cannot be null");
        }

        if(tokenProvider == null)
        {
            throw new IllegalArgumentException("tokenProvider cannot be null");
        }

        if(StringUtils.isEmpty(customerId))
        {
            throw new IllegalArgumentException("customerId is empty or null");
        }

        try
        {
            azureAuth = Azure.authenticate(new AzureTokenProvider(properties, tokenProvider, customerId));
            azure = azureAuth.withDefaultSubscription();
            resourceGroups = azure.resourceGroups().list();

            resourceGroups.forEach(group -> {
                System.out.println(group.name());
            });
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Demonstrates how a partner can interact with Microsoft Graph in the context of the customer.
     *  
     * @param properties The configuration information stored in the application.properties file.
     * @param tokenProvider Provides the ability to request access tokens.
     * @param customerId Identifier of the customer.
     */
    private static void RunGraphTask(Properties properties, IAccessTokenProvider tokenProvider, String customerId)
    {
        if(properties == null)
        {
            throw new IllegalArgumentException("properties cannot be null");
        }

        if(tokenProvider == null)
        {
            throw new IllegalArgumentException("tokenProvider cannot be null");
        }

        if(StringUtils.isEmpty(customerId))
        {
            throw new IllegalArgumentException("customerId is empty or null");
        }

        IGraphServiceClient graphClient = GraphServiceClient
            .builder()
            .authenticationProvider(new GraphAuthenticationProvider(properties, tokenProvider, customerId))
            .buildClient();

        IDomainCollectionPage domains = graphClient.domains().buildRequest().get();

        for(Domain domain : domains.getCurrentPage())
        {
            System.out.println(domain.id);
        }
    }
}