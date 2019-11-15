package com.rhosseini.adakreqres.model.webService.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class ResponseWrapper<out T>(val status: Status, val data: T?, val error: String?) {
    companion object {
        @JvmStatic
        fun <T> loading(): ResponseWrapper<T> {
            return ResponseWrapper(
                Status.LOADING,
                null,
                null
            )
        }

        @JvmStatic
        fun <T> success(data: T?): ResponseWrapper<T> {
            return ResponseWrapper(
                Status.SUCCESS,
                data,
                null
            )
        }

        @JvmStatic
        fun <T> error(error: String): ResponseWrapper<T> {
            return ResponseWrapper(
                Status.ERROR,
                null,
                error
            )
        }
    }


    /**
     * Status of a resource that is provided to the UI.
     *
     *
     * These are usually created by the Repository classes where they return
     * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
     */
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
