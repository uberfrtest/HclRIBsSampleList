package com.hcl.uberpoc.sample.presentation.ribs.root.displaylist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.sample.R
import com.hcl.uberpoc.sample.entity.AirPortType
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Scope

/**
 * Builder for the {@link SelectAirportScope}.
 */
class DisplayListBuilder(dependency: ParentComponent) :
    ViewBuilder<DisplayListView, DisplayListRouter, DisplayListBuilder.ParentComponent>(
        dependency
    ) {

    /**
     * Builds a new [DisplayListRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [DisplayListRouter].
     */
    fun build(parentViewGroup: ViewGroup, airPortType: AirPortType): DisplayListRouter {
        val view = createView(parentViewGroup)
        val interactor = DisplayListInteractor()
        val component = DaggerSelectAirportBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .airPortType(airPortType)
            .build()
        return component.selectairportRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup,
    ): DisplayListView {
        return inflater.inflate(
            R.layout.select_airport_rib,
            parentViewGroup,
            false
        ) as DisplayListView
    }

    interface ParentComponent {
        fun stations(): Stations
        fun selectAirPortListener(): DisplayListInteractor.Listener
    }

    @dagger.Module
    abstract class Module {

        @SelectAirportScope
        @Binds
        internal abstract fun presenter(view: DisplayListView): DisplayListInteractor.SelectAirportPresenter

        @dagger.Module
        companion object {

            @SelectAirportScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: DisplayListView,
                interactor: DisplayListInteractor,
            ): DisplayListRouter = DisplayListRouter(view, interactor, component)
        }
    }

    @SelectAirportScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<DisplayListInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: DisplayListInteractor): Builder

            @BindsInstance
            fun view(view: DisplayListView): Builder

            @BindsInstance
            fun airPortType(airPortType: AirPortType): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun selectairportRouter(): DisplayListRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectAirportScope
}
