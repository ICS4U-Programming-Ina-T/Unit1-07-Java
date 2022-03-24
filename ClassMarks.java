import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The ClassMarks program implements an application that
 * reads a text file into a list, converts it to an array,
 * then calls a different functions to randomly generate marks
 * for each student in the array.
 *
 * @author  Ina Tolo
 * @version 1.0
 * @since   2022-3-22
 */

class ClassMarks {

    /**
     * Declaring constant for the mean.
     */
    private static final double MEAN = 75;
    /**
     * Declaring constant for standard deviation.
     */
    private static final double STAND_DEV = 10;

    /**
     * Generates random marks for each of the students in the list.
     *
     * @param studentArray accepted
     * @param assignmentsArray accepted
     * @return marks2D sent back to main function
     */
    public static String[][] generateMarks(String[] studentArray,
        String[] assignmentsArray) {
        // declaring variables
        final Random random = new Random();
        final String[][] marks2D =
            new String[assignmentsArray.length + 1][studentArray.length];

        // adds names of students to the array
        for (int nameRowCounter = 0; nameRowCounter
            < studentArray.length; nameRowCounter++) {
            marks2D[0][nameRowCounter] = studentArray[nameRowCounter];
        }

        // adds marks for each of the students to the assignments
        for (int counterStudents = 0; counterStudents
            < studentArray.length; counterStudents++) {
            for (int counterMarks = 1; counterMarks
                < assignmentsArray.length + 1; counterMarks++) {
                marks2D[counterMarks][counterStudents] =
                String.valueOf(Math.round(random.nextGaussian()
                    * STAND_DEV + MEAN));
            }
        }
        return marks2D;
    }

    /**
     * Main entry into the program.
     *
     * @param args nothing passed in
     * @throws IOException is being thrown
     */
    public static void main(String[] args) throws IOException {
        // declaring variables
        List<String> listOfStudents = new ArrayList<String>();
        List<String> listOfAssignments = new ArrayList<String>();
        final String[] studentArrayFile;
        final String[] assignmentsArrayFile;
        final String[][] markArrayUser;
        final BufferedWriter writer;
        final StringBuilder builder;

        // reads contents of files into list
        try {
            listOfStudents = Files.readAllLines(Paths.get("students.txt"));
            listOfAssignments =
            Files.readAllLines(Paths.get("assignments.txt"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // converts contents of list to an array
        studentArrayFile = listOfStudents.toArray(new String[0]);
        assignmentsArrayFile = listOfAssignments.toArray(new String[0]);

        try {
            // function call
            markArrayUser =
            generateMarks(studentArrayFile, assignmentsArrayFile);

            // Add the 2D array to a csv file.
            builder = new StringBuilder();
            for (int rows = 0; rows < markArrayUser[0].length; ++rows) {
                for (int columns = 0; columns
                    < markArrayUser.length; ++columns) {
                    builder.append(markArrayUser[columns][rows]);
                    builder.append("       ");
                }
                builder.append("\n");
            }

            writer =
                new BufferedWriter(new FileWriter(
                    "/home/ubuntu/environment"
                    + "/Unit1/Unit1-07/Unit1-07"
                    + "-Java/marks.csv"));
            writer.write(builder.toString());
            writer.close();

            // displays alert saying marks have been assigned
            System.out.println("Marks have been assigned.");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
