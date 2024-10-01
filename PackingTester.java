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
// Online Sources:  I/O Flood - https://ioflood.com/blog/math-random-java/#:~:text=To%20generate%20a%20random%20number,0.0%20and%20less%20than%201.0.&text=In%20this%20example%2C%20we've%20used%20Math.
                    // for help with Math.random() function
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * Class used for testing the methods in the Packing class.
 */
public class PackingTester {
  /**
   * Tester method for the Packing.rushedPacking() method base cases.
   * It should test at least the following scenarios: 
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingBaseTest() {

    // TEST 1: no items left to pack in the suitcase
    // create items arraylist that is empty (won't add any items to it)
    ArrayList<Item> items = new ArrayList<>();
    
    Suitcase s1 = new Suitcase(10, 6, items);
    Suitcase s1Packed = Packing.rushedPacking(s1); // there are no items to pack
    
    // s1Packed should still equal s1, there should be no changes
    // returns false if they are not equal
    if (!(s1.equals(s1Packed))) return false;
    
    
    // TEST 2: items left to pack, but none of them fit
    Item phone = new Item("Phone", 6, 5);
    Item shirt = new Item("Shirt", 4, 9);
    ArrayList<Item> items2 = new ArrayList<>();
    items2.add(phone);
    items2.add(shirt);
    
    Suitcase s2 = new Suitcase(3, 2, items2);
    Suitcase s2Packed = Packing.rushedPacking(s2); // none of the items fit, nothing will be packed
    
    // s2Packed should still equal s2, there should be no changes
    // returns false if they are not equal
    if (!(s2.equals(s2Packed))) return false;
    
    // returns true if all tests pass
    return true;
    
  }

  /**
   * Tester method for the Packing.rushedPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - All the items remaining can fit in the suitcase
   * - At least one item remaining cannot fit in the suitcase
   * Will also check:
   * - If a large item can't be added, a smaller item after it can be added
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingRecursiveTest() {
    
    // TEST 1: all the items remaining can fit in the suitcase
    Item phone = new Item("Phone", 2, 2);
    Item shirt = new Item("Shirt", 2, 3);
    ArrayList<Item> items = new ArrayList<>();
    items.add(phone);
    items.add(shirt);
    
    Suitcase s1 = new Suitcase(10, 8, items);
    Suitcase s1Packed = Packing.rushedPacking(s1); // all items should be packed
    // the items should be packed in the order of arraylist items
    // therefore, the packedItems arraylist of s1Packed should still equal arraylist items
    
    // returns false if the packed items are NOT equal to the original arraylist of items
    if (!(s1Packed.getPackedItems().equals(items))) return false;
    
    // TEST 2: at least one item remaining cannot fit in the suitcase
    Item bigPhone = new Item("Big Phone", 7, 10);
    Item bigShirt = new Item("Big Shirt", 12, 14);
    ArrayList<Item> items2 = new ArrayList<>();
    items2.add(phone);
    items2.add(shirt);
    items2.add(bigPhone);
    items2.add(bigShirt);
    
    Suitcase s2 = new Suitcase(10, 8, items2);
    Suitcase s2Packed = Packing.rushedPacking(s2); // only phone and shirt should be packed
    // the packedItems arraylist of s2Packed should equal an arraylist with phone and shirt
    // the unpackedItems arraylist of s2Packed should equal an arraylist with bigPhone and bigShirt
    // return false if these criteria are not met:
    
    // check packedItems
    ArrayList<Item> packedExpected = new ArrayList<>();
    packedExpected.add(phone);
    packedExpected.add(shirt);
    if (!(packedExpected.equals(s2Packed.getPackedItems()))) return false;
    
    // check unpackedItems
    ArrayList<Item> unpackedExpected = new ArrayList<>();
    unpackedExpected.add(bigPhone);
    unpackedExpected.add(bigShirt);
    if (!(unpackedExpected.equals(s2Packed.getUnpackedItems()))) return false;

    // TEST 3: even if one item can't be added, check if a smaller item after it can still be added
    Item socks = new Item("Socks", 1, 1);
    ArrayList<Item> items3 = new ArrayList<>();
    items3.add(phone);
    items3.add(shirt);
    items3.add(bigPhone);
    items3.add(bigShirt);
    items3.add(socks);
    
    Suitcase s3 = new Suitcase(10, 8, items3);
    Suitcase s3Packed = Packing.rushedPacking(s3); // only phone, shirt, and socks should be packed
    // the packedItems arraylist of s3Packed should equal an arraylist with phone, shirt, and socks
    // the unpackedItems arraylist of s3Packed should equal an arraylist with bigPhone and bigShirt
    // return false if these criteria are not met:
    // check packedItems
    ArrayList<Item> packedExpected3 = new ArrayList<>();
    packedExpected3.add(phone);
    packedExpected3.add(shirt);
    packedExpected3.add(socks);
    if (!(packedExpected3.equals(s3Packed.getPackedItems()))) return false;
    
    // check unpackedItems
    ArrayList<Item> unpackedExpected3 = new ArrayList<>();
    unpackedExpected3.add(bigPhone);
    unpackedExpected3.add(bigShirt);
    if (!(unpackedExpected3.equals(s3Packed.getUnpackedItems()))) return false;
    
    // returns true if all tests pass
    return true;
  }

  /**
   * Tester method for the Packing.greedyPacking() method base cases.
   * It should test at least the following scenarios:
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingBaseTest() {
    
    // TEST 1: no items left to pack in suitcase
    // create items arraylist that is empty (won't add any items to it)
    ArrayList<Item> items = new ArrayList<>();
    
    Suitcase s1 = new Suitcase(10, 6, items);
    Suitcase s1Packed = Packing.greedyPacking(s1); // there are no items to pack
    
    // s1Packed should still equal s1, there should be no changes
    // returns false if they are not equal
    if (!(s1.equals(s1Packed))) return false;
    
    // TEST 2: items left to pack, but none of them fit
    Item phone = new Item("Phone", 6, 5);
    Item shirt = new Item("Shirt", 4, 9);
    ArrayList<Item> items2 = new ArrayList<>();
    items2.add(phone);
    items2.add(shirt);
    
    Suitcase s2 = new Suitcase(3, 2, items2);
    Suitcase s2Packed = Packing.greedyPacking(s2); // none of the items fit, nothing will be packed
    
    // s2Packed should still equal s2, there should be no changes
    // returns false if they are not equal
    if (!(s2.equals(s2Packed))) return false;
    
    // returns true if all tests pass
    return true;
    
  }

  /**
   * Tester method for the Packing.greedyPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - At least one item is packed out of order (an item with a higher index
   *   is packed before an item with a lower index)
   * - A scenario where the greedy packing method packs more of the suitcase
   *   than the rushed packing (you can use the example given in the writeup)
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingRecursiveTest() {
    
    // TEST 1: one item is packed out of order
    Item phone = new Item("Phone", 2, 2); // area of 4
    Item shirt = new Item("Shirt", 4, 3); // area of 12
    ArrayList<Item> items = new ArrayList<>();
    items.add(phone);
    items.add(shirt);
    
    Suitcase s1 = new Suitcase(10, 8, items);
    Suitcase s1Packed = Packing.greedyPacking(s1); // shirt should be packed before phone
    
    // check if shirt is indeed packed before phone
    ArrayList<Item> packedExpected = new ArrayList<>();
    packedExpected.add(shirt);
    packedExpected.add(phone);
    if (!(packedExpected.equals(s1Packed.getPackedItems()))) return false;
    
    // TEST 2: greedy packing method packs more of suitcase than rushed packing
    Item A = new Item("A", 4, 2);
    Item B = new Item("B", 6, 3);
    Item C = new Item("C", 7, 4);
    Item D = new Item("D", 4, 5);
    Item E = new Item("E", 4, 5);
    Item F = new Item("F", 5, 4);
    Item G = new Item("G", 2, 6);
    
    ArrayList<Item> items2 = new ArrayList<>();
    items2.add(A);
    items2.add(B);
    items2.add(C);
    items2.add(D);
    items2.add(E);
    items2.add(F);
    items2.add(G);
    
    Suitcase s2 = new Suitcase(10, 10, items2);
    
    // as per the example in the writeup, greedyPacking should pack more than rushedPacking here
    Suitcase s2Rushed = Packing.rushedPacking(s2);
    Suitcase s2Greedy = Packing.greedyPacking(s2);
    
    // return false if greedyPacking did not in fact pack more items than rushedPacking
    if (s2Rushed.areaPacked() >= s2Greedy.areaPacked()) return false;
    
    // returns true if all tests pass
    return true;
  }

  /**
   * Tester method for the Packing.optimalPacking() method.
   * This tester should test the optimalPacking() method by
   * randomly generating at least TEN (10) different scenarios,
   * and randomly generating at least ONE-HUNDRED (100)
   * different packing solutions for EACH of the scenarios.
   * Each scenario should have at least FIVE (5) random items,
   * and the suitcases should be of size at least 5x5.
   * If any random solution is better than the optimal packing then
   * it is not actually optimal, so the method does not pass the test.
   * You should use the Utilities method to generate random lists of
   * items, and to randomly pack the suitcases.
   * @return true if all tests pass, false otherwise
   */
  public static boolean optimalPackingRandomTest() {
    
    // creating 10 different suitcase scenarios
    for (int i = 0; i < 10; i++) {
      
      // CITE: I/O Flood - helped with generating a Math.random() number for the various scenarios
      // set up suitcase (with random number of items, width, and height)
      int numItems = (int)(Math.random() * 4) + 5; // generates random num 5 - 8
      ArrayList<Item> items = Utilities.randomItems(numItems);
      int sWidth = (int)(Math.random() * 4) + 5; // generates random num 5 - 8
      int sHeight = (int)(Math.random() * 4) + 5; // generates random num 5 - 8
      Suitcase sToPack = new Suitcase(sWidth, sHeight, items); // creates suitcase
      
      // run optimalPacking to compare with randomPacking in the next for loop
      Suitcase sOptimal = Packing.optimalPacking(sToPack);
      
      // randomly generating 100 packing solutions
      for (int j = 0; j < 100; j++) {
        Suitcase sRandom = Utilities.randomlyPack(sToPack);
        // optimalPacking should have packed greater than or equal to what randomly packing packed
        // if it didn't, we return false
        if (sRandom.areaPacked() > sOptimal.areaPacked()) return false;
      }
      
    }
    
    // return true if all tests pass
    return true;
  }

  public static void main(String[] args) {
    boolean allPass = true;
    String printFormat = "%-29s %s\n";

    boolean rushedBase = rushedPackingBaseTest();
    allPass &= rushedBase;
    System.out.printf(printFormat, "rushedPackingBaseTest():", rushedBase);

    boolean rushedRecur = rushedPackingRecursiveTest();
    allPass &= rushedRecur;
    System.out.printf(printFormat, "rushedPackingRecursiveTest():", rushedRecur);

    boolean greedyBase = greedyPackingBaseTest();
    allPass &= greedyBase;
    System.out.printf(printFormat, "greedyPackingBaseTest():", greedyBase);

    boolean greedyRecur = greedyPackingRecursiveTest();
    allPass &= greedyRecur;
    System.out.printf(printFormat, "greedyPackingRecursiveTest():", greedyRecur);

    boolean optimalRandom = optimalPackingRandomTest();
    allPass &= optimalRandom;
    System.out.printf(printFormat, "optimalPackingRandomTest():", optimalRandom);

    System.out.printf(printFormat, "All tests:", allPass);
  }
}