// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.samples.helpers;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.microsoft.store.partnercenter.utils.StringHelper;

/**
 * Provides useful helpers that aid in writing to the console.
 */
public class ConsoleHelper
{
    /**
     * A lazy reference to the singleton console helper instance.
     */
    private static ConsoleHelper instance = null;

    private Scanner scanner = new Scanner( System.in );

    /**
     * Prevents a default instance of the {@link #ConsoleHelper} class from being created.
     */
    private ConsoleHelper()
    {
    }

    /**
     * Gets the single instance of the {@link #ConsoleHelper} .
     */
    public static ConsoleHelper getInstance()
    {
        if ( instance == null )
        {
            instance = new ConsoleHelper();
        }
        return instance;
    }

    /**
     * Writes a success message to the console.
     * 
     * @param message The message to write.
     * @param newLine Whether or not to write a new line after the message.
     */
    public void success( String message )
    {
        this.success( message, true );
    }

    public void success( String message, Boolean newLine )
    {
        System.out.print( "[success] " + message );
        if ( newLine )
        {
            System.out.println();
        }
    }

    /**
     * Writes a warning message to the console.
     * 
     * @param message The message to write.
     * @param newLine Whether or not to write a new line after the message.
     */
    public void warning( String message )
    {
        this.warning( message, true );
    }

    public void warning( String message, boolean newLine )
    {
        System.out.print( message );
        if ( newLine )
        {
            System.out.println();
        }
    }

    /**
     * Writes an error message to the console.
     * 
     * @param message The message to write.
     * @param newLine Whether or not to write a new line after the message.
     */
    public void error( String message )
    {
        this.error( message, true );
    }

    public void error( String message, boolean newLine )
    {
        System.out.print( "[Error] " + message );
        if ( newLine )
        {
            System.out.println();
        }
    }

    public void startProgress( String message )
    {
        System.out.println( message + "... " );
    }

    public void stopProgress()
    {
        System.out.println( "Done!" );
    }

    public Scanner getScanner()
    {
        return this.scanner;
    }

    /**
     * Reads a non empty string from the console.
     * 
     * @param promptMessage The prompt message to display.
     * @param validationMessage The error message to show in case of user input error.
     * @return The string input from the console.
     */
    public String readNonEmptyString( String promptMessage, String validationMessage )
    {
        String input;
        validationMessage = validationMessage != null ? validationMessage : "Enter a non-empty value";
        while ( true )
        {
            System.out.println( promptMessage );
            input = this.getScanner().nextLine();
            if ( StringHelper.isNullOrWhiteSpace( input ) )
            {
                this.error( validationMessage );
            }
            else
            {
                break;
            }
        }
        return input;
    }

    /**
     * Reads a string from the console (it can be empty as it is intended to be used with optional values).
     * 
     * @param promptMessage The prompt message to display.
     * @return The string input from the console.
     */
    public String readOptionalString(String promptMessage)
    {
        String input;
        System.out.println( promptMessage );
        input = this.getScanner().nextLine();
        return input;
    }

    /**
     * Writes an object and its properties recursively to the console. Properties are automatically indented.
     * 
     * @param object The object to print to the console.
     * @param title An optional title to display.
     * @param indent The starting indentation.
     */
    public void writeObject( Object object, String title )
    {
        ObjectMapper mapper =
            new ObjectMapper().configure( SerializationFeature.INDENT_OUTPUT, true ).registerModule( new JodaModule() );
        ;
        System.out.println();
        System.out.println( title );
        System.out.println( new String( new char[90] ).replace( '\0', '-' ) );
        try
        {
            System.out.println( mapper.writeValueAsString( object ) );
        }
        catch ( JsonProcessingException e )
        {
            throw new RuntimeException( e );
        }
    }

}
