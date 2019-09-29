// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cpvsample.security;

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
}