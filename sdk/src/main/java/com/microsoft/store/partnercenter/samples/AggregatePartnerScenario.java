// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples;

import java.text.MessageFormat;
import java.util.List;

import com.microsoft.store.partnercenter.samples.scenarioexecution.AggregateScenarioExecutionStrategy;

/**
 * A scenarios that is composed of one or more sub-scenarios.
 */
public class AggregatePartnerScenario
    extends BasePartnerScenario
{
    /**
     * Initializes a new instance of the {@link #AggregatePartnerScenario} class.
     * 
     * @param title The scenario title.
     * @param childScenarios A list of child scenarios.
     * @param context The scenario context.
     */
    public AggregatePartnerScenario( String title, List<IPartnerScenario> childScenarios, IScenarioContext context )
    {
        super( title, context, new AggregateScenarioExecutionStrategy(), childScenarios );
    }

    /**
     * Runs the aggregate scenario.
     */
    @Override
    protected void runScenario()
    {
        for ( int i = 0; i < this.getChildren().size(); ++i )
        {
            // display the child scenarios
            System.out.println( MessageFormat.format( "{0}: {1}", i + 1, this.getChildren().get( i ).getTitle() ) );
        }
    }
}