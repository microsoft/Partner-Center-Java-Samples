// -----------------------------------------------------------------------
// <copyright file="IScenarioContext.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.samples.configuration.Configuration;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * Holds context properties useful to the scenarios.
 */
public interface IScenarioContext
{
    /**
     * Gets a partner operations instance which is user based authenticated.
     */
    IAggregatePartner getUserPartnerOperations();

    /**
     * Gets a partner operations instance which is application based authenticated.
     */
    IAggregatePartner getAppPartnerOperations();

    /**
     * Gets a configuration instance.
     */
    Configuration getConfiguration();

    /**
     * Gets a console helper instance.
     */
    ConsoleHelper getConsoleHelper();

}
