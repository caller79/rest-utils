package caller79.numericrange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@lombok.Builder
@lombok.Getter
@lombok.Data
public class MultipleNumericRange {
    private final List<NumericRange> ranges;

    public boolean contains(Number value) {
        return ranges != null && ranges.stream().anyMatch(numericRange -> numericRange.contains(value));
    }

    public boolean overlaps(NumericRange range) {
        return ranges != null && ranges.stream().anyMatch(numericRange -> numericRange.overlaps(range));
    }

    public boolean overlaps(MultipleNumericRange range) {
        return ranges != null && ranges.stream().anyMatch(numericRange -> {
            if (range.getRanges() == null) {
                return false;
            }
            return range.getRanges().stream().anyMatch(nr -> nr.overlaps(numericRange));
        });
    }

    public MultipleNumericRange join(MultipleNumericRange... others) {
        List<NumericRange> resultingRanges = new ArrayList<>(ranges);
        for (MultipleNumericRange other : others) {
            resultingRanges.addAll(other.ranges);
        }
        return MultipleNumericRange.builder().ranges(Collections.unmodifiableList(resultingRanges)).build();
    }

    @Override
    public String toString() {
        if (ranges == null || ranges.isEmpty()) {
            return "[,]";
        } else {
            return ranges.stream().map(Object::toString).collect(Collectors.joining());
        }
    }
}
