// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.scenarioexecution;

import java.text.MessageFormat;

import com.microsoft.store.partnercenter.samples.IPartnerScenario;
import com.microsoft.store.partnercenter.samples.helpers.ConsoleHelper;

/**
 * An execution strategy which prompts the user to select a child scenario or exit the current scenario.
 */
public class AggregateScenarioExecutionStrategy
    implements IScenarioExecutionStrategy
{
    /**
     * Initializes a new instance of the {@link #AggregateScenarioExecutionStrategy} class.
     */
    public AggregateScenarioExecutionStrategy()
    {
    }

    /**
     * Determines whether the scenario is complete or it should be repeated.
     * 
     * @param scenario The scenario under consideration.
     * @return True is the scenario is complete, False is it should be repeated.
     */
    public Boolean isScenarioComplete( IPartnerScenario scenario )
    {
        if ( scenario == null )
        {
            throw new IllegalArgumentException( "scenario can't be null" );
        }

        if ( scenario.getChildren() == null || scenario.getChildren().size() <= 0 )
        {
            throw new IllegalArgumentException( "childScenarios must not be empty." );
        }

        int scenarioNumber =
            AggregateScenarioExecutionStrategy.readScenarioNumberFromConsole( scenario.getChildren().size() );
        if ( scenarioNumber > 0 )
        {
            // run the selected child scenario
            scenario.getChildren().get( scenarioNumber - 1 ).run();
            return false;
        }
        else
        {
            return true;
        }
    }

    // the current scenario should be restarted
    // user pressed escape, exit scenario
    /**
     * Reads user input from the console and extracts a scenario number from it. Returns 0 if the user entered nothing.
     * 
     * @param maxScenarioNumber The maximum scenario number to allow.
     * @return The scenario number the user entered or 0 if the user cancelled.
     */
    private static int readScenarioNumberFromConsole( int maxScenarioNumber )
    {
        if ( maxScenarioNumber < 1 )
        {
            throw new IllegalArgumentException( "maxScenarioNumber must be at least 1" );
        }

        int scenarioNumber;
        while ( true )
        {
            ConsoleHelper.getInstance().warning( "Enter the scenario number to run (press Q to exit to previous screen): ",
                                                 false );
            String input = ConsoleHelper.getInstance().getScanner().nextLine();
            if ( input.equalsIgnoreCase( "q" ) )
            {
                scenarioNumber = 0;
                break;
            }
            else
            {
                try
                {
                    scenarioNumber = Integer.parseInt( input );
                    if ( 1 <= scenarioNumber && scenarioNumber <= maxScenarioNumber )
                    {
                        break;
                    }
                }
                catch ( NumberFormatException e )
                {
                    ConsoleHelper.getInstance().error( MessageFormat.format( "Enter a scenario number between 1 and {0}",
                                                                             maxScenarioNumber ) );
                }
            }
        }
        return scenarioNumber;
    }

}
