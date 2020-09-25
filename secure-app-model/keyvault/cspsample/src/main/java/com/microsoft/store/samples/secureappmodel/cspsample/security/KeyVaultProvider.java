// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cspsample.security;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
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
     */
    public KeyVaultProvider(String vaultBaseUrl)
    {
        client = getKeyVaultClient();
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
     * @return A client that is capable of interacting with the Azure Key Vault service.
     */
    private SecretClient getKeyVaultClient()
    {
        DefaultAzureCredential defaultAzureCredential = new DefaultAzureCredentialBuilder().build();
        
        client = new SecretClientBuilder()
            .vaultUrl(vaultBaseUrl)
            .credential(defaultAzureCredential)
            .buildClient();

        return client;
    }
}