package org.sr_g3.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DbBackupRestoreUtil {

    public static void backupPGSQL( int version ) {
        try {
            // Define paths and PostgreSQL details
            String backupDirPath = "E:\\MEGA\\CloudStore_Dev\\Github\\JavaMiniProject\\src\\main\\Backup\\";
            String pgDumpPath = "C:\\Program Files\\PostgreSQL\\18\\bin\\pg_dump.exe";
            //PostgreSQL variables
            String ip = "localhost";
            String user = "postgres";
            String database = "stockmanagementdb";
            String password = "admin123";

            // Generate filename
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());
            String backupFile = backupDirPath + "version"+ version +"-product-backup-" + date + ".sql";

            // Ensure the backup directory exists
            File backupDir = new File(backupDirPath);
            if (!backupDir.exists() && !backupDir.mkdirs()) {
                throw new IOException("Could not create backup directory: " + backupDirPath);
            }


            // Configure ProcessBuilder for pg_dump
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

            // Start the process and capture output
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } finally {
                reader.close();
            }

            // Wait for process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("pg_dump failed with exit code: " + exitCode);
            }

            System.out.println("Backup completed successfully: " + backupFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void restorePGSQL() {
        try {
            // Define paths and PostgreSQL details
            String backupDirPath = "E:\\MEGA\\CloudStore_Dev\\Github\\JavaMiniProject\\src\\main\\Backup\\Backup20260304.sql";
            String psql = "C:\\Program Files\\PostgreSQL\\18\\bin\\psql.exe";
            //PostgreSQL variables
            String ip = "localhost";
            String user = "postgres";
            String database = "stockmanagementdb";
            String password = "admin123";

//            // Generate filename with today’s date
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//            String date = dateFormat.format(new Date());
//            String backupFile = backupDirPath + date + ".sql";

//            // Ensure the backup directory exists
//            File backupDir = new File(backupDirPath);
//            if (!backupDir.exists() && !backupDir.mkdirs()) {
//                throw new IOException("Could not create backup directory: " + backupDirPath);
//            }
//


            // Configure ProcessBuilder for pg_dump
            ProcessBuilder pb = new ProcessBuilder(
                    psql,
                    "-U", user,
                    "-d",  database,
                    "-f",backupDirPath
            );
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);

            // Start the process and capture output
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } finally {
                reader.close();
            }

            // Wait for process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("pg_dump failed with exit code: " + exitCode);
            }

            System.out.println("Backup completed successfully: ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void getVersion() {
        File directory = new File("E:\\MEGA\\CloudStore_Dev\\Github\\JavaMiniProject\\src\\main\\Backup\\");

        String[] fileNames = Objects.requireNonNull(directory.list());
        for (String fileName : fileNames) {
            if (fileName.endsWith(".sql")) {
                System.out.println(fileName);
            }
        }


        Stream<String> stream = Arrays.stream(fileNames);
        int maxVersion = Arrays.stream(fileNames)
                .filter(s -> s.startsWith("version"))
                .map(s -> s.substring("version".length()))
                .map(s -> s.split("-")[0])
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(-1);


        System.out.println(maxVersion);




//        return 1;
    }


}