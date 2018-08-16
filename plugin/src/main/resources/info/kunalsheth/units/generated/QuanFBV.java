package info.kunalsheth.units.generated;

import org.jetbrains.annotations.NotNull;

// KT-26012 IDE IndexOutOfBoundsException for typealias with recursive type parameters
public interface QuanFBV<
        This extends QuanFBV<This, Integral, Derivative>,
        Integral extends QuanFBV<Integral, ?, This>,
        Derivative extends QuanFBV<Derivative, This, ?>
        > extends Comparable<QuanFBV<This, ?, ?>> {

    @NotNull
    Double fbvSiValue();

    @NotNull
    String fbvAbrev();

    @NotNull
    This fbvNew(Double siValue);

    @NotNull
    Integral fbvTimes(@NotNull QuanFBV<L0M0T1I0Theta0N0J0, ?, ?> that);

    @NotNull
    Derivative fbvDiv(@NotNull QuanFBV<L0M0T1I0Theta0N0J0, ?, ?> that);

    default int compareTo(@NotNull QuanFBV<This, ?, ?> other) {
        return this.fbvSiValue().compareTo(other.fbvSiValue());
    }
}