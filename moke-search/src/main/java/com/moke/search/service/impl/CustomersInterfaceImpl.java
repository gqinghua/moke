package com.moke.search.service.impl;


import com.alibaba.nacos.client.logger.Logger;
import com.alibaba.nacos.client.logger.LoggerFactory;
import com.moke.search.model.Customer;
import com.moke.search.repository.CustomerRepository;
import com.moke.search.service.CustomersInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomersInterfaceImpl implements CustomersInterface {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomerRepository customerRepository;

   @Override
    public List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        return null;
    }
}
