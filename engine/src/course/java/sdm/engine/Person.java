package course.java.sdm.engine;

class Person implements HasName {

    protected final Long m_IDNumber;
    protected String m_Name;


    protected Person(long i_IDNumber,String i_Name) {
        this.m_IDNumber = i_IDNumber;
        m_Name= i_Name;
    }

    protected Long getId () {return m_IDNumber;}

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
