const developmentChains = ["hardhat", "local"];
const networkConfig = {
    11155111:{
        name: "sepolia",
        router: "0x0BF3dE8c5D3e8A2B34D2BEeB17ABfCeBaf363A59",
        linkToken: "0x779877A7B0D9E8603169DdbD7836e478b4624789",
        companionChainSelector: "2183018362218727504" // Monad 的 chain selector
    },
    // 80002:{
    //     name: "amoy",
    //     router: "0x9C32fCB86BF0f4a1A8921a9Fe46de3198bb884B2",
    //     linkToken: "0x0Fd9e8d3aF1aaee056EB9e802c3A762a667b1904",
    //     companionChainSelector: "16015286601757825753"
    // },
    10143:{
        name: "monad",
        router: "0x5f16e51e3Dcb255480F090157DD01bA962a53E54",
        linkToken: "0x6fE981Dbd557f81ff66836af0932cba535Cbc343",
        companionChainSelector: "16015286601757825753" // Sepolia 的 chain selector
    }
}
module.exports = {
  developmentChains,networkConfig
};