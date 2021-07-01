package com.hcl.uberpoc.sample.presentation.ribs.splash

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SplashBuilder.SplashScope}.
 */
class SplashRouter(
    view: SplashView,
    interactor: SplashInteractor,
    component: SplashBuilder.Component
) : ViewRouter<SplashView, SplashInteractor, SplashBuilder.Component>(view, interactor, component)
