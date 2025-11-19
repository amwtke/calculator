package org.example.calculator.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private final String inputFile;

    public FileReader(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<String> readExpressions() throws IOException {
        List<String> expressions = new ArrayList<>();

        File file = new File(inputFile);
        if (!file.exists()) {
            throw new FileNotFoundException("输入文件不存在: " + inputFile);
        }

        if (!file.canRead()) {
            throw new IOException("无法读取输入文件: " + inputFile);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    expressions.add(trimmedLine);
                }
            }

            if (expressions.isEmpty()) {
                throw new IOException("输入文件为空: " + inputFile);
            }
        }

        return expressions;
    }
}
