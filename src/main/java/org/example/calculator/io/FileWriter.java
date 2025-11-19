package org.example.calculator.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {
    private final String outputFile;

    public FileWriter(String outputFile) {
        this.outputFile = outputFile;
    }

    public void writeResults(List<String> results) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(Paths.get(outputFile)), StandardCharsets.UTF_8))) {
            for (int i = 0; i < results.size(); i++) {
                writer.write(results.get(i));
                if (i < results.size() - 1) {
                    writer.newLine();
                }
            }

        } catch (IOException ex) {
            System.err.println("错误: 写入文件失败 - " + ex.getMessage());
            System.exit(1);
        }
    }
}
