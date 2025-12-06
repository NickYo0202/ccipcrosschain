package com.crosschain.nft;

import org.junit.jupiter.api.Test;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

public class ContractGeneratorTest {

    @Test
    public void generateWrappers() throws Exception {
        String[] contracts = {"MyToken", "NFTPoolLockAndRelease", "NFTPoolBurnAndMint", "WrappedMyToken"};
        String packageName = "com.crosschain.nft.contracts";
        
        // 这里的路径是相对于 backend 目录的
        String resourcePath = "src/main/resources/solidity";
        String outputPath = "src/main/java";

        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        for (String contract : contracts) {
            String abiFile = Paths.get(resourcePath, contract + ".abi").toString();
            String binFile = Paths.get(resourcePath, contract + ".bin").toString();
            
            System.out.println("Generating wrapper for " + contract + "...");
            
            // 调用 Web3j 的生成器
            // 参数: -b <binFile> -a <abiFile> -o <outputDir> -p <packageName>
            SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-b", binFile,
                "-a", abiFile,
                "-o", outputPath,
                "-p", packageName
            ).toArray(new String[0]));
        }
        
        System.out.println("Contract wrappers generated successfully!");
    }
}
