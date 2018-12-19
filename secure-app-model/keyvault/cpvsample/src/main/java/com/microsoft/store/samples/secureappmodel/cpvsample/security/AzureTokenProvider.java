// -----------------------------------------------------------------------
// <copyright file="AzureTokenProvider.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cpvsample.security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.store.samples.secureappmodel.cpvsample.PropertyName;

/**
 * Provides authentication for a Microsoft Azure befor a request is sent.
 */
public class AzureTokenProvider extends AzureTokenCredentials
{
    /**
     * Provides the ability to request access tokens.
     */
    private IAccessTokenProvider tokenProvider; 

    /**
     * Provides access to configuration information stored in the application.properties file.
     */
    private Properties properties; 

    /**
     * The identifier of the customer that owns the subscription.
     */
    private String customerId;

    /**
     * Initializes a new instance of the {@link AzureTokenProvider} class.
     * 
     * @param properties Provides access to configuration information stored in the application.properties file.
     * @param tokenProvider Provides the ability to request access tokens.
     * @param customerId Identifier of the customer.
     */
    public AzureTokenProvider(Properties properties, IAccessTokenProvider tokenProvider, String customerId)
    {
        super(null, customerId);

        if(properties == null)
        {
            throw new IllegalArgumentException("properties cannot be null.");
        }

        if(tokenProvider == null)
        {
            throw new IllegalArgumentException("tokenProvider cannot be null");
        }

        this.customerId = customerId;
        this.properties = properties; 
        this.tokenProvider = tokenProvider;
    }

    /**
     * The mechanism to get a token.
     *
     * @param resource Identifier of the target resource that is the recipient of the requested token.
     * @return The token to access the resource
     * @throws IOException exceptions from IO
     */
    @Override
    public String getToken(String resource) throws IOException
    {
        AuthenticationResult authResult = null;
        
        try 
        {
            authResult = tokenProvider.getAccessTokenBySecureRefreshToken(
                customerId, 
                resource, 
                properties.getProperty(PropertyName.PARTNER_CENTER_ACCOUNT_ID), 
                properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_ID),
                properties.getProperty(PropertyName.PARTNER_CENTER_CLIENT_SECRET));
        }
        catch(ExecutionException ex)
        {
            ex.printStackTrace();
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
        catch(MalformedURLException ex)
        {
            ex.printStackTrace();
        }

        if(authResult == null)
        {
            throw new NullPointerException("Unable to obtain an access token.");
        }

        return authResult.getAccessToken();
    }
}