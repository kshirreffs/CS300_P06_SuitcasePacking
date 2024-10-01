//////////////// FILE HEADER //////////////////////////
//
// Title:    PO6 Suitcase Packing
// Course:   CS 300 Spring 2024
//
// Author:   Katelyn Shirreffs
// Email:    kshirreffs@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         NONE
// Online Sources:  Geeks for Geeks - https://www.geeksforgeeks.org/recursive-program-to-find-all-indices-of-a-number/
                    // help with using an index as a parameter for a recursive method
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * Class used for packing a 2D suitcase with items using various strategies.
 */
public class Packing {
  /**
   * Tries to pack each item in the items list in-order.
   * If an item can fit, it must be packed. Otherwise, it should be skipped.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a strategy in which the items
   * were attempted to be packed in-order.
   */
  public static Suitcase rushedPacking(Suitcase suitcase) {
    // calling our rushedPacking helper method:
      // we want to start looking at the suitcase's unpackedItems arraylist at index 0
      // thus setting our currIndex for the helper method to 0
    int currIndex = 0;
    return rushedPackingHelper(suitcase, currIndex);
  }
  
  /**
  * Helper method for rushedPacking
  * Tries to pack each item in the items list in-order.
  * If an item can fit, it must be packed. Otherwise, it should be skipped.
  * 
  * @param suitcase current Suitcase object
  * @param currIndex an int, current index on Suitcase object's unpackedItems arraylist
  * @return a Suitcase representing the outcome of a strategy in which the items
  * were attempted to be packed in-order.
  */
  // CITE: Geeks for Geeks - provided an example of how to use indexing in a recursive method
  private static Suitcase rushedPackingHelper(Suitcase suitcase, int currIndex) {
    
    // base case: if our current index is >= the items we have left to pack,
    // we (1) don't have any more items to pack OR (2) none of the items left fit
    // we are done, return suitcase
    if (currIndex >= suitcase.getUnpackedItems().size()) return suitcase;
    
    // find the next item to pack in our current suitcase
    Item currItem = suitcase.getUnpackedItems().get(currIndex);
    
    // check if we can or can't pack this item
    if (suitcase.canPackItem(currItem)) {
      // if we can pack the current item, pack it
      Suitcase sNew = suitcase.packItem(currItem);
      
      // call the method again with the updated suitcase
      // keep the same currIndex,
      // because the unpackedItems arraylist will have removed the item we just packed
      return rushedPackingHelper(sNew, currIndex);
      
    } else {
      // if we can't pack the current item, we check the next item in unpackedItems
      // we do this by incrementing our currIndex by 1
      // thus reducing our problem set size, avoiding a stack overflow error
      return rushedPackingHelper(suitcase, currIndex + 1);
    }
    
  }

  /**
   * Packs items by greedily packing the largest item which can currently be packed.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a greedy strategy in which at each
   * point the largest item that can fit is packed.
   */
  public static Suitcase greedyPacking(Suitcase suitcase) {
    // base case 1: if size of unpackedItems is 0, we don't have any more items to pack
    // we are done, return suitcase
    if (suitcase.getUnpackedItems().size() == 0) return suitcase;
    
    // using a helper method so we can pack the largest items first
    int largestItemIndex = findLargestItemIndex(suitcase);
    
    // base case 2: we don't have any more items to pack or none of the items left fit
    // (see findLargestItemIndex method)
    if (largestItemIndex == -1) {
      return suitcase;
    }
    
    // pack the largest item
    Suitcase sPacked = suitcase.packItem(suitcase.getUnpackedItems().get(largestItemIndex));
    
    // calling the method again with the updated suitcase to finish packing any remaining items
    return greedyPacking(sPacked);
  }
  
  /**
   * Finds the index of the largest item in suitcase
   *
   * @param suitcase current Suitcase object
   * @return an int representing the index of the largest item in the current suitcase.,
   * returns -1 if there are no more items left to pack, 
   * or if there are no items left to pack that fit
   */
  private static int findLargestItemIndex(Suitcase suitcase) {
    
    // we want our first item in suitcase to be equal to the largestItem to start with,
    // thus setting largestItemIndex and maxSize to -1 temporarily
    // if we don't end up finding an index other than -1, that means we have no more items to pack
    // or that none of the items left do not fit in the suitcase
    int largestItemIndex = -1;
    int maxSize = -1;
    
    // using for loop to look through unpackedItems to find the largest item
    for (int i = 0; i < suitcase.getUnpackedItems().size(); i++) {
      Item currItem = suitcase.getUnpackedItems().get(i);
      int currItemSize = getSize(currItem);
      
      // will only select an item as the "largest" item if it's a bigger size AND we can pack it
      if (suitcase.canPackItem(currItem) && currItemSize > maxSize) {
        largestItemIndex = i;
        maxSize = currItemSize;
      }
    }
    
    // done looping through all items, can return the index of the largest one 
    return largestItemIndex;
  }
  
  /**
   * gets the size of an item, aka the item's width * height
   *
   * @param item, an Item object
   * @return the size of the item
   */
  private static int getSize(Item item) {
    return item.height * item.width;
  }

  /**
   * Finds the optimal packing of items by trying all packing orders.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase
   * @return a Suitcase representing the optimal outcome.
   */  
  public static Suitcase optimalPacking(Suitcase suitcase) {
    // array of the suitcase's unpacked items
    ArrayList<Item> unpackedItems = suitcase.getUnpackedItems(); 
    
    // base case: if there are no items to be packed left, we are done, return suitcase
    if (unpackedItems.size() == 0) return suitcase;
    
    Suitcase optimalSuitcase = suitcase; // temporary place holder for our "optimal" suitcase
    
    /* this for-loop loops through all the different permutations of packing our suitcase
     * in other words, for our first optimalPacking method call, this for-loop will try
       packing the first item; if it can pack this item, we will call optimalPacking again
     * now the for-loop will pack the second item (which is now the first item in unpackedItems)
     * once we recursively pack the rest of our items in the suitcase (if they fit),
       we return to our most recent for-loop iteration, which will then loop through the other
       indexes of unpackedItems, testing if they can pack more area than previous solutions
     */
    for (int i = 0; i < unpackedItems.size(); i++) {
      Item toPack = unpackedItems.get(i);
      // check if we can pack the item
      if (suitcase.canPackItem(toPack)) {
        // if we can pack it, we do
        Suitcase packedSuitcase = suitcase.packItem(toPack);
        
        // recursive call, will pack the items left in unpackedItems
        Suitcase result = optimalPacking(packedSuitcase);
        
        // keeping track of our optimalSuitcase as we loop through all possible permutations
        if (result.areaPacked() > optimalSuitcase.areaPacked()) {
          optimalSuitcase = result;
        }
      }
    }
    
    // once we've looped through all the different possibilities of packing, we return the most
    // optimal suitcase packing order we've found
    return optimalSuitcase;
  }
  
 }
