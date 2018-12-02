# Partner Consent

This is a sample web application developed in Java that utilizes the [authorization code flow](https://docs.microsoft.com/azure/active-directory/develop/v1-protocols-oauth-code) for authentication. Control panel vendors and Cloud Solution Provider partners can leverage this sample to obtain the required consent. Whenever a partner access the web application they will be redirected to Azure AD where they will authenticated using multi-factor authentication (MFA) and provide the required consent. After successfully authenticating and providing consent, the partner will be redirected back to the web application. Finally, the web application will request an access token for use with the Partner Center API. The refresh token returned as part of the token acquisition process will be stored in an instance of Azure Key Vault. This refresh token value is what control panel vendor and Cloud Solution Provider partners will use in their application to request an access to perform operations against the Partner Center API on the partner's behalf.

## Configurations

The required configurations for this sample are found in the [web.xml](src/main/webapp/WEB-INF/web.xml). Please update the following values with the corresponding values for your environment.

* **client_id** - This is the application identifier that represent your application.
* **client_secret** - This is the secret associated with the application that represents your application.
* **keyvault_url** - This the base address for the instance of Azure Key Vault you have deployed (e.g. <https://myvault.vault.azure.net/>).
* **keyvault_cient_id** - This is the application identifier that you have configured to access the instance of Azure Key Vault.
* **keyvault_client_secret** - THis the application secret associated with the application configured to access the instance of Azure Key Vault.