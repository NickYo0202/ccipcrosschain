const { getNamedAccounts, deployments } = require("hardhat");
const { ethers, network } = require("hardhat");
const{devlomentChains,networkConfig, developmentChains} = require("../helper-hardhat-config");

module.exports = async ({ getNamedAccounts, deployments }) => {
    const {firstAccount}= await getNamedAccounts();

    const { deploy ,log} = deployments;
    log("deploying NFTPoolBurnAndMint contract...");
    let destchainRouter,linkAddress;
    if (developmentChains.includes(network.name)){
        const ccipSimulatorDeployment = await deployments.get("CCIPLocalSimulator");
        const ccipSimulator = await ethers.getContractAt("CCIPLocalSimulator",ccipSimulatorDeployment.address);
        const ccipSimulatorConfig  = await ccipSimulator.configuration();
        destchainRouter  = ccipSimulatorConfig.destinationRouter_;
        linkAddress = ccipSimulatorConfig.linkToken_;
    }else{
        destchainRouter = networkConfig[network.config.chainId].router;
        linkAddress = networkConfig[network.config.chainId].linkToken;
    }
    
    const wnftDeployment = await deployments.get("WrappedMyToken");
    const wnftAddress = wnftDeployment.address;
    await deploy("NFTPoolBurnAndMint",{
        contract: "NFTPoolBurnAndMint",
        from: firstAccount,
        args: [destchainRouter,linkAddress,wnftAddress],
        log:true,
    })
    log("NFTPoolBurnAndMint contract deployed successfully");
}

module.exports.tags = ["destchain","all"];