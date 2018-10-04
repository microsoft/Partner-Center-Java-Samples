// -----------------------------------------------------------------------
// <copyright file="AadUserLoginHandler.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
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
        // read AAD configuration
        String authority =
            ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "AuthenticationAuthorityEndpoint" );
        String commonDomain =
            ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "CommonDomain" );
        String resourceUrl =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "ResourceUrl" );
        String clientId =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "ClientId" );
        String userName =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "UserName" );
        String password =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "Password" );

        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try
        {
            URI addAuthority = new URI( authority ).resolve( new URI( commonDomain ) );

            service = Executors.newFixedThreadPool( 1 );
            context = new AuthenticationContext( addAuthority.toString(), false, service );
            Future<AuthenticationResult> future =
                context.acquireToken( resourceUrl, clientId, userName, password, null );
            result = future.get();
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        catch ( ExecutionException e )
        {
            e.printStackTrace();
        }
        finally
        {
            service.shutdown();
        }

        if ( result == null )
        {
            throw new NullPointerException( "authentication result was null" );
        }

        return new AuthenticationToken( result.getAccessToken(), new DateTime( result.getExpiresOnDate() ) );
    }
}