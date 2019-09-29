// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.secureappmodel.cpvsample.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationGrant 
{
    @JsonProperty("enterpriseApplicationId")
    private String enterpriseApplicationId; 

    @JsonProperty("scope")
    private String scope;

    public String getEnterpriseApplicationId()
    {
        return enterpriseApplicationId;
    }

    public String getScope()
    {
        return scope;
    }

    public void setEnterpriseApplication(String value)
    {
        enterpriseApplicationId = value; 
    }

    public void setScope(String value)
    {
        scope = value;
    }
}