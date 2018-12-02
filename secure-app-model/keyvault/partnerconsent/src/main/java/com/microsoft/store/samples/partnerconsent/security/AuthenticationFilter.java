// -----------------------------------------------------------------------
// <copyright file="AuthenticationFilter.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.samples.partnerconsent.security;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.naming.ServiceUnavailableException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.store.samples.partnerconsent.models.StateData;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;

import org.apache.commons.lang3.StringUtils;

public class AuthenticationFilter implements Filter 
{
    /**
     * The key for the code parameter.
     */
    static final String CODE_KEY = "code";

    /**
     * The key for the error parameter.
     */
    static final String ERROR_KEY = "error"; 

    /**
     * The failed to validate error message.
     */
    static final String FAILED_TO_VALIDATE_MESSAGE = "Failed to validate data received from Authorization service - ";

    /**
     * The key for the id_token parameter.
     */
    static final String ID_TOKEN_KEY = "id_token";

    /**
     * The name of the HTTP POST method.
     */
    static final String POST_METHOD_NAME = "POST"; 

    /**
     * The name of the principal session.
     */
    static final String PRINCIPAL_SESSION_NAME = "principal";

    /**
     * The name of the state attribute stored in the HTTP session.
     */
    static final String STATE_NAME = "state";

    /**
     * The time to live value for the entries in the state map.
     */
    static final Integer STATE_TTL = 3600;

    /**
     * The address of the authority to issue the token
     */
    private String authority; 

    /**
     * The identifier of the client requesting the token.
     */
    private String clientId; 

    /**
     * The secret of the client requesting the token.
     */
    private String clientSecret;

    /**
     * Provides a secure mechanism for retrieving and store sensitive information.
     */
    private IVaultProvider vault;

    /**
     * The <code>doFilter</code> method of the Filter is called by the
     * container each time a request/response pair is passed through the
     * chain due to a client request for a resource at the end of the chain.
     * The FilterChain passed in to this method allows the Filter to pass
     * on the request and response to the next entity in the chain.
     *
     * <p>A typical implementation of this method would follow the following
     * pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain
     * using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to
     * the next entity in the filter chain to
     * block the request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the
     * next entity in the filter chain.
     * </ol>
     *
     * @param request the <code>ServletRequest</code> object contains the client's request
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     *
     * @see UnavailableException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        AuthenticationResult authResult; 
        HttpServletRequest httpRequest; 
        HttpServletResponse httpResponse; 

        if (request instanceof HttpServletRequest)
        {
            httpRequest = (HttpServletRequest)request; 
            httpResponse = (HttpServletResponse)response; 

            try
            {
                if(httpRequest.getSession().getAttribute(PRINCIPAL_SESSION_NAME) == null)
                {
                    if(containsAuthenticationData(httpRequest))
                    {
                        processAuthenticationResponse(httpRequest);
                    }
                    else
                    {
                        sendRedirect(httpRequest, httpResponse);
                        return; 
                    }
                }

                authResult = (AuthenticationResult) httpRequest.getSession().getAttribute(PRINCIPAL_SESSION_NAME);

                if(authResult.getExpiresOnDate().before(new Date()) ? true : false)
                {
                    authResult = getAccessTokenFromRefreshToken(authResult.getRefreshToken());
                    httpRequest.getSession().setAttribute(PRINCIPAL_SESSION_NAME, authResult);
                    storeRefreshToken(authResult);
                }
            } 
            catch (AuthenticationException ex) 
            {
                removeStateFromSession(httpRequest);
                sendRedirect(httpRequest, httpResponse);
                return;
            }
            catch (Throwable ex)
            {
                httpResponse.setStatus(500);
                request.setAttribute(ERROR_KEY, ex.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }

        chain.doFilter(request, response);
    }

    /** 
     * <p>Called by the web container
     * to indicate to a filter that it is being placed into service.</p>
     *
     * <p>The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.</p>
     * 
     * <p>The web container cannot place the filter into service if the init
     * method either</p>
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     * 
     * @implSpec
     * The default implementation takes no action.
     *
     * @param filterConfig a <code>FilterConfig</code> object containing the
     *                     filter's configuration and initialization parameters 
     * @throws ServletException if an exception has occurred that interferes with
     *                          the filter's normal operation
     */
    public void init(FilterConfig config) throws ServletException 
    {
        clientId = config.getInitParameter("client_id");
        authority = config.getServletContext().getInitParameter("authority");
        clientSecret = config.getInitParameter("client_secret");

        vault = new KeyVaultProvider(
            config.getInitParameter("keyvault_base_url"),
            config.getInitParameter("keyvault_client_id"),
            config.getInitParameter("keyvault_client_secret"));
    }

    /**
     * Checks if the HTTP request contains a response from the authentication authority.
     * 
     * @param request The HTTP request ot be checked.
     * @return true if the request contains a response from the authentication authority; otherwise false.
     */
    private boolean containsAuthenticationData(HttpServletRequest request)
    {
        Map <String, String[]> parametersMap = request.getParameterMap();

        return request.getMethod().equalsIgnoreCase(POST_METHOD_NAME) && 
            (parametersMap.containsKey(CODE_KEY) || parametersMap.containsKey(ERROR_KEY) || parametersMap.containsKey(ID_TOKEN_KEY));
    }

    /**
     * Gets an access token from the authority.
     * 
     * @param request The HTTP request made by the client.
     * @param code The authorization code returned from the authority.
     */
    private AuthenticationResult getAccessToken(HttpServletRequest request, AuthorizationCode code)
        throws Throwable
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult;
        ExecutorService service = null;
        Future<AuthenticationResult> future;

        try
        {
            service =  Executors.newFixedThreadPool(1);

            authContext = new AuthenticationContext(
                MessageFormat.format("{0}common", authority), 
                true, 
                service);

            future = authContext.acquireTokenByAuthorizationCode(
                code.getValue(),
                new URI(request.getRequestURL().toString()),
                new ClientCredential(
                    clientId,
                    clientSecret), 
                null); 

            authResult = future.get();
        }
        catch(ExecutionException ex)
        {
            throw ex.getCause();
        }
        finally
        {
            service.shutdown();
        }

        if(authResult == null)
        {
            throw new ServiceUnavailableException("Unable to obtain an access token");
        }

        return authResult;
    }

    /**
     * Gets an access token using the refresh token.
     * 
     * @param refreshToken The refresh token to be used when requesting an access token. 
     */
    private AuthenticationResult getAccessTokenFromRefreshToken(String refreshToken)
        throws Throwable
    {
        AuthenticationContext authContext; 
        AuthenticationResult authResult;
        ClientCredential credential; 
        ExecutorService service = null;
        Future<AuthenticationResult> future;

        try
        {
            service = Executors.newFixedThreadPool(1);

            credential = new ClientCredential(
                clientId,
                clientSecret);

            authContext = new AuthenticationContext(
                MessageFormat.format("{0}common", authority), 
                true, 
                service);

            future = authContext.acquireTokenByRefreshToken(
                refreshToken,
                credential, 
                null); 

            authResult = future.get();
        }
        catch(ExecutionException ex)
        {
            throw ex.getCause();
        }
        finally
        {
            service.shutdown();
        }

        if(authResult == null)
        {
            throw new ServiceUnavailableException("Unable to obtain an access token");
        }

        return authResult;
    }

    /**
     * Removes the state information from the HTTP session.
     * 
     * @throws Exception If the state information was not found in the HTTP session.
     */
    @SuppressWarnings("unchecked")
    private StateData removeStateFromSession(HttpServletRequest request)
    {
        Date currentTime = new Date();
        Iterator<Map.Entry<String, StateData>> iterator;
        Map<String, StateData> states; 
        Map.Entry<String, StateData> entry;
        StateData stateData; 
        String state = request.getParameter("state");
        long difference; 

        if(StringUtils.isNotEmpty(state))
        {
            states = (Map<String, StateData>)request.getSession().getAttribute(STATE_NAME);

            if(states != null)
            {
                iterator = states.entrySet().iterator();

                // Remove all expired state data records.
                while(iterator.hasNext())
                {
                    entry = iterator.next();

                    difference = TimeUnit.MILLISECONDS.toSeconds(currentTime.getTime() - entry.getValue().getExpirationDate().getTime());

                    if (difference > STATE_TTL) {
                        iterator.remove();
                    }
                }

                stateData = states.get(state);
                
                if(stateData != null)
                {
                    states.remove(state);

                    return stateData;
                }
            }
        }
        
        return null;
    }

    /**
     * Perform the authentication request and stores the authentication result in the HTTP session.
     * 
     * @param request The HTTP request made by the client.
     * @throws Exception If there was a failure with the authentication request.
     */
    private void processAuthenticationResponse(HttpServletRequest request)
        throws Throwable
    {
        AuthenticationResponse authResponse; 
        AuthenticationErrorResponse errorResponse;
        AuthenticationResult authResult; 
        AuthenticationSuccessResponse oidcResponse;
        HashMap<String, String> parameters = new HashMap<>();
        StateData stateData; 
        String nonce; 

        stateData = removeStateFromSession(request);

        if(stateData == null)
        {
            throw new Exception(FAILED_TO_VALIDATE_MESSAGE + "could not validate state");
        }

        for (String key : request.getParameterMap().keySet()) {
            parameters.put(key, request.getParameterMap().get(key)[0]);
        }

        authResponse = AuthenticationResponseParser.parse(new URI(request.getRequestURL().toString()), parameters);

        if(authResponse instanceof AuthenticationSuccessResponse)
        {
            oidcResponse = (AuthenticationSuccessResponse)authResponse;

            // Validate that the OIDC response matches the authorization code flow. 
            if (oidcResponse.getAccessToken() != null || oidcResponse.getAuthorizationCode() == null || oidcResponse.getIDToken() != null) 
            {
                throw new Exception(FAILED_TO_VALIDATE_MESSAGE + "unexpected set of artifacts received");
            }

            authResult = getAccessToken(request, oidcResponse.getAuthorizationCode());

            // Validate the nonce to prevent replay attacks (code maybe substitued to one with broader access).
            nonce = (String)JWTParser.parse(authResult.getIdToken()).getJWTClaimsSet().getClaim("nonce");

            if(StringUtils.isEmpty(nonce) || !nonce.equals(stateData.getNonce()))
            {
                throw new Exception(FAILED_TO_VALIDATE_MESSAGE + "could not validate nonce.");
            }

            request.getSession().setAttribute(PRINCIPAL_SESSION_NAME, authResult);

            storeRefreshToken(authResult);
        }
        else 
        {
            errorResponse = (AuthenticationErrorResponse)authResponse;

            throw new Exception(
                MessageFormat.format(
                    "Request for authorization code failed: {0} - {1}",
                    errorResponse.getErrorObject().getClass(),
                    errorResponse.getErrorObject().getDescription())); 
        }
    }

    /**
     * Sends a temporary redirect response to the client using the
     * specified redirect location URL and clears the buffer.
     * 
     * @param request The HTTP request that was made.
     * @param response The HTTP response sent back to the client.
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void sendRedirect(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        String nonce = UUID.randomUUID().toString();
        String state = UUID.randomUUID().toString();

        String redirectUri = MessageFormat.format(
            "{0}common/oauth2/authorize?response_type=code&scope=openid&response_mode=form_post&redirect_uri={1}&client_id={2}&state={3}&nonce={4}", 
            authority,
            URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"),
            clientId,
            state, 
            nonce);

        response.setStatus(302);

        if(request.getSession().getAttribute(STATE_NAME) == null)
        {
            request.getSession().setAttribute(STATE_NAME, new HashMap<String, StateData>());
        }

        ((Map<String, StateData>) request.getSession().getAttribute(STATE_NAME)).put(state, new StateData(nonce, new Date()));

        response.sendRedirect(redirectUri);
    }

    /**
     * Stores the refresh token in a secure repository.
     * 
     * @param authResult The result of the token acquisition operation. 
     */
    private void storeRefreshToken(AuthenticationResult authResult)
    {
        /**
         * The use of the Azure AD tenant identifier was leveraged below as an example. 
         * You can choose any name that you see fit but remember if you are storing multiple 
         * refresh tokens you will need a way to identify the correct one. 
         */
        vault.setSecret(authResult.getUserInfo().getTenantId(), authResult.getRefreshToken());
    }
}