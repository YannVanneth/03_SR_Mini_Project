package org.sr_g3.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DbBackupRestoreUtil {

    static Dotenv dotenv = Dotenv.load();

    public void backupPGSQL( int version ) {

        try {

            if (version < 0){
                version++;
            }

            String backupDirPath = dotenv.get("BACKUP_DIR");
            String pgDumpPath = dotenv.get("PGDUMP_PATH");
            //PostgreSQL variables
            String ip = dotenv.get("DB_HOST");
            String user =  dotenv.get("DB_USER");
            String database = dotenv.get("DB_NAME");
            String password = dotenv.get("DB_PASSWORD");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());
            String backupFile = backupDirPath + "version"+ (version + 1) +"-product-backup-" + date + ".sql";

            File backupDir = new File(backupDirPath);
            if (!backupDir.exists() && !backupDir.mkdirs()) {
                throw new IOException("Could not create backup directory: " + backupDirPath);
            }

            ProcessBuilder pb = new ProcessBuilder(
                    pgDumpPath,
                    "-f", backupFile,
                    "-v",
                    "--clean",
                    "--if-exists",
                    "-h", ip,
                    "-U", user,
                    database
            );
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("pg_dump failed with exit code: " + exitCode);
            }

            System.out.println("Restore successfully: " + backupFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void restorePGSQL(String version) {
        try {

            String backupDirPath = dotenv.get("BACKUP_DIR")+version;
            String psql = dotenv.get("PSQL_PATH");

            String user =  dotenv.get("DB_USER");
            String database = dotenv.get("DB_NAME");
            String password = dotenv.get("DB_PASSWORD");

            File backupDir = new File(backupDirPath);
            if (!backupDir.exists() && !backupDir.mkdirs()) {
                throw new IOException("Could not create backup directory: " + backupDirPath);
            }

            ProcessBuilder pb = new ProcessBuilder(
                    psql,
                    "-U", user,
                    "-d",  database,
                    "-f",backupDirPath
            );
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("pg_dump failed with exit code: " + exitCode);
            }

            System.out.println("Restore " + version + " completed successfully: ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static int getVersion() {
        try{

            String backupDirPath = dotenv.get("BACKUP_DIR");
            if (backupDirPath == null || backupDirPath.isBlank()) {
                return -1;
            }
            else {

                File directory = new File(dotenv.get("BACKUP_DIR"));

                String[] fileNames = directory.list();

                if (fileNames == null || fileNames.length == 0) {
                    return -1;
                }else {
                    return Arrays.stream(fileNames)
                            .filter(s -> s.startsWith("version"))
                            .map(s -> s.substring("version".length()))
                            .map(s -> s.split("-")[0])
                            .mapToInt(Integer::parseInt)
                            .max()
                            .orElse(-1);
                }

            }



        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public List<String> getBackupFiles() {

        try{
            File directory = new File(dotenv.get("BACKUP_DIR"));
            String[] fileNames = directory.list();

            if (fileNames == null || fileNames.length == 0) {
                return new ArrayList<>();
            }else {
                return Arrays.stream(fileNames)
                        .filter(s -> s.endsWith(".sql"))
                        .collect(Collectors.toList());
            }


        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }


    }


    public void showBackupMenu(){

        //get backup files
        List<String> backupFiles = getBackupFiles();

        //if none found, exit

        if (backupFiles.isEmpty()) {
            System.out.println("No backup files found.");
            return;
        }else {

            System.out.println("Available backup files:");
            for (int i = 0; i < backupFiles.size(); i++) {
                System.out.println((i + 1) + ". " + backupFiles.get(i));
            }

            System.out.print("Enter the number of the backup file to restore: ");
            Scanner scanner = new Scanner(System.in);


            int choice = scanner.nextInt();
            scanner.nextLine();


            if (choice < 1 || choice > backupFiles.size()) {
                System.out.println("Invalid choice.");
                return;
            }else{
                String selectedFile = backupFiles.get(choice - 1);
                System.out.println("You selected: " + selectedFile);
                restorePGSQL(selectedFile);

            }
        }



    }


}