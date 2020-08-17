package course.java.sdm.engine;

public class Person implements HasName {

    private final Long m_IDNumber; //TODO check that in system there is only 1 id with map
    private String m_Name;


    public Person(long i_IDNumber,String i_Name) {
        this.m_IDNumber = i_IDNumber;
        m_Name= i_Name;
    }

    @Override
    public String getName() {
        return m_Name;
    }

    @Override
    public void setName(String Input) {
        m_Name=Input;
    }

    public long getIDNumber ()
    {
        return m_IDNumber;
    }
}
