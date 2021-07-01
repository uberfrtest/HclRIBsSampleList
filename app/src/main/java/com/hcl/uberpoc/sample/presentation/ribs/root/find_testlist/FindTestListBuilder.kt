package com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hcl.uberpoc.data.network.NetworkServiceFactory

import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.sample.R
import com.hcl.uberpoc.sample.presentation.ribs.root.displaylist.DisplayListBuilder
import com.hcl.uberpoc.sample.presentation.ribs.root.displaylist.DisplayListInteractor
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

/**
 * Builder for the {@link FindFlightsScope}.
 */
class FindTestListBuilder(dependency: ParentComponent) :
    ViewBuilder<FindTestListView, FindTetsListRouter, FindTestListBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [FindTetsListRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [FindTetsListRouter].
     */
    fun build(parentViewGroup: ViewGroup, stations: Stations): FindTetsListRouter {
        val view = createView(parentViewGroup)
        val interactor = FindTetListInteractor()
        val component = DaggerFindFlightsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .stations(stations)
            .build()
        return component.findflightsRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup,
    ): FindTestListView {
        return inflater.inflate(
            R.layout.find_flights_rib,
            parentViewGroup,
            false
        ) as FindTestListView
    }

    interface ParentComponent {
        @Named("prod")
        fun prodNetworkServiceFactory(): NetworkServiceFactory
    }

    @dagger.Module
    abstract class Module {

        @FindFlightsScope
        @Binds
        internal abstract fun presenter(view: FindTestListView): FindTetListInteractor.FindFlightsPresenter

        @dagger.Module
        companion object {

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: FindTestListView,
                interactor: FindTetListInteractor,
            ): FindTetsListRouter {
                return FindTetsListRouter(view,
                    interactor,
                    component,
                    DisplayListBuilder(component),
                )
            }

//            @FindFlightsScope
//            @Provides
//            @JvmStatic
//            internal fun flightNetworkService(
//                @Named("prod")
//                networkServiceFactory: NetworkServiceFactory,
//            ): FlightNetworkService = networkServiceFactory.create(FlightNetworkService::class.java)
//
//            @FindFlightsScope
//            @Provides
//            @JvmStatic
//            internal fun flightsAvailabilityMapper(): Mapper<FlightsAvailabilityEntity, FlightOptions> =
//                FlightsAvailabilityEntityToFlightOptionsMapper()
//
//            @FindFlightsScope
//            @Provides
//            @JvmStatic
//            internal fun flightRepository(
//                flightNetworkService: FlightNetworkService,
//                flightsAvailabilityMapper: Mapper<FlightsAvailabilityEntity, FlightOptions>,
//            ): FlightRepository =
//                FlightRepositoryImpl(flightNetworkService, flightsAvailabilityMapper)
//
//            @FindFlightsScope
//            @Provides
//            @JvmStatic
//            internal fun getFlightOptionsUseCase(
//                flightRepository: FlightRepository,
//            ): GetFlightOptionsUseCase = GetFlightOptionsUseCase(flightRepository)

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun selectAirportListener(
                interactor: FindTetListInteractor,
            ): DisplayListInteractor.Listener = interactor.SelectAirportListener()
        }
    }

    @FindFlightsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<FindTetListInteractor>, BuilderComponent,
        DisplayListBuilder.ParentComponent{

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: FindTetListInteractor): Builder

            @BindsInstance
            fun view(view: FindTestListView): Builder

            @BindsInstance
            fun stations(stations: Stations): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun findflightsRouter(): FindTetsListRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class FindFlightsScope
}
