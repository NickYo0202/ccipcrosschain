const{task} = require("hardhat/config");

task("check-nft").setAction(async(taskArgs,hre)=>{
    const { firstAccount } = await hre.getNamedAccounts();
    const nft = await hre.ethers.getContract("MyToken",firstAccount);
    const totalSupply = await nft.totalSupply();
    console.log("checking status of MyToken");
    for(let tokenId=0;tokenId<totalSupply;tokenId++){
        const owner = await nft.ownerOf(tokenId);
        console.log(`tokenId ${tokenId} is owned by ${owner}`); }
}
);

module.exports={};