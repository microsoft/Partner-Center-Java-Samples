// -----------------------------------------------------------------------
// <copyright file="ScenarioContext.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.context;

import com.microsoft.store.partnercenter.IAadLoginHandler;
import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.IPartnerCredentials;
import com.microsoft.store.partnercenter.PartnerService;
import com.microsoft.store.partnercenter.extensions.PartnerCredentials;
import com.microsoft.store.partnercenter.samples.AadUserLoginHandler;
import com.microsoft.store.partnercenter.samples.IScenarioContext;
import com.microsoft.store.partnercenter.samples.configuration.Configuration;
import com.microsoft.store.partnercenter.samples.configuration.ConfigurationHolder;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * Scenario context implementation class.
 */
public class ScenarioContext
    implements IScenarioContext
{
    /**
     * A lazy reference to an user based partner operations.
     */
    private IAggregatePartner userPartnerOperations = null;

    /**
     * A lazy reference to an application based partner operations.
     */
    private IAggregatePartner appPartnerOperations = null;

    /**
     * Initializes a new instance of the {@link #ScenarioContext} class.
     */
    public ScenarioContext()
    {
        PartnerService.getInstance().setApiRootUrl( ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "PartnerServiceApiEndpoint" ) );
    }

    /**
     * Gets a partner operations instance which is application based authenticated.
     */
    @Override
    public IAggregatePartner getAppPartnerOperations()
    {
        if ( this.appPartnerOperations == null )
        {
            System.out.println( "Authenticating application... " );
            IPartnerCredentials appCredentials =
                PartnerCredentials.getInstance().generateByApplicationCredentials( ConfigurationHolder.getInstance().getConfiguration().getAppAuthentication().get( "ApplicationId" ),
                                                                     ConfigurationHolder.getInstance().getConfiguration().getAppAuthentication().get( "ApplicationSecret" ),
                                                                     ConfigurationHolder.getInstance().getConfiguration().getAppAuthentication().get( "Domain" ),
                                                                     ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "AuthenticationAuthorityEndpoint" ),
                                                                     ConfigurationHolder.getInstance().getConfiguration().getPartnerServiceSettings().get( "GraphEndpoint" ) );

            System.out.println( "Authenticated!" );
            this.appPartnerOperations = PartnerService.getInstance().createPartnerOperations( appCredentials );
        }

        return this.appPartnerOperations;
    }

    /**
     * Gets a configuration instance.
     */
    @Override
    public Configuration getConfiguration()
    {
        return ConfigurationHolder.getInstance().getConfiguration();
    }

    /**
     * Gets a console helper instance.
     */
    @Override
    public ConsoleHelper getConsoleHelper()
    {
        return ConsoleHelper.getInstance();
    }

    /**
     * Gets a partner operations instance which is user based authenticated.
     */
    @Override
    public IAggregatePartner getUserPartnerOperations()
    {
        if ( this.userPartnerOperations == null )
        {
            System.out.println( "Authenticating user... " );
            // give the partner SDK the new add token information
            IAadLoginHandler loginHandler = new AadUserLoginHandler();
            
            IPartnerCredentials userCredentials = PartnerCredentials.getInstance().generateByUserCredentials(
                    this.getConfiguration().getUserAuthentication().get( "ClientId" ),
                    loginHandler.authenticate(), 
                    loginHandler );

            System.out.println( "Authenticated!" );
            this.userPartnerOperations = PartnerService.getInstance().createPartnerOperations( userCredentials );
        }

        return this.userPartnerOperations;
    }

}
