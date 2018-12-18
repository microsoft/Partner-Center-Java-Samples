// -----------------------------------------------------------------------
// <copyright file="IAccessTokenProvider.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cspsample.security;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import com.microsoft.aad.adal4j.AuthenticationResult;

/**
 * Represent the ability to obtain access tokens.
 */
public interface IAccessTokenProvider
{
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
        throws ExecutionException, InterruptedException, MalformedURLException;

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
        throws ExecutionException, InterruptedException, MalformedURLException;

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
        throws ExecutionException, InterruptedException, MalformedURLException;
}