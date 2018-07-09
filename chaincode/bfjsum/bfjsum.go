package main

import(
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"encoding/json"
	"bytes"
)

// 定义智能合约
type BfjSumSmartContract struct{

}

// 定义备付金日总余额
type BfjSum struct {
	BfjNo string `json:"bfjNo"`// 备付金帐号
	TransactionDate string `json:"transactionDate"`// 交易日期
	AccountBalance float64 `json:"accountBalance"`// 备付金账户余额
}

// 初始化
func (b *BfjSumSmartContract) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

// 调用
func (b *BfjSumSmartContract) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	// 获取调用invoke方法时传入的function名和parameter值并赋给相应的变量
	function, args := stub.GetFunctionAndParameters();
	if function == "insert" {// 代表数据上链
		return b.insert(stub, args)
	} else if function == "query" {
		return b.query(stub, args)
	}
	return shim.Error("Invalid BfjSum Smart Contract function name.")
}

func (b *BfjSumSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	var bfjSum BfjSum// 定义一个BfjSum对象
	bfjSumBytes := []byte(args[0])// 将传进来的BfjSum对象字符串参数转换为字节数组并赋值给bfjSumBytes变量
	err := json.Unmarshal(bfjSumBytes, &bfjSum); // 将字节数组反序列化为BfjSum对象并赋值给bfjSum变量
	if err != nil {// 反序列化过程出错
		return shim.Error(fmt.Sprintf("json unmarshal failed because %s", err.Error()))
	}

	compositeKey, _ := stub.CreateCompositeKey("bfjNo-transactionDate", []string{bfjSum.BfjNo, bfjSum.TransactionDate})
	err = stub.PutState(compositeKey, bfjSumBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state failed by compositeKey: " + compositeKey + " because %s", err.Error()))
	}

	compositeKey, _ = stub.CreateCompositeKey("transactionDate-bfjNo", []string{bfjSum.TransactionDate, bfjSum.BfjNo})
	err = stub.PutState(compositeKey, bfjSumBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state failed by compositeKey: " + compositeKey + " because %s", err.Error()))
	}

	return shim.Success(nil)
}

func (b *BfjSumSmartContract) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	if len(args) != 2 {
		return shim.Error("incorrect number of arguments. expecting 2")
	}

	var iteratorResult shim.StateQueryIteratorInterface
	if args[0] != "" {
		if args[1] != "" {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-transactionDate", []string{args[0], args[1]})
		} else {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-transactionDate", []string{args[0]})
		}

	} else {
		if args[1] != "" {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("transactionDate-bfjNo", []string{args[1]})
		} else {
			return shim.Error("incorrect arguments. every argument is empty")
		}
	}
	result, err := getResultList(iteratorResult)
	if err != nil {
		return shim.Error("handle iteratorResult error, message: " + err.Error())
	}

	return shim.Success(result)
}

func getResultList(iteratorResult shim.StateQueryIteratorInterface) ([]byte, error) {
	defer iteratorResult.Close()
	// buffer is a json array contains QueryRecords
	var buffer bytes.Buffer
	buffer.WriteString("[")

	// 定义一个json数组是否已经被写入过的标识
	//bArrayMemberAlreadyWritten := false
	for iteratorResult.HasNext() {
		queryResult, err := iteratorResult.Next()
		if err != nil {
			return nil, err
		}

		// 如果该json数组已经被写入过，则在当前的json数组元素后面追加一个','标识符
		//if bArrayMemberAlreadyWritten == true {
		//	buffer.WriteString(",")
		//}
		buffer.WriteString(string(queryResult.Value))
		if iteratorResult.HasNext() {
			buffer.WriteString(",")
		}
	}
	buffer.WriteString("]")
	fmt.Printf("queryResult:\n%s\n", buffer.String())

	return buffer.Bytes(), nil
}

func main() {
	err := shim.Start(new(BfjSumSmartContract))
	if err != nil {
		fmt.Printf("error creating a new BfjSmartContract: %s", err.Error())
	}
}