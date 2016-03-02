package com.xing.bshopping.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentController {

	private int containerId;
	private FragmentManager fm;
	private ArrayList<Fragment> fragements;

	private static FragmentController controller;

	public static FragmentController getInstance(FragmentActivity activity,
			int containerId) {
		if (controller == null) {
			controller = new FragmentController(activity, containerId);
		}
		return controller;
	}

	public static void onDestroy() {
		controller = null;
	}

	public FragmentController(FragmentActivity activity, int containerId) {

		this.containerId = containerId;
		fm = activity.getSupportFragmentManager();
		initFragment();
	}

	private void initFragment() {

		fragements = new ArrayList<Fragment>();
		fragements.add(new HomeFragment());
		fragements.add(new PoiFragment());
		fragements.add(new UserFragment());
		fragements.add(new MoreFragment());

		FragmentTransaction ft = fm.beginTransaction();
		for (Fragment fragment : fragements) {
			ft.add(containerId, fragment);
		}
		ft.commit();
	}

	public void showFragment(int position) {
		hideFragments();
		Fragment fragment = fragements.get(position);
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fragment);
		ft.commit();
	}

	public void hideFragments() {
		FragmentTransaction ft = fm.beginTransaction();
		for (Fragment fragment : fragements) {
			if (fragment != null) {
				ft.hide(fragment);
			}
		}
		ft.commit();
	}

	public Fragment getFragment(int position) {
		return fragements.get(position);
	}

}
