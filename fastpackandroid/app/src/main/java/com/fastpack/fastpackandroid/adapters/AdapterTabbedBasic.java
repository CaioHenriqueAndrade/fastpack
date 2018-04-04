package com.fastpack.fastpackandroid.adapters;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fastpack.fastpackandroid.interfaces.Interfaces;

/**
 * Created by root on 02/04/18.
 */


public abstract class AdapterTabbedBasic extends FragmentPagerAdapter {


    private static Interfaces.AdapterTabbed adapterTabbed;

    public AdapterTabbedBasic(Interfaces.AdapterTabbed adapterTabbed) {
        super(adapterTabbed.getSupportV4FragmentManager());
        AdapterTabbedBasic.adapterTabbed = adapterTabbed;
    }

    public void init() {
        adapterTabbed.getViewPager().setAdapter(this);
        initTabsAndSetViewPager();
        //initAdaptadorAutomatico();
    }

    private void initTabsAndSetViewPager() {
        adapterTabbed.getTabLayout().setupWithViewPager(adapterTabbed.getViewPager());
    }

    /*
        private void initAdaptadorAutomatico() {
            TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(  adapterTabbed.getTabLayout() , adapterTabbed.getViewPager() );
            mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        }
    */
    protected FragmentManager getFragmentManager() {
        return adapterTabbed.getSupportV4FragmentManager();
    }

    String getString(int idRes) {
        return getResources().getString(idRes);
    }

    Resources getResources() {
        return adapterTabbed.getResources();
    }

    Interfaces.AdapterTabbed getAdapterTabbed() {
        return adapterTabbed;
    }

    public static void setMethods(Interfaces.AdapterTabbed adapterTabbed) {
        AdapterTabbedBasic.adapterTabbed = adapterTabbed;
    }

    public static Interfaces.AdapterTabbed getMethods() {
        return adapterTabbed;
    }
}
