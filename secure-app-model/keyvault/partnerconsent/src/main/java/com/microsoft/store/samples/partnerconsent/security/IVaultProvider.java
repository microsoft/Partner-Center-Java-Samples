// -----------------------------------------------------------------------
// <copyright file="IVaultProvider.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.partnerconsent.security;

/**
 * Represents a secure mechanism for retrieving and store sensitive information.
 */
public interface IVaultProvider 
{
    /**
     * Gets the specified value from the vault.
     * 
     * @param secretName Identifier of the value to be retrieved.
     * @return The value for the specified secret.
     */
    String getSecret(String secretName); 

    /**
     * Stores the specified value in the vault.
     * 
     * @param secretName Identifier of the value to be stored.
     * @param value The value to be stored.
     */
    void setSecret(String secretName, String value);
}