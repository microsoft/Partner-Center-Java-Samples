// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.microsoft.aad.msal4j.DeviceCode;
import com.microsoft.aad.msal4j.DeviceCodeFlowParameters;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.store.partnercenter.AuthenticationToken;
import com.microsoft.store.partnercenter.IAadLoginHandler;
import com.microsoft.store.partnercenter.samples.configuration.ConfigurationHolder;

import org.joda.time.DateTime;

public class AadUserLoginHandler
    implements IAadLoginHandler
{
    /**
     * Logs into Azure active directory.
     * 
     * @return The authentication result.
     */
    @Override
    public AuthenticationToken authenticate()
    {
        CompletableFuture<IAuthenticationResult> future;
        Consumer<DeviceCode> deviceCodeConsumer;
        IAuthenticationResult authResult = null;
        PublicClientApplication app;
        String authority; 
        String clientId;
        String scope;

        try
        {
            authority = ConfigurationHolder
                .getInstance()
                .getConfiguration()
                .getPartnerServiceSettings()
                .get("AuthenticationAuthorityEndpoint");

            clientId = ConfigurationHolder
                .getInstance()
                .getConfiguration()
                .getUserAuthentication()
                .get("ClientId");
            
            scope = ConfigurationHolder
                .getInstance()
                .getConfiguration()
                .getUserAuthentication()
                .get("Scope");

            app = PublicClientApplication.builder(clientId)
                .authority(authority + "/common")
                .build();

            deviceCodeConsumer = (DeviceCode deviceCode) -> {
                System.out.println(deviceCode.message());
            };    
            
            future = app.acquireToken(
                DeviceCodeFlowParameters.builder(
                    Collections.singleton(scope),
                    deviceCodeConsumer)
                .build());

            future.handle((result, ex) -> {
                if(ex != null) 
                {
                    ex.printStackTrace();
                }

                return result;
            });

            authResult = future.join();

            if ( authResult == null )
            {
                throw new NullPointerException( "authentication result was null" );
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return new AuthenticationToken(authResult.accessToken(), new DateTime(authResult.expiresOnDate()));
    }
}