module.exports = async ({ getNamedAccounts, deployments }) => {
  const { deploy ,log} = deployments;
  const {firstAccount}= await getNamedAccounts();  
    log("deploying NFT contract...");
    await deploy("MyToken",{
        contract: "MyToken",
        from: firstAccount,
        args: ["MyToken","MT"],
        log:true,
    })
    log("NFT contract deployed");
}

module.exports.tags = ["sourcechain","all"];