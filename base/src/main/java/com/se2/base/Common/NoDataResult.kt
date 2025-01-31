//package com.se2.base.Common
//
//sealed class NoDataResult(, val message: String? = null) {
//    class Success<T>(data: T) : NoDataResult<T>(data)
//    class Error<T>(message: String, data: T? = null) : NoDataResult<T>(data, message)
//    class Loading<T>(data: T? = null) : NoDataResult<T>(data)
//}