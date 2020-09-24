// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cpvsample.security;

import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.store.samples.secureappmodel.cpvsample.PropertyName;

/**
 * Provides the ability to request access tokens.
 */
public class AccessTokenProvider implements IAccessTokenProvider
{
    /**
     * Provides access to configuration information stored in the application.properties file.
     */
    private Properties properties; 

    /**
     * Initializes a new instance of the {@link AccessTokenProvider} class.
     * 
     * @param properties The persistent set of application properties.
     */
    public AccessTokenProvider(Properties properties)
    {
        if(properties == null)
        {
            throw new IllegalArgumentException("The properties parameter cannot be null.");
        }

        this.properties = properties;
    }

    /**
     * Gets an access token from the authority.
     * 
     * @param tenantId Identifier of the Azure AD tenant requesting the token.
     * @param resource Identifier of the target resource that is the recipient of the requested token.
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     * @return An instance of {@link AuthenticationResult} that contians an access token and refresh token.
     * 
     * @throws ExecutionException {@link ExecutionException}
     * @throws InterruptedException {@link InterruptedException}
     * @throws MalformedURLException {@link MalformedURLException}
     */
    public AuthenticationResult getAccessToken(String tenantId, String resource, String clientId, String clientSecret) 
        throws ExecutionException, InterruptedException, MalformedURLException
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult;
        ExecutorService service = null;
        Future<AuthenticationResult> future;

        try
        {
            service = Executors.newFixedThreadPool(1);
            authContext = new AuthenticationContext(
                MessageFormat.format(
                    "{0}/{1}", 
                    properties.getProperty(PropertyName.AZURE_AD_AUTHORITY), 
                    tenantId), 
                true, 
                service);

            future = authContext.acquireToken(
                resource,
                new ClientCredential(
                    clientId, 
                    clientSecret), 
                null);
            
            authResult = future.get();

            return authResult;
        }
        finally
        {
            service.shutdown();
        }
    }

    /**
     * Gets an access token from the authority.
     * 
     * @param tenantId Identifier of the Azure AD tenant requesting the token.
     * @param resource Identifier of the target resource that is the recipient of the requested token.
     * @param refreshToken The refresh token to use in the request.
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     * @return An instance of {@link AuthenticationResult} that contians an access token and refresh token.
     * 
     * @throws ExecutionException {@link ExecutionException}
     * @throws InterruptedException {@link InterruptedException}
     * @throws MalformedURLException {@link MalformedURLException}
     */
    public AuthenticationResult getAccessTokenByRefreshToken(String tenantId, String resource, String refreshToken, String clientId, String clientSecret)
        throws ExecutionException, InterruptedException, MalformedURLException
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult;
        ExecutorService service = null;
        Future<AuthenticationResult> future;

        try
        {
            service = Executors.newFixedThreadPool(1);
            authContext = new AuthenticationContext(
                MessageFormat.format(
                    "{0}/{1}", 
                    properties.getProperty(PropertyName.AZURE_AD_AUTHORITY), 
                    tenantId), 
                true, 
                service);

            future = authContext.acquireTokenByRefreshToken(
                refreshToken, 
                new ClientCredential(
                    clientId, 
                    clientSecret), 
                resource, 
                null);

            authResult = future.get();

            return authResult;
        }
        finally
        {
            service.shutdown();
        }
    }

    /**
     * Gets an access token from the authority.
     * 
     * @param tenantId Identifier of the Azure AD tenant requesting the token.
     * @param resource Identifier of the target resource that is the recipient of the requested token.
     * @param identifier Name of the refresh token stored in an instance of Azure Key Vault.
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     * @return An instance of {@link AuthenticationResult} that contians an access token and refresh token.
     * 
     * @throws ExecutionException {@link ExecutionException}
     * @throws InterruptedException {@link InterruptedException}
     * @throws MalformedURLException {@link MalformedURLException}
     */
    public AuthenticationResult getAccessTokenBySecureRefreshToken(String tenantId, String resource, String identifier, String clientId, String clientSecret)
        throws ExecutionException, InterruptedException, MalformedURLException
    {
        IVaultProvider vault = new KeyVaultProvider(
            properties.getProperty(PropertyName.KEY_VAULT_BASE_URL));

        return getAccessTokenByRefreshToken(
            tenantId,  
            resource, 
            vault.getSecret(identifier), 
            clientId, 
            clientSecret);
    }
}