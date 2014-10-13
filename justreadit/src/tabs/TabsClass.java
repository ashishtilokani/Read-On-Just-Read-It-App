package tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.justreadit.CHALLENGING;
import com.example.justreadit.EASY;
import com.example.justreadit.INTERMEDIATE;

public class TabsClass extends FragmentPagerAdapter {

	public TabsClass(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0: return new EASY();
		case 1: return new INTERMEDIATE();
		case 2: return new CHALLENGING();
		
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
