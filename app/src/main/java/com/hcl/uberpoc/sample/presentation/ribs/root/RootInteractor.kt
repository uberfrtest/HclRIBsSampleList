package com.hcl.uberpoc.sample.presentation.ribs.root

import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.sample.presentation.ribs.splash.SplashInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 */
@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject
    lateinit var presenter: RootPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        router.attachSplashScreen()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter

    inner class SplashListener : SplashInteractor.Listener {
        override fun onStationsLoaded(stations: Stations) {
            router.detachCurrentChild()
            router.attachFindFlightsScreen(stations)
        }
    }
}
