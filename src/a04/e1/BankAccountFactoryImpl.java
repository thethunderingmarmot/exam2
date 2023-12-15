package a04.e1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    private BankAccount generic(UnaryOperator<Integer> feeFunction,
                                Predicate<Integer> allowedCredit,
                                UnaryOperator<Integer> rateFunction,
                                BiPredicate<Integer, Integer> blockingPolicy) {
        return new BankAccount() {

            private int balance = 0;
            private boolean locked = false;

            @Override
            public int getBalance() {
                return balance;
            }

            @Override
            public void deposit(int amount) {
                if(!locked) {
                    balance += amount;
                }
            }

            @Override
            public boolean withdraw(int amount) {
                locked = locked == false ? blockingPolicy.test(amount, balance) : locked;
                if(!locked && allowedCredit.test((balance - amount) * -1)) {
                    balance -= amount; // Subtract withdrawed money from balance
                    balance -= feeFunction.apply(amount); // Subtract any fees
                    balance -= balance - amount < 0 ? rateFunction.apply(balance) : 0; // Subtract a fixed rate if 
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public BankAccount createBasic() {
        return generic(amount -> 0, credit -> credit <= 0, balance -> 0, (amount, balance) -> false);
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return generic(feeFunction, credit -> credit <= 0, balance -> 0, (amount, balance) -> false);
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return generic(amount -> 0, allowedCredit, rateFunction, (amount, balance) -> false);
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return generic(amount -> 0, credit -> credit <= 0, balance -> 0, blockingPolicy);
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        return generic(feeFunction, allowedCredit, rateFunction, (amount, balance) -> false);
    }

}
