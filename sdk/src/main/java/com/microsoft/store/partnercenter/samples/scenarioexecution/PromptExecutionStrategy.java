// -----------------------------------------------------------------------
// <copyright file="PromptExecutionStrategy.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.scenarioexecution;

import com.microsoft.store.partnercenter.samples.IPartnerScenario;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * An scenario execution strategy that prompts the user repeat or exit the current scenario.
 */
public class PromptExecutionStrategy
    implements IScenarioExecutionStrategy
{
    /**
     * Determines whether the scenario is complete or it should be repeated.
     * 
     * @param scenario The scenario under consideration.
     * @return True is the scenario is complete, False is it should be repeated.
     */
    public Boolean isScenarioComplete( IPartnerScenario scenario )
    {
        ConsoleHelper.getInstance().warning( "Press Q to return to the previous screen or R to repeat the current scenario:",
                                             false );
        String keyRead = ConsoleHelper.getInstance().getScanner().nextLine();
        while ( !keyRead.equalsIgnoreCase( "r" ) && !keyRead.equalsIgnoreCase( "q" ) )
        {
            keyRead = ConsoleHelper.getInstance().getScanner().nextLine();
        }
        return keyRead.equalsIgnoreCase( "q" );
    }

}
