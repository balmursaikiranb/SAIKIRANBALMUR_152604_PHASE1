package com.cg.paymentwallet.dao;

import java.math.BigDecimal;

import com.cg.paymentwallet.dto.Customer;
import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.IPaymentWalletException;
import com.cg.paymentwallet.exception.PaymentWalletException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PaymentWalletDao implements IPaymentWalletDao{
  
        private static Map<String,Customer> accounts=new HashMap<String, Customer>();
        private static Map<String,String> transactions=new HashMap<String, String>();
        private static Map<String,String> login=new HashMap<String,String>();
        
	public Customer registerCustomer(Customer wallet) throws PaymentWalletException {
		Wallet wall=new Wallet();
		wall.setPassword(wallet.getWallet().getPassword());
		
		accounts.put(wallet.getPhoneNumber(),wallet);
		String str=(wallet.getName().concat(" ").concat("your transactions are "));
		transactions.put(wallet.getPhoneNumber(),str);
		login.put(wallet.getPhoneNumber(),wall.getPassword());
		return wallet;
	}

	public Customer depositMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		Customer wallet=accounts.get(phone);
		Wallet wall=wallet.getWallet();
		
                    if(wallet!=null)
                    {
                    	Wallet wall1=wallet.getWallet();
            			BigDecimal money=wall1.getBalance();
            			BigDecimal update=money.add(balance);
            			wall1.setBalance(update);
            			wallet.setWallet(wall1);
                        LocalDateTime now=LocalDateTime.now();
                        String str1=transactions.get(phone);
                        StringBuilder builder1=new StringBuilder(str1);
                        builder1.append("zzz");
                        builder1.append(("you have deposited money: ").concat(balance.toString().concat(" ").concat("Date and Time is: ").concat(now.toString())));
                        transactions.put(phone,builder1.toString());
                       
                        return wallet;
                    }
                    else
                    {
                    	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                    }
	}

	public Customer withdrawMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		Customer wallet=accounts.get(phone);
		if(wallet!=null){
			Wallet wall1=wallet.getWallet();
        	BigDecimal big=(wall1.getBalance()).max(new BigDecimal(1000));
        if(wall1.getBalance()==big){
                    BigDecimal updateBalance=((wall1.getBalance()).subtract(balance));
                    wall1.setBalance(updateBalance);
        			wallet.setWallet(wall1);
					LocalDateTime now=LocalDateTime.now();
					String str1=transactions.get(phone);
					StringBuilder builder1=new StringBuilder(str1);
					builder1.append("zzz");
					builder1.append(("you have withdrawed money: ").concat(balance.toString().concat(" ").concat("Date and Time is: ").concat(now.toString())));
					transactions.put(phone,builder1.toString());
					return wallet;
        }
        else
        {
        	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
        }
		}
		 else
         {
         	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
         }
	}

	public Customer fundTransfer(String sourcePhone, String targetPhone, BigDecimal balance) throws PaymentWalletException {
		Customer wallet =accounts.get(sourcePhone);
                Customer wallet1=accounts.get(targetPhone);
                if((wallet!=null)&&(wallet1!=null))
                {
                	Wallet wall1=wallet.getWallet();
                	Wallet wall2=wallet1.getWallet();
                	 if((wallet!=null)&&(wallet1!=null)){
                     	BigDecimal big=(wall1.getBalance()).max(balance);
                     if(wall1.getBalance()==big){
                	
                      BigDecimal updateBalance=((wall2.getBalance()).subtract(balance));
                      wall1.setBalance(updateBalance);
          			wallet.setWallet(wall1);
                        BigDecimal updateBalance1=((wall2.getBalance()).add(balance));
                        wall2.setBalance(updateBalance1);
            			wallet.setWallet(wall2);
                        LocalDateTime now=LocalDateTime.now();
                        String str1=transactions.get(sourcePhone);
                        StringBuilder builder1=new StringBuilder(str1);
                        builder1.append("zzz");
                        builder1.append(("you have made fund transfer to: ").concat(targetPhone));
                        builder1.append((" of amount: ").concat(balance.toString()).concat(" ").concat("Date and Time is: ").concat(now.toString()));
                        transactions.put(targetPhone,builder1.toString());
                       
                       
                        String str=transactions.get(targetPhone);
                        StringBuilder builder=new StringBuilder(str);
                        builder.append("zzz");
                        builder.append(("you have received fund transfer from: ").concat(sourcePhone));
                        builder.append((" of amount: ").concat(balance.toString()).concat(" ").concat("Date and Time is: ").concat(now.toString()));
                        transactions.put(targetPhone,builder.toString());
                        return wallet;
                }
                     else
                     {
                     	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                     }
                }
                	 else
                     {
                     	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                     }
                }
                else
                {
                	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                }
                
				
	}

	public Customer showBalance(String phone) throws PaymentWalletException {
		Customer wallet=accounts.get(phone);
                if(wallet!=null)
                {
                    return wallet;
                }
                else
                {
                	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                }
	}

	public String printTransaction(String phone) throws PaymentWalletException {
		Customer wallet=accounts.get(phone);
                if(wallet!=null)
                {
                    //return (HashMap<String, String>) transactions;
                	return transactions.get(phone);
                }
                else
                {
                	throw new PaymentWalletException(IPaymentWalletException.MESSAGE1);
                }
	}

	public boolean checkLogin(String number, String password) throws PaymentWalletException {
		String loginId=login.get(number);
		boolean result=false;
		if(loginId==null)
		{
			throw new PaymentWalletException(IPaymentWalletException.MESSAGE10);
		}
		else
		{
			if(loginId.equals(password))
			{
				result=true;
				return result;
			}
		}
		return result;
	}

   

}
