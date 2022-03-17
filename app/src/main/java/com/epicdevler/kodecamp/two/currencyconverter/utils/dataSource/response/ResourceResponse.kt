package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response

//typealias Response = ResourceResponse<Unit, Unit>
sealed class ResourceResponse<E, S>(val errorMsg: E? = null, val data: S? = null) {
    class Error<E, S>(errorMsg: E? = null) : ResourceResponse<E, S>(errorMsg = errorMsg)
    class Success<E, S>(data: S? = null) : ResourceResponse<E, S>(data = data)
}