package com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist

import com.hcl.uberpoc.sample.entity.AirPortType
import com.hcl.uberpoc.sample.presentation.ribs.root.displaylist.DisplayListBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link FindTestListBuilder.FindFlightsScope}.
 */
class FindTetsListRouter(
    view: FindTestListView,
    interactor: FindTetListInteractor,
    component: FindTestListBuilder.Component,
    private val displayListBuilder: DisplayListBuilder,
) : ViewRouter<FindTestListView, FindTetListInteractor, FindTestListBuilder.Component>(
    view,
    interactor,
    component
) {

    private var currentChild: Router<*, *>? = null

    fun attachSelectAirPortScreen(airPortType: AirPortType) =
        attachCurrentChild(displayListBuilder.build(view, airPortType))

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
