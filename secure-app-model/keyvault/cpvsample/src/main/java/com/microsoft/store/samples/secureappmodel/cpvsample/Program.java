// -----------------------------------------------------------------------
// <copyright file="Program.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cpvsample;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
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
import com.microsoft.store.samples.secureappmodel.cpvsample.models.ApplicationConsent;
import com.microsoft.store.samples.secureappmodel.cpvsample.models.ApplicationGrant;
import com.microsoft.store.samples.secureappmodel.cpvsample.security.AccessTokenProvider;
import com.microsoft.store.samples.secureappmodel.cpvsample.security.AzureTokenProvider;
import com.microsoft.store.samples.secureappmodel.cpvsample.security.GraphAuthenticationProvider;
import com.microsoft.store.samples.secureappmodel.cpvsample.security.IAccessTokenProvider;
import com.microsoft.store.samples.secureappmodel.cpvsample.security.SecureLoginHandler;

import org.apache.commons.lang3.StringUtils;

/** 
 * Sample application that demonstrates how a Control Panel Vendor should 
 * utilize the secure application model to interact with the Partner Center
 * API, Microsoft Azure Resource Manager, and Microsoft Graph.
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
        IAccessTokenProvider tokenProvider; 
        Properties properties; 
        String customerId; 
        String partnerId; 

        try
        {
            partnerId = "SPECIFY-THE-PARTNER-TENANT-ID-HERE"; 
            customerId = "SPECIFY-THE-CUSTOMER-TENANT-ID-HERE";

            properties = new Properties(); 
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PropertyName.APP_PROPERTIES));

            tokenProvider = new AccessTokenProvider(properties);

            RunPartnerCenterTask(properties, tokenProvider, partnerId, customerId);

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

    private static void RunPartnerCenterTask(Properties properties, IAccessTokenProvider tokenProvider, String partnerId, String customerId)
    {
        IAadLoginHandler loginHandler = new SecureLoginHandler(properties, tokenProvider);

        IPartnerCredentials credentials = PartnerCredentials.getInstance().generateByUserCredentials(
            properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_ID), 
            loginHandler.authenticate(), 
            loginHandler);

        IPartner partnerOperations = PartnerService.getInstance().createPartnerOperations(credentials);
    
        if(StringUtils.isNoneBlank(customerId))
        {
            ApplicationGrant azureAppGrant = new ApplicationGrant();

            azureAppGrant.setEnterpriseApplication("797f4846-ba00-4fd7-ba43-dac1f8f63013");
            azureAppGrant.setScope("user_impersonation");

            ApplicationGrant graphAppGrant = new ApplicationGrant(); 

            graphAppGrant.setEnterpriseApplication("00000002-0000-0000-c000-000000000000");
            graphAppGrant.setScope("Domain.ReadWrite.All,User.ReadWrite.All,Directory.Read.All");
    
            ApplicationConsent consent = new ApplicationConsent();

            consent.setApplicationGrants(Arrays.asList(azureAppGrant, graphAppGrant));
            consent.setApplicationId(properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_ID));
            consent.setDisplayName(properties.getProperty(PropertyName.PARTNER_CENTER_DISPLAY_NAME));

            // Deletes the existing grant into the customer it is present.
            partnerOperations.getServiceClient().delete(
                partnerOperations,
                new TypeReference<ApplicationConsent>(){},
                MessageFormat.format(
                    "customers/{0}/applicationconsents/{1}", 
                    customerId, 
                    properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_ID)));

            // Consent to the defined applications and the respective scopes.
            partnerOperations.getServiceClient().post(
                partnerOperations, 
                new TypeReference<ApplicationConsent>(){},
                MessageFormat.format(
                    "customers/{0}/applicationconsents", 
                    customerId),
                consent);
        }

        OrganizationProfile profile = partnerOperations.getProfiles().getOrganizationProfile().get();

        System.out.println(profile.getCompanyName());
    }
}