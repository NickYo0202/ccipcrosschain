const fs = require('fs');
const path = require('path');

const artifactsDir = path.join(__dirname, '../artifacts/contracts');
const outputDir = path.join(__dirname, '../backend/src/main/resources/solidity');

// 需要生成的合约列表
const contracts = [
    'MyToken',
    'NFTPoolLockAndRelease',
    'NFTPoolBurnAndMint',
    'WrappedMyToken'
];

if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir, { recursive: true });
}

contracts.forEach(contractName => {
    // 查找 artifact 文件
    // 路径通常是 artifacts/contracts/ContractName.sol/ContractName.json
    const artifactPath = path.join(artifactsDir, `${contractName}.sol`, `${contractName}.json`);
    
    if (fs.existsSync(artifactPath)) {
        const artifact = JSON.parse(fs.readFileSync(artifactPath, 'utf8'));
        
        const abiPath = path.join(outputDir, `${contractName}.abi`);
        const binPath = path.join(outputDir, `${contractName}.bin`);
        
        // Filter out functions with tuple types (structs) because Web3j 4.8.7 (Java 8) doesn't support them
        const filteredAbi = artifact.abi.filter(item => {
            if (item.type !== 'function') return true;
            
            const hasTuple = (inputs) => inputs.some(input => input.type.includes('tuple'));
            
            if (item.inputs && hasTuple(item.inputs)) {
                console.warn(`Skipping function ${item.name} in ${contractName} due to tuple/struct parameters (Web3j 4.8.7 limitation)`);
                return false;
            }
            if (item.outputs && hasTuple(item.outputs)) {
                console.warn(`Skipping function ${item.name} in ${contractName} due to tuple/struct outputs (Web3j 4.8.7 limitation)`);
                return false;
            }
            return true;
        });

        fs.writeFileSync(abiPath, JSON.stringify(filteredAbi));
        fs.writeFileSync(binPath, artifact.bytecode);
        
        console.log(`Extracted ${contractName} to ${outputDir}`);
    } else {
        console.warn(`Artifact not found for ${contractName} at ${artifactPath}`);
    }
});
