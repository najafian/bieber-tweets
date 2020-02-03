package org.interview.twitter.exception

/**
 * @authors Mehdi Najafian
 */
class TwitterAuthenticationException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, t: Throwable?) : super(message, t)
    constructor(t: Throwable?) : super(t)
}