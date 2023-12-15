package a04.sol1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    private class BasicBankAccount implements BankAccount {

        protected int balance;

        @Override
        public int getBalance() {
            return balance;
        }

        @Override
        public void deposit(int amount) {
            balance += amount;
        }

        @Override
        public boolean withdraw(int amount) {
            if(balance > amount) {
                balance -= amount;
                return true;
            }
            return false;
        }
    }

    @Override
    public BankAccount createBasic() {
        return new BasicBankAccount();
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new BasicBankAccount() {
            @Override
            public boolean withdraw(int amount) {
                return super.withdraw(amount + feeFunction.apply(amount));
            }
        };
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction){
        return new BasicBankAccount() {

            @Override
            public boolean withdraw(int amount) {
                return allowedCredit.test((balance - amount) * -1)
                     ? super.withdraw(amount + rateFunction.apply(amount))
                     : false;                
            }

        };
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new BasicBankAccount() {

            
            @Override
            public boolean withdraw(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
            }

        };
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        return new BasicBankAccount() {

            @Override
            public void deposit(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'deposit'");
            }

            @Override
            public boolean withdraw(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
            }

        };
    }
}
