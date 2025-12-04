const {task} = require("hardhat/config");

task("mint-nft").setAction(async (taskArgs, hre) => {
    const { firstAccount } = await getNamedAccounts();
    const nft = await hre.ethers.getContract("MyToken", firstAccount);
    console.log("minting a new NFT to", firstAccount);
    const tx = await nft.safeMint(firstAccount);
    await tx.wait(5);
    console.log(`minted NFT to ${firstAccount}`);
});

module.exports = {};