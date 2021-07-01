package com.hcl.uberpoc.sample.presentation.ribs.root.displaylist

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcl.uberpoc.domain.entity.station.Station
import com.hcl.uberpoc.domain.entity.station.StationDescription
import com.hcl.uberpoc.sample.presentation.ribs.root.adapter.DisplayTestListAdapter
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.select_airport_rib.view.*

/**
 * Top level view for {@link DisplayListBuilder.SelectAirportScope}.
 */
class DisplayListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle), DisplayListInteractor.SelectAirportPresenter {

    companion object {
        private const val SEARCH_DEBOUNCE = 300L
    }

    private val onClosePublisher = PublishRelay.create<Unit>()
    private val onItemSelectedPublisher = PublishRelay.create<StationDescription>()
    private val airportsAdapter = DisplayTestListAdapter(onItemSelectedPublisher)


    override fun onFinishInflate() {
        super.onFinishInflate()

        initUI()
    }

    override fun onClose(): Observable<Unit> = onClosePublisher

//    override fun onSearchFieldChanged(): Observable<CharSequence> =
//        RxTextView.textChanges(vSearchField)
//            .skipInitialValue()
//            .debounce(SEARCH_DEBOUNCE, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())

    override fun onAirportSelected(): Observable<StationDescription> = onItemSelectedPublisher

    override fun updateAirports(stations: List<Station>) {
        airportsAdapter.stations = ArrayList(stations)
    }

    private fun initUI() {
        vToolbar.setNavigationOnClickListener { onClosePublisher.accept(Unit) }
        vRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = airportsAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }
}
