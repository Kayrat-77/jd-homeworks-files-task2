import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static Date date = new Date();
    static StringBuilder sb = new StringBuilder();
    static String path = "D:\\Games\\savegames";
    static List<String> gpList = new ArrayList<>();

    public static void main(String[] args) {


        GameProgress gp1 = new GameProgress(90, 50, 23, 5.5);
        GameProgress gp2 = new GameProgress(80, 55, 33, 5.0);
        GameProgress gp3 = new GameProgress(75, 60, 28, 6.5);

        saveGames(path + "\\gp1.dat", gp1);
        saveGames(path + "\\gp2.dat", gp2);
        saveGames(path + "\\gp3.dat", gp3);

        zipGameProgress(path + "\\zip.zip", gpList);

        gpList.stream()
                .forEach(x -> {
                    File file = new File(x);
                    if (file.delete()) {
                        sb.append(date + " <> ‘‡ÈÎ: " + file.getName() + " Û‰‡Î∏Ì");
                        sb.append("\n");
                    }
                });
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("D:\\Games\\temp\\temp.txt"))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void saveGames(String path, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeObject(gameProgress);
            gpList.add(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipGameProgress(String path, List<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String files : arrayList) {
                File file = new File(files);
                FileInputStream fin = new FileInputStream(files);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zout.putNextEntry(zipEntry);
                byte[] buffer = new byte[fin.available()];
                fin.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fin.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
