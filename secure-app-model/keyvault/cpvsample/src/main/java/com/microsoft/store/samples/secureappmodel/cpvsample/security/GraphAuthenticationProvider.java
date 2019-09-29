// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cpvsample.security;

import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.store.samples.secureappmodel.cpvsample.PropertyName;

import org.apache.commons.lang3.StringUtils;

/**
 * Provides authentication for a Microsoft Graph request before it is sent.
 */
public class GraphAuthenticationProvider implements IAuthenticationProvider
{
    /**
     * The authorization header name.
     */
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * The bearer prefix.
     */
    private static final String OAUTH_BEARER_PREFIX = "Bearer ";
   
    /**
     * Provides the ability to request access tokens.
     */
    private IAccessTokenProvider tokenProvider; 

    /**
     * Provides access to configuration information stored in the application.properties file.
     */
    private Properties properties; 
   
    /**
     * The identifier of the Azure AD tenant. 
     */
    private String customerId; 

    /**
     * Initializes a new instance of the {@link GraphAuthenticationProvider} class.
     * 
     * @param customerId The identifier of the Azure AD tenant.
     */
    public GraphAuthenticationProvider(Properties properties, IAccessTokenProvider tokenProvider, String customerId)
    {
        if(properties == null)
        {
            throw new IllegalArgumentException("properties cannot be null");
        }

        if(StringUtils.isEmpty(customerId))
        {
            throw new IllegalArgumentException("tenantId cannot be null");
        }

        if(tokenProvider == null)
        {
            throw new IllegalArgumentException("tokenProvider cannot be null");
        }

        this.properties = properties;
        this.customerId = customerId;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Authenticates the request
     * 
     * @param request the request to authenticate
     */
    @Override
    public void authenticateRequest(final IHttpRequest request)
    {   
        AuthenticationResult authResult = null; 
        
        // If the request already has an authorization header, do not intercept it.
        for (final HeaderOption option : request.getHeaders()) {
            if (option.getName().equals(AUTHORIZATION_HEADER_NAME)) {
                return;
            }
        }

        try
        {
            authResult = tokenProvider.getAccessTokenBySecureRefreshToken(
                customerId, 
                "https://graph.microsoft.com", 
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

        request.addHeader(AUTHORIZATION_HEADER_NAME, OAUTH_BEARER_PREFIX + authResult.getAccessToken());
    }
}