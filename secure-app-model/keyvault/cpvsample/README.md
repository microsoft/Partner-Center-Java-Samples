# Secure App Model

## Control Panel Vendor (CPV) Sample

This sample demonstrates how a Control Panel Vendor partner can utilize the refresh token obtained using the [Partner Consent](../partnerconsent/README.md) sample with the [Partner Center Java SDK](https://docs.microsoft.com/java/partnercenter/overview).

### Configuration

The following configurations in the [application.properties](src/main/resources/application.properties) file need to be modified:

* **keyvault.baseurl** - The base address for the instance of Azure Key Vault where the refresh token has been stored.
* **keyvault.clientId** - The identifier for the Azure AD application that has been allowed access to the instance of Azure Key Vault.
* **keyvault.clientSecret** - The application secret associated with the application configured to access the instance of Azure Key Vault.
* **keyvault.tenantId** - The application tenant id associated with the application configured to access the instance of Azure Key Vault.
* **partnercenter.accountId** - The account identifier, also known as the Azure AD tenant identifier, for the partner.
* **partnercenter.clientId** - The application identifier for the Azure AD application configured for use with the Partner Center API.
* **partnercenter.clientSecret** - The application secret associated with the application configured to access the Partner Center API.
* **partnercenter.displayName** - The display name for the Azure AD application. This will be used during the consent process, so it must what is in Azure AD.

Please note that in production scenarios we recommend that you use certificate based authentication to access the instance of Azure Key Vault. The [confidential client flow](https://github.com/AzureAD/azure-activedirectory-library-for-dotnet/wiki/Confidential-client-applications-flows) has been used in the sample for simplicity.
