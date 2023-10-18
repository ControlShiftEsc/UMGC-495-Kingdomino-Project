/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph Lewis File: LandType.java
 * Description: This java file contains the enum definitions of LandType, which characterizes the
 * differnt types of land representations of a domino in Kingdomino
 */

public enum LandType {
  WHEATFIELD("WHEATFIELD", "W"), // wheatfield
  FOREST("FOREST", "F"),  // forest
  LAKE("LAKE", "L"),  // lake
  GRASSLAND("GRASSLAND", "G"),  // grassland
  SWAMP("SWAMP", "S"),  // swamp
  MINE("MINE", "M"), // mine
  CASTLE("CASTLE", "C"), // starting castle
  EMPTY("EMPTY", "E"); // empty space on game board

  // attributes
  private final String fullName; // full name description of domino/ game board space type
  private final String initialName; // first initial of name

  // constructor
  LandType(String fullName, String initialName) {
    this.fullName = fullName;
    this.initialName = initialName;
  }

  // getters
  public String getFullName() {
    return this.fullName;
  }

  public String getInitialName() {
    return this.initialName;
  }
}
