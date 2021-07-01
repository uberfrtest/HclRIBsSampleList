package com.hcl.uberpoc.sample.presentation.ribs.root

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hcl.uberpoc.sample.R
import com.hcl.uberpoc.sample.di.NetworkModule
import com.hcl.uberpoc.sample.presentation.ribs.root.find_testlist.FindTestListBuilder
import com.hcl.uberpoc.sample.presentation.ribs.splash.SplashBuilder
import com.hcl.uberpoc.sample.presentation.ribs.splash.SplashInteractor
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Scope

/**
 * Builder for the {@link RootScope}.
 */
class RootBuilder(dependency: ParentComponent) :
    ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(parentViewGroup: ViewGroup): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor()
        val component = DaggerRootBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.rootRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView {
        return inflater.inflate(R.layout.rib_root, parentViewGroup, false) as RootView
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @RootScope
        @Binds
        internal abstract fun presenter(view: RootView): RootInteractor.RootPresenter

        @dagger.Module
        companion object {

            @RootScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: RootView,
                interactor: RootInteractor
            ): RootRouter = RootRouter(
                view,
                interactor,
                component,
                SplashBuilder(component),
                FindTestListBuilder(component)
            )

            @RootScope
            @Provides
            @JvmStatic
            internal fun splashListener(interactor: RootInteractor): SplashInteractor.Listener =
                interactor.SplashListener()
        }
    }

    @RootScope
    @dagger.Component(
        modules = [Module::class, NetworkModule::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<RootInteractor>, BuilderComponent,
        SplashBuilder.ParentComponent, FindTestListBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RootInteractor): Builder

            @BindsInstance
            fun view(view: RootView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class RootScope
}
