package info.kunalsheth.unitsofmeasure.processor.data

import info.kunalsheth.unitsofmeasure.annotations.Dimension

/**
 * Created by kunal on 8/4/17.
 */
data class MetaDimension(
        val L: Int = 0,
        val M: Int = 0,
        val T: Int = 0,
        val I: Int = 0,
        val Theta: Int = 0,
        val N: Int = 0,
        val J: Int = 0
) {
    constructor(dimension: Dimension) : this(
            L = dimension.L,
            M = dimension.M,
            T = dimension.T,
            I = dimension.I,
            Theta = dimension.Theta,
            N = dimension.N,
            J = dimension.J
    )

    val safeName = {
        val (numerator, denominator) = mapOf(
                "L" to L,
                "M" to M,
                "T" to T,
                "I" to I,
                "Theta" to Theta,
                "N" to N,
                "J" to J
        )
                .toList()
                .partition { (_, power) -> power >= 0 }

        val numeratorString = numerator
                .joinToString(separator = "") { (unit, power) -> unit + power }

        val denominatorString = denominator
                .map { (unit, power) -> unit to Math.abs(power) }
                .joinToString(separator = "") { (unit, power) -> unit + power }

        (numeratorString + if (denominatorString.isNotEmpty()) "_per_$denominatorString" else "")
                .takeUnless { it.isBlank() } ?: "Dimensionless"
    }()

    private val name = mapOf(
            "L" to L,
            "M" to M,
            "T" to T,
            "I" to I,
            "Θ" to Theta,
            "N" to N,
            "J" to J
    )
            .filterValues { it != 0 }
            .mapValues { (_, power) ->
                power.toString()
                        .map {
                            mapOf(
                                    '+' to '⁺',
                                    '-' to '⁻',
                                    '1' to '¹',
                                    '2' to '²',
                                    '3' to '³',
                                    '4' to '⁴',
                                    '5' to '⁵',
                                    '6' to '⁶',
                                    '7' to '⁷',
                                    '8' to '⁸',
                                    '9' to '⁹'
                            )[it] ?: it
                        }
                        .joinToString(separator = "")
                        .takeUnless { it == "¹" } ?: ""
            }
            .map { (base, power) -> base + power }
            .joinToString(
                    separator = "⋅",
                    prefix = "`",
                    postfix = "`"
            )
            .takeUnless { it == "``" } ?: "Dimensionless"

    override fun toString() = name
}