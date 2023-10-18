/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph Lewis File: ProjectInfo.java
 * Description: This java file contains the project information, authors, start date, etc.
 */

public class ProjectInfo {

  // attributes
  private static final String[] authors = {"Alan Anderson", "William Feighner", "Michael Wood Jr.",
      "Ibadet Mijit", "Jenna Seipel"};
  private static final String groupName = "The Domino Dynasty";
  private static final String title = "Kingdomino Digital Board Game";

  // getters
  public static String[] getAuthors() {
    return authors;
  }

  public static String getGroupName() {
    return groupName;
  }

  public static String getTitle() {
    return title;
  }
}
