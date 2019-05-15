package com.tripco.t12.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an itinerary in compressed form. Instead of storing
 * entire place objects, we store indexes into a places list, and
 * turn the Tour back into a re-ordered places list right before
 * we send it back to the client.
 * <p>
 * This is both smaller (because a 4-byte int is smaller than a Map)
 * and faster (because swapping two 4-byte ints is faster than
 * swapping two Maps.)
 */
public class Tour {
    public int[] tour;
    public int placesAdded = 0;

    /**
     * Create a Tour object and allocate memory for the entire tour. More
     * efficient than creating an ArrayList.
     * @param n length of tour
     */
    public Tour(int n) {
        tour = new int[n];
    }

    /**
     * Create Tour object from array. For testing only.
     * @param tourArray array to use
     */
    public Tour(int[] tourArray) {
        tour = tourArray;
    }

    /**
     * Similar to `add` method of ArrayList
     * @param toAdd the int to add to the end of a list
     */
    public void add(int toAdd) {
        tour[placesAdded] = toAdd;
        placesAdded++;
    }

    /**
     * Gets how many times add() has been called
     * @return placesAdded
     */
    public int getPlacesAdded() {
        return placesAdded;
    }


    /**
     * Rotate the tour until the starting place is back in the
     * first position.
     */
    public void moveStartingPlaceToFirstPosition() {
        int index = indexOfStartingPlace();
        rotateTour(index);
    }

    /**
     * Find the index of the starting place. The starting place
     * is represented by 0, so search for the 0.
     * @return index of starting place
     */
    private int indexOfStartingPlace() {
        for (int i = 0; i < tour.length; i++) {
            if(tour[i] == 0) {
                return i;
            }
        }
        // Can't find starting place
        throw new RuntimeException("Starting place is missing!");
    }

    /**
     * Remove `offset` elements from the start of the tour, and add them to the end.
     * <p>
     * Example: [0, 1, 2] -> rotate by 1 -> [1, 2, 0]
     * @param offset - the number of elements to shift left by
     */
    public void rotateTour(int offset) {
        if (tour.length < 2) {
            // A list of length 0 has nothing to rotate
            // A list of length 1 will be unchanged
            return;
        }
        if (offset == 0) {
            // Rotating by zero is a no-op
            return;
        }
        int[] rotatedArray = new int[tour.length];
        for (int i = 0; i < tour.length; i++) {
            rotatedArray[i] = tour[(i + offset ) % tour.length];
        }
        tour = rotatedArray;
    }

    /**
     * Distance of this Tour
     * @param distances distance matrix, see OptimizeTour.pairwiseDistances
     * @return sum of distances along each leg
     */
    public long totalDistance(long[][] distances) {
        // This method's value is not meaningful unless it's a complete tour
        assert isPermutation();
        long totalDistance = 0;
        if (tour.length == 0) {
            // An empty tour has no distance
            return 0L;
        }

        // Sum the distances of everything except between
        // the last location and the first location
        for (int i = 0; i < tour.length - 1; i++) {
            int currentIndex = tour[i];
            int nextIndex = tour[i + 1];
            totalDistance += distances[currentIndex][nextIndex];
        }
        int firstIndex = tour[0];
        int lastIndex = tour[tour.length - 1];
        totalDistance += distances[firstIndex][lastIndex];
        return totalDistance;
    }

    /**
     * Put the Tour into the original, uncompressed form. Remember,
     * Tour stores only the indexes into the places List. Instead of
     * [Boulder, Denver, Fort Collins] we store [0, 1, 2]. Once we optimize it,
     * we get something like [0, 2, 1], which this method turns back into
     * [Boulder, Fort Collins, Denver].
     * @param originalPlaces The places provided by the client.
     * @return The re-ordered places, which can be sent back to the client.
     */
    public List<Location> toPlaceList(List<Location> originalPlaces) {
        List<Location> permutedPlaces = new ArrayList<>();
        for(int index : tour) {
            permutedPlaces.add(originalPlaces.get(index));
        }
        return permutedPlaces;
    }

    /**
     * Simple sanity check: If we're given a list of places, the
     * optimized version should be a re-ordering of that list. We
     * shouldn't delete or duplicate places. This checks that there
     * are no duplicate places, which is sufficient to check that
     * there are no deleted places, too.
     * @return whether or not the Tour is a permutation
     */
    public boolean isPermutation() {
        int numberPlaces = tour.length;
        boolean[] visited = new boolean[numberPlaces];
        for(int placeIndex : tour) {
            if(0 > placeIndex || placeIndex >= tour.length) {
                // Out of range
                return false;
            }
            if(visited[placeIndex]) {
                // Have duplicate index
                return false;
            }
            visited[placeIndex] = true;
        }

        /*
        We have numberPlaces indexes, none are duplicates,
        and all are in range. Therefore, `tour` must be a
        permutation. (Which is good.)
        */
        return true;
    }
}
