package tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.justreadit.CHALLENGINGSENTENCE;
import com.example.justreadit.EASYSENTENCE;
import com.example.justreadit.INTERMEDIATESENTENCE;

public class TabsClassSentence extends FragmentPagerAdapter {

	public TabsClassSentence(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0: return new EASYSENTENCE();
		case 1: return new INTERMEDIATESENTENCE();
		case 2: return new CHALLENGINGSENTENCE();
		
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
