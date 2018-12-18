// -----------------------------------------------------------------------
// <copyright file="AadUserLoginHandler.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.aad.adal4j.AdalErrorCode;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.DeviceCode;
import com.microsoft.store.partnercenter.AuthenticationToken;
import com.microsoft.store.partnercenter.IAadLoginHandler;
import com.microsoft.store.partnercenter.exception.PartnerException;
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
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        Instant expiresOn; 
        DeviceCode deviceCode;
        ExecutorService service = null;

        // read AAD configuration
        String authority =
            ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "AuthenticationAuthorityEndpoint" );
        String commonDomain =
            ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "CommonDomain" );
        String resourceUrl =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "ResourceUrl" );
        String clientId =
            ConfigurationHolder.getInstance().getConfiguration().getUserAuthentication().get( "ClientId" );

        try
        {
            URI addAuthority = new URI( authority ).resolve( new URI( commonDomain ) );

            service = Executors.newFixedThreadPool( 1 );
            context = new AuthenticationContext( addAuthority.toString(), false, service );
         
            deviceCode = context.acquireDeviceCode(clientId, resourceUrl, null).get(); 
            expiresOn = Instant.now().plusSeconds(deviceCode.getExpiresIn());

            System.out.println(deviceCode.getMessage());
      
            result = SendTokenRequest(context, deviceCode, expiresOn);
        }
        catch (ExecutionException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }
        catch ( InterruptedException e )
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

    private AuthenticationResult SendTokenRequest(AuthenticationContext context, DeviceCode deviceCode, Instant expiresOn)
    {
        AuthenticationResult result = null;
        long duration; 

        duration = expiresOn.getEpochSecond() - Instant.now().getEpochSecond();

        while(duration > 0)
        {
            try
            {
                result = context.acquireTokenByDeviceCode(deviceCode, null).get();
                break;
            }
            catch(AuthenticationException ex)
            {
                if(ex.getErrorCode() != AdalErrorCode.AUTHORIZATION_PENDING)
                {
                    throw ex; 
                }
            }
            catch (ExecutionException ex)
            {
                if(ex.getCause() instanceof AuthenticationException)
                {
                    if(((AuthenticationException)ex.getCause()).getErrorCode() != AdalErrorCode.AUTHORIZATION_PENDING)
                    {
                        ex.printStackTrace();
                    }
                }
                else 
                {
                    ex.printStackTrace();
                }
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            
            try
            {
                Thread.sleep(deviceCode.getInterval() * 1000);
            }
            catch(InterruptedException ex)
            {
            }

            duration = expiresOn.getEpochSecond() - Instant.now().getEpochSecond();
        }

        if(result == null)
        {
            throw new PartnerException("Verification code expired before contacting the server.");
        }

        return result;
    }
}