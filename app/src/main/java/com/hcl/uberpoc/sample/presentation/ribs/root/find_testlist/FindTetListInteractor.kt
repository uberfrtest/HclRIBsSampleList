package com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist

import com.hcl.uberpoc.domain.entity.station.StationDescription
import com.hcl.uberpoc.sample.entity.AirPortType
import com.hcl.uberpoc.sample.entity.AirPortType.*
import com.hcl.uberpoc.sample.presentation.ribs.root.displaylist.DisplayListInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [FindFlightsScope].
 */
@RibInteractor
class FindTetListInteractor :
    Interactor<FindTetListInteractor.FindFlightsPresenter, FindTetsListRouter>() {

    @Inject
    lateinit var presenter: FindFlightsPresenter

    companion object {
        private const val REQUEST_DATE_FORMAT = "yyyy-MM-dd"
        private const val PREVIEW_DATE_FORMAT = "dd MMMM YYYY"
    }

    private val compositeDisposable = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        updateSearchFlightsParams()
        setupRx()
    }

    override fun willResignActive() {
        compositeDisposable.clear()
        super.willResignActive()
    }

    private fun setupRx() {
        compositeDisposable.addAll(
            presenter.onSearchFlights().subscribe { selectAirPort(ORIGIN)}

        )
    }

    private fun updateSearchFlightsParams() {
//        updateOrigin()
//        updateDestination()
//        updatePassengers()
//        searchFlightsRequest.apply {
//            presenter.updateDateOut(
//                dateOut.toFormattedDate(REQUEST_DATE_FORMAT, PREVIEW_DATE_FORMAT))
//        }
        updateSearchAbility()
    }

//    private fun searchFlights() {
//        compositeDisposable.add(
//            getFlightOptionsUseCase.execute(searchFlightsRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map { it.apply { path = "${provideOrigin()} - ${provideDestination()}" } }
//                .doOnSubscribe { presenter.updateProgressVisibility(true) }
//                .doFinally { presenter.updateProgressVisibility(false) }
//                .subscribe({ router.attachAvailableFlightsScreen(it) }, {
//                    if (it.isNotFoundException()) router.attachAvailableFlightsScreen(getEmptyOptions())
//                    else presenter.showError(it)
//                })
//        )
//    }

    private fun selectAirPort(type: AirPortType) = router.attachSelectAirPortScreen(type)

    private fun updateSearchAbility() =
        presenter.updateSearchAbilityState(true)

    inner class SelectAirportListener : DisplayListInteractor.Listener {
        override fun onClose() = router.detachCurrentChild()

        override fun onAirPortSelected(
            airPortType: AirPortType,
            stationDescription: StationDescription,
        ) {
//            if (airPortType == ORIGIN) {
//                searchFlightsRequest.apply {
//                    originCity = stationDescription.name
//                    originCode = stationDescription.code
//                }
//                updateOrigin()
//            } else {
//                searchFlightsRequest.apply {
//                    destinationCity = stationDescription.name
//                    destinationCode = stationDescription.code
//                }
//                updateDestination()
//            }
            updateSearchAbility()
        }
    }

//    private fun provideOrigin(): String =
//        "${searchFlightsRequest.originCity}(${searchFlightsRequest.originCode})"
//
//    private fun provideDestination(): String =
//        "${searchFlightsRequest.destinationCity}(${searchFlightsRequest.destinationCode})"

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface FindFlightsPresenter {
        fun showError(error: Throwable)

        fun updateProgressVisibility(isVisible: Boolean)
        fun updateSearchAbilityState(isEnabled: Boolean)

         fun onSearchFlights(): Observable<Any>
    }
}
