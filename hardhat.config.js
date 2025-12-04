require("@nomicfoundation/hardhat-toolbox");
require("hardhat-deploy");
require("hardhat-deploy-ethers");
require("@nomicfoundation/hardhat-ethers");
require("@chainlink/env-enc").config();
require("./task");

const PRIVATE_KEY = process.env.PRIVATE_KEY;
const SEPOLIA_RPC_URL = process.env.SEPOLIA_RPC_URL;
const AMOY_RPC_URL = process.env.AMOY_RPC_URL;
/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
  solidity: "0.8.28",
  namedAccounts: {
    firstAccount: {
      default: 0,
    },
  },
  networks:{
    sepolia:{
      url: SEPOLIA_RPC_URL,
      accounts: [PRIVATE_KEY] ,
      chainId: 11155111,
      blockConfirmations: 6,
      companionNetworks:{
        destChain:"amoy"
      }
    },
    amoy:{
      url: AMOY_RPC_URL,
      accounts: [PRIVATE_KEY] ,
      chainId: 80002,
      blockConfirmations: 6,
      companionNetworks:{
        destChain:"sepolia"
      }
    }
  },
  paths: {
    sources: "./contracts", // 你的合约目录
    artifacts: "./artifacts",
    cache: "./cache",
    tests: "./test",
  },
  // 关键：让 Hardhat 识别 node_modules 中的合约
  external: {
    contracts: [
      {
        artifacts: "./node_modules/@chainlink/contracts/1.4.0/artifacts",
        sources: "./node_modules/@chainlink/contracts/1.4.0/src",
      },
    ],
  },
};
