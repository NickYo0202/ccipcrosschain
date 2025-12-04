const{getNamedAccounts,deployments}=require("hardhat");
const{developmentChains}=require("../helper-hardhat-config");

module.exports = async ({ getNamedAccounts, deployments }) => {

  if (developmentChains.includes(network.name)){
  const { deploy ,log} = deployments;
  const {firstAccount} = await getNamedAccounts();
    log("deploying CCIPLocalSimulator contract...");
    await deploy("CCIPLocalSimulator",{
        contract: "CCIPLocalSimulator",
        from: firstAccount,
        args: [],
        log:true,
    })
    log("CCIPLocalSimulator contract deployed successfully");
}else{
  console.log("You are on a real network, no need to deploy CCIPLocalSimulator contract");
}
}
module.exports.tags = ["sourcechain","all"];