package course.java.sdm.console;

public class MenuItem {

    //internal event ClickInvoker Clicked;
    Runnable Clicked;
    private final int r_Index;
    private String m_Title;
    private boolean m_IsMenu = false;

    MenuItem(int i_Index, String i_Title, Runnable i_Clicked) {
        r_Index = i_Index;
        m_Title = i_Title;
        Clicked = i_Clicked;
    }

    public String getTitle() {return m_Title; }

    public void setTitle(String i_title) { m_Title = i_title; }

    public void setMenu(boolean newState) {m_IsMenu =newState;}

    public boolean IsMenu() {return m_IsMenu;}

    public int getIndex () { return r_Index;}

    public void setClick (Runnable newFun)
    {
        Clicked = newFun;
    }

    //public void setIndex (int newIndex) {r_Index=newIndex;}


    public void Show() {
        System.out.println(r_Index +". " +m_Title);}

    public void OnClicked() {
        if (Clicked != null)
            Clicked.run();
    }
}


