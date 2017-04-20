package com.avalanche.tmcs.company;

/**
 * Created by zanegrasso
 * Created on 4/20/17.
 */
public class CompanyNotFoundException  extends RuntimeException{
    public CompanyNotFoundException(long id){
        super("Could not find company with id" + id);
    }
}
