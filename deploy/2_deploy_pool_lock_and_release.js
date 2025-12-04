const { ethers, network } = require("hardhat");
const{developmentChains,networkConfig} = require("../helper-hardhat-config");
module.exports = async ({ getNamedAccounts, deployments }) => {
    const {firstAccount}= await getNamedAccounts();

    const { deploy ,log} = deployments;
    let sourcechainRouter,linkAddress;
    log("deploying NFTPoolLockAndRelease contract...");
    if(developmentChains.includes(network.name)){
        const ccipSimulatorDeployment = await deployments.get("CCIPLocalSimulator");
        const ccipSimulator = await ethers.getContractAt("CCIPLocalSimulator",ccipSimulatorDeployment.address);
        const ccipSimulatorConfig  = await ccipSimulator.configuration();
         sourcechainRouter  = ccipSimulatorConfig.sourceRouter_;
         linkAddress = ccipSimulatorConfig.linkToken_;
    }else{
        sourcechainRouter = networkConfig[network.config.chainId].router;
        linkAddress = networkConfig[network.config.chainId].linkToken;
    }
    
    const nftDeployment = await deployments.get("MyToken");
    const nftAddress = nftDeployment.address;
    await deploy("NFTPoolLockAndRelease",{
        contract: "NFTPoolLockAndRelease",
        from: firstAccount,
        args: [sourcechainRouter,linkAddress,nftAddress],
        log:true,
    })
    log("NFTPoolLockAndRelease contract deployed successfully");
}

module.exports.tags = ["sourcechain","all"];