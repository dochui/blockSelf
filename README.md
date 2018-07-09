# blockchain
fabric
 环境搭建参考 ： http://qiangjiyi.iteye.com/blog/2423411
 修改用户全选： sudo chown -R ynet.ynet /appdata
 docker环境设置 ：export COMPOSE_PROJECT_NAME=net
docker常用命令:
   1> docker images: 查看docker镜像
   2> docker log -f peer0.blockchian.com 查看peer 日志
   3> ps -ef |grep peer  查看peer 进程
   4> docker ps  查看docker 进程
   5> service docker start /stop  启动和停止docker
   6> docker kill $(docker ps -q) 停止正在运行的所有docker 容器
   7> docker rm $(docker ps -qa)  删除所还有的docker容器的文件
   8> docker exec -it cli bash   //启动容器进入容器
 

网络搭建的常见命令：
 export ORDERER_CA =crypto-   config/ordererOrganizations/belink.com/orderers/orderer.belink.com/msp/tlscacerts/tlsca.belink.com-cert.pem
  
  1>创建通道
        ./peer channel create -o orderer.belink.com:7050 -c mychannel -f channel-artifacts/channel.tx --tls true --cafile $ORDERER_CA

2> 加入通道 
   ./peer channel join -b mychannel.block  
 
 3> 安装智能合约
    ./peer chaincode install -n assert -v 1.0 -p github.com/hyperledger/chaincode/assert
 
 4> 实例化智能合约
    ./peer chaincode instantiate -o orderer.belink.com:7050 --tls true --cafile $ORDERER_CA -C mychannel -n     assert -v 1.0 -c '{"Args":["init","a","100","b","200"]}' -P "OR('XinwangMSP.member','BelinkMSP.member')"
    实例化可以通过  -l golang 指定语言
  
  5>升级智能合约
    ./peer chaincode upgrade -o orderer.belink.com:7050 --tls true --cafile  $ORDERER_CA -C mychannel -n         fee -v 2.0 -c '{"Args":["init","a","100","b","200"]}' -P "OR ('XinwangMSP.member','BelinkMSP.member')"
  
  6>导出环境变量进行同一客户端进行不同的peer操作
      export CORE_PEER_ADDRESS=peer0.blockchain.belink.com:7051
      export CORE_PEER_LOCALMSPID=BelinkMSP
      export CORE_PEER_TLS_ENABLED=true
      export CORE_PEER_TLS_CERT_FILE=crypto-             config/peerOrganizations/blockchain.belink.com/peers/peer0.blockchain.belink.com/tls/server.crt
      export CORE_PEER_TLS_KEY_FILE=crypto-config/peerOrganizations/blockchain.belink.com/peers/peer0.blockchain.belink.com/tls/server.key
      export CORE_PEER_TLS_ROOTCERT_FILE=crypto-config/peerOrganizations/blockchain.belink.com/peers/peer0.blockchain.belink.com/tls/ca.crt
      export CORE_PEER_MSPCONFIGPATH=crypto-config/peerOrganizations/blockchain.belink.com/users/Admin@blockchain.belink.com/msp
     7> 
      
      
