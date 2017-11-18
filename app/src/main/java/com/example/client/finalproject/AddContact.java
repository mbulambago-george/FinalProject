package com.example.client.finalproject;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class AddContact extends MainActivity {
    TextInputLayout fname,lname,num,mail;
    Button btnAdd;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).builder();
        OnClickListener addClicListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

            }

            public void onCLick(View view) {

                ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
                int rawContact_NewId = operations.size();
                try

                {
                    operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());
                } catch (
                        Exception e)

                {
                    Log.e("Add", "could not be preformed");
                    return;
                }

                String firstName = String.valueOf(((TextInputLayout) findViewById(R.id.sname)).getEditText().getText());
                String lastName = String.valueOf(((TextInputLayout) findViewById(R.id.oname)).getEditText().getText());
                String displayName = firstName + " " + lastName;

                String phoneNumber = String.valueOf(((TextInputLayout) findViewById(R.id.pnumber)).getEditText().getText());
                String pemail = String.valueOf(((TextInputLayout) findViewById(R.id.email)).getEditText().getText());


                operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContact_NewId)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, firstName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, lastName)
                        .build());

                operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContact_NewId)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, pemail)
                        .build());

                operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContact_NewId)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build());

                ContentProviderResult[] results = new ContentProviderResult[0];
                try

                {
                    results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
                } catch (
                        RemoteException e)

                {
                    Log.e("getContentResolver()", e.getMessage());

                } catch (
                        OperationApplicationException e)

                {
                    Log.e("getContentResolver()", e.toString());
                } catch (
                        Exception e)

                {
                    Log.e("getContentResolver()", e.toString());
                } finally

                {
                }
                if (results != null && results[0] != null)

                {
                    Uri newContactUri = results[0].uri;
                    Log.d("AddContact", "URI added contact:" + newContactUri);
                } else

                {
                    Log.e("AddContact", "contact not added");
                }
            }
        };
        OnClickListener contactsClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Creating an intent to open Android's Contacts List
                Intent contacts = new Intent(Intent.ACTION_VIEW,ContactsContract.Contacts.CONTENT_URI);

                // Starting the activity
                startActivity(contacts);
            }
        };

        // Getting reference to "Add Contact" button
        Button btnAdd = (Button) findViewById(R.id.button);

        // Getting reference to "Contacts List" button
       // Button btnContacts = (Button) findViewById(R.id.btn_contacts);

        // Setting click listener for the "Add Contact" button
        btnAdd.setOnClickListener(addClicListener);

        // Setting click listener for the "List Contacts" button
        //btnContacts.setOnClickListener(contactsClickListener);
    }


}




//    public void btnAdd_Contact_onClick(View view){
//        //create intent contact add
//
//        Intent intent= new Intent(ContactsContract.Intents.Insert.ACTION);
//        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//
//
//        //get entered data
//        fname=findViewById(R.id.sname);
//        lname=findViewById(R.id.oname);
//        num=findViewById(R.id.pnumber);
//        mail=findViewById(R.id.email);
//
//
//
//        intent
//                .putExtra(ContactsContract.Intents.Insert.EMAIL,mail.getEditText().getText())
//                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                .putExtra(ContactsContract.Intents.Insert.PHONE,num.getEditText().getText())
//                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
//                .putExtra(ContactsContract.Intents.Insert.NAME,fname.getEditText().getText()+" "+lname.getEditText().getText())
//        ;
//        startActivity(intent);
//
//
//
//    }

