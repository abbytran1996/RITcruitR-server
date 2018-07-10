package com.avalanche.tmcs.utils;

import java.util.Set;

public class SetUtilities {
    public static double weightedPercentageSetIntersection(Set<?> desired, Set<?> destination, float weight){

        // if there aren't any desired items to check against, the destination set has 100% of them
        if(desired == null || desired.isEmpty()){ return weight; }

        // if the destination set has no elements, it has 0% of the desired elements
        if(destination == null || destination.isEmpty()){ return 0; }

        int numDesiredElementsInDestinationSet = getSetIntersection(desired, destination).size();
        return (weight * numDesiredElementsInDestinationSet) / desired.size();
    }

    public static Set<?> getSetIntersection(Set<?> desired, Set<?> destination){
        desired.retainAll(destination);
        return desired;
    }
}
