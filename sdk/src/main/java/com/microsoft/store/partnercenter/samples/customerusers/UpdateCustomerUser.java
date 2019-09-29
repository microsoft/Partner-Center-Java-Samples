// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.customerusers;

import java.util.UUID;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.models.customers.Customer;
import com.microsoft.store.partnercenter.models.users.CustomerUser;
import com.microsoft.store.partnercenter.models.users.PasswordProfile;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

/**
 * A scenario that updates a customer user.
 */
public class UpdateCustomerUser extends BasePartnerScenario {

        public UpdateCustomerUser(IScenarioContext context) {
                super("Updates a Customer User", context);
        }

        /**
         * executes the update customer user scenario by creating a customer and then
         * updating it.
         */
        @Override
        protected void runScenario() {
                String customerId = this.obtainCustomerId();
                IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
                this.getContext().getConsoleHelper()
                                .startProgress("Getting the customer to run the update user scenario for");
                Customer selectedCustomer = partnerOperations.getCustomers().byId(customerId).get();

                // read the selected customer id from the application state.
                String selectedCustomerId = selectedCustomer.getId();
                PasswordProfile passwordProfile = new PasswordProfile();
                passwordProfile.setForceChangePassword(true);
                passwordProfile.setPassword("Password!1");

                CustomerUser userToCreate = new CustomerUser();
                userToCreate.setPasswordProfile(passwordProfile);
                userToCreate.setDisplayName("TestDisplayName");
                userToCreate.setFirstName("TestFirstName");
                userToCreate.setLastName("TestLastName");
                userToCreate.setUsageLocation("US");
                userToCreate.setUserPrincipalName(UUID.randomUUID().toString().toUpperCase() + "@"
                                + selectedCustomer.getCompanyProfile().getDomain());

                this.getContext().getConsoleHelper().writeObject(userToCreate, "New user Information");
                this.getContext().getConsoleHelper().startProgress("Creating user");
                CustomerUser createdCustomerUser = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers()
                                .create(userToCreate);
                this.getContext().getConsoleHelper().writeObject(createdCustomerUser, "Created user Information");

                // Specifying update attributes.
                CustomerUser customerUserToUpdate = new CustomerUser();
                passwordProfile.setPassword("testPw@!122B");
                customerUserToUpdate.setPasswordProfile(passwordProfile);
                customerUserToUpdate.setDisplayName("Michael Phelps");
                customerUserToUpdate.setFirstName("Michael");
                customerUserToUpdate.setLastName("Phelps");
                customerUserToUpdate.setUsageLocation("US");
                customerUserToUpdate.setUserPrincipalName(UUID.randomUUID().toString().toUpperCase() + "@"
                                + selectedCustomer.getCompanyProfile().getDomain());

                // Now update the newly created customer user
                this.getContext().getConsoleHelper().startProgress("Created user. Updating the user");
                CustomerUser updatedCustomerUser = partnerOperations.getCustomers().byId(selectedCustomerId).getUsers()
                                .byId(createdCustomerUser.getId()).patch(customerUserToUpdate);
                this.getContext().getConsoleHelper().stopProgress();
                this.getContext().getConsoleHelper().success("Success!");
                this.getContext().getConsoleHelper().writeObject(updatedCustomerUser, "Updated user Information");
        }
}
