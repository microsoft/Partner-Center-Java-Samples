// -----------------------------------------------------------------------
// <copyright file="IScenarioExecutionStrategy.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.scenarioexecution;

import com.microsoft.store.partnercenter.samples.IPartnerScenario;

/**
 * Defines the behavior to apply when a scenario is complete.
 */
public interface IScenarioExecutionStrategy
{
    /**
     * Determines whether the scenario is complete or it should be repeated.
     * 
     * @param scenario The scenario under consideration.
     * @return True is the scenario is complete, False is it should be repeated.
     */
    Boolean isScenarioComplete( IPartnerScenario scenario );

}
