package com.hcl.uberpoc.sample.presentation.ribs.root

import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist.FindTestListBuilder
import com.hcl.uberpoc.sample.presentation.ribs.splash.SplashBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 */
class RootRouter(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val splashBuilder: SplashBuilder,
    private val findTestListBuilder: FindTestListBuilder,
) : ViewRouter<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component) {

    private var currentChild: Router<*, *>? = null

    fun attachSplashScreen() = attachCurrentChild(splashBuilder.build(view))

    fun attachFindFlightsScreen(stations: Stations) =
        attachCurrentChild(findTestListBuilder.build(view, stations))

    fun detachCurrentChild() {
        currentChild?.let {
            detachChild(it)
            if (it is ViewRouter<*, *, *>) view.removeView(it.view)
            currentChild = null
        }
    }

    private fun attachCurrentChild(child: Router<*, *>?) {
        currentChild = child.let {
            attachChild(it)
            if (it is ViewRouter<*, *, *>) view.addView(it.view)
            it
        }
    }

    override fun handleBackPress(): Boolean = currentChild?.handleBackPress() ?: false
}
