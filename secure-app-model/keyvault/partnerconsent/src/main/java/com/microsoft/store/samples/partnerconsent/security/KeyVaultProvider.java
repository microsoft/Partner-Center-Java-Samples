// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.partnerconsent.security;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

/**
 * Provides a secure mechanism for retrieving and store sensitive information using Azure Key Vault.
 */
public class KeyVaultProvider implements IVaultProvider
{
    /**
     * The client used to interact with the Azure Key Vault service.
     */
    private SecretClient client;

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
     * @param tenantId The tenant Id of the client requesting the token. 
     */
    public KeyVaultProvider(String vaultBaseUrl, String clientId, String clientSecret,String tenantId)
    {
        client = getKeyVaultClient(clientId, clientSecret, tenantId);
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
        return client.getSecret(secretName).getValue();
    }

    /**
     * Stores the specified value in the vault.
     * 
     * @param secretName Identifier of the value to be stored.
     * @param value The value to be stored.
     */
    public void setSecret(String secretName, String value)
    {
        client.setSecret(secretName, value);
    }


    /**
     * Gets a client that is capable of interacting with the Azure Key Vault service.
     *
     * @param clientId The identifier of the client requesting the token.
     * @param clientSecret The secure secret of the client requesting the token.
     * @param tenantId The tenant Id of the client requesting the token.  
     * 
     * @return A client that is capable of interacting with the Azure Key Vault service.
     */
    private SecretClient getKeyVaultClient(String clientId, String clientSecret, String tenantId)
    {
        ClientSecretCredential clientSecretCredential=new ClientSecretCredentialBuilder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .tenantId(tenantId)
        .build();
        
        client = new SecretClientBuilder()
        .vaultUrl(vaultBaseUrl)
        .credential(clientSecretCredential)
        .buildClient();

        return client;
    }
}