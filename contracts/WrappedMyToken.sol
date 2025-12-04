// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;
import {MyToken} from "./MyToken.sol";

contract WrappedMyToken is MyToken {
    constructor(string memory tokenName,string memory tokenSymbol)
        MyToken(tokenName,tokenSymbol){}

        //mint一个特定tokenId的NFT
        function mintTokenWithSpecificTokenId(address to, uint256 tokenId)public {
        _safeMint(to, tokenId);
        }
    }
        

    