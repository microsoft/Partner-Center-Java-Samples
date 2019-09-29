// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.auditrecords;

import org.joda.time.DateTime;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.auditing.AuditRecord;
import com.microsoft.store.partnercenter.models.auditing.AuditRecordSearchField;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.query.filters.FieldFilterOperation;
import com.microsoft.store.partnercenter.models.query.filters.SimpleFieldFilter;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class SearchAuditRecords extends BasePartnerScenario 
{
	public SearchAuditRecords(IScenarioContext context) {
		super("Filter by company name", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
	protected void runScenario() {
		IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        String companyName  =
                this.getContext().getConsoleHelper().readNonEmptyString( 
                    "Enter the company name to filter by",
					"The company name cannot be empty" );		
					
		this.getContext().getConsoleHelper().startProgress( "Searching Audit Records" );
        
        DateTime startDate = new DateTime().dayOfMonth().withMinimumValue();
        SimpleFieldFilter filter = new SimpleFieldFilter(AuditRecordSearchField.COMPANY_NAME.toString(), companyName, FieldFilterOperation.SUBSTRING );
        SeekBasedResourceCollection<AuditRecord> auditRecordsCollection = 
        		partnerOperations.getAuditRecords().query(startDate, null, QueryFactory.getInstance().buildSimpleQuery( filter ) );
        this.getContext().getConsoleHelper().stopProgress();

        IResourceCollectionEnumerator<SeekBasedResourceCollection<AuditRecord>> auditRecordssEnumerator =
                partnerOperations.getEnumerators().getAuditRecords().create( auditRecordsCollection );

        while ( auditRecordssEnumerator.hasValue() )
        {
        	System.out.println("Record count: " + auditRecordssEnumerator.getCurrent().getTotalCount());

        	for (AuditRecord record : auditRecordssEnumerator.getCurrent().getItems())
            {
            	System.out.println("Customer Id:    " + record.getCustomerId() );
            	System.out.println("Customer Name:  " + record.getCustomerName() );
            	System.out.println("Resource Type:  " + record.getResourceType() );
            	System.out.println("Date:           " + record.getOperationDate().toString("yyyy-MM-dd") );
            	System.out.println("Operation Type: " + record.getOperationType() );
            	System.out.println("Status:         " + record.getOperationStatus() );
            	System.out.println("User:           " + record.getUserPrincipalName() );
            	System.out.println("Application:    " + record.getApplicationId() );
            	System.out.println("New Value:      " + record.getResourceNewValue() );
                System.out.println();
            }

            auditRecordssEnumerator.next();
        }
	}
}