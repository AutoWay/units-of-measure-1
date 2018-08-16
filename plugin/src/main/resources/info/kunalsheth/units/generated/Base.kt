package info.kunalsheth.units.generated

import kotlin.math.sign
import kotlin.math.abs

/**
 * Created by kunal on 8/6/17.
 */
typealias Quan<Q> = QuanFBV<Q, *, *>
typealias Quantity<Q, I, D> = QuanFBV<Q, I, D>

val <Q : Quan<Q>> Quan<Q>.siValue get() = fbvSiValue()
val <Q : Quan<Q>> Quan<Q>.abrev get() = fbvAbrev()
fun <Q : Quan<Q>> Quan<Q>.new(siValue: Double) = fbvNew(siValue)

operator fun <Q : Quan<Q>> Q.unaryPlus() = this
operator fun <Q : Quan<Q>> Q.unaryMinus() = new(-siValue)

operator fun <Q : Quan<Q>> Quan<Q>.plus(that: Quan<Q>) = new(this.siValue + that.siValue)
operator fun <Q : Quan<Q>> Quan<Q>.minus(that: Quan<Q>) = new(this.siValue - that.siValue)
operator fun <Q : Quan<Q>> Quan<Q>.times(that: Number) = new(this.siValue * that.toDouble())
operator fun <Q : Quan<Q>> Quan<Q>.div(that: Number) = new(this.siValue / that.toDouble())
operator fun <Q : Quan<Q>> Quan<Q>.rem(that: Quan<Q>) = new(this.siValue % that.siValue)

operator fun <Q : Quantity<Q, IQ, *>, IQ> Q.times(that: Quan<T>) = fbvTimes(that)
operator fun <Q : Quantity<Q, *, DQ>, DQ> Q.div(that: Quan<T>) = fbvDiv(that)

@Suppress("UNCHECKED_CAST")
operator fun <Q : Quan<Q>> Quan<Q>.rangeTo(that: Quan<Q>) = object : ClosedRange<Q> {
    override val start = (this@rangeTo min that)
    override val endInclusive = (this@rangeTo max that)
}

infix fun <Q : Quan<Q>> Quan<Q>.min(that: Quan<Q>): Q = (if (this < that) this else that) as Q
infix fun <Q : Quan<Q>> Quan<Q>.max(that: Quan<Q>): Q = (if (this > that) this else that) as Q

val <Q : Quan<Q>> Quan<Q>.abs get() = new(abs(siValue))
val <Q : Quan<Q>> Quan<Q>.signum get() = siValue.sign
val <Q : Quan<Q>> Quan<Q>.isNegative: Boolean get() = siValue < 0
val <Q : Quan<Q>> Quan<Q>.isPositive: Boolean get() = siValue > 0

private inline fun <reified Q : Quan<Q>> Q.eq(that: Any?) = when (that) {
    is Q -> this.fbvSiValue() == that.fbvSiValue()
    else -> false
}

private val Number.d get() = toDouble()

operator fun <Q : Quan<Q>> Number.times(that: Quan<Q>): Q = that * this
// operator fun <Q : Quan<Q>> Number.div(that: Quan<Q>): Q = need some sort of reciprocal op

fun <Q : Quan<Q>> Number.exa(f: UomConverter<Q>) = f(d * 1E18)
fun <Q : Quan<Q>> Q.exa(f: UomConverter<Q>) = f(this) * 1E-18

fun <Q : Quan<Q>> Number.peta(f: UomConverter<Q>) = f(d * 1E15)
fun <Q : Quan<Q>> Q.peta(f: UomConverter<Q>) = f(this) * 1E-15

fun <Q : Quan<Q>> Number.tera(f: UomConverter<Q>) = f(d * 1E12)
fun <Q : Quan<Q>> Q.tera(f: UomConverter<Q>) = f(this) * 1E-12

fun <Q : Quan<Q>> Number.giga(f: UomConverter<Q>) = f(d * 1E9)
fun <Q : Quan<Q>> Q.giga(f: UomConverter<Q>) = f(this) * 1E-9

fun <Q : Quan<Q>> Number.mega(f: UomConverter<Q>) = f(d * 1E6)
fun <Q : Quan<Q>> Q.mega(f: UomConverter<Q>) = f(this) * 1E-6

fun <Q : Quan<Q>> Number.kilo(f: UomConverter<Q>) = f(d * 1E3)
fun <Q : Quan<Q>> Q.kilo(f: UomConverter<Q>) = f(this) * 1E-3

fun <Q : Quan<Q>> Number.hecto(f: UomConverter<Q>) = f(d * 1E2)
fun <Q : Quan<Q>> Q.hecto(f: UomConverter<Q>) = f(this) * 1E-2

fun <Q : Quan<Q>> Number.deca(f: UomConverter<Q>) = f(d * 1E1)
fun <Q : Quan<Q>> Q.deca(f: UomConverter<Q>) = f(this) * 1E-1

fun <Q : Quan<Q>> Number.deci(f: UomConverter<Q>) = f(d * 1E-1)
fun <Q : Quan<Q>> Q.deci(f: UomConverter<Q>) = f(this) * 1E1

fun <Q : Quan<Q>> Number.centi(f: UomConverter<Q>) = f(d * 1E-2)
fun <Q : Quan<Q>> Q.centi(f: UomConverter<Q>) = f(this) * 1E2

fun <Q : Quan<Q>> Number.milli(f: UomConverter<Q>) = f(d * 1E-3)
fun <Q : Quan<Q>> Q.milli(f: UomConverter<Q>) = f(this) * 1E3

fun <Q : Quan<Q>> Number.micro(f: UomConverter<Q>) = f(d * 1E-6)
fun <Q : Quan<Q>> Q.micro(f: UomConverter<Q>) = f(this) * 1E6

fun <Q : Quan<Q>> Number.nano(f: UomConverter<Q>) = f(d * 1E-9)
fun <Q : Quan<Q>> Q.nano(f: UomConverter<Q>) = f(this) * 1E9

fun <Q : Quan<Q>> Number.pico(f: UomConverter<Q>) = f(d * 1E-12)
fun <Q : Quan<Q>> Q.pico(f: UomConverter<Q>) = f(this) * 1E12

fun <Q : Quan<Q>> Number.femto(f: UomConverter<Q>) = f(d * 1E-15)
fun <Q : Quan<Q>> Q.femto(f: UomConverter<Q>) = f(this) * 1E15

fun <Q : Quan<Q>> Number.atto(f: UomConverter<Q>) = f(d * 1E-18)
fun <Q : Quan<Q>> Q.atto(f: UomConverter<Q>) = f(this) * 1E18

interface UomConverter<Q : Quan<Q>> {
    val unitName: String
    operator fun invoke(x: Number): Q
    operator fun invoke(x: Q): Double
}