package info.kunalsheth.unitsofmeasure.source

// parent of all UOM classes
// allows for implementations to be "one-liners"
val uomBase = """
import java.io.Serializable

/**
 * Created by kunal on 8/6/17.
 */
interface Uom<This : Uom<This>> : Comparable<This>, Serializable {
    val quantity: Double

    operator fun unaryPlus() = copy(+quantity)
    operator fun unaryMinus() = copy(-quantity)

    operator fun plus(that: This) = copy(this.quantity + that.quantity)
    operator fun minus(that: This) = copy(this.quantity - that.quantity)
    fun abs() = copy(Math.abs(quantity))

    fun copy(quantity: Double): This

    operator fun rangeTo(that: This) = object : ClosedRange<This> {
        override val start = (this@Uom min that) as This
        override val endInclusive = (this@Uom max that) as This
    }

    infix fun min(that: This) = if (this < that) this else that
    infix fun max(that: This) = if (this > that) this else that
    val isNegative: Boolean get() = quantity < 0
    val isPositive: Boolean get() = quantity > 0

    override fun compareTo(other: This) = this.quantity.compareTo(other.quantity)
}
"""