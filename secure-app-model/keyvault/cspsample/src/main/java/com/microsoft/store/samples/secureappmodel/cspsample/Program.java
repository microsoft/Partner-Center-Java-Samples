// -----------------------------------------------------------------------
// <copyright file="Program.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cspsample;

import java.util.Properties;

import com.microsoft.store.partnercenter.IAadLoginHandler;
import com.microsoft.store.partnercenter.IPartner;
import com.microsoft.store.partnercenter.IPartnerCredentials;
import com.microsoft.store.partnercenter.PartnerService;
import com.microsoft.store.partnercenter.extensions.PartnerCredentials;
import com.microsoft.store.partnercenter.models.partners.OrganizationProfile;
import com.microsoft.store.samples.secureappmodel.cspsample.security.SecureLoginHandler;

public class Program {
    /**
     * The name of the azure.properties resource file.
     */
    private static final String AZURE_PROPERTIES = "application.properties";

    /**
     * The name of the partnercenter client identifier property. 
     */
    private static final String PARTNER_CENTER_CLIENT_ID = "partnercenter.clientId";

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
        OrganizationProfile profile;
        Properties properties; 

        try
        {
            properties = new Properties(); 

            // Load the properties using the azure.properties file found in the resources directory.
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(AZURE_PROPERTIES));

            loginHandler = new SecureLoginHandler(properties);

            credentials = PartnerCredentials.getInstance().generateByUserCredentials(
                properties.getProperty(PARTNER_CENTER_CLIENT_ID), 
                loginHandler.authenticate(), 
                loginHandler);

            partnerOperations = PartnerService.getInstance().createPartnerOperations(credentials);

            profile = partnerOperations.getProfiles().getOrganizationProfile().get();
            
            System.out.println(profile.getCompanyName());

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}