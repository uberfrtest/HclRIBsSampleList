package com.hcl.uberpoc.sample.presentation.ribs.splash

import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.domain.use_case.GetStationsUseCase
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Coordinates Business Logic for [SplashScope].
 */
@RibInteractor
class SplashInteractor : Interactor<SplashInteractor.SplashPresenter, SplashRouter>() {

    @Inject
    lateinit var presenter: SplashPresenter
    @Inject
    lateinit var getStationsUseCase: GetStationsUseCase
    @Inject
    lateinit var ribListener: Listener

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        loadStations()
    }

    override fun willResignActive() {
        compositeDisposable.clear()
        super.willResignActive()
    }

    private fun loadStations() {
        compositeDisposable.add(
            getStationsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ribListener.onStationsLoaded(it)
                }, {
                    presenter.showError(it) { loadStations() }
                })
        )
    }

    interface Listener {
        fun onStationsLoaded(stations: Stations)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SplashPresenter {
        fun showError(error: Throwable, onDismiss: () -> Unit)
    }
}
