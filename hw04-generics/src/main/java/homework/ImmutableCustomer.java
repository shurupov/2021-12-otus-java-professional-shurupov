package homework;

public class ImmutableCustomer extends Customer {

    public ImmutableCustomer(Customer customer) {
        super(customer.getId(), customer.getName(), customer.getScores());
    }

    public ImmutableCustomer(long id, String name, long scores) {
        super(id, name, scores);
    }


    @Override
    public void setName(String name) {
        //Do Nothing
    }

    @Override
    public void setScores(long scores) {
        //Do nothing
    }
}
