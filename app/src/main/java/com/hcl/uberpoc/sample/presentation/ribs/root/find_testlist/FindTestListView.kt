package com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.hcl.uberpoc.sample.extensions.showErrorDialog
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.find_flights_rib.view.*
import java.util.Date

/**
 * Top level view for {@link FindTestListBuilder.FindFlightsScope}.
 */
class FindTestListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle), FindTetListInteractor.FindFlightsPresenter {

    private val dateSelectedPublisher = PublishRelay.create<Date>()

    override fun showError(error: Throwable) = showErrorDialog(error) {}

    override fun updateProgressVisibility(isVisible: Boolean) {
        vProgress.visibility = if (isVisible) VISIBLE else GONE
    }

    override fun updateSearchAbilityState(isEnabled: Boolean) {
        vSearchFlights.isEnabled = isEnabled
    }



    override fun onSearchFlights(): Observable<Any> = RxView.clicks(vSearchFlights)

}
