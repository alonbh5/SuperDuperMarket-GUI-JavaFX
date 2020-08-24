package course.java.sdm.engine;

class Person implements HasName {

    private final Long m_IDNumber;
    private String m_Name;


    Person(long i_IDNumber,String i_Name) {
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

    long getIDNumber ()
    {
        return m_IDNumber;
    }
}
