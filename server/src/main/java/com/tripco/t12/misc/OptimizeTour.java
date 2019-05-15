package com.tripco.t12.misc;

import com.tripco.t12.TIP.ClientSideException;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods for performing Nearest Neighbor, 2-opt, or 3-opt on an itinerary.
 */
public abstract class OptimizeTour {

    /**
     * Compute distances between each pair of places.
     * @param places the List of places from TIPItinerary object
     * @param earthRadius Radius of the earth in whatever unit the user selected.
     * @return A matrix where matrix[x][y] represents the distance between
     *         place x and place y
     */
    public static long[][] pairwiseDistances(List<Location> places, double earthRadius) {
        int size = places.size();
        long[][] distances = new long[size][size];
        // Compute distances between a location and the places before it
        // in the places array. Doesn't compute the distance to the places after it;
        // that would duplicate work that will be done later.
        for (int i = 0; i < size; i++) {
            Location place1 = places.get(i);
            for (int j = 0; j < i; j++) {
                Location place2 = places.get(j);

                long distance = GreatCircleDistance.distance(place1, place2, earthRadius);
                distances[i][j] = distance;
                // The distance between A and B is the same as the distance
                // between B and A. Don't recompute it; just copy from above
                distances[j][i] = distance;
            }
        }
        return distances;
    }

    private static List<Tour> nearestNeighborAll(long[][] distances) {
        int numberPlaces = distances.length;
        List<Tour> allTours = new ArrayList<>();
        for(int startingPlace = 0; startingPlace < numberPlaces; startingPlace++) {
            allTours.add(nearestNeighbor(distances, startingPlace));
        }
        return allTours;
    }

    /**
     * Do a nearest-neighbor search, given a particular starting location.
     * @param distances pairwise distances between nodes
     * @param startingIndex index of starting node
     * @return list of indexes of cities to visit
     */
    public static Tour nearestNeighbor(long[][] distances, int startingIndex) {
        int numberPlaces = distances.length;
        if(numberPlaces == 0) {
            /*
            If there are 0 places, the answer is always an empty list.
            We must handle this case specially, because the code below
            assumes that there is at least 1 place.
            */
            return new Tour(0);
        }

        // Keep track of which places have been visited
        boolean[] visited = new boolean[numberPlaces];

        // This holds the indexes of each place, in the order that we visit them
        Tour newTour = new Tour(numberPlaces);
        int currentIndex = startingIndex;

        // Add it to the tour, mark it as visited
        newTour.add(currentIndex);
        visited[currentIndex] = true;

        // If newTour is equal to numberPlaces, then every place has been visited
        while (newTour.getPlacesAdded() < numberPlaces) {
            // Move to the place closest to the current place that has not been visited
            currentIndex = indexOfMinDistance(distances[currentIndex], visited);

            // Add it to the tour, mark it as visited
            newTour.add(currentIndex);
            visited[currentIndex] = true;
        }
        return newTour;
    }

    /**
     * Find the closest unvisited city.
     * <p>
     * Requires that at least one city is unvisited.
     * @param currentNodeDistances The distances from the current city
     * @param visited An array representing whether a city has been visited
     * @return index of closest unvisited city
     */
    public static int indexOfMinDistance(long[] currentNodeDistances, boolean[] visited) {
        // Precondition: visited must contain at least one
        int minIndex = -1;
        long minDistance = Long.MAX_VALUE;

        // Then loop over all nodes, and try to find one that's shorter
        for (int otherIndex = 0; otherIndex < visited.length; otherIndex++) {
            // Only add to the tour if we haven't visited it and
            // it's closer than our current best option
            if (!visited[otherIndex]
                    && currentNodeDistances[otherIndex] < minDistance) {
                // This is the new best distance
                minDistance = currentNodeDistances[otherIndex];
                minIndex = otherIndex;
            }
        }
        if (minIndex == -1) {
            throw new IllegalArgumentException("Cannot find minimum distance of 0 nodes");
        }
        return minIndex;
    }

    /**
     * Optimize a tour using nearest neighbor, 2-opt, or 3-opt.
     * @param places List of places that the user wants to be optimized. Comes from the places attribute of
     *               TIPItinerary.
     * @param earthRadius the radius of the earth in whatever unit the user wants to use.
     * @param optLevel What optimization level the user selected. Can be "short", "shorter", or "shortest".
     * @return The optimized list of places, with the first place still in the first position.
     */
    public static List<Location> optimizeTour(
            List<Location> places, double earthRadius, String optLevel)
            throws ClientSideException {
        int numberPlaces = places.size();
        if(numberPlaces < 2) {
            // A 0 or 1 place request cannot be optimized
            return places;
        }
        long[][] distances = pairwiseDistances(places, earthRadius);

        // No matter what, we're going to need to do nearest neighbor tours
        List<Tour> nnTours = nearestNeighborAll(distances);

        assert nnTours.size() == numberPlaces;

        Tour finalTour;
        switch(optLevel) {
        case "none":
            // Should never be reached, but just in case...
            return places;
        case "short":
            finalTour = chooseShortestTour(nnTours, distances);
            break;
        case "shorter":
            List<Tour> twoOptTours = nnTours;
            twoOptAll(twoOptTours, distances);
            finalTour = chooseShortestTour(twoOptTours, distances);
            break;
        default:
            /*
            Unknown kind of optimization
            Blame it on the client
            */
            throw new ClientSideException();
        }
        /*
        finalTour cannot be null here. places.size() must be at least 2,
        therefore nnTours must be at least 2 elements long;
        therefore chooseShortestTour must have at least two tours to pick
        */

        /*
        Tour begins at some other node. Find the original start, then rotate the list so
        the original first element is still first
        */
        finalTour.moveStartingPlaceToFirstPosition();
        assert finalTour.isPermutation();

        List<Location> optimizedPlaces = finalTour.toPlaceList(places);
        return optimizedPlaces;
    }

    /**
     * Find shortest tour from list. Prefer earlier tours if there's a tie.
     * @param tours list of Tour objects
     * @param distances distance matrix, see pairwiseDistances
     * @return shortest tour, or null if no tours were provided
     */
    private static Tour chooseShortestTour(List<Tour> tours, long[][] distances) {
        Tour bestTour = null;
        long bestDistance = Long.MAX_VALUE;
        for(Tour tour : tours) {
            long tourDistance = tour.totalDistance(distances);
            if(bestTour == null || tourDistance < bestDistance) {
                bestTour = tour;
                bestDistance = tourDistance;
            }
        }
        return bestTour;
    }

    public static void twoOptAll(List<Tour> tours, long[][] distances) {
        for (Tour tour: tours) {
            twoOpt(distances, tour);
        }
    }

    //code adapted from https://github.com/csucs314s19/tripco/blob/master/guides/optimizations/2-opt.md
    public static void twoOpt(long[][] distances, Tour tour) {
        int n = distances.length;
        boolean improvement = true;
        int[] tourArray = tour.tour;
        while (improvement) {
            improvement = false;
            for (int i = 0; i <= n-3; i++) {  // assert n>4
                for (int k = i + 2; k <= n-1; k++) {
                    long delta = -dis(tourArray, distances, i, i+1)
                                 -dis(tourArray, distances, k, k+1)
                                 +dis(tourArray, distances, i, k)
                                 +dis(tourArray, distances, i+1, k+1);
                    if (delta < 0) { //improvement?
                        twoOptReverse(tourArray,i+1, k);
                        improvement = true;
                    }
                }
            }
        }
    }

    //code adapted from https://github.com/csucs314s19/tripco/blob/master/guides/optimizations/2-opt.md

    /**
     * Reverse part of list in place
     * @param tour Input tour
     * @param begin index to start reversing. Inclusive.
     * @param end index to stop reversing. Inclusive.
     */
    public static void twoOptReverse(int[] tour, int begin, int end) {
        while(begin < end) {
            int temp = tour[begin];
            tour[begin] = tour[end];
            tour[end] = temp;
            begin++;
            end--;
        }
    }

    public static long dis(int[] tour, long[][] distances, int x, int y) {
        // if y = distances.length it wraps to beginning of list
        if (y == distances.length) {
            y = 0;
        }
        int row = tour[x];
        int col = tour[y];

        return distances[row][col];
    }


}
