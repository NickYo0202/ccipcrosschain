const {task} = require("hardhat/config");
const {networkConfig} = require("../helper-hardhat-config");

task("burn-and-cross")
    .addOptionalParam("chainselector","chain selector of dest chain")
    .addOptionalParam("receiver","receiver address on dest chain")
    .addParam("tokenid","token Id to be crossed")
    .setAction(async(taskArgs,hre)=>{  
        let receiver,chainselector;
        const {firstAccount} = await hre.getNamedAccounts();
        if(taskArgs.chainselector){
            chainselector = taskArgs.chainselector;
        }else{
            chainselector = networkConfig[hre.network.config.chainId].companionChainSelector;
            console.log("chain selector is not set in command");
        }

        console.log("using chain selector:",chainselector);
        if(taskArgs.receiver){
           receiver = taskArgs.receiver
        }else{
           const nftPoolLockAndReleaseDeployment = await hre.companionNetworks["destChain"].deployments.get("NFTPoolLockAndRelease");
           receiver = nftPoolLockAndReleaseDeployment.target;
           console.log("receiver is not set in command");
        }
        console.log("using receiver address:",receiver);
          
        const tokenId = taskArgs.tokenid;
        const nftPoolBurnAndMint = await hre.ethers.getContract("NFTPoolBurnAndMint",firstAccount);
        const signer = await hre.ethers.getSigner(firstAccount);
        console.log(`locking and crossing NFT tokenId ${tokenId} to chain ${chainselector} receiver ${receiver}`);
        
        //1.approve NFT
        const wnft = await hre.ethers.getContract("WrappedMyToken",signer);
        console.log(`approving NFT tokenId ${tokenId} to NFTPoolBurnAndMint contract...`);
        const approvalTx = await wnft.approve(nftPoolBurnAndMint.target,tokenId);
        await approvalTx.wait(2);
        console.log(`approved NFT`);

        //2.fund with LINK
        const linkTokenAddress = networkConfig[hre.network.config.chainId].linkToken;
        const linkToken = await hre.ethers.getContractAt("LinkToken",linkTokenAddress,signer);
        const fee = hre.ethers.parseEther("2");
        console.log(`transferring ${fee} LINK to NFTPoolBurnAndMint contract at ${nftPoolBurnAndMint.target}`);
        const tx2 = await linkToken.transfer(nftPoolBurnAndMint.target,fee);
        await tx2.wait(3);

        //3.lock and send
        const tx3 = await nftPoolBurnAndMint.burnAndSendNFT(
            tokenId,
            firstAccount,
            chainselector,
            receiver);
        await tx3.wait(3);
        console.log(`ccip transfer request sent,and the tx hash is ${tx3.hash}`);
      });

      module.exports={};