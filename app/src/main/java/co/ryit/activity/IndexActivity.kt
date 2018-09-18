package co.ryit.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import co.ryit.R
import co.ryit.activity.home.FragmentHome
import co.ryit.base.BaseActivity
import co.ryit.base.BaseFragment
import co.ryit.db.bean.ThemeIndex
import co.ryit.db.db.DataBase
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_index.*
import java.util.ArrayList

class IndexActivity : BaseActivity() {
    private val mFragments = ArrayList<BaseFragment>()
    private val themeIcon = ArrayList<CustomTabEntity>()

    var themeItem: DataBase<ThemeIndex, Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        setRemoveTitle()
        themeItem = DataBase(context, ThemeIndex::class.java)
        initData()
        initView()
    }

    private fun initData() {
        val home1 = ThemeIndex()
        home1.tabSelectedIcon = R.mipmap.ic_launcher
        home1.tabUnselectedIcon = R.mipmap.ic_launcher_round
        home1.tabTitle = "主页"
        themeIcon.add(home1)
        
        val home2 = ThemeIndex()
        home2.tabSelectedIcon = R.mipmap.ic_launcher
        home2.tabUnselectedIcon = R.mipmap.ic_launcher_round
        home2.tabTitle = "消息"
        themeIcon.add(home2)

        val home3 = ThemeIndex()
        home3.tabSelectedIcon = R.mipmap.ic_launcher
        home3.tabUnselectedIcon = R.mipmap.ic_launcher_round
        home3.tabTitle = "会员"
        themeIcon.add(home3)

        val home4 = ThemeIndex()
        home4.tabSelectedIcon = R.mipmap.ic_launcher
        home4.tabUnselectedIcon = R.mipmap.ic_launcher_round
        home4.tabTitle = "我的"
        themeIcon.add(home4)

        mFragments.add(FragmentHome())
        mFragments.add(FragmentHome())
        mFragments.add(FragmentHome())
        mFragments.add(FragmentHome())
    }

    private fun initView() {
        content_viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): BaseFragment? {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        content_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tabhost.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        tabhost.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    else -> content_viewpager.currentItem = position
                }
            }

            override fun onTabReselect(position: Int) {

            }
        })




        try {
            val themeItems = themeItem!!.queryAll() as ArrayList<CustomTabEntity>
            if (themeItems == null) {
                tabhost.setTabData(themeIcon)
                tabhost.textSelectColor = Color.parseColor("#278CFC")
                tabhost.textUnselectColor = Color.parseColor("#333333")
                return
            }
            if (themeItems.size == 0) {
                tabhost.setTabData(themeIcon)
                tabhost.textSelectColor = Color.parseColor("#278CFC")
                tabhost.textUnselectColor = Color.parseColor("#333333")
                return
            }

            tabhost.setTabData(themeItems)
//            tabhost.textSelectColor = Color.parseColor("#" + themeItems[0].getFocus())
//            tabhost.textUnselectColor = Color.parseColor("#" + themeItems[0].getNormal())
        } catch (e: Exception) {

        }
    }

}