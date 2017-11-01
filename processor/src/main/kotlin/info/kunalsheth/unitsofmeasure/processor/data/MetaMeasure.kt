package info.kunalsheth.unitsofmeasure.processor.data

import info.kunalsheth.unitsofmeasure.annotations.Measure

/**
 * Created by kunal on 8/4/17.
 */
data class MetaMeasure(
        val length: Int = 0,
        val mass: Int = 0,
        val time: Int = 0,
        val electricCurrent: Int = 0,
        val thermodynamicTemperature: Int = 0,
        val amountOfSubstance: Int = 0,
        val luminousIntensity: Int = 0
) {
    constructor(measure: Measure) : this(
            length = measure.length,
            mass = measure.mass,
            time = measure.time,
            electricCurrent = measure.electricCurrent,
            thermodynamicTemperature = measure.thermodynamicTemperature,
            amountOfSubstance = measure.amountOfSubstance,
            luminousIntensity = measure.luminousIntensity
    )

    private val name = mapOf(
            "L" to length,
            "M" to mass,
            "T" to time,
            "I" to electricCurrent,
            "Θ" to thermodynamicTemperature,
            "N" to amountOfSubstance,
            "J" to luminousIntensity
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