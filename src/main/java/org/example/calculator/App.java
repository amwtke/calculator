package org.example.calculator;

import org.example.calculator.core.Calculator;
import org.example.calculator.io.FileReader;
import org.example.calculator.io.FileWriter;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 从命令行参数获取文件路径
        if (args.length < 2) {
            System.out.println("用法: java App <输入文件> <输出文件>");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];

        // 显示使用的文件路径
        System.out.println("输入文件: " + inputFile);
        System.out.println("输出文件: " + outputFile);

        FileReader fileReader = new FileReader(inputFile);
        FileWriter fileWriter = new FileWriter(outputFile);
        try {
            List<String> expressions = fileReader.readExpressions();
            List<String> result = Calculator.batchCalculate(expressions);
            fileWriter.writeResults(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
