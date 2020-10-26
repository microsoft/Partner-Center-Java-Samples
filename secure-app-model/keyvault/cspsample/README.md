# Secure App Model

## Cloud Solution Provider (CSP) Sample

This sample demonstrates how a Cloud Solution Provider partner can utilize the refresh token obtained using the [Partner Consent](../partnerconsent/README.md) sample with the [Partner Center Java SDK](https://docs.microsoft.com/java/partnercenter/overview).

### Configuration

The following configurations in the [application.properties](src/main/resources/application.properties) file need to be modified:

* **keyvault.baseurl** - The base address for the instance of Azure Key Vault where the refresh token has been stored.
* **AZURE_CLIENT_ID** - The identifier for the Azure AD application that has been allowed access to the instance of Azure Key Vault.
* **AZURE_CLIENT_SECRET** - The application secret associated with the application configured to access the instance of Azure Key Vault.
* **AZURE_TENANT_ID** - The application tenant id associated with the application configured to access the instance of Azure Key Vault.
* **partnercenter.accountId** - The account identifier, also known as the Azure AD tenant identifier, for the partner.
* **partnercenter.clientId** - The application identifier for the Azure AD application configured for use with the Partner Center API.
* **partnercenter.clientSecret** - The application secret associated with the application configured to access the Partner Center API.

Please note that in production scenarios we recommend that you use certificate based authentication to access the instance of Azure Key Vault. The [confidential client flow](https://github.com/AzureAD/azure-activedirectory-library-for-dotnet/wiki/Confidential-client-applications-flows) has been used in the sample for simplicity.
