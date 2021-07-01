package com.hcl.uberpoc.sample.presentation.ribs.splash

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.hcl.uberpoc.sample.extensions.showErrorDialog

/**
 * Top level view for {@link SplashBuilder.SplashScope}.
 */
class SplashView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), SplashInteractor.SplashPresenter {

    override fun showError(error: Throwable, onDismiss: () -> Unit) =
        showErrorDialog(error, onDismiss)
}
