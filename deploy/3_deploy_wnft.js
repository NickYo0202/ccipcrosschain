module.exports = async ({ getNamedAccounts, deployments }) => {
  const { deploy ,log} = deployments;
  const {firstAccount}= await getNamedAccounts();  
    log("deploying WrappedMyToken contract...");
    await deploy("WrappedMyToken",{
        contract: "WrappedMyToken",
        from: firstAccount,
        args: ["WrappedMyToken","WMT"],
        log:true,
    })
    log("WrappedMyToken contract deployed");
}

module.exports.tags = ["destchain","all"];