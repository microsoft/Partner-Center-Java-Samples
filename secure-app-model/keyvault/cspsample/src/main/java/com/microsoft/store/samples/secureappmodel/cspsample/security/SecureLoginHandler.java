// -----------------------------------------------------------------------
// <copyright file="SecureLoginHandler.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.secureappmodel.cspsample.security;

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
import com.microsoft.store.partnercenter.AuthenticationToken;
import com.microsoft.store.partnercenter.IAadLoginHandler;

import org.joda.time.DateTime;

public class SecureLoginHandler implements IAadLoginHandler
{
    /**
     * The name of the Azure AD authority property.
     */
    private static final String AZURE_AD_AUTHORITY = "azuread.authority";

    /**
     * The name of the Azure Key Vault base URL property.
     */
    private static final String KEY_VAULT_BASE_URL = "keyvault.baseurl";

    /**
     * The name of the client identifier property.
     */
    private static final String KEY_VAULT_CLIENT_ID = "keyvault.clientId";

    /**
     * The name of the client secret property.
     */
    private static final String KEY_VAULT_CLIENT_SECRET = "keyvault.clientSecret";

    /** 
     * The name of the Partner Center account identifier property.
     */
    private static final String PARTNER_CENTER_ACCOUNT_ID = "partnercenter.accountId";

    /**
     * The name of the Partner Center client identifier property.
     */
    private static final String PARTNER_CENTER_CLIENT_ID = "partnercenter.clientId";

    /**
     * The name of the Partner Center client secret property.
     */
    private static final String PARTNER_CENTER_CLIENT_SECRET = "partnercenter.clientSecret";

    /**
     * Provides access to configuration information stored in the application.properties file.
     */
    private Properties properties; 

    /**
     * Initializes a new instance of the {@link SecureLoginHandler}
     */
    public SecureLoginHandler(Properties properties)
    {
        this.properties = properties;
    }

    @Override
    public AuthenticationToken authenticate()
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult = null;
        ExecutorService service = null;
        Future<AuthenticationResult> future;
        IVaultProvider vault;
        String refreshToken; 
        
        try
        {
            service = Executors.newFixedThreadPool(1);

            authContext = new AuthenticationContext(
                MessageFormat.format(
                    "{0}/{1}",
                    properties.getProperty(AZURE_AD_AUTHORITY),
                    properties.getProperty(PARTNER_CENTER_ACCOUNT_ID)), 
                true, 
                service);

            vault = new KeyVaultProvider(
                properties.getProperty(KEY_VAULT_BASE_URL),
                properties.getProperty(KEY_VAULT_CLIENT_ID), 
                properties.getProperty(KEY_VAULT_CLIENT_SECRET));

            refreshToken = vault.getSecret(properties.getProperty(PARTNER_CENTER_ACCOUNT_ID));

            future = authContext.acquireTokenByRefreshToken(
                refreshToken,
                new ClientCredential(
                    properties.getProperty(PARTNER_CENTER_CLIENT_ID),
                    properties.getProperty(PARTNER_CENTER_CLIENT_SECRET)), 
                null); 

            authResult = future.get();
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
        finally
        {
            service.shutdown();
        }

        if(authResult == null)
        {
            throw new NullPointerException("Unable to obtain an access token.");
        }

        return new AuthenticationToken(authResult.getAccessToken(), new DateTime(authResult.getExpiresOnDate()));
    }
}