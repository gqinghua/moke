package com.moke.search.service;

import com.moke.search.model.Customer;

import java.util.List;



public interface CustomersInterface {

    public List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent);


}
