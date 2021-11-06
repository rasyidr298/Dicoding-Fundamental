package id.rrdevfundamental.data.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.rrdevfundamental.R
import id.rrdevfundamental.ui.detail.follow.FollowersFragment
import id.rrdevfundamental.ui.detail.follow.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.profile_follower,
        R.string.profile_following
    )

    private val fragment: List<Fragment> = listOf(
        FollowersFragment(),
        FollowingFragment()
    )

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.getString(tabTitles[position])
    }

    override fun getCount() = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}