package com.hcl.uberpoc.data.network.extensions

import retrofit2.HttpException

fun Throwable.isNotFoundException(): Boolean = this is HttpException && this.code() == 404