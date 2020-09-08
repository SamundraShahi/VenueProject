package com.bignerdranch.android.musicpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class VenueDetailsFragment extends Fragment
{
    public static final String EXTRAS_VENUE_ID = "venue_id";

    private Venue mVenue;

    private EditText mVenueName;
    private EditText mVenueAddress;
    private EditText mOpeningTime;

    private Button mDoneButton;
    private Button mDiscardButton;
    private Button mSendButton; // invitation button declaration
    boolean newVenue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null)
        {
            UUID venueId = (UUID) extras.getSerializable(EXTRAS_VENUE_ID);
            mVenue = DbProvider.get(getActivity()).getVenue(venueId);

            newVenue = false;

        }
        else
        {
            mVenue = new Venue();
            newVenue = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_venue_details, container, false);

        mVenueName = (EditText) v.findViewById(R.id.venue_name);
        mVenueName.setText(mVenue.getName());

        mVenueAddress = (EditText) v.findViewById(R.id.venue_address);
        mVenueAddress.setText(mVenue.getAddress());

        mOpeningTime = (EditText) v.findViewById(R.id.venue_opening_time);
        mOpeningTime.setText(mVenue.getOpeningTime());

        mDiscardButton = (Button) v.findViewById(R.id.discard_button);
        mDiscardButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmDelete();
            }
        });

        mDoneButton = (Button) v.findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmDone();
            }
        });

        mSendButton = (Button) v.findViewById(R.id.send_button);

        // Listener for send invitation button
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if Venue Name and Address are Empty
                // Do not respond to button click when condition is satisfied
                if ((mVenueName.getText().toString().equals("")) ||
                        (mVenueAddress.getText().toString().equals(""))) {
                    Toast.makeText(getActivity(), "Invitation not Sent, Details Empty",
                            Toast.LENGTH_SHORT).show(); // show this message when empty fields
                }

                // Send Invitation when IF condition is false
                else {
                    Intent intentSendInvitation = new Intent(Intent.ACTION_SEND); // sends data to another activity/process
                    intentSendInvitation.setType("text/plain");
                    intentSendInvitation.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invitation)); // Subject line on email
                    intentSendInvitation.putExtra(Intent.EXTRA_TEXT,
                            getEmailInvitation()); // body part of email
                    intentSendInvitation = Intent.createChooser(intentSendInvitation, getString(R.string.chooser)); // provides with chooser option
                    startActivity(intentSendInvitation); // start this activity
                }

            }

        });

        return v;
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    // to send the email invitation
    private String getEmailInvitation() {

        String mSendInvitation = getString(R.string.
                email_invitation_body_text,
                mVenue.getName(),
                mVenue.getOpeningTime());

        return mSendInvitation;
    }

    public void errorMsg()
    {
        AlertDialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.warning)
                .setMessage(R.string.incomplete)
                .setPositiveButton(R.string.ok, null)
                .create();

        d.show();
    }

    public void confirmDone()
    {
        String name = mVenueName.getText().toString();
        String address = mVenueAddress.getText().toString();
        String openingTime = mOpeningTime.getText().toString();

        if (name.isEmpty() && address.isEmpty() )
        {
            DbProvider.get(getActivity()).deleteVenue(mVenue.getId());
            getActivity().finish();
        }
        else if (name.isEmpty() || address.isEmpty() )
        {
            errorMsg();
        }
        else
        {
            mVenue.setName(name);
            mVenue.setAddress(address);
            mVenue.setOpeningTime(openingTime);

            if (newVenue)
            {
                DbProvider.get(getActivity()).addVenue(mVenue);
            }
            else
            {
                DbProvider.get(getActivity()).updateVenue(mVenue);
            }

            getActivity().finish();
        }
    }

    public void confirmDelete()
    {
        AlertDialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirm)
                .setMessage(R.string.delete_venue)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        DbProvider.get(getActivity()).deleteVenue(mVenue.getId());
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create();

        d.show();
    }
}
