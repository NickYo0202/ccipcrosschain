package com.crosschain.nft.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.7.
 */
@SuppressWarnings("rawtypes")
public class NFTPoolLockAndRelease extends Contract {
    public static final String BINARY = "0x60a060405234801561001057600080fd5b506040516129cf3803806129cf833981810160405281019061003291906103ed565b3380600085600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036100a95760006040517fd7f733340000000000000000000000000000000000000000000000000000000081526004016100a0919061044f565b60405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff1660808173ffffffffffffffffffffffffffffffffffffffff168152505050600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361014d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610144906104c7565b60405180910390fd5b816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16146101d1576101d08161025e60201b60201c565b5b50505081600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050610553565b3373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036102cc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102c390610533565b60405180910390fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167fed8889f560326eb138920d842192f0eb3dd22b4f139c87a2c57538e05bae127860405160405180910390a350565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006103ba8261038f565b9050919050565b6103ca816103af565b81146103d557600080fd5b50565b6000815190506103e7816103c1565b92915050565b6000806000606084860312156104065761040561038a565b5b6000610414868287016103d8565b9350506020610425868287016103d8565b9250506040610436868287016103d8565b9150509250925092565b610449816103af565b82525050565b60006020820190506104646000830184610440565b92915050565b600082825260208201905092915050565b7f43616e6e6f7420736574206f776e657220746f207a65726f0000000000000000600082015250565b60006104b160188361046a565b91506104bc8261047b565b602082019050919050565b600060208201905081810360008301526104e0816104a4565b9050919050565b7f43616e6e6f74207472616e7366657220746f2073656c66000000000000000000600082015250565b600061051d60178361046a565b9150610528826104e7565b602082019050919050565b6000602082019050818103600083015261054c81610510565b9050919050565b60805161246161056e600039600061085f01526124616000f3fe6080604052600436106100a05760003560e01c806379ba50971161006457806379ba50971461019257806385572ffb146101a95780638da5cb5b146101d2578063b0f479a1146101fd578063e34ed57514610228578063f2fde38b14610265576100a7565b806301ffc9a7146100ac578063263596a5146100e95780633aeac4e11461011557806347ccca021461013e57806351cff8d914610169576100a7565b366100a757005b600080fd5b3480156100b857600080fd5b506100d360048036038101906100ce9190611535565b61028e565b6040516100e0919061157d565b60405180910390f35b3480156100f557600080fd5b506100fe610360565b60405161010c929190611641565b60405180910390f35b34801561012157600080fd5b5061013c600480360381019061013791906116cf565b6103fd565b005b34801561014a57600080fd5b506101536104ed565b604051610160919061176e565b60405180910390f35b34801561017557600080fd5b50610190600480360381019061018b9190611789565b610513565b005b34801561019e57600080fd5b506101a7610611565b005b3480156101b557600080fd5b506101d060048036038101906101cb91906117da565b6107a6565b005b3480156101de57600080fd5b506101e7610832565b6040516101f49190611832565b60405180910390f35b34801561020957600080fd5b5061021261085b565b60405161021f9190611832565b60405180910390f35b34801561023457600080fd5b5061024f600480360381019061024a91906118c3565b610883565b60405161025c919061192a565b60405180910390f35b34801561027157600080fd5b5061028c60048036038101906102879190611789565b610958565b005b60007f85572ffb000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916148061035957507f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916145b9050919050565b60006060600254600380805461037590611974565b80601f01602080910402602001604051908101604052809291908181526020018280546103a190611974565b80156103ee5780601f106103c3576101008083540402835291602001916103ee565b820191906000526020600020905b8154815290600101906020018083116103d157829003601f168201915b50505050509050915091509091565b61040561096c565b60008173ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b81526004016104409190611832565b602060405180830381865afa15801561045d573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061048191906119ba565b9050600081036104bd576040517fd0d04f6000000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6104e883828473ffffffffffffffffffffffffffffffffffffffff166109fc9092919063ffffffff16565b505050565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b61051b61096c565b60004790506000810361055a576040517fd0d04f6000000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60008273ffffffffffffffffffffffffffffffffffffffff168260405161058090611a18565b60006040518083038185875af1925050503d80600081146105bd576040519150601f19603f3d011682016040523d82523d6000602084013e6105c2565b606091505b505090508061060c573383836040517f9d11f56300000000000000000000000000000000000000000000000000000000815260040161060393929190611a3c565b60405180910390fd5b505050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146106a1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069890611abf565b60405180910390fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a350565b6107ae61085b565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461081d57336040517fd7f733340000000000000000000000000000000000000000000000000000000081526004016108149190611832565b60405180910390fd5b61082f8161082a90611e42565b610a7b565b50565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60007f0000000000000000000000000000000000000000000000000000000000000000905090565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330886040518463ffffffff1660e01b81526004016108e493929190611a3c565b600060405180830381600087803b1580156108fe57600080fd5b505af1158015610912573d6000803e3d6000fd5b505050506000848660405160200161092b929190611e55565b60405160208183030381529060405290506000610949858584610b40565b90508092505050949350505050565b61096061096c565b61096981610f78565b50565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109fa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109f190611eca565b60405180910390fd5b565b610a76838473ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8585604051602401610a2f929190611e55565b604051602081830303815290604052915060e01b6020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050506110a4565b505050565b60008160600151806020019051810190610a959190611f4f565b9050600081602001519050600082600001519050600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3083856040518463ffffffff1660e01b8152600401610b0893929190611a3c565b600060405180830381600087803b158015610b2257600080fd5b505af1158015610b36573d6000803e3d6000fd5b5050505050505050565b600080610b708484600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1661113b565b905060003073ffffffffffffffffffffffffffffffffffffffff1663b0f479a16040518163ffffffff1660e01b8152600401602060405180830381865afa158015610bbf573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610be39190611f7c565b905060008173ffffffffffffffffffffffffffffffffffffffff166320487ded88856040518363ffffffff1660e01b8152600401610c22929190612194565b602060405180830381865afa158015610c3f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c6391906119ba565b9050600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401610cc09190611832565b602060405180830381865afa158015610cdd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610d0191906119ba565b811115610de257600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401610d639190611832565b602060405180830381865afa158015610d80573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610da491906119ba565b816040517f8f0f4206000000000000000000000000000000000000000000000000000000008152600401610dd99291906121c4565b60405180910390fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663095ea7b383836040518363ffffffff1660e01b8152600401610e3f929190611e55565b6020604051808303816000875af1158015610e5e573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610e829190612219565b508173ffffffffffffffffffffffffffffffffffffffff166396f4e9f988856040518363ffffffff1660e01b8152600401610ebe929190612194565b6020604051808303816000875af1158015610edd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610f01919061225b565b93508667ffffffffffffffff16847f3d8a9f055772202d2c3c1fddbad930d3dbe588d8692b75b84cee0719462829118888600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1686604051610f6694939291906122d2565b60405180910390a35050509392505050565b3373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610fe6576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610fdd9061236a565b60405180910390fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167fed8889f560326eb138920d842192f0eb3dd22b4f139c87a2c57538e05bae127860405160405180910390a350565b60006110cf828473ffffffffffffffffffffffffffffffffffffffff1661121e90919063ffffffff16565b905060008151141580156110f45750808060200190518101906110f29190612219565b155b1561113657826040517f5274afe700000000000000000000000000000000000000000000000000000000815260040161112d9190611832565b60405180910390fd5b505050565b611143611454565b6040518060a001604052808560405160200161115f9190611832565b6040516020818303038152906040528152602001848152602001600067ffffffffffffffff81111561119457611193611ae4565b5b6040519080825280602002602001820160405280156111cd57816020015b6111ba611499565b8152602001906001900390816111b25790505b5081526020018373ffffffffffffffffffffffffffffffffffffffff168152602001611212604051806040016040528062030d40815260200160011515815250611234565b81525090509392505050565b606061122c838360006112b3565b905092915050565b606063181dcf1060e01b8260405160240161124f91906123c8565b604051602081830303815290604052907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050509050919050565b6060814710156112fa57306040517fcd7860590000000000000000000000000000000000000000000000000000000081526004016112f19190611832565b60405180910390fd5b6000808573ffffffffffffffffffffffffffffffffffffffff1684866040516113239190612414565b60006040518083038185875af1925050503d8060008114611360576040519150601f19603f3d011682016040523d82523d6000602084013e611365565b606091505b5091509150611375868383611380565b925050509392505050565b606082611395576113908261140f565b611407565b600082511480156113bd575060008473ffffffffffffffffffffffffffffffffffffffff163b145b156113ff57836040517f9996b3150000000000000000000000000000000000000000000000000000000081526004016113f69190611832565b60405180910390fd5b819050611408565b5b9392505050565b6000815111156114225780518082602001fd5b6040517f1425ea4200000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6040518060a00160405280606081526020016060815260200160608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001606081525090565b6040518060400160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b6000604051905090565b600080fd5b600080fd5b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b611512816114dd565b811461151d57600080fd5b50565b60008135905061152f81611509565b92915050565b60006020828403121561154b5761154a6114d3565b5b600061155984828501611520565b91505092915050565b60008115159050919050565b61157781611562565b82525050565b6000602082019050611592600083018461156e565b92915050565b6000819050919050565b6115ab81611598565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156115eb5780820151818401526020810190506115d0565b60008484015250505050565b6000601f19601f8301169050919050565b6000611613826115b1565b61161d81856115bc565b935061162d8185602086016115cd565b611636816115f7565b840191505092915050565b600060408201905061165660008301856115a2565b81810360208301526116688184611608565b90509392505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061169c82611671565b9050919050565b6116ac81611691565b81146116b757600080fd5b50565b6000813590506116c9816116a3565b92915050565b600080604083850312156116e6576116e56114d3565b5b60006116f4858286016116ba565b9250506020611705858286016116ba565b9150509250929050565b6000819050919050565b600061173461172f61172a84611671565b61170f565b611671565b9050919050565b600061174682611719565b9050919050565b60006117588261173b565b9050919050565b6117688161174d565b82525050565b6000602082019050611783600083018461175f565b92915050565b60006020828403121561179f5761179e6114d3565b5b60006117ad848285016116ba565b91505092915050565b600080fd5b600060a082840312156117d1576117d06117b6565b5b81905092915050565b6000602082840312156117f0576117ef6114d3565b5b600082013567ffffffffffffffff81111561180e5761180d6114d8565b5b61181a848285016117bb565b91505092915050565b61182c81611691565b82525050565b60006020820190506118476000830184611823565b92915050565b6000819050919050565b6118608161184d565b811461186b57600080fd5b50565b60008135905061187d81611857565b92915050565b600067ffffffffffffffff82169050919050565b6118a081611883565b81146118ab57600080fd5b50565b6000813590506118bd81611897565b92915050565b600080600080608085870312156118dd576118dc6114d3565b5b60006118eb8782880161186e565b94505060206118fc878288016116ba565b935050604061190d878288016118ae565b925050606061191e878288016116ba565b91505092959194509250565b600060208201905061193f60008301846115a2565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061198c57607f821691505b60208210810361199f5761199e611945565b5b50919050565b6000815190506119b481611857565b92915050565b6000602082840312156119d0576119cf6114d3565b5b60006119de848285016119a5565b91505092915050565b600081905092915050565b50565b6000611a026000836119e7565b9150611a0d826119f2565b600082019050919050565b6000611a23826119f5565b9150819050919050565b611a368161184d565b82525050565b6000606082019050611a516000830186611823565b611a5e6020830185611823565b611a6b6040830184611a2d565b949350505050565b7f4d7573742062652070726f706f736564206f776e657200000000000000000000600082015250565b6000611aa96016836115bc565b9150611ab482611a73565b602082019050919050565b60006020820190508181036000830152611ad881611a9c565b9050919050565b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b611b1c826115f7565b810181811067ffffffffffffffff82111715611b3b57611b3a611ae4565b5b80604052505050565b6000611b4e6114c9565b9050611b5a8282611b13565b919050565b600080fd5b611b6d81611598565b8114611b7857600080fd5b50565b600081359050611b8a81611b64565b92915050565b600080fd5b600080fd5b600067ffffffffffffffff821115611bb557611bb4611ae4565b5b611bbe826115f7565b9050602081019050919050565b82818337600083830152505050565b6000611bed611be884611b9a565b611b44565b905082815260208101848484011115611c0957611c08611b95565b5b611c14848285611bcb565b509392505050565b600082601f830112611c3157611c30611b90565b5b8135611c41848260208601611bda565b91505092915050565b600067ffffffffffffffff821115611c6557611c64611ae4565b5b602082029050602081019050919050565b600080fd5b600060408284031215611c9157611c90611adf565b5b611c9b6040611b44565b90506000611cab848285016116ba565b6000830152506020611cbf8482850161186e565b60208301525092915050565b6000611cde611cd984611c4a565b611b44565b90508083825260208201905060408402830185811115611d0157611d00611c76565b5b835b81811015611d2a5780611d168882611c7b565b845260208401935050604081019050611d03565b5050509392505050565b600082601f830112611d4957611d48611b90565b5b8135611d59848260208601611ccb565b91505092915050565b600060a08284031215611d7857611d77611adf565b5b611d8260a0611b44565b90506000611d9284828501611b7b565b6000830152506020611da6848285016118ae565b602083015250604082013567ffffffffffffffff811115611dca57611dc9611b5f565b5b611dd684828501611c1c565b604083015250606082013567ffffffffffffffff811115611dfa57611df9611b5f565b5b611e0684828501611c1c565b606083015250608082013567ffffffffffffffff811115611e2a57611e29611b5f565b5b611e3684828501611d34565b60808301525092915050565b6000611e4e3683611d62565b9050919050565b6000604082019050611e6a6000830185611823565b611e776020830184611a2d565b9392505050565b7f4f6e6c792063616c6c61626c65206279206f776e657200000000000000000000600082015250565b6000611eb46016836115bc565b9150611ebf82611e7e565b602082019050919050565b60006020820190508181036000830152611ee381611ea7565b9050919050565b600081519050611ef9816116a3565b92915050565b600060408284031215611f1557611f14611adf565b5b611f1f6040611b44565b90506000611f2f84828501611eea565b6000830152506020611f43848285016119a5565b60208301525092915050565b600060408284031215611f6557611f646114d3565b5b6000611f7384828501611eff565b91505092915050565b600060208284031215611f9257611f916114d3565b5b6000611fa084828501611eea565b91505092915050565b611fb281611883565b82525050565b600081519050919050565b600082825260208201905092915050565b6000611fdf82611fb8565b611fe98185611fc3565b9350611ff98185602086016115cd565b612002816115f7565b840191505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b61204281611691565b82525050565b6120518161184d565b82525050565b60408201600082015161206d6000850182612039565b5060208201516120806020850182612048565b50505050565b60006120928383612057565b60408301905092915050565b6000602082019050919050565b60006120b68261200d565b6120c08185612018565b93506120cb83612029565b8060005b838110156120fc5781516120e38882612086565b97506120ee8361209e565b9250506001810190506120cf565b5085935050505092915050565b600060a08301600083015184820360008601526121268282611fd4565b915050602083015184820360208601526121408282611fd4565b9150506040830151848203604086015261215a82826120ab565b915050606083015161216f6060860182612039565b50608083015184820360808601526121878282611fd4565b9150508091505092915050565b60006040820190506121a96000830185611fa9565b81810360208301526121bb8184612109565b90509392505050565b60006040820190506121d96000830185611a2d565b6121e66020830184611a2d565b9392505050565b6121f681611562565b811461220157600080fd5b50565b600081519050612213816121ed565b92915050565b60006020828403121561222f5761222e6114d3565b5b600061223d84828501612204565b91505092915050565b60008151905061225581611b64565b92915050565b600060208284031215612271576122706114d3565b5b600061227f84828501612246565b91505092915050565b600082825260208201905092915050565b60006122a482611fb8565b6122ae8185612288565b93506122be8185602086016115cd565b6122c7816115f7565b840191505092915050565b60006080820190506122e76000830187611823565b81810360208301526122f98186612299565b90506123086040830185611823565b6123156060830184611a2d565b95945050505050565b7f43616e6e6f74207472616e7366657220746f2073656c66000000000000000000600082015250565b60006123546017836115bc565b915061235f8261231e565b602082019050919050565b6000602082019050818103600083015261238381612347565b9050919050565b61239381611562565b82525050565b6040820160008201516123af6000850182612048565b5060208201516123c2602085018261238a565b50505050565b60006040820190506123dd6000830184612399565b92915050565b60006123ee82611fb8565b6123f881856119e7565b93506124088185602086016115cd565b80840191505092915050565b600061242082846123e3565b91508190509291505056fea264697066735822122071861a5d6c9f655fb0d0e082bea89fb2da58c697cf96dcf468edab5b4b67b6d764736f6c634300081c0033";

    public static final String FUNC_ACCEPTOWNERSHIP = "acceptOwnership";

    public static final String FUNC_GETLASTRECEIVEDMESSAGEDETAILS = "getLastReceivedMessageDetails";

    public static final String FUNC_GETROUTER = "getRouter";

    public static final String FUNC_LOCKANDSENDNFT = "lockAndSendNFT";

    public static final String FUNC_NFT = "nft";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event MESSAGESENT_EVENT = new Event("MessageSent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint64>(true) {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERREQUESTED_EVENT = new Event("OwnershipTransferRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TOKENUNLOCKED_EVENT = new Event("TokenUnlocked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected NFTPoolLockAndRelease(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFTPoolLockAndRelease(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFTPoolLockAndRelease(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFTPoolLockAndRelease(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<MessageSentEventResponse> getMessageSentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MESSAGESENT_EVENT, transactionReceipt);
        ArrayList<MessageSentEventResponse> responses = new ArrayList<MessageSentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MessageSentEventResponse typedResponse = new MessageSentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.messageId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.destinationChainSelector = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.text = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.feeToken = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.fees = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MessageSentEventResponse> messageSentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MessageSentEventResponse>() {
            @Override
            public MessageSentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MESSAGESENT_EVENT, log);
                MessageSentEventResponse typedResponse = new MessageSentEventResponse();
                typedResponse.log = log;
                typedResponse.messageId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.destinationChainSelector = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.text = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.feeToken = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.fees = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MessageSentEventResponse> messageSentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MESSAGESENT_EVENT));
        return messageSentEventFlowable(filter);
    }

    public List<OwnershipTransferRequestedEventResponse> getOwnershipTransferRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERREQUESTED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferRequestedEventResponse> responses = new ArrayList<OwnershipTransferRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferRequestedEventResponse typedResponse = new OwnershipTransferRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferRequestedEventResponse> ownershipTransferRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferRequestedEventResponse>() {
            @Override
            public OwnershipTransferRequestedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERREQUESTED_EVENT, log);
                OwnershipTransferRequestedEventResponse typedResponse = new OwnershipTransferRequestedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferRequestedEventResponse> ownershipTransferRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERREQUESTED_EVENT));
        return ownershipTransferRequestedEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TokenUnlockedEventResponse> getTokenUnlockedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENUNLOCKED_EVENT, transactionReceipt);
        ArrayList<TokenUnlockedEventResponse> responses = new ArrayList<TokenUnlockedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenUnlockedEventResponse typedResponse = new TokenUnlockedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenUnlockedEventResponse> tokenUnlockedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenUnlockedEventResponse>() {
            @Override
            public TokenUnlockedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENUNLOCKED_EVENT, log);
                TokenUnlockedEventResponse typedResponse = new TokenUnlockedEventResponse();
                typedResponse.log = log;
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenUnlockedEventResponse> tokenUnlockedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENUNLOCKED_EVENT));
        return tokenUnlockedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<byte[], String>> getLastReceivedMessageDetails() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETLASTRECEIVEDMESSAGEDETAILS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<byte[], String>>(function,
                new Callable<Tuple2<byte[], String>>() {
                    @Override
                    public Tuple2<byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], String>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> getRouter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROUTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> lockAndSendNFT(BigInteger tokenId, String newOwner, BigInteger chainSelector, String receiver) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKANDSENDNFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Address(160, newOwner), 
                new org.web3j.abi.datatypes.generated.Uint64(chainSelector), 
                new org.web3j.abi.datatypes.Address(160, receiver)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> nft() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NFT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _beneficiary) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _beneficiary)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String _beneficiary, String _token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _beneficiary), 
                new org.web3j.abi.datatypes.Address(160, _token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static NFTPoolLockAndRelease load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTPoolLockAndRelease(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFTPoolLockAndRelease load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTPoolLockAndRelease(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFTPoolLockAndRelease load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NFTPoolLockAndRelease(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFTPoolLockAndRelease load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFTPoolLockAndRelease(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFTPoolLockAndRelease> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _router, String _link, String nftAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _router), 
                new org.web3j.abi.datatypes.Address(160, _link), 
                new org.web3j.abi.datatypes.Address(160, nftAddress)));
        return deployRemoteCall(NFTPoolLockAndRelease.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NFTPoolLockAndRelease> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _router, String _link, String nftAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _router), 
                new org.web3j.abi.datatypes.Address(160, _link), 
                new org.web3j.abi.datatypes.Address(160, nftAddress)));
        return deployRemoteCall(NFTPoolLockAndRelease.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFTPoolLockAndRelease> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _router, String _link, String nftAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _router), 
                new org.web3j.abi.datatypes.Address(160, _link), 
                new org.web3j.abi.datatypes.Address(160, nftAddress)));
        return deployRemoteCall(NFTPoolLockAndRelease.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFTPoolLockAndRelease> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _router, String _link, String nftAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _router), 
                new org.web3j.abi.datatypes.Address(160, _link), 
                new org.web3j.abi.datatypes.Address(160, nftAddress)));
        return deployRemoteCall(NFTPoolLockAndRelease.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class MessageSentEventResponse extends BaseEventResponse {
        public byte[] messageId;

        public BigInteger destinationChainSelector;

        public String receiver;

        public byte[] text;

        public String feeToken;

        public BigInteger fees;
    }

    public static class OwnershipTransferRequestedEventResponse extends BaseEventResponse {
        public String from;

        public String to;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String from;

        public String to;
    }

    public static class TokenUnlockedEventResponse extends BaseEventResponse {
        public String newOwner;

        public BigInteger tokenId;
    }
}
