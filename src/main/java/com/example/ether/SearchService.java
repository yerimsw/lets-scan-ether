package com.example.ether;

import java.io.IOException;
import java.util.Map;

public interface SearchService {
    Map<String, String> getBlockByHash(String blockHash);
    Map<String, String> getTransactionByHash(String txHash);
    Map<String, String> getAddressByHash(String addrHash);
}
