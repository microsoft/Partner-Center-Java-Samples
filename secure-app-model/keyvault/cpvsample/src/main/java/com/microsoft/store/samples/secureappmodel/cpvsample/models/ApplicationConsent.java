// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cpvsample.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConsent 
{
    @JsonProperty("applicationGrants")
    private List<ApplicationGrant> applicationGrants; 

    @JsonProperty("applicationId")
    private String applicationId; 

    @JsonProperty("displayName")
    private String displayName;

    public List<ApplicationGrant> getApplicationGrants()
    {
        return applicationGrants; 
    }

    public String getApplicationId()
    {
        return applicationId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setApplicationGrants(List<ApplicationGrant> value)
    {
        applicationGrants = value; 
    }

    public void setApplicationId(String value)
    {
        applicationId = value;
    }

    public void setDisplayName(String value)
    {
        displayName = value;
    }
}