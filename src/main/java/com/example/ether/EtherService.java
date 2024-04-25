package com.example.ether;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class EtherService implements SearchService {

    @Value("${alchemy.api}")
    String api;

    Web3j web3 = Web3j.build(new HttpService(api));

    @Override
    public Map<String, String> getBlockByHash(String blockHash) {
        Map<String, String> attributes = new HashMap<>();
        EthBlock.Block block = null;

        try {
            block = web3.ethGetBlockByHash(blockHash, true).send().getBlock();
        } catch (IOException e) {

        }

        if (block != null) {
            attributes.put("blockHash", block.getHash());
            attributes.put("parentHash", block.getParentHash());
            attributes.put("miner", block.getMiner());
            attributes.put("stateRoot", block.getStateRoot());
            attributes.put("size", block.getSize().toString());
        }

        return attributes;
    }

    @Override
    public Map<String, String> getTransactionByHash(String txHash) {
        Map<String, String> attributes = new HashMap<>();
        Transaction tx = null;

        try {
            tx = web3.ethGetTransactionByHash(txHash).send().getResult();
        } catch (IOException e) {

        }

        if (tx != null) {
            attributes.put("blockHash", tx.getBlockHash());
            attributes.put("blockNumber", tx.getBlockNumber().toString());
            attributes.put("from", tx.getFrom());
            attributes.put("to", tx.getTo());
            attributes.put("value", tx.getValue().toString());
            attributes.put("gas", tx.getGas().toString());
        }

        return attributes;
    }

    @Override
    public Map<String, String>  getAddressByHash(String addrHash) {
        Map<String, String> attributes = new HashMap<>();
        BigInteger balance = BigInteger.valueOf(0);
        BigInteger txCount = BigInteger.valueOf(0);

        try {
            web3.ethGetBalance(addrHash, DefaultBlockParameterName.LATEST).send().getBalance();
            web3.ethGetTransactionCount(addrHash, DefaultBlockParameterName.LATEST).send().getTransactionCount();
        } catch(IOException e) {

        }

        attributes.put("balance", balance.toString());
        attributes.put("txCount", txCount.toString());

        return attributes;
    }
}
