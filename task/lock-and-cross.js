const {task} = require("hardhat/config");
const {networkConfig} = require("../helper-hardhat-config");

task("lock-and-cross")
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
           const nftPoolBurnAndMintDeployment = await hre.companionNetworks["destChain"].deployments.get("NFTPoolBurnAndMint");
           receiver = nftPoolBurnAndMintDeployment.address;
           console.log("receiver is not set in command");
        }
        console.log("using receiver address:",receiver);
          
        const tokenId = taskArgs.tokenid;
        const nftPoolLockAndRelease = await hre.ethers.getContract("NFTPoolLockAndRelease",firstAccount);
        console.log(`locking and crossing NFT tokenId ${tokenId} to chain ${chainselector} receiver ${receiver}`);
        const tx = await nftPoolLockAndRelease.lockAndCrossNFT(chainselector,receiver,tokenId);
        await tx.wait(5);
        console.log(`locked and crossed NFT tokenId ${tokenId} to chain ${chainselector} receiver ${receiver}`);

        //transfer link token to address of the pool
        const linkTokenAddress = networkConfig[hre.network.config.chainId].linkToken;
        const linkToken = await hre.ethers.getContractAt("LinkToken",linkTokenAddress,firstAccount);
        const fee = await nftPoolLockAndRelease.crossChainFee(chainselector);
        console.log(`transferring ${fee} LINK to NFTPoolLockAndRelease contract at ${nftPoolLockAndRelease.address}`);
        const tx2 = await linkToken.transfer(nftPoolLockAndRelease.address,fee);
        await tx2.wait(5);
        const balance = await linkToken.balanceOf(nftPoolLockAndRelease.address);
        console.log(`NFTPoolLockAndRelease contract LINK balance: ${balance}`);
        console.log("transferred LINK to NFTPoolLockAndRelease contract");
        //approve pool address to call transferFrom
        const nft = await hre.ethers.getContract("MyToken",firstAccount);
        const isApproved = await nft.approve(nftPoolLockAndRelease.address,tokenId);
        console.log(`approved NFT tokenId ${tokenId} to NFTPoolLockAndRelease contract: ${isApproved}`);

        //call locakAndSendNFT
        const tx3 = await nftPoolLockAndRelease.lockAndSendNFT(
            tokenId,
            firstAccount,
            chainselector,
            receiver);
        await tx3.wait(3);
        console.log(`ccip transfer request sent,and the tx hash is ${tx3.hash}`);
      });

      module.exports={};