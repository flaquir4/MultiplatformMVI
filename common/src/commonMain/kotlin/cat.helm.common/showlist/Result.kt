package cat.helm.common.showlist

/**
 * Created by Borja on 4/1/17.
 */

sealed class Result<out Value, out Exception : kotlin.Exception> {

    class Success<out Value>(val value: Value) : Result<Value, Nothing>()

    class Failure<out Exception : kotlin.Exception>(val exception: Exception) : Result<Nothing, Exception>()

    inline fun <Return> fold(successPathFunction: (Value) -> Return, failurePathFunction: (Exception) -> Return):
            Return = when (this) {
        is Success -> successPathFunction(value)
        is Failure -> failurePathFunction(exception)
    }

    inline fun success(successPathFunction: (Value) -> Unit) = fold(successPathFunction, {})
    inline fun failure(failurePathFunction: (Exception) -> Unit) = fold({}, failurePathFunction)

    fun isSuccess(): Boolean =
        when (this) {
            is Success -> true
            is Failure -> false
        }

    fun <NewValue> map(mapFunction: (Value) -> NewValue): Result<NewValue, Exception> =
        when (this) {
            is Success -> Success(mapFunction(value))
            is Failure -> Failure(exception)
        }

    fun <NewValue> getValueOrNull(mapFunction: (Value) -> NewValue): NewValue? =
        when (this) {
            is Success -> mapFunction(value)
            is Failure -> null
        }

    companion object {

        fun Failure() = Failure(Exception())

        inline infix fun <Value> of(executableFunction: () -> Value?): Result<Value, kotlin.Exception> {
            try {
                val result = executableFunction()
                result?.let { value ->
                    return Success(value)
                }
                return Result.Failure()
            } catch (e: kotlin.Exception) {
                return Result.Failure(e)
            }
        }
    }
}

inline fun <Value, Exception : kotlin.Exception> Result<Value, Exception>.recover(
    recoverFunction: ()
    -> Result<Value, Exception>
): Result<Value, Exception> =
    when (this) {
        is Result.Success -> this
        is Result.Failure -> recoverFunction()
    }

inline fun <Value, NewValue, Exception : kotlin.Exception>
        Result<Value, Exception>.flatMap(mapFunction: (Value) -> Result<NewValue, Exception>):
        Result<NewValue, Exception> = fold({ mapFunction(it) }, { Result.Failure(it) })
