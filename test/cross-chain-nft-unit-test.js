const { expect } = require("chai");
const{getNamedAccounts,ethers, deployments}=require("hardhat");

let firstAccount;
let ccipSimulator;
let nft;
let nftPoolLockAndRelease;
let wrappedMyToken;
let nftPoolBurnAndMint;
let chainSelector;
before(async function(){
    //prepare variables:contract ,accounts
    firstAccount = (await getNamedAccounts()).firstAccount; 
    await deployments.fixture(["all"]);
    ccipSimulator = await ethers.getContract("CCIPLocalSimulator",firstAccount);
    nft = await ethers.getContract("MyToken",firstAccount);
    nftPoolLockAndRelease = await ethers.getContract("NFTPoolLockAndRelease",firstAccount);
    wrappedMyToken = await ethers.getContract("WrappedMyToken",firstAccount);
    nftPoolBurnAndMint = await ethers.getContract("NFTPoolBurnAndMint",firstAccount);
    const ccipSimulatorConfig  = await ccipSimulator.configuration();
    chainSelector = ccipSimulatorConfig.chainSelector_;
})

//sourcechain => destchain 
describe("source chain -> dest chain cross chain ",function(){
    it("test if user can mint a nft from nft contract successfully",
        async function(){
        await nft.safeMint(firstAccount);
        expect(await nft.ownerOf(0)).to.equal(firstAccount);
    })
    it("test if user can lock the nft in the pool on source chain",
        async function(){
        await ccipSimulator.requestLinkFromFaucet(nftPoolLockAndRelease.target,ethers.parseEther("10"));
        await nft.approve(nftPoolLockAndRelease.target,0);
        await nftPoolLockAndRelease.lockAndSendNFT(0,firstAccount ,chainSelector,nftPoolBurnAndMint.target);
        expect(await nft.ownerOf(0)).to.equal(nftPoolLockAndRelease.target);
    })

    it("test if user can get a wrapped nft in dest chain",async function(){
        const owner = await wrappedMyToken.ownerOf(0);
        expect(owner).to.equal(firstAccount);
    })

})

describe("dest chain -> source chain ",function(){
    it("test if user can burn the wrapped nft in dest chain and send ccip mesg on destchain",async function(){
        await ccipSimulator.requestLinkFromFaucet(nftPoolBurnAndMint.target,ethers.parseEther("10"));
        await wrappedMyToken.approve(nftPoolBurnAndMint.target,0);
        await nftPoolBurnAndMint.burnAndSendNFT(0,firstAccount,chainSelector,nftPoolLockAndRelease.target);
        const totalSupply = await wrappedMyToken.totalSupply();
        expect(totalSupply).to.equal(0);
    })

    it("test if user have the nft unlocked on source chain",async function(){
        const owner = await nft.ownerOf(0);
        expect(owner).to.equal(firstAccount);})
})
