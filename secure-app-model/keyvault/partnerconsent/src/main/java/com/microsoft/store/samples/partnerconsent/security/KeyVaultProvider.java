// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.partnerconsent.security;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;


/**
 * Provides a secure mechanism for retrieving and store sensitive information using Azure Key Vault.
 */
public class KeyVaultProvider implements IVaultProvider
{
    /**
     * The client used to manage Secrets in the Azure KeyVault by interacting with the Azure Key Vault service.
     */
    private SecretClient client;

    /**
     * The Vault URL, e.g. https://myvault.vault.azure.net
     */
    private String vaultBaseUrl; 

    /**
     * Initializes a new instance of the {@link KeyVaultProvider} class.
     * 
     * @param vaultBaseUrl The vault name, e.g. https://myvault.vault.azure.net
     */
    public KeyVaultProvider(String vaultBaseUrl)
    {
        client = getKeyVaultClient();
        this.vaultBaseUrl = vaultBaseUrl;
    }

    /**
     * Gets the value of the specified secret from the Azure Key Vault..
     * 
     * @param secretName Identifier of the value to be retrieved.
     * @return The value for the specified secret.
     */
    public String getSecret(String secretName)
    {
        return client.getSecret(secretName).getValue();
    }

    /**
     * Adds a secret with the specified {@code secretName} and {@code value} to the key vault if it does not exist. 
     * If the named secret exists, a new version of the secret is created.
     * 
     * @param secretName Identifier of the value to be stored.
     * @param value The value to be stored.
     */
    public void setSecret(String secretName, String value)
    {
        client.setSecret(secretName, value);
    }

    /**
     * Gets the Secret Client, capable of managing Secrets in the Azure Key Vault by interacting with Azure Key Vault service.
     * 
     * @return The Secret Client, capable of managing Secrets in the Azure Key Vault by interacting with Azure Key Vault service.
     */
    private SecretClient getKeyVaultClient()
    {
        client = new SecretClientBuilder()
            .vaultUrl(vaultBaseUrl)
            .credential(new DefaultAzureCredentialBuilder().build())
            .buildClient();

        return client;
        
    }
}