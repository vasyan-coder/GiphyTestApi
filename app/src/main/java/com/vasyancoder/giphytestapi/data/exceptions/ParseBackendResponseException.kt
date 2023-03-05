package com.vasyancoder.giphytestapi.data.exceptions

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)