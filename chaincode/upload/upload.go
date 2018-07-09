package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"encoding/json"
	"bytes"
)

// Define the Smart Contract structure
type SmartContract struct {
}


type UploadFile struct {
	LoanNumber          string   			`json:"loanNumber"`
	FileName 			string   			`json:"fileName"`
	FileType 			string   			`json:"fileType"`
	FileSize 			int64				`json:"fileSize"`
	UploadDate          string   			`json:"uploadDate"`
	FileHash 			string   			`json:"fileHash"`
}

func (s *SmartContract) Init(APIStub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIStub shim.ChaincodeStubInterface) peer.Response {

	// Retrieve the requested Smart Contract function and arguments
	function, args := APIStub.GetFunctionAndParameters()
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "insert" {
		return s.insert(APIStub, args)
	} else if function == "query" {
		return s.query(APIStub, args)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

func (s *SmartContract) insert(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	var uploadFile UploadFile
	uploadFileBytes := []byte(args[0])
	err := json.Unmarshal(uploadFileBytes, &uploadFile)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because: %s", err.Error()))
	}

	id := fmt.Sprintf("%s_%s_%s", uploadFile.LoanNumber, uploadFile.UploadDate, uploadFile.FileType)

	compositeKey3, _ := APIStub.CreateCompositeKey("uploadDate", []string{uploadFile.UploadDate, id})
	err = APIStub.PutState(compositeKey3, uploadFileBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey4, _ := APIStub.CreateCompositeKey("upload", []string{uploadFile.LoanNumber, id})
	err = APIStub.PutState(compositeKey4, uploadFileBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

func (s *SmartContract) query(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	var buf bytes.Buffer
	buf.WriteString("[")
	if args[0] == "" {
		rqi, _ := APIStub.GetStateByPartialCompositeKey("uploadDate", []string{args[1]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
		buf.WriteString("]")
		return shim.Success(buf.Bytes())
	} else {
		rqi, _ := APIStub.GetStateByPartialCompositeKey("upload", []string{args[0]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
		buf.WriteString("]")
		return shim.Success(buf.Bytes())
	}
}

// The main function is only relevant in unit test mode. Only included here for completeness.
func main() {

	// Create a new Smart Contract
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}
