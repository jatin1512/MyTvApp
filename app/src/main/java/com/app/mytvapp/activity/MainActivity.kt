package com.app.mytvapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.mytvapp.R
import com.app.mytvapp.databinding.ActivityMainBinding
import com.app.mytvapp.fragment.HomeFragment
import com.app.mytvapp.fragment.KidsFragment
import com.app.mytvapp.fragment.MoviesFragment
import com.app.mytvapp.fragment.SearchFragment
import com.app.mytvapp.fragment.SettingsFragment
import com.app.mytvapp.fragment.SportsFragment
import com.app.mytvapp.utils.AppUtils
import com.app.mytvapp.utils.Constants

class MainActivity : FragmentActivity() ,View.OnKeyListener{
    private lateinit var mBinding:ActivityMainBinding
    private var sideMenu = false
    private var selectedMenu = Constants.MENU_HOME
    private lateinit var lastSelectedMenu: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        init()
    }

    private fun init(){
        setListener()
        lastSelectedMenu = mBinding.btnHome
        lastSelectedMenu.isActivated = true
        changeFragment(HomeFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

       // closeSideMenu()
    }


    private fun setListener(){
        mBinding.btnSearch.setOnKeyListener(this)
        mBinding.btnHome.setOnKeyListener(this)
        mBinding.btnMovies.setOnKeyListener(this)
        mBinding.btnSports.setOnKeyListener(this)
        mBinding.btnSettings.setOnKeyListener(this)
        mBinding. btnKids.setOnKeyListener(this)

    }

    private fun openSideMenu() {
        val animSlide : Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        mBinding.blfNavBar.startAnimation(animSlide)

        mBinding.blfNavBar.requestLayout()
        mBinding.blfNavBar.layoutParams.width = AppUtils.getWidthInPercent(this, 16)
    }

    private fun closeSideMenu() {
        mBinding.blfNavBar.requestLayout()
        mBinding.blfNavBar.layoutParams.width = AppUtils.getWidthInPercent(this, 5)

        mBinding.container.requestFocus()
        sideMenu = false
    }

    override fun onKey(view: View?, i: Int, key_event: KeyEvent?): Boolean {
        when (i) {
            KeyEvent.KEYCODE_DPAD_CENTER -> {

                lastSelectedMenu.isActivated = false
                view?.isActivated = true
                lastSelectedMenu = view!!

                when (view.id) {
                    R.id.btnSearch -> {
                        selectedMenu = Constants.MENU_SEARCH
                        changeFragment(SearchFragment())
                    }
                    R.id.btnHome -> {
                        selectedMenu = Constants.MENU_HOME
                        changeFragment(HomeFragment())
                    }
                    R.id.btnMovies -> {
                        selectedMenu = Constants.MENU_MOVIE
                        changeFragment(MoviesFragment())
                    }
                    R.id.btnSports -> {
                        selectedMenu = Constants.MENU_SPORTS
                        changeFragment(SportsFragment())
                    }
                    R.id.btnKids -> {
                        selectedMenu = Constants.MENU_KIDS
                        changeFragment(KidsFragment())
                    }
                    R.id.btnSettings -> {
                        selectedMenu = Constants.MENU_SETTINGS
                        changeFragment(SettingsFragment())
                    }
                }

            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!sideMenu) {
                    switchToLastSelectedMenu()

                    openSideMenu()
                    sideMenu = true
                }
            }
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && sideMenu) {
            sideMenu = false
            closeSideMenu()
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (sideMenu) {
            sideMenu = false
            closeSideMenu()
        } else {
            super.onBackPressed()
        }
    }

    private fun switchToLastSelectedMenu() {
        when (selectedMenu) {
            Constants.MENU_SEARCH -> {
                mBinding.btnSearch.requestFocus()
            }
            Constants.MENU_HOME -> {
                mBinding.btnHome.requestFocus()
            }

            Constants.MENU_MOVIE -> {
                mBinding.btnMovies.requestFocus()
            }
            Constants.MENU_SPORTS -> {
                mBinding.btnSports.requestFocus()
            }
            Constants.MENU_KIDS -> {
                mBinding.btnKids.requestFocus()
            }
            Constants.MENU_SETTINGS -> {
                mBinding.btnSettings.requestFocus()
            }
        }
    }
}