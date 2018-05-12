package info.kunalsheth.units

import info.kunalsheth.units.data.Dimension
import info.kunalsheth.units.data.Quantity
import info.kunalsheth.units.data.Relation
import info.kunalsheth.units.data.UnitOfMeasure
import info.kunalsheth.units.suite.*
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class GenerateUnitsOfMeasureTask @Inject constructor(p: Project) : DefaultTask() {
    override fun getGroup() = "UnitsOfMeasure"
    override fun getDescription() = "(Re)generate DSL for type-safe dimensional analysis and unit conversion."

    @TaskAction
    fun generate() {
        generatedSrcDir.delete()
        generatedSrcDir.mkdirs()
        val srcWriter = generatedSrc.printWriter()
        writeBase(srcWriter)

        val allDimensions = (relationships.flatMap { listOf(it.a, it.b, it.result) } +
                quantities.map(Quantity::dimension) +
                unitsOfMeasure.map(UnitOfMeasure::dimension) +
                Dimension(T = 1))
                .toSet()

        val graph = (allDimensions
                .map { it to emptySet<Relation>() }
                .toMap() +
                relationships.groupBy { it.a })
                .mapValues { it.value.toSet() }

        graph.map { (k, v) -> k.src(v) }
                .forEach(srcWriter::println)

        quantities.map(Quantity::src).forEach(srcWriter::println)
        unitsOfMeasure.map(UnitOfMeasure::src).forEach(srcWriter::println)

        srcWriter.done()
    }

    @Input
    var relationships: Set<Relation> = emptySet()

    @Input
    var quantities: Set<Quantity> = emptySet()

    @Input
    var unitsOfMeasure: Set<UnitOfMeasure> = emptySet()

    @OutputDirectory
    val generatedSrcDir = File(p.buildDir, "uom")

    @OutputFile
    val generatedSrc = File(generatedSrcDir, "UnitsOfMeasure.kt")


    // for Groovy to Kotlin interop

    fun dimension(params: Map<String, Any>?) =
            if (params != null) Dimension(
                    L = (params["L"] ?: 0) as Int,
                    M = (params["M"] ?: 0) as Int,
                    T = (params["T"] ?: 0) as Int,
                    I = (params["I"] ?: 0) as Int,
                    Theta = (params["Theta"] ?: 0) as Int,
                    N = (params["N"] ?: 0) as Int,
                    J = (params["J"] ?: 0) as Int
            ) else Dimension()

    fun d(params: Map<String, Any>?) = dimension(params)

    fun relation(vararg dimensions: Dimension) = Relation(*dimensions)
    fun r(vararg dimensions: Dimension) = relation(*dimensions)

    fun quantity(name: String, dimension: Dimension) = Quantity(name, dimension)
    fun q(name: String, dimension: Dimension) = quantity(name, dimension)

    fun unitOfMeasure(name: String, factorToSI: Double, dimension: Dimension) = UnitOfMeasure(name, factorToSI, dimension)
    fun u(name: String, factorToSI: Double, dimension: Dimension) = unitOfMeasure(name, factorToSI, dimension)

    val squants = SquantsExport
    val si = InternationalSystemOfUnits
}