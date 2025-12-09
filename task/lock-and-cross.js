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
        const signer = await hre.ethers.getSigner(firstAccount);
        const nftPoolLockAndRelease = await hre.ethers.getContract("NFTPoolLockAndRelease",signer);

        // 1. Approve NFT
        const nft = await hre.ethers.getContract("MyToken",signer);
        console.log(`Approving NFT tokenId ${tokenId} to NFTPoolLockAndRelease contract...`);
        const approvalTx = await nft.approve(nftPoolLockAndRelease.target,tokenId);
        await approvalTx.wait(2);
        console.log(`Approved NFT`);

        // 2. Fund with LINK
        const linkTokenAddress = networkConfig[hre.network.config.chainId].linkToken;
        const linkToken = await hre.ethers.getContractAt("LinkToken",linkTokenAddress,signer);
        
        // Hardcode fee to 2 LINK for simplicity
        const fee = hre.ethers.parseEther("2"); 
        
        console.log(`Transferring ${hre.ethers.formatEther(fee)} LINK to NFTPoolLockAndRelease contract at ${nftPoolLockAndRelease.target}`);
        const transferTx = await linkToken.transfer(nftPoolLockAndRelease.target,fee);
        await transferTx.wait(1);
        console.log(`Transferred LINK`);

        // 3. Lock and Send
        console.log(`Locking and crossing NFT tokenId ${tokenId} to chain ${chainselector} receiver ${receiver}`);
        const tx = await nftPoolLockAndRelease.lockAndSendNFT(tokenId,firstAccount,chainselector,receiver);
        await tx.wait(5);
        console.log(`CCIP transfer request sent, tx hash: ${tx.hash}`);
      });

      module.exports={};