// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples;

import java.util.List;

/**
 * Represents a partner scenario that demos one or more related partner center APIs.
 */
public interface IPartnerScenario
{
    /**
     * Gets the scenario title.
     */
    String getTitle();

    /**
     * Gets the children scenarios of the current scenario.
     */
    List<IPartnerScenario> getChildren();

    /**
     * Gets the scenario context.
     */
    IScenarioContext getContext();

    /**
     * Runs the scenario.
     */
    void run();
}