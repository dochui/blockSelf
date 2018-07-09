package com.ynet.xwfabric.client;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ynet.xwfabric.util.SecurityUtils;

public class FabricClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CONFIG_BASE_DIR = "fabric/";
    
    private HFClient client;
    private Channel channel;
    private ChaincodeID ccId;
    private long timeoutMillis;

    public FabricClient timeoutMillis(long timeoutMillis) {
        logger.info("new fabric client");
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    public FabricClient ccId(String ccName, String ccVersion) {
        this.ccId = ChaincodeID.newBuilder().setName(ccName).setVersion(ccVersion).build();
        return this;
    }

    public FabricClient createChannel(String msp, String configName) throws Exception {
        InputStream configFile = getClass().getClassLoader().getResourceAsStream(CONFIG_BASE_DIR + msp + "/" + configName);
        NetworkConfig config = NetworkConfig.fromYamlStream(configFile);
        channel = client.loadChannelFromConfig("mychannel", config);
        channel.initialize();
        return this;
    }

    public FabricClient initClient(String msp) throws Exception {
        InputStream certFile = getClass().getClassLoader().getResourceAsStream(CONFIG_BASE_DIR + msp + "/adminCert.pem");
        InputStream privateFile = getClass().getClassLoader().getResourceAsStream(CONFIG_BASE_DIR + msp + "/admin.key");

        String certificate = new String(IOUtils.toByteArray(certFile), "UTF-8");
        PrivateKey privateKey = SecurityUtils.getPrivateKeyFromBytes(IOUtils.toByteArray(privateFile));

        Enrollment enrollment = new FabricEnrollment(privateKey, certificate);
        this.client = HFClient.createNewInstance();
        this.client.setCryptoSuite(CryptoSuiteFactory.getDefault().getCryptoSuite());
        this.client.setUserContext(new FabricUser("admin", msp, enrollment));
        return this;
    }

    public String callChaincode(Consumer<String> resultConsumer, boolean query, String function, String... args)
            throws Exception {
        TransactionProposalRequest request = client.newTransactionProposalRequest();
        request.setChaincodeID(ccId);
        request.setFcn(function);
        request.setArgs(args);
        request.setProposalWaitTime(timeoutMillis);
        // endorse
        Collection<ProposalResponse> responses = channel.sendTransactionProposal(request);
        String result = responses.stream().findFirst().map((response) -> {
            if (!response.isVerified() || response.getStatus() != ChaincodeResponse.Status.SUCCESS) {
                throw new RuntimeException(response.getMessage());
            }
            try {
                return new String(response.getChaincodeActionResponsePayload());
            } catch (InvalidArgumentException e) {
                throw new RuntimeException(e);
            }
        }).orElse("");
        if (!query) {
            // commit
            channel.sendTransaction(responses).get(timeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (resultConsumer != null) {
            resultConsumer.accept(result);
        }
        return result;
    }

    public void shutdown() throws Exception {
        if (channel != null) {
            channel.shutdown(true);
        }
        if (client != null) {
            final Field executorService = HFClient.class.getDeclaredField("executorService");
            executorService.setAccessible(true);
            ((ExecutorService) executorService.get(client)).shutdownNow();
        }
    }

    public HFClient getClient() {
        return client;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChaincodeID getCcId() {
        return ccId;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

/*    public static void callMutilEndorseChaincode(List<DemoClient> clients, String function, String... args) throws Exception {
        Collection<ProposalResponse> mutliResponses = new ArrayList<>();
        DemoClient anClient = clients.get(0);
        TransactionProposalRequest request = anClient.getClient().newTransactionProposalRequest();
        request.setChaincodeID(anClient.getCcId());
        request.setFcn(function);
        request.setArgs(args);
        request.setProposalWaitTime(anClient.getTimeoutMillis());
        Pair<FabricProposal.Proposal, String> proposalAndTransaction =  anClient.getChannel().getProposal(request);
        FabricProposal.Proposal proposal = proposalAndTransaction.getLeft();
        System.out.println("got transaction id:" + proposalAndTransaction.getRight());

        for (DemoClient client : clients) {
            // endorse
            Collection<ProposalResponse> responses = client.getChannel().sendProposal(request, proposal);
            String result = responses.stream().findFirst().map((response) -> {
                if (!response.isVerified() || response.getStatus() != ChaincodeResponse.Status.SUCCESS) {
                    throw new RuntimeException(response.getMessage());
                }
                //System.out.println(response.getProposalResponse().getEndorsement());

                //System.out.println(new String(Hex.encode(response.getProposalResponse().getPayload().toByteArray())));
                //System.out.println(new String(Hex.encode(response.getProposalResponse().getPayload().concat(response.getProposalResponse().getEndorsement().getEndorser()).toByteArray())));
                //System.out.println(new String(Hex.encode(response.getProposalResponse().getEndorsement().getSignature().toByteArray())));
                //System.out.println("got transaction id:" + response.getTransactionID());
                try {
                    return new String(response.getChaincodeActionResponsePayload());
                } catch (InvalidArgumentException e) {
                    throw new RuntimeException(e);
                }
            }).orElse("");
            mutliResponses.addAll(responses);
        }
        // commit
        anClient.getChannel().sendTransaction(mutliResponses, proposalAndTransaction.getRight()).get(anClient.getTimeoutMillis(), TimeUnit.MILLISECONDS);
    }*/

    public void callChaincodeWithMutilEndorse(String function, String... args) throws Exception {
        TransactionProposalRequest request = client.newTransactionProposalRequest();
        request.setChaincodeID(ccId);
        request.setFcn(function);
        request.setArgs(args);
        request.setProposalWaitTime(timeoutMillis);

        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
        Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(request);
        for (ProposalResponse response : transactionPropResp) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                logger.info(String.format("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
                successful.add(response);
            } else {
                failed.add(response);
            }
        }
        logger.info(String.format("Received %d transaction proposal responses. Successful+verified: %d . Failed: %d",
                transactionPropResp.size(), successful.size(), failed.size()));

        // commit
        channel.sendTransaction(successful).get(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws Exception {
        String chaincodeName = "loandetail";
        FabricClient belinkClient = new FabricClient().timeoutMillis(30 * 1000)
                .ccId(chaincodeName, "1.0")
                .initClient("BelinkMSP")
                .createChannel("BelinkMSP", "network-config.yaml");

        /*DemoClient xinWangClient = new DemoClient().timeoutMillis(30 * 1000)
                .ccId(chaincodeName, "1.0")
                .initClient("XinWangMSP")
                .createChannel("xinwang-config.yaml");*/

        //List<DemoClient> clients = new ArrayList<>();
        //clients.add(belinkClient);
        //clients.add(xinWangClient);
        final long ll = System.currentTimeMillis();
        String dateStr = "2018/03/22";
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callMutilEndorseChaincode(clients,"insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR"+(ll-2)%1000+"\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"2017060717113783560"+(ll-2) +"\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callMutilEndorseChaincode(clients,"insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR"+(ll-1)%1000+"\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"2017060717113783560"+(ll-1) +"\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();*/

        belinkClient.callChaincodeWithMutilEndorse("insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR"+ll%1000+"\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\""+dateStr+"\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\""+dateStr+"\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"2017060717113783560"+ll +"\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"人人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\""+dateStr+"\",\"transactionFlowNo\":\"0\"}");
        //callMutilEndorseChaincode(clients,"insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR"+ll%1000+"\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"2017060717113783560"+ll +"\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}");
        //belinkClient.callChaincode(null, false, "insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR04\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"20170607171137835606929584404492\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}")
        //        .callChaincode(System.out::println, true, "query", "", "2017/06/08");

        //xinWangClient.callChaincode(null, false, "insert", "{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR05\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"20170607171137835606929584404492\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}");
        Thread.sleep(5000);
        //xinWangClient.callChaincode(System.out::println, true, "query", "", "2017/06/08");
        belinkClient.callChaincode(System.out::println, true, "query", "", dateStr);

//        Thread.sleep(5000);
//        belinkClient
//                .callChaincode(System.out::println, true, "query", "TTTLRR06", "")
//                .callChaincode(System.out::println, true, "query", "TTTLRR07", "")
//                .callChaincode(System.out::println, true, "query", "TTTLRR08", "")
//                .callChaincode(System.out::println, true, "query", "", "2017/06/08")
//                .shutdown();

        System.exit(0);
    }
}
