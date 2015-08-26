package com.dawgandpony.pd2skills.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.R;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class EditBuildActivity2 extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    @Override
    public void onInt(Bundle savedInstanceState) {
        // User Information
        this.userName.setText("Rudson Lima");
        this.userEmail.setText("rudsonlive@gmail.com");
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add("Mastermind");
        mHelpLiveo.add("Enforcer");
        mHelpLiveo.add("Ghost");
        mHelpLiveo.add("Tech");

        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("PerkDeck");
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("Primary");
        mHelpLiveo.add("Secondary");

        //with(this, Navigation.THEME_DARK). add theme dark
        //with(this, Navigation.THEME_LIGHT). add theme light

        with(this) // default theme is dark
                .startingPosition(2) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .footerItem("Home", R.drawable.ic_home_white_24dp)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_build2);
    }



    @Override
    public void onItemClick(int position) {
        FragmentManager mFragmentManager = getFragmentManager();

        switch (position){
            case 0:
                mFragmentManager.beginTransaction().replace(R.id.container, SkillTreeFragment.newInstance(position)).commit();
                break;
            case 1:
                mFragmentManager.beginTransaction().replace(R.id.container, SkillTreeFragment.newInstance(position)).commit();
                break;
            default:
                mFragmentManager.beginTransaction().replace(R.id.container, new BlankFragment()).commit();
                break;
        }


    }

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };
}
