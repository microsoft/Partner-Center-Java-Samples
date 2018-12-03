// -----------------------------------------------------------------------
// <copyright file="KeyVaultProvider.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cspsample.security;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.KeyVaultClientCustom;
import com.microsoft.azure.keyvault.authentication.KeyVaultCredentials;

/**
 * Provides a secure mechanism for retrieving and store sensitive information using Azure Key Vault.
 */
public class KeyVaultProvider implements IVaultProvider
{
    /**
     * The client used to interact with the Azure Key Vault service.
     */
    private KeyVaultClientCustom client;

    /**
     * The vault name, e.g. https://myvault.vault.azure.net
     */
    private String vaultBaseUrl; 

    /**
     * Initializes a new instance of the {@link KeyVaultProvider} class.
     * 
     * @param vaultBaseUrl The vault name, e.g. https://myvault.vault.azure.net
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     */
    public KeyVaultProvider(String vaultBaseUrl, String clientId, String clientSecret)
    {
        client = getKeyVaultClient(clientId, clientSecret);
        this.vaultBaseUrl = vaultBaseUrl;
    }

    /**
     * Gets the specified value from the vault.
     * 
     * @param secretName Identifier of the value to be retrieved.
     * @return The value for the specified secret.
     */
    public String getSecret(String secretName)
    {
        return client.getSecret(vaultBaseUrl, secretName).value();
    }

    /**
     * Stores the specified value in the vault.
     * 
     * @param secretName Identifier of the value to be stored.
     * @param value The value to be stored.
     */
    public void setSecret(String secretName, String value)
    {
        client.setSecret(vaultBaseUrl, secretName, value);
    }

    /**
     * Gets an access token from the authority.
     * 
     * @param authorization Address of the authority to issue the token.
     * @param resource Identifier of the target resource that is the recipient of the requested token.
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     * @return An instance of {@link AuthenticationResult} that contians an access token and refresh token.
     * 
     * @throws ExecutionException {@link ExecutionException}
     * @throws InterruptedException {@link InterruptedException}
     * @throws MalformedURLException {@link MalformedURLException}
     */
    private AuthenticationResult getAccessToken(String authorization, String resource, String clientId, String clientSecret) 
        throws ExecutionException, InterruptedException, MalformedURLException
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult;
        ExecutorService service = null;
        Future<AuthenticationResult> future;

        try
        {
            service = Executors.newFixedThreadPool(1);
            authContext = new AuthenticationContext(authorization, true, service);

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
     * Gets a client that is capable of interacting with the Azure Key Vault service.
     *
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token. 
     * 
     * @return A client that is capable of interacting with the Azure Key Vault service.
     */
    private KeyVaultClientCustom getKeyVaultClient(String clientId, String clientSecret)
    {
        return new KeyVaultClient(new KeyVaultCredentials() 
        {
            /**
             * @param authorization Address of the authority to issue the token.
             * @param resource Identifier of the target resource that is the recipient of the requested token, a URL.
             * @param scope The scope of the authentication request.
             *
             * @return Access token to be used with Azure Key Vault operations.
             */
            @Override
             public String doAuthenticate(String authorization, String resource, String scope) 
             {
                 AuthenticationResult authResult; 
                 
                 try 
                 {
                     authResult = getAccessToken(authorization, resource, clientId, clientSecret);
 
                     return authResult.getAccessToken();
                 }
                 catch(Exception ex)
                 {
                     ex.printStackTrace();
                 }
 
                 return "";
             }
        });
    }
}