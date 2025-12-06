package com.crosschain.nft;

import com.crosschain.nft.contracts.MyToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@SpringBootTest
@TestPropertySource(locations = "classpath:contracts.properties")
public class MyTokenIntegrationTest {

    @Value("${contract.mytoken.address}")
    private String myTokenAddress;

    // 注意：这里需要填入你的 RPC 节点地址 (例如 Infura/Alchemy 的 Sepolia 地址，或者本地 http://127.0.0.1:8545)
    private final String RPC_URL = "https://sepolia.infura.io/v3/YOUR_INFURA_PROJECT_ID"; 
    
    // 注意：这里需要填入你的私钥 (用于发送交易，如果是只读操作可以随便填一个或者不传 Credentials)
    // 警告：不要将真实私钥提交到 Git！
    private final String PRIVATE_KEY = "YOUR_PRIVATE_KEY"; 

    @Test
    public void testMyToken() throws Exception {
        System.out.println("Testing MyToken at address: " + myTokenAddress);

        // 1. 连接到区块链网络
        Web3j web3j = Web3j.build(new HttpService(RPC_URL));
        
        // 2. 加载凭证 (如果你只是读取数据，Credentials 可以是空的或者随机的，但 load 方法通常需要它)
        // 如果没有私钥，可以使用 Credentials.create("0x...") 创建一个假的，只要不发送写交易即可
        Credentials credentials = Credentials.create(PRIVATE_KEY);

        // 3. 加载合约
        MyToken myToken = MyToken.load(
                myTokenAddress, 
                web3j, 
                credentials, 
                new DefaultGasProvider()
        );

        // 4. 调用合约方法 (读取)
        // 注意：Web3j 的 send() 方法会抛出异常，所以需要处理
        try {
            String name = myToken.name().send();
            String symbol = myToken.symbol().send();
            myToken.safeMint("WALLET_ADDRESS_ON_SEPOLIA").send();
            BigInteger totalSupply = myToken.totalSupply().send();

            System.out.println("Token Name: " + name);
            System.out.println("Token Symbol: " + symbol);
            System.out.println("Total Supply: " + totalSupply);
            
        } catch (Exception e) {
            e.printStackTrace();
            // 如果连接失败，可能是 RPC URL 不对或者网络问题
            System.err.println("Failed to connect to contract. Please check RPC_URL and network connection.");
        }
    }
}
