package co.ryit.activity.welcome

import android.os.Bundle
import co.ryit.R
import co.ryit.base.WActivityBase

class WelcomeActivity : WActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_app)
        setRemoveTitle()
    }
}