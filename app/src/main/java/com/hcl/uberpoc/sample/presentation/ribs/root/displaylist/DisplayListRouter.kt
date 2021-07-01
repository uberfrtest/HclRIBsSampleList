package com.hcl.uberpoc.sample.presentation.ribs.root.displaylist

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link DisplayListBuilder.SelectAirportScope}.
 */
class DisplayListRouter(
    view: DisplayListView,
    interactor: DisplayListInteractor,
    component: DisplayListBuilder.Component,
) : ViewRouter<DisplayListView, DisplayListInteractor, DisplayListBuilder.Component>(view,
    interactor, component) {

    override fun handleBackPress(): Boolean = interactor.handleBackPress()
}
