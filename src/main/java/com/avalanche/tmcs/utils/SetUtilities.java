package com.avalanche.tmcs.utils;

import java.util.Set;

public class SetUtilities {
    public static double percentageSetIntersection(Set<?> desired, Set<?> destination){
        return weightedPercentageSetIntersection(desired, destination, 1.0f);
    }

    public static double weightedPercentageSetIntersection(Set<?> desired, Set<?> destination, float weight){
        int numDesiredElementsInDestinationSet = getSetIntersection(desired, destination).size();
        return (weight * numDesiredElementsInDestinationSet) / desired.size();
    }

    public static Set<?> getSetIntersection(Set<?> desired, Set<?> destination){
        desired.retainAll(destination);
        return desired;
    }
}
