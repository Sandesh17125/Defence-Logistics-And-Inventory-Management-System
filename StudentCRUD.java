import java.sql.*;

public class StudentCRUD {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            // 1️⃣ DISPLAY RECORDS
            System.out.println("---- Initial Records ----");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " +
                                   rs.getString(2) + " " +
                                   rs.getString(3));
            }

            // 2️⃣ INSERT TWO RECORDS
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student VALUES (?, ?, ?)");

            ps.setInt(1, 3);
            ps.setString(2, "Rahul");
            ps.setString(3, "Pune");
            ps.executeUpdate();

            ps.setInt(1, 4);
            ps.setString(2, "Sneha");
            ps.setString(3, "Goa");
            ps.executeUpdate();

            System.out.println("\nTwo records inserted");

            // 3️⃣ UPDATE ONE RECORD
            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE student SET address=? WHERE rollno=?");

            ps2.setString(1, "Bangalore");
            ps2.setInt(2, 1);
            ps2.executeUpdate();

            System.out.println("Record Updated");

            // 4️⃣ DELETE ONE RECORD
            PreparedStatement ps3 = con.prepareStatement(
                    "DELETE FROM student WHERE rollno=?");

            ps3.setInt(1, 2);
            ps3.executeUpdate();

            System.out.println("Record Deleted");

            // 5️⃣ DISPLAY FINAL RECORDS
            System.out.println("\n---- Final Records ----");

            ResultSet rs2 = st.executeQuery("SELECT * FROM student");

            while (rs2.next()) {
                System.out.println(rs2.getInt(1) + " " +
                                   rs2.getString(2) + " " +
                                   rs2.getString(3));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}