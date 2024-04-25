package com.example.ether;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/search/block")
    public String searchBlock(@RequestParam(name="blhash")String blockHash, Model model) {
        model.addAttribute("map", searchService.getBlockByHash(blockHash));
        return "block";
    }

    @PostMapping("/search/transaction")
    public String searchTx(@RequestParam(name = "txhash")String txHash, Model model) {
        model.addAttribute("map", searchService.getTransactionByHash(txHash));
        return "transaction";
    }

    @PostMapping("/search/address")
    public String searchAddress(@RequestParam(name="addrhash") String addrHash, Model model) {
        model.addAttribute("map", searchService.getAddressByHash(addrHash));
        return "address";
    }
}
