// -----------------------------------------------------------------------
// <copyright file="GetAuditRecords.java" company="Microsoft">
//      Copyright (c) Microsoft Corporation. All rights reserved.
// </copyright>
// -----------------------------------------------------------------------

package com.microsoft.store.partnercenter.samples.auditrecords;

import java.util.Iterator;

import org.joda.time.DateTime;

import com.microsoft.store.partnercenter.IAggregatePartner;
import com.microsoft.store.partnercenter.enumerators.IResourceCollectionEnumerator;
import com.microsoft.store.partnercenter.models.SeekBasedResourceCollection;
import com.microsoft.store.partnercenter.models.auditing.AuditRecord;
import com.microsoft.store.partnercenter.models.query.QueryFactory;
import com.microsoft.store.partnercenter.models.utils.KeyValuePair;
import com.microsoft.store.partnercenter.samples.BasePartnerScenario;
import com.microsoft.store.partnercenter.samples.IScenarioContext;

public class GetAuditRecords extends BasePartnerScenario 
{
	public GetAuditRecords(IScenarioContext context) {
		super("Query for the partner's audit records", context);
	}

    /**
     * Executes the scenario.
     */
	@Override
	protected void runScenario() {
		IAggregatePartner partnerOperations = this.getContext().getUserPartnerOperations();
        this.getContext().getConsoleHelper().startProgress( "Querying audit records" );
        
        DateTime startDate = DateTime.now().minusDays(30);

        // query the audit records, get the first page if a page size was set, otherwise get all customers
        SeekBasedResourceCollection<AuditRecord> auditRecordsCollection = 
        		partnerOperations.getAuditRecords().query(startDate, null, QueryFactory.getInstance().buildIndexedQuery( 10 ) );
        this.getContext().getConsoleHelper().stopProgress();

        // create an AuditRecord enumerator which will aid us in traversing the customer pages
        IResourceCollectionEnumerator<SeekBasedResourceCollection<AuditRecord>> auditRecordssEnumerator =
            partnerOperations.getEnumerators().getAuditRecords().create( auditRecordsCollection );

        while ( auditRecordssEnumerator.hasValue() )
        {
            // print the current customer results page
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

                if (record.getCustomizedData()!=null && record.getCustomizedData().iterator()!=null)
                {
                	Iterator<KeyValuePair<String,String>> customizedDataIterator = record.getCustomizedData().iterator();
                    System.out.println("Customized Data:");
                    KeyValuePair<String,String> currentPair = null;
                    while ( customizedDataIterator.hasNext() )
                    {
                    	currentPair = customizedDataIterator.next();
                    	System.out.println("                "+currentPair.getKey() +" : "+ currentPair.getValue() );
                    }
                }

                System.out.println();
            }

            System.out.println();
            auditRecordssEnumerator.next();
        }
	}
}