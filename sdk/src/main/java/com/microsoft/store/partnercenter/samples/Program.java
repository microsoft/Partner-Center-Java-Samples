// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.store.partnercenter.models.customers.CustomerSearchField;
import com.microsoft.store.partnercenter.samples.agreements.CreateCustomerAgreement;
import com.microsoft.store.partnercenter.samples.agreements.GetAgreementDetails;
import com.microsoft.store.partnercenter.samples.agreements.GetCustomerAgreements;
import com.microsoft.store.partnercenter.samples.analytics.GetCustomerLicensesDeploymentAnalytics;
import com.microsoft.store.partnercenter.samples.analytics.GetCustomerLicensesUsageAnalytics;
import com.microsoft.store.partnercenter.samples.analytics.GetPartnerLicensesDeploymentAnalytics;
import com.microsoft.store.partnercenter.samples.analytics.GetPartnerLicensesUsageAnalytics;
import com.microsoft.store.partnercenter.samples.auditrecords.GetAuditRecords;
import com.microsoft.store.partnercenter.samples.auditrecords.SearchAuditRecords;
import com.microsoft.store.partnercenter.samples.carts.CheckoutCart;
import com.microsoft.store.partnercenter.samples.carts.CreateCart;
import com.microsoft.store.partnercenter.samples.carts.UpdateCart;
import com.microsoft.store.partnercenter.samples.context.ScenarioContext;
import com.microsoft.store.partnercenter.samples.customerdirectoryroles.AddUserMemberToDirectoryRole;
import com.microsoft.store.partnercenter.samples.customerdirectoryroles.GetCustomerDirectoryRoleUserMembers;
import com.microsoft.store.partnercenter.samples.customerdirectoryroles.GetCustomerDirectoryRoles;
import com.microsoft.store.partnercenter.samples.customerdirectoryroles.RemoveCustomerUserMemberFromDirectoryRole;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerAvailabilities;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerAvailabilitiesByTargetSegment;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerAvailability;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerProduct;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerProducts;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerProductsByTargetSegment;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerSku;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerSkus;
import com.microsoft.store.partnercenter.samples.customerproducts.GetCustomerSkusByTargetSegment;
import com.microsoft.store.partnercenter.samples.customers.CheckDomainAvailability;
import com.microsoft.store.partnercenter.samples.customers.CreateCustomer;
import com.microsoft.store.partnercenter.samples.customers.DeleteCustomerFromTipAccount;
import com.microsoft.store.partnercenter.samples.customers.FilterCustomers;
import com.microsoft.store.partnercenter.samples.customers.GetCustomerDetails;
import com.microsoft.store.partnercenter.samples.customers.GetCustomerManagedServices;
import com.microsoft.store.partnercenter.samples.customers.GetCustomerQualification;
import com.microsoft.store.partnercenter.samples.customers.GetCustomerRelationshipRequest;
import com.microsoft.store.partnercenter.samples.customers.GetPagedCustomers;
import com.microsoft.store.partnercenter.samples.customers.UpdateCustomerBillingProfile;
import com.microsoft.store.partnercenter.samples.customers.UpdateCustomerQualification;
import com.microsoft.store.partnercenter.samples.customers.ValidateCustomerAddress;
import com.microsoft.store.partnercenter.samples.customerservicecosts.GetCustomerServiceCostsLineItems;
import com.microsoft.store.partnercenter.samples.customerservicecosts.GetCustomerServiceCostsSummary;
import com.microsoft.store.partnercenter.samples.customersubscribedskus.CustomerSubscribedSkus;
import com.microsoft.store.partnercenter.samples.customerusers.CreateCustomerUser;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignGroup1Licenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignGroup2Licenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignLicenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignedGroup1AndGroup2Licenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignedGroup1Licenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignedGroup2Licenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserAssignedLicenses;
import com.microsoft.store.partnercenter.samples.customerusers.CustomerUserRestore;
import com.microsoft.store.partnercenter.samples.customerusers.DeleteCustomerUser;
import com.microsoft.store.partnercenter.samples.customerusers.GetCustomerInactiveUsers;
import com.microsoft.store.partnercenter.samples.customerusers.GetCustomerUserDetails;
import com.microsoft.store.partnercenter.samples.customerusers.GetCustomerUserDirectoryRoles;
import com.microsoft.store.partnercenter.samples.customerusers.GetPagedCustomerUsers;
import com.microsoft.store.partnercenter.samples.customerusers.UpdateCustomerUser;
import com.microsoft.store.partnercenter.samples.devicesdeployment.CreateConfigurationPolicy;
import com.microsoft.store.partnercenter.samples.devicesdeployment.CreateDeviceBatch;
import com.microsoft.store.partnercenter.samples.devicesdeployment.CreateDevices;
import com.microsoft.store.partnercenter.samples.devicesdeployment.DeleteConfigurationPolicy;
import com.microsoft.store.partnercenter.samples.devicesdeployment.DeleteDevice;
import com.microsoft.store.partnercenter.samples.devicesdeployment.GetAllConfigurationPolicies;
import com.microsoft.store.partnercenter.samples.devicesdeployment.GetBatchUploadStatus;
import com.microsoft.store.partnercenter.samples.devicesdeployment.GetDevices;
import com.microsoft.store.partnercenter.samples.devicesdeployment.GetDevicesBatches;
import com.microsoft.store.partnercenter.samples.devicesdeployment.UpdateConfigurationPolicy;
import com.microsoft.store.partnercenter.samples.devicesdeployment.UpdateDevicesPolicy;
import com.microsoft.store.partnercenter.samples.entitlements.GetEntitlements;
import com.microsoft.store.partnercenter.samples.indirectmodel.CreateCustomerForIndirectReseller;
import com.microsoft.store.partnercenter.samples.indirectmodel.GetCustomersOfIndirectReseller;
import com.microsoft.store.partnercenter.samples.indirectmodel.GetIndirectResellers;
import com.microsoft.store.partnercenter.samples.indirectmodel.GetIndirectResellersOfCustomer;
import com.microsoft.store.partnercenter.samples.indirectmodel.GetSubscriptionsByMpnId;
import com.microsoft.store.partnercenter.samples.indirectmodel.PlaceOrderForCustomer;
import com.microsoft.store.partnercenter.samples.indirectmodel.VerifyPartnerMpnId;
import com.microsoft.store.partnercenter.samples.invoice.GetAccountBalance;
import com.microsoft.store.partnercenter.samples.invoice.GetInvoice;
import com.microsoft.store.partnercenter.samples.invoice.GetInvoiceLineItems;
import com.microsoft.store.partnercenter.samples.invoice.GetInvoiceStatement;
import com.microsoft.store.partnercenter.samples.invoice.GetInvoiceSummary;
import com.microsoft.store.partnercenter.samples.invoice.GetPagedInvoices;
import com.microsoft.store.partnercenter.samples.invoice.GetUsageLineItemsForOpenPeriod;
import com.microsoft.store.partnercenter.samples.offers.GetCustomerOfferCategories;
import com.microsoft.store.partnercenter.samples.offers.GetCustomerOffers;
import com.microsoft.store.partnercenter.samples.offers.GetOffer;
import com.microsoft.store.partnercenter.samples.offers.GetOfferCategories;
import com.microsoft.store.partnercenter.samples.offers.GetOffers;
import com.microsoft.store.partnercenter.samples.offers.GetPagedOffers;
import com.microsoft.store.partnercenter.samples.orders.CreateAzureReservationOrder;
import com.microsoft.store.partnercenter.samples.orders.CreateOrder;
import com.microsoft.store.partnercenter.samples.orders.GetOrderDetails;
import com.microsoft.store.partnercenter.samples.orders.GetOrders;
import com.microsoft.store.partnercenter.samples.products.CheckInventory;
import com.microsoft.store.partnercenter.samples.products.GetAvailabilities;
import com.microsoft.store.partnercenter.samples.products.GetAvailabilitiesByTargetSegment;
import com.microsoft.store.partnercenter.samples.products.GetAvailability;
import com.microsoft.store.partnercenter.samples.products.GetProduct;
import com.microsoft.store.partnercenter.samples.products.GetProducts;
import com.microsoft.store.partnercenter.samples.products.GetProductsByTargetSegment;
import com.microsoft.store.partnercenter.samples.products.GetSku;
import com.microsoft.store.partnercenter.samples.products.GetSkus;
import com.microsoft.store.partnercenter.samples.products.GetSkusByTargetSegment;
import com.microsoft.store.partnercenter.samples.profiles.GetBillingProfile;
import com.microsoft.store.partnercenter.samples.profiles.GetLegalBusinessProfile;
import com.microsoft.store.partnercenter.samples.profiles.GetMpnProfile;
import com.microsoft.store.partnercenter.samples.profiles.GetOrganizationProfile;
import com.microsoft.store.partnercenter.samples.profiles.GetSupportProfile;
import com.microsoft.store.partnercenter.samples.profiles.UpdateBillingProfile;
import com.microsoft.store.partnercenter.samples.profiles.UpdateLegalBusinessProfile;
import com.microsoft.store.partnercenter.samples.profiles.UpdateOrganizationProfile;
import com.microsoft.store.partnercenter.samples.profiles.UpdateSupportProfile;
import com.microsoft.store.partnercenter.samples.ratecards.GetAzureRateCard;
import com.microsoft.store.partnercenter.samples.ratecards.GetAzureSharedRateCard;
import com.microsoft.store.partnercenter.samples.ratedusage.GetCustomerSubscriptionsUsage;
import com.microsoft.store.partnercenter.samples.ratedusage.GetCustomerUsageSummary;
import com.microsoft.store.partnercenter.samples.ratedusage.GetSubscriptionUsageRecords;
import com.microsoft.store.partnercenter.samples.ratedusage.GetSubscriptionUsageSummary;
import com.microsoft.store.partnercenter.samples.serviceincidents.GetServiceIncidents;
import com.microsoft.store.partnercenter.samples.servicerequests.CreatePartnerServiceRequest;
import com.microsoft.store.partnercenter.samples.servicerequests.GetAllCustomerServiceRequests;
import com.microsoft.store.partnercenter.samples.servicerequests.GetPagedPartnerServiceRequests;
import com.microsoft.store.partnercenter.samples.servicerequests.GetPartnerServiceRequestDetails;
import com.microsoft.store.partnercenter.samples.servicerequests.GetServiceRequestSupportTopics;
import com.microsoft.store.partnercenter.samples.servicerequests.UpdatePartnerServiceRequest;
import com.microsoft.store.partnercenter.samples.subscriptions.AddSubscriptionAddOn;
import com.microsoft.store.partnercenter.samples.subscriptions.GetSubscription;
import com.microsoft.store.partnercenter.samples.subscriptions.GetSubscriptions;
import com.microsoft.store.partnercenter.samples.subscriptions.GetSubscriptionsByOrder;
import com.microsoft.store.partnercenter.samples.subscriptions.RegisterSubscription;
import com.microsoft.store.partnercenter.samples.subscriptions.UpdateSubscription;
import com.microsoft.store.partnercenter.samples.subscriptions.UpgradeSubscription;
import com.microsoft.store.partnercenter.samples.utilization.GetAzureSubscriptionUtilization;
import com.microsoft.store.partnercenter.samples.validations.AddressValidation;

/**
 * The main program class for the partner center .NET SDK samples.
 */
public class Program
{
    /**
     * The main entry function.
     * 
     * @param args Program arguments.
     */
    public static void main(String[] args)
    {
        ScenarioContext context = new ScenarioContext();
        List<IPartnerScenario> mainScenarios = new ArrayList<IPartnerScenario>();

        mainScenarios.add(Program.getCustomerScenarios(context));
        mainScenarios.add(Program.getAgreementsScenarios(context));
        mainScenarios.add(Program.getOfferScenarios(context));
        mainScenarios.add(Program.getProductScenarios(context));
        mainScenarios.add(Program.getCustomerProductsScenarios(context));
        mainScenarios.add(Program.getOrderScenarios(context));
        mainScenarios.add(Program.getSubscriptionScenarios(context));
        mainScenarios.add(Program.getRatedUsageScenarios(context));
        mainScenarios.add(Program.getServiceRequestScenarios(context));
        mainScenarios.add(Program.getInvoiceScenarios(context));
        mainScenarios.add(Program.getProfileScenarios(context));
        mainScenarios.add(Program.getCustomerUserScenarios(context));
        mainScenarios.add(Program.getCustomerSubscribedSkusScenarios(context));
        mainScenarios.add(Program.getCustomerDirectoryRoleScenarios(context));
        mainScenarios.add(Program.getAuditRecordScenarios(context));
        mainScenarios.add(Program.getRateCardScenarios(context));
        mainScenarios.add(Program.getIndirectModelScenarios(context));
        mainScenarios.add(Program.getServiceIncidentScenarios(context));
        mainScenarios.add(Program.getUtilizationScenarios(context));
        mainScenarios.add(Program.getPartnerAnalyticsScenarios(context));
        mainScenarios.add(Program.getCustomerServiceCostsScenarios(context));
        mainScenarios.add(Program.getAddressValidationsScenarios(context));
        mainScenarios.add(Program.getDevicesScenarios(context));
        mainScenarios.add(Program.getCartScenarios(context));
        mainScenarios.add(Program.getEntitlementScenarios(context));

        // run the main scenario
        new AggregatePartnerScenario("Partner Center Java SDK samples", mainScenarios, context).run();
    }

    /**
     * Gets the customer scenarios.
     * 
     * @param context A scenario context.
     * @return The customer scenarios.
     */
    private static IPartnerScenario getCustomerScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerFilteringScenarios = new ArrayList<IPartnerScenario>();
        customerFilteringScenarios.add(new FilterCustomers(
            "Filter by company name", CustomerSearchField.COMPANY_NAME,
            context));
        customerFilteringScenarios.add(
            new FilterCustomers("Filter by domain name", CustomerSearchField.DOMAIN_NAME,
            context));
        
        List<IPartnerScenario> customerScenarios = new ArrayList<IPartnerScenario>();

        customerScenarios.add(new CreateCustomer(context));
        customerScenarios.add(new CheckDomainAvailability(context));
        customerScenarios.add(new GetPagedCustomers(
            context,
            Integer.parseInt(context.getConfiguration().getScenarioSettings().get("CustomerPageSize"))));
        customerScenarios.add(new AggregatePartnerScenario(
            "Customer filtering", customerFilteringScenarios,
            context));
        customerScenarios.add(new GetCustomerDetails(context));
        customerScenarios.add(new GetCustomerQualification(context));
        customerScenarios.add(new UpdateCustomerQualification(context));
        customerScenarios.add(new DeleteCustomerFromTipAccount(context));
        customerScenarios.add(new GetCustomerManagedServices(context));
        customerScenarios.add(new GetCustomerRelationshipRequest(context));
        customerScenarios.add(new UpdateCustomerBillingProfile(context));
        customerScenarios.add(new ValidateCustomerAddress(context));

        return new AggregatePartnerScenario("Customer samples", customerScenarios, context);
    }

    /**
     * Gets the offer scenarios.
     *
     * @param context A scenario context.
     * @return The offer scenarios.
     */
    private static IPartnerScenario getOfferScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> offerScenarios = new ArrayList<IPartnerScenario>();

        offerScenarios.add(new GetOffer(context));
        offerScenarios.add(new GetOfferCategories(context));
        offerScenarios.add(new GetOffers(context));
        offerScenarios.add(new GetPagedOffers(context));
        offerScenarios.add(new GetCustomerOffers(context));
        offerScenarios.add(new GetCustomerOfferCategories(context));

        return new AggregatePartnerScenario("Offer samples", offerScenarios, context);
    }

    /**
     * Gets the order scenarios.
     *
     * @param context A scenario context.
     * @return The order scenarios.
     */
    private static IPartnerScenario getOrderScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> orderScenarios = new ArrayList<IPartnerScenario>();

        orderScenarios.add(new CreateOrder(context));
        orderScenarios.add(new GetOrderDetails(context));
        orderScenarios.add(new GetOrders(context));
        orderScenarios.add(new CreateAzureReservationOrder(context));

        return new AggregatePartnerScenario("Order samples", orderScenarios, context);
    }

    /**
     * Gets the subscription scenarios.
     *
     * @param context A scenario context.
     * @return The subscription scenarios.
     */
    private static IPartnerScenario getSubscriptionScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> subscriptionScenarios = new ArrayList<IPartnerScenario>();

        subscriptionScenarios.add(new GetSubscription(context));
        subscriptionScenarios.add(new GetSubscriptions(context));
        subscriptionScenarios.add(new GetSubscriptionsByOrder(context));
        subscriptionScenarios.add(new UpdateSubscription(context));
        subscriptionScenarios.add(new UpgradeSubscription(context));
        subscriptionScenarios.add(new AddSubscriptionAddOn(context));
        subscriptionScenarios.add(new RegisterSubscription(context));

        return new AggregatePartnerScenario("Subscription samples", subscriptionScenarios, context);
    }

    /**
     * Gets the subscription scenarios.
     *
     * @param context A scenario context.
     * @return The subscription scenarios.
     */
    private static IPartnerScenario getRatedUsageScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> ratedUsageScenarios = new ArrayList<IPartnerScenario>();

        ratedUsageScenarios.add(new GetCustomerUsageSummary(context));
        ratedUsageScenarios.add(new GetCustomerSubscriptionsUsage(context));
        ratedUsageScenarios.add(new GetSubscriptionUsageRecords(context));
        ratedUsageScenarios.add(new GetSubscriptionUsageSummary(context));

        return new AggregatePartnerScenario("Rated Usage samples", ratedUsageScenarios, context);
    }

    /**
     * Gets the service request scenarios.
     *
     * @param context A scenario context.
     * @return The service request scenarios.
     */
    private static IPartnerScenario getServiceRequestScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> serviceRequestScenarios = new ArrayList<IPartnerScenario>();

        serviceRequestScenarios.add(new CreatePartnerServiceRequest(context));
        serviceRequestScenarios.add(new GetAllCustomerServiceRequests(context));
        serviceRequestScenarios.add(new GetPagedPartnerServiceRequests(context, Integer.parseInt(context.getConfiguration().getScenarioSettings().get("ServiceRequestPageSize"))));
        serviceRequestScenarios.add(new GetPartnerServiceRequestDetails(context));
        serviceRequestScenarios.add(new GetServiceRequestSupportTopics(context));
        serviceRequestScenarios.add(new UpdatePartnerServiceRequest(context));

        return new AggregatePartnerScenario("Service request samples", serviceRequestScenarios, context);
    }

    /**
     * Gets the invoice scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getInvoiceScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> invoiceScenarios = new ArrayList<IPartnerScenario>();

        invoiceScenarios.add(new GetInvoiceSummary(context));
        invoiceScenarios.add(new GetInvoice(context));
        invoiceScenarios.add(new GetInvoiceLineItems(context, Integer.parseInt(context.getConfiguration().getScenarioSettings().get("InvoicePageSize"))));
        invoiceScenarios.add(new GetPagedInvoices(context, Integer.parseInt(context.getConfiguration().getScenarioSettings().get("InvoicePageSize"))));
        invoiceScenarios.add(new GetAccountBalance(context));
        invoiceScenarios.add (new GetInvoiceStatement(context));
        invoiceScenarios.add(new GetUsageLineItemsForOpenPeriod(context));

        return new AggregatePartnerScenario("Invoice samples", invoiceScenarios, context);
    }

    /**
     * Gets the Partner Profile scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getProfileScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> profileScenarios = new ArrayList<IPartnerScenario>();

        profileScenarios.add(new GetBillingProfile(context));
        profileScenarios.add(new GetLegalBusinessProfile(context));
        profileScenarios.add(new GetOrganizationProfile(context));
        profileScenarios.add(new GetMpnProfile(context));
        profileScenarios.add(new GetSupportProfile(context));
        profileScenarios.add(new UpdateBillingProfile(context));
        profileScenarios.add(new UpdateLegalBusinessProfile(context));
        profileScenarios.add(new UpdateOrganizationProfile(context));
        profileScenarios.add(new UpdateSupportProfile(context));

        return new AggregatePartnerScenario("Partner Profile samples", profileScenarios, context);
    }

    /**
     * Gets the Customer User scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getCustomerUserScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerUserScenarios = new ArrayList<IPartnerScenario>();

        customerUserScenarios.add(new CreateCustomerUser(context));
        customerUserScenarios.add(new DeleteCustomerUser(context));
        customerUserScenarios.add(new GetCustomerUserDetails(context));
        customerUserScenarios.add(new UpdateCustomerUser(context));
        customerUserScenarios.add(new GetPagedCustomerUsers(context));
        customerUserScenarios.add(new GetCustomerUserDirectoryRoles(context));
        customerUserScenarios.add(new CustomerUserAssignedLicenses(context));
        customerUserScenarios.add(new CustomerUserAssignedGroup1AndGroup2Licenses(context));
        customerUserScenarios.add(new CustomerUserAssignedGroup1Licenses(context));
        customerUserScenarios.add(new CustomerUserAssignedGroup2Licenses(context));
        customerUserScenarios.add(new CustomerUserAssignLicenses(context));
        customerUserScenarios.add(new CustomerUserAssignGroup1Licenses(context));
        customerUserScenarios.add(new CustomerUserAssignGroup2Licenses(context));
        customerUserScenarios.add(new GetCustomerInactiveUsers(context));
        customerUserScenarios.add(new CustomerUserRestore(context));

        return new AggregatePartnerScenario("Customer User samples", customerUserScenarios, context);
    }
    
    /**
     * Gets the customer subscribed skus scenarios.
     *
     * @param context A scenario context.
     * @return The Customer subscribed skus scenarios.
     */
    private static IPartnerScenario getCustomerSubscribedSkusScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerSubscribedSkusScenarios = new ArrayList<IPartnerScenario>();

        customerSubscribedSkusScenarios.add(new CustomerSubscribedSkus(context));
        
        return new AggregatePartnerScenario("Customer Subscribed Skus samples", customerSubscribedSkusScenarios, context);
    }

    /**
     * Gets the customer directory role scenarios.
     *
     * @param context A scenario context.
     * @return The directory role scenarios.
     */
    private static IPartnerScenario getCustomerDirectoryRoleScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerDirectoryRoleScenarios = new ArrayList<IPartnerScenario>();

        customerDirectoryRoleScenarios.add(new GetCustomerDirectoryRoles(context));
        customerDirectoryRoleScenarios.add(new AddUserMemberToDirectoryRole(context));
        customerDirectoryRoleScenarios.add(new GetCustomerDirectoryRoleUserMembers(context));
        customerDirectoryRoleScenarios.add(new RemoveCustomerUserMemberFromDirectoryRole(context));

        return new AggregatePartnerScenario("Customer Directory Role samples", customerDirectoryRoleScenarios, context);
    }

    /**
     * Gets the Audit record scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getAuditRecordScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> auditRecordScenarios = new ArrayList<IPartnerScenario>();

        auditRecordScenarios.add(new GetAuditRecords(context));
        auditRecordScenarios.add(new SearchAuditRecords(context));

        return new AggregatePartnerScenario("Auditing samples", auditRecordScenarios, context);
    }

    /**
     * Gets the rate card scenarios.
     *
     * @param context A scenario context.
     * @return The rate card scenarios.
     */
    private static IPartnerScenario getRateCardScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> rateCardScenarios = new ArrayList<IPartnerScenario>();

        rateCardScenarios.add(new GetAzureRateCard(context));
        rateCardScenarios.add(new GetAzureSharedRateCard(context));

        return new AggregatePartnerScenario("Rate Card samples", rateCardScenarios, context);
    }

    /**
     *  Gets the indirect model scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getIndirectModelScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> indirectModelScenarios = new ArrayList<IPartnerScenario>();

        indirectModelScenarios.add(new GetIndirectResellers(context));
        indirectModelScenarios.add(new CreateCustomerForIndirectReseller(context));
        indirectModelScenarios.add(new GetIndirectResellersOfCustomer(context));
        indirectModelScenarios.add(new PlaceOrderForCustomer(context));
        indirectModelScenarios.add(new GetCustomersOfIndirectReseller(context));
        indirectModelScenarios.add(new VerifyPartnerMpnId(context));
        indirectModelScenarios.add(new GetSubscriptionsByMpnId(context));

        return new AggregatePartnerScenario("Indirect model samples", indirectModelScenarios, context);
    }

    /**
     * Gets the Customer User scenarios.
     *
     * @param context A scenario context.
     * @return The invoice scenarios.
     */
    private static IPartnerScenario getServiceIncidentScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> serviceIncidentScenarios = new ArrayList<IPartnerScenario>();

        serviceIncidentScenarios.add(new GetServiceIncidents(context));
        
        return new AggregatePartnerScenario("Service Incident samples", serviceIncidentScenarios, context);   
    }

    /**
     * Gets the partner analytics scenarios..
     *
     * @param context A scenario context.
     * @return The Partner Analytics scenarios
     */
    private static IPartnerScenario getPartnerAnalyticsScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> partnerAnalyticsScenarios = new ArrayList<IPartnerScenario>();

        partnerAnalyticsScenarios.add(new GetPartnerLicensesDeploymentAnalytics(context));
        partnerAnalyticsScenarios.add(new GetPartnerLicensesUsageAnalytics(context));
        partnerAnalyticsScenarios.add(new GetCustomerLicensesDeploymentAnalytics(context));
        partnerAnalyticsScenarios.add(new GetCustomerLicensesUsageAnalytics(context));

        return new AggregatePartnerScenario("Partner Analytics samples", partnerAnalyticsScenarios , context);   
    }

    /**
     * Gets the partner analytics scenarios.
     *
     * @param context A scenario context.
     * @return The Partner Analytics scenarios
     */
    private static IPartnerScenario getCustomerProductsScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerProductsScenarios  = new ArrayList<IPartnerScenario>();
        
        customerProductsScenarios.add(new GetCustomerProducts(context));
        customerProductsScenarios.add(new GetCustomerProductsByTargetSegment(context));
        customerProductsScenarios.add(new GetCustomerProduct(context));
        customerProductsScenarios.add(new GetCustomerSkus(context));
        customerProductsScenarios.add(new GetCustomerSkusByTargetSegment(context));
        customerProductsScenarios.add(new GetCustomerSku(context));
        customerProductsScenarios.add(new GetCustomerAvailabilities(context));
        customerProductsScenarios.add(new GetCustomerAvailabilitiesByTargetSegment(context));
        customerProductsScenarios.add(new GetCustomerAvailability(context));

        return new AggregatePartnerScenario("Products for customers samples", customerProductsScenarios, context);   
    }

    /**
     * Gets the cart scenarios of create, update and checkout
     *
     * @param context A scenario context.
     * @return The cart scenarios.
     */
    private static IPartnerScenario getCartScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> cartScenarios   = new ArrayList<IPartnerScenario>();
     
        cartScenarios.add(new CreateCart(context));
        cartScenarios.add(new UpdateCart(context));
        cartScenarios.add(new CheckoutCart(context));

        return new AggregatePartnerScenario("Cart Scenarios", cartScenarios, context);   
    }

    /**
     * Gets the customer service costs scenarios.
     *
     * @param context A scenario context.
     * @return The customer service costs scenarios.
     */
    private static IPartnerScenario getCustomerServiceCostsScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> customerServiceCostsScenarios   = new ArrayList<IPartnerScenario>();

        customerServiceCostsScenarios.add(new GetCustomerServiceCostsSummary(context));
        customerServiceCostsScenarios.add(new GetCustomerServiceCostsLineItems(context));

        return new AggregatePartnerScenario("Customer service costs samples", customerServiceCostsScenarios, context);   
    }

    /**
     * Gets the Devices scenarios.
     *
     * @param context A scenario context.
     * @return The Devices scenarios.
     */
    private static IPartnerScenario getDevicesScenarios(ScenarioContext context)
    {
        List<IPartnerScenario> devicesScenarios   = new ArrayList<IPartnerScenario>();

        devicesScenarios.add(new CreateConfigurationPolicy(context));
        devicesScenarios.add(new GetAllConfigurationPolicies(context));
        devicesScenarios.add(new UpdateConfigurationPolicy(context));
        devicesScenarios.add(new DeleteConfigurationPolicy(context));
        devicesScenarios.add(new CreateDeviceBatch(context));
        devicesScenarios.add(new GetDevicesBatches(context));
        devicesScenarios.add(new CreateDevices(context));
        devicesScenarios.add(new GetDevices(context));
        devicesScenarios.add(new UpdateDevicesPolicy(context));
        devicesScenarios.add(new DeleteDevice(context));
        devicesScenarios.add(new GetBatchUploadStatus(context));

        return new AggregatePartnerScenario("Devices", devicesScenarios, context);   
    }

    /**
     * Gets the Entitlement scenarios.
     *
     * @param context A scenario context.
     * @return The Entitlement scenarios.
     */
    private static IPartnerScenario getEntitlementScenarios(ScenarioContext context)
    {
        List<IPartnerScenario> entitlementScenarios   = new ArrayList<IPartnerScenario>();

        entitlementScenarios.add(new GetEntitlements(context));

        return new AggregatePartnerScenario("Entitlements", entitlementScenarios, context);   
    }

    /**
     * Gets the Utilization scenarios.
     *
     * @param context A scenario context.
     * @return The Utilization scenarios.
     */
    private static IPartnerScenario getUtilizationScenarios(ScenarioContext context)
    {
        List<IPartnerScenario> utilizationScnearios  = new ArrayList<IPartnerScenario>();

        utilizationScnearios.add(new GetAzureSubscriptionUtilization(context));

        return new AggregatePartnerScenario("Utilization samples", utilizationScnearios, context);   
    }

    /**
     * Gets the product scenarios.
     *
     * @param context A scenario context.
     * @return The product scenarios
     */
    private static IPartnerScenario getProductScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> productScenarios  = new ArrayList<IPartnerScenario>();

        productScenarios.add(new CheckInventory(context));
        productScenarios.add(new GetAvailabilities(context));
        productScenarios.add(new GetAvailabilitiesByTargetSegment(context));
        productScenarios.add(new GetAvailability(context));
        productScenarios.add(new GetProduct(context));
        productScenarios.add(new GetProducts(context));
        productScenarios.add(new GetProductsByTargetSegment(context));
        productScenarios.add(new GetSku(context));
        productScenarios.add(new GetSkusByTargetSegment(context));
        productScenarios.add(new GetSkus(context));

        return new AggregatePartnerScenario("Product samples", productScenarios, context);   
    }

    /**
     * Gets the address validation scenarios.
     *
     * @param context A scenario context.
     * @return The address validation scenarios.
     */
    private static IPartnerScenario getAddressValidationsScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> addressValidationScenarios  = new ArrayList<IPartnerScenario>();

        addressValidationScenarios.add(new AddressValidation(context));

        return new AggregatePartnerScenario("Address validation samples", addressValidationScenarios, context);   
    }

    /**
     *  Gets the agreements scenarios.
     *
     * @param context A scenario context.
     * @return The agreements scenarios.
     */
    private static IPartnerScenario getAgreementsScenarios(IScenarioContext context)
    {
        List<IPartnerScenario> agreementsScenario  = new ArrayList<IPartnerScenario>();

        agreementsScenario.add(new GetAgreementDetails(context));
        agreementsScenario.add(new GetCustomerAgreements(context));
        agreementsScenario.add(new CreateCustomerAgreement(context));

        return new AggregatePartnerScenario("Agreements", agreementsScenario, context);   
    }
}