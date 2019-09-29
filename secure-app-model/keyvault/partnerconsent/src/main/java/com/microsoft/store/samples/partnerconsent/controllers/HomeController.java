// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.samples.partnerconsent.controllers;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import com.microsoft.aad.adal4j.AuthenticationResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController 
{
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getIndex(ModelMap model, HttpServletRequest httpRequest)
    {
        AuthenticationResult authResult = (AuthenticationResult)httpRequest.getSession().getAttribute("principal");
        
        if (authResult == null) {
            model.addAttribute("error", new Exception("An unexpceted error has occurred."));
            return "/error";
        }

        model.addAttribute(
            "name",
            MessageFormat.format(
                "{0} {1}", 
                authResult.getUserInfo().getGivenName(),
                authResult.getUserInfo().getFamilyName()));

        return "index";
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/error")
    public String getError()
    {
        return "error";
    }
}